package Connect4;

import com.fazecast.jSerialComm.SerialPort;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
public class MaestroCommands {
    private static final SerialPort comPort = SerialPort.getCommPort("COM3");

    /**
     * Sets the main thread to sleep for the specified number of milliseconds.
     * @param millisecondsToSleep the time to sleep in milliseconds
     */
    public static void sleep(int millisecondsToSleep) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsToSleep);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sends a command to the specified servo to move with the given target value.
     *
     * @param  commandByte  the command byte for the servo
     * @param  servoByte    the channel number byte for the servo
     * @param  targetByte   the target byte for the servo
     */
    public static void sendCommandToMaestro(byte commandByte, byte servoByte, byte targetByte) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        comPort.openPort();
        OutputStream out = comPort.getOutputStream();
        byte[] setTarget = new byte[]{commandByte, servoByte, targetByte};
        try {
            out.flush();
            out.write(setTarget);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        comPort.closePort();
        int currentPosition = readFromMaestro(servoByte);
        while (currentPosition != targetByte) {
            sleep(500);
            currentPosition = readFromMaestro(servoByte);
        }
    }



    /**
     * Stops the movement by sending a stop signal
     */
    public static void stopAllServos() {
        comPort.openPort();
        OutputStream out = comPort.getOutputStream();
        byte[] stop = new byte[]{(byte) 0xA2};
        try {
            out.flush();
            out.write(stop);
            out.close();
        } catch (Exception e) {System.out.println("Error: " + e.getMessage());}
        comPort.closePort();
    }

    /**
     * Converts a little-endian byte array to a big-endian integer.
     *
     * @param  bytes  the little-endian byte array to convert
     * @return        the big-endian integer result
     */
    public static int convertLittleEndianToBigEndian(byte[] bytes) {
        int littleEndian = 0;
        try {
            InputStream bytesData = new ByteArrayInputStream(bytes);
            DataInputStream dataInputStream = new DataInputStream(bytesData);
            littleEndian = dataInputStream.readShort();
        } catch (Exception e) {
            System.out.println("Error converting between endians: " + e.getMessage());
        }
        return Short.toUnsignedInt(Short.reverseBytes((short) littleEndian));

    }

    /**
     * Read from Maestro and return the column number if the light detector for that column returns a value.
     * @param channelNumber the channel number to read
     * @return the column number with a light detector value less than 100, or -1 if no move detected
     */
    public static int readFromMaestro(byte channelNumber) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        byte[] read = new byte[]{(byte) 0x90, channelNumber};
        InputStream in = comPort.getInputStream();
        OutputStream out = comPort.getOutputStream();
        byte[] littleEndianBytes = new byte[0];
        try {
            comPort.openPort();
            out.flush();
            out.write(read);
            littleEndianBytes = in.readNBytes(2);
            out.close();
            in.close();
            comPort.closePort();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return convertLittleEndianToBigEndian(littleEndianBytes);
    }
}
