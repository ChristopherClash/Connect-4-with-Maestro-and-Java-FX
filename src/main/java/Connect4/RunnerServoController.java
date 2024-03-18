package Connect4;

import static Connect4.MaestroCommands.*;

public class RunnerServoController extends MaestroController{
    private static final int DISTANCE_TO_COLUMN_1 = 200;

    /**
     * Default position of the runner, it returns to this after making a move
     */
    private static final int PARK_POSITION = 127;

    private static int RUNNER_SERVO_CHANNEL_NUMBER;

    public RunnerServoController() {
        super();
    }
    //sendCommand((byte) 0xFF, (byte)0x0B, (byte) 0x64);

    /**
     * Calculates the distance to move the servo to the specified column by multiplying the value needed to move to column 1 by the desired column num
     * Sends a command to the Maestro device to move the servo to the specified distance
     * @param columnNumber the desired column
     */
    public static void runRunnerToColumn(int columnNumber){
        int distanceToRun = columnNumber * DISTANCE_TO_COLUMN_1;
        sendCommandToMaestro((byte) 0xFF, (byte) RUNNER_SERVO_CHANNEL_NUMBER, (byte) distanceToRun);
    }

    /**
     * Returns the servo to its park position.
     */
    public static void returnToParkPosition(){
        int currentPosition = readFromMaestro((byte) RUNNER_SERVO_CHANNEL_NUMBER);
        int amountToMove = currentPosition - PARK_POSITION;
        if (currentPosition != PARK_POSITION) {
            sendCommandToMaestro((byte) 0xFF, (byte) RUNNER_SERVO_CHANNEL_NUMBER, (byte) amountToMove);
        }
    }

    /**
     * Gets the channel number of the servo
     * @return RUNNER_SERVO_CHANNEL_NUMBER
     */
    public static int getRunnerServoChannelNumber(){
        return RUNNER_SERVO_CHANNEL_NUMBER;
    }

    /**
     * Gets the distance to move to column 1
     * @return DISTANCE_TO_COLUMN_1
     */
    public static int getDistanceToColumn1(){
        return DISTANCE_TO_COLUMN_1;
    }

    /**
     * Gets the park position
     * @return PARK_POSITION
     */
    public static int getParkPosition(){
        return PARK_POSITION;
    }

    /**
     * Sets the channel number of the servo
     * @param channelNumber the channel number
     */
    public static void setRunnerServoChannelNumber(int channelNumber){
        RUNNER_SERVO_CHANNEL_NUMBER = channelNumber;
    }
}
