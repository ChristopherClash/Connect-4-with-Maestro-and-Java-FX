package Connect4;

public class MaestroController  {

    /**
     * Set the servo channel numbers for the grabber and runner.
     */
    public MaestroController() {
        RunnerServoController.setRunnerServoChannelNumber(11);
        GrabberServoController.setGrabberServoChannelNumber(10);
    }

    /**
     * Retrieves the move made by the human player.
     *
     * @return the column number that the human player just played in
     */
    public static int getHumanPlayerMove() {
        return LightDetector.getPlayerMove();
    }

    /**
     * Checks if the runner is in the park position
     * Returns it to the park position if not.
     */
    public static void checkRunnerInPark() {
        int currentRunnerPosition = MaestroCommands.readFromMaestro((byte) RunnerServoController.getRunnerServoChannelNumber());
        if (currentRunnerPosition != RunnerServoController.getParkPosition()){
            RunnerServoController.returnToParkPosition();
        }
    }

    /**
     * Checks if the grabber is open
     * Returns it to the open position if not.
     */
    public static void checkGrabberIsOpen() {
        int currentGrabberPosition = MaestroCommands.readFromMaestro((byte) GrabberServoController.getGrabberServoChannelNumber());
        if (currentGrabberPosition != GrabberServoController.getOpenPosition()){
            GrabberServoController.openGrabber();
        }
    }

    /**
     * Makes a computer move by performing a series of actions:
     * checks if the runner is in the park,
     * checks if the grabber is open,
     * closes the grabber and runs the runner to a specified column,
     * opens the grabber, waits for 200 ms to allow the token to fall,
     * Closes the grabber, and returns the runner to the park position.
     *
     * @param  column  the column to which the runner should be moved
     */
    public void makeComputerMove(int column) {
        checkRunnerInPark();
        System.out.println("Runner is in park");
        checkGrabberIsOpen();
        System.out.println("Grabber is open");
        MaestroCommands.sleep(3000);
        GrabberServoController.closeGrabber();
        System.out.println("Grabber is closed");
        System.out.println("Runner is running to column " + column);
        RunnerServoController.runRunnerToColumn(column);
        GrabberServoController.openGrabber();
        System.out.println("Grabber is opened");
        System.out.println("Computer's move is made, waiting for 200 ms");
        MaestroCommands.sleep(200);
        GrabberServoController.closeGrabber();
        System.out.println("Grabber is closed");
        RunnerServoController.returnToParkPosition();
        System.out.println("Runner is returned to park");
    }
}

