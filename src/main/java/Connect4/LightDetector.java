package Connect4;

import static Connect4.MaestroCommands.*;
public class LightDetector extends MaestroController {

    public LightDetector() {
        super();
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
        //TODO Calculate the column that has detected a token passing the sensor
        return -1;
    }
}