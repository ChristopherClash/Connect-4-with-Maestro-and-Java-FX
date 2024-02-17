package Connect4;

import static com.connect4.connect4javafx.MaestroCommands.*;
public class LightDetector extends MaestroController {

    public LightDetector(int runnerServoChannelNumber, int grabberServoChannelNumber) {
        super(runnerServoChannelNumber, grabberServoChannelNumber);
    }

    /**
     * Retrieves the player's move from the Maestro device.
     */
    public static int getPlayerMove() {
        for (int columnNum = 0; columnNum < 7; columnNum++) {
            int valueRaw = readFromMaestro((byte) columnNum);
            int correctedValue = valueRaw / 4;
            System.out.println("Column " + columnNum + ": " + correctedValue);
            if (correctedValue < -1) {
                return correctedValue;
            }
        }
        return -1;
    }
}