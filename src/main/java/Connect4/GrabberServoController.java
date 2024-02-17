package Connect4;

import static com.connect4.connect4javafx.MaestroCommands.*;

public class GrabberServoController extends MaestroController {

    private static final int OPEN_POSITION = 0x64;

    private static final int CLOSE_POSITION = 0x64;

    private static int GRABBER_SERVO_CHANNEL_NUMBER;

    public GrabberServoController(int runnerServoChannelNumber, int grabberServoChannelNumber) {
        super(runnerServoChannelNumber, grabberServoChannelNumber);
    }

    //sendCommand((byte) 0xFF, (byte)0x00, (byte) 0x64);

    public static void openGrabber() {
        sendCommandToMaestro((byte) 0xFF, (byte) GRABBER_SERVO_CHANNEL_NUMBER, (byte) OPEN_POSITION);
    }

    public static void closeGrabber() {
        sendCommandToMaestro((byte) 0xFF, (byte) GRABBER_SERVO_CHANNEL_NUMBER, (byte) CLOSE_POSITION);
    }

    public static void setGrabberServoChannelNumber(int grabberServoChannelNumber) {
        GRABBER_SERVO_CHANNEL_NUMBER = grabberServoChannelNumber;
    }

    public static int getGrabberServoChannelNumber() {
        return GRABBER_SERVO_CHANNEL_NUMBER;
    }

    public static int getOpenPosition() {
        return OPEN_POSITION;
    }

    public static int getClosePosition() {
        return CLOSE_POSITION;
    }
}
