package Connect4;

import static com.connect4.connect4javafx.MaestroCommands.*;
public class RunnerServoController extends MaestroController{
    private static final int DISTANCE_TO_COLUMN_1 = 100;
    private static final int PARK_POSITION = 0x64;
    private static int RUNNER_SERVO_CHANNEL_NUMBER;

    public RunnerServoController(int runnerServoChannelNumber, int grabberServoChannelNumber) {
        super(runnerServoChannelNumber, grabberServoChannelNumber);
    }
    //sendCommand((byte) 0xFF, (byte)0x0B, (byte) 0x64);

    public static void runRunnerToColumn(int columnNumber){
        int distanceToRun = columnNumber * DISTANCE_TO_COLUMN_1;
        sendCommandToMaestro((byte) 0xFF, (byte) RUNNER_SERVO_CHANNEL_NUMBER, (byte) distanceToRun);
    }

    public static void returnToParkPosition(){
        int currentPosition = readFromMaestro((byte) RUNNER_SERVO_CHANNEL_NUMBER);
        int amountToMove = currentPosition - PARK_POSITION;
        if (currentPosition != PARK_POSITION) {
            sendCommandToMaestro((byte) 0xFF, (byte) RUNNER_SERVO_CHANNEL_NUMBER, (byte) amountToMove);
        }
    }

    public static int getRunnerServoChannelNumber(){
        return RUNNER_SERVO_CHANNEL_NUMBER;
    }

    public static int getDistanceToColumn1(){
        return DISTANCE_TO_COLUMN_1;
    }

    public static int getParkPosition(){
        return PARK_POSITION;
    }

    public static void setRunnerServoChannelNumber(int channelNumber){
        RUNNER_SERVO_CHANNEL_NUMBER = channelNumber;
    }
}
