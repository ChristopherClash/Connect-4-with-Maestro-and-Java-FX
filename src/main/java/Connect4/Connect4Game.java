package Connect4;

import java.util.HashSet;
import java.util.Set;

public class Connect4Game {
    private final int[][] board;
    private static int noOfColumns;
    private static int noOfRows;
    private final ComputerPlayer computerPlayer;
    private final HumanPlayer humanPlayer;
    private final MaestroController maestroController;
    private int selectedColumn;
    private final GameController gameController;

    public Connect4Game(ComputerPlayer computerPlayer, HumanPlayer humanPlayer, MaestroController maestroController, GameController gameController) {
        this.computerPlayer = computerPlayer;
        this.humanPlayer = humanPlayer;
        this.gameController = gameController;
        this.maestroController = maestroController;
        noOfColumns = GameController.getNoOfColumns();
        noOfRows = gameController.getNoOfRows();
        board = new int[noOfRows][noOfColumns];
        initialiseArray();
    }

    /**
     * Initializes the game board array with zero in each index location.
     */
    private void initialiseArray(){
        for (int i = 0; i < noOfRows; i++){
            for (int j = 0; j < noOfColumns; j++){
                board[i][j] = 0;
            }
        }
    }

    /**
     * Takes a turn for the player.
     * Checks if the move made was valid and asks player to make another move if not.
     * Increments the number of tokens for each player after a successful respective move.
     *
     * @param  player The player taking the turn.
     *
     * @return       True if the turn was successful, false otherwise.
     */
    public boolean takeTurn(Player player) {
        int[] move;
        if (player == humanPlayer){
            move = humanPlayer.takeTurn(board, selectedColumn);
            if (!isValidMove(move)){
                return false;
            }
            gameController.createCircleAtNode(player, move[0], selectedColumn);
            player.incrementTotalTokens();
            board[move[0] - 1][move[1]] = 1;
            return true;
        } else if (player == computerPlayer){
            move = computerPlayer.takeTurn(board);
            gameController.createCircleAtNode(player, move[0], move[1]);
            maestroController.makeComputerMove(move[1]);
            player.incrementTotalTokens();
            board[move[0] - 1][move[1]] = 2;
            return true;
        }
        return false;
    }

    /**
     * Checks if the given move is valid.
     * Checks if the row index is -1 (i.e. the column is full).
     *
     * @param  move  an array representing the move
     *
     * @return       true if the move is valid, false otherwise
     */
    private boolean isValidMove(int[] move){
        return !(move[0] == -1);
    }

    /**
     * Determines if the game has been won by a player or if it is a draw.
     *
     * @return  An integer representing the result of the game:
     *          - 1 if player 1 has won
     *          - 2 if player 2 has won
     *          - 0 if it is a draw
     *          - -1 if no win conditions have been met
     */
    public int checkGameWin() {
        int checkColumnsResult = checkColumns(board);
        int checkRowsResult = checkRows(board);
        int checkDiagonalsResult = checkDiagonals(board);

        //If player 1 has met any of the win conditions
        if (checkColumnsResult == 1 || checkRowsResult == 1 || checkDiagonalsResult == 1){
            return (1);
        }
        //If player 2 has met any of the win conditions
        else if (checkColumnsResult == 2 || checkRowsResult == 2 || checkDiagonalsResult == 2){
            return (2);
        }
        //If no win conditions have been met, check for a draw
        else if (checkDraw()){
            return (0);
        }
        return -1;
    }

    /**
     * Checks each array index, if a 0 is found then the board is not full,
     * so a draw has not been reached
     *
     * @return true if there are no empty slots (i.e. slots containing 0) in the array,
     * return false
     */
    public boolean checkDraw(){
        int[][] gameArray = getBoard();
        for (int i = 0; i < noOfRows; i++){
            for (int j = 0; j < noOfColumns; j++){
                if (gameArray[i][j] == 0){
                    return false;
                }
            }
        }
        // Return true if there are no empty slots left
        return true;
    }

