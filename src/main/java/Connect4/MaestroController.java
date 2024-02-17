package Connect4;

public class MaestroController  {

    public MaestroController(int runnerServoChannelNumber, int grabberServoChannelNumber) {
        RunnerServoController.setRunnerServoChannelNumber(runnerServoChannelNumber);
        GrabberServoController.setGrabberServoChannelNumber(grabberServoChannelNumber);
    }

    public static int getHumanPlayerMove() {
        return LightDetector.getPlayerMove();
    }

    public static void checkRunnerInPark() {
        int currentRunnerPosition = MaestroCommands.readFromMaestro((byte) RunnerServoController.getRunnerServoChannelNumber());
        if (currentRunnerPosition != RunnerServoController.getParkPosition()){
            RunnerServoController.returnToParkPosition();
        }
    }

    public static void checkGrabberIsOpen() {
        int currentGrabberPosition = MaestroCommands.readFromMaestro((byte) GrabberServoController.getGrabberServoChannelNumber());
        if (currentGrabberPosition != GrabberServoController.getOpenPosition()){
            GrabberServoController.openGrabber();
        }
    }

    public void makeComputerMove(int column) {
        checkRunnerInPark();
        System.out.println("Runner is in park");
        checkGrabberIsOpen();
        System.out.println("Grabber is open");
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

