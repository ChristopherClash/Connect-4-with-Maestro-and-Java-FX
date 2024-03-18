package Connect4;

import static Connect4.MaestroCommands.*;

public class GrabberServoController extends MaestroController {

    /**
     * Position to open the gripper
     */
    private static final int OPEN_POSITION = 127;

    /**
     * Position to close the gripper
     */
    private static final int CLOSE_POSITION = 64;

    private static int GRABBER_SERVO_CHANNEL_NUMBER;

    public GrabberServoController() {
        super();
    }

    //sendCommand((byte) 0xFF, (byte)0x00, (byte) 0x64);

    /**
     * Sends a command to move the grabber servo to the open position.
     */
    public static void openGrabber() {
        sendCommandToMaestro((byte) 0xFF, (byte) GRABBER_SERVO_CHANNEL_NUMBER, (byte) OPEN_POSITION);
    }

    /**
     * Sends a command to move the grabber servo to the close position.
     */
    public static void closeGrabber() {
        sendCommandToMaestro((byte) 0xFF, (byte) GRABBER_SERVO_CHANNEL_NUMBER, (byte) CLOSE_POSITION);
    }

    /**
     * Set the grabber servo channel number.
     * @param grabberServoChannelNumber the grabber servo channel number
     */
    public static void setGrabberServoChannelNumber(int grabberServoChannelNumber) {
        GRABBER_SERVO_CHANNEL_NUMBER = grabberServoChannelNumber;
    }

    /**
     * Get the grabber servo channel number.
     * @return GRABBER_SERVO_CHANNEL_NUMBER
     */
    public static int getGrabberServoChannelNumber() {
        return GRABBER_SERVO_CHANNEL_NUMBER;
    }

    /**
     * Get the open position value
     * @return OPEN_POSITION
     */
    public static int getOpenPosition() {
        return OPEN_POSITION;
    }

    /**
     * Get the close position value
     * @return CLOSE_POSITION
     */
    public static int getClosePosition() {
        return CLOSE_POSITION;
    }
}