    /**
     * Check if there are any winning rows.
     *
     * @return 1 if player 1 has a winning row, or 2 if player two has a winning row.
     * If neither has won return 0.
     */
    public static int checkRows(int[][] board){
        for (int rowNo = 0; rowNo < noOfRows ; rowNo++){
            for (int columnNo = 0; columnNo < noOfColumns - 3; columnNo++){
                Set<Integer> checkRowSet = new HashSet<>();
                for (int offset = 0; offset < 4; offset++){
                    checkRowSet.add(board[rowNo][columnNo + offset]);
                }
                if (checkRowSet.size() == 1){
                    if (checkRowSet.contains(1)){
                        return 1;
                    }
                    else if (checkRowSet.contains(2)){
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Check if there are any winning columns.
     *
     * @return 1 if player 1 has a winning column, or 2 if player two has a winning column.
     * If neither has won return 0.
     */
    public static int checkColumns(int[][] board) {
        for (int columnNo = 0; columnNo < noOfColumns; columnNo++){
            for (int rowNo = noOfRows - 1; rowNo >= noOfRows/2 ; rowNo--){
                Set<Integer> checkColumnSet = new HashSet<>();
                for (int offset = 0; offset < 4; offset++){
                    checkColumnSet.add(board[rowNo - offset][columnNo]);
                }
                if (checkColumnSet.size() == 1){
                    if (checkColumnSet.contains(1)){
                        return 1;
                    }
                    else if (checkColumnSet.contains(2)){
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Check if there are any winning diagonals. Calls checkTopRightToBottomLeft and checkTopLeftToBottomRight
     *
     * @return 1 if player 1 has a winning diagonal, or 2 if player two has a winning diagonal.
     * If neither has won return 0.
     */
    public static int checkDiagonals(int[][] board) {
        int checkTopRightToBottomLeftResult = checkTopRightToBottomLeft(board);
        int checkTopLeftToBottomRightResult = checkTopLeftToBottomRight(board);
        if (checkTopLeftToBottomRightResult == 1 || checkTopRightToBottomLeftResult == 1){
            return 1;
        }
        else if (checkTopLeftToBottomRightResult == 2 || checkTopRightToBottomLeftResult == 2){
            return 2;
        }
        return 0;
    }

    private int[][] getBoard() {
        return board;
    }

    /**
     * Checks for winning diagonals in the top left to bottom right direction,
     *
     * @param gameArray the array of the game board
     *
     * @return 1 if player 1 has a winning diagonal, 2 if player 2 has a winning diagonal
     * Return 0 if neither has a winning diagonal
     */
    private static int checkTopLeftToBottomRight(int[][] gameArray) {
        for (int columnNo = 0; columnNo <= noOfColumns - 4; columnNo++){
            for (int rowNo = 0; rowNo <= noOfRows - 4; rowNo++){
                Set<Integer> checkCurrentDiagonalSet = new HashSet<>();
                for (int offset = 0; offset < 4; offset++) {
                    checkCurrentDiagonalSet.add(gameArray[rowNo + offset][columnNo + offset]);
                }
                if (checkCurrentDiagonalSet.size() == 1){
                    if (checkCurrentDiagonalSet.contains(1)){
                        return 1;
                    }
                    else if (checkCurrentDiagonalSet.contains(2)){
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Checks for winning diagonals in the top right to bottom left direction,
     *
     * @param gameArray the array of the game board
     *
     * @return 1 of player 1 has met a winning diagonal, 2 if player 2 has a winning diagonal.
     * 0 if neither player has a winning diagonal
     */
    private static int checkTopRightToBottomLeft(int[][] gameArray) {
        for (int columnNo = noOfColumns - 1; columnNo >= 3; columnNo--){
            for (int rowNo = 0; rowNo <= noOfRows - 4; rowNo++){
                Set<Integer> checkCurrentDiagonalSet = new HashSet<>();
                for (int offset = 0; offset < 4; offset++) {
                    checkCurrentDiagonalSet.add(gameArray[rowNo + offset][columnNo - offset]);
                }
                if (checkCurrentDiagonalSet.size() == 1){
                    if (checkCurrentDiagonalSet.contains(1)){
                        return 1;
                    }
                    else if (checkCurrentDiagonalSet.contains(2)){
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Returns the number of columns
     *
     * @return the number of columns
     */
    public static int getNoOfColumns() {
        return noOfColumns;
    }

    /**
     * Returns the number of rows
     *
     * @return the number of rows
     */
    public static int getNoOfRows() {
        return noOfRows;
    }

    /**
     * Sets the selected column.
     *
     * @param  selectedColumn  the value to set as the selected column
     */
    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }
}
