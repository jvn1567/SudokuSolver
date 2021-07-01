package sudokuSolver;

import java.util.HashSet;

/**
 * This class represents the state of a Sudoku game board.
 *
 * @author John
 */
public class SudokuBoard {

    private int[][] board;

    /**
     * Constructor for an empty board.
     */
    public SudokuBoard() {
        board = new int[9][9];
    }

    /**
     * Returns the value of the board at the passed in location.
     *
     * @param row the row of the location to check
     * @param col the column of the location to check
     * @return the value at the passed in location
     */
    int get(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the array of integers representing the board.
     *
     * @return the board field
     */
    int[][] getArray() {
        return board;
    }

    /**
     * Sets the value at the passed in location.
     *
     * @param num the value to set
     * @param row the row of the location to set
     * @param col the column of the location to set
     */
    void set(int num, int row, int col) {
        board[row][col] = num;
    }

    /**
     * Checks whether the board is considered valid by Sudoku rules. No row,
     * column, or 3x3 block can contain the same number and must contain all
     * numbers between 1 and 9 inclusive.
     *
     * @return true if valid, false if invalid.
     */
    public boolean isValidBoard() {
        boolean valid = true;
        HashSet<Integer>[] rowCheck = emptyCheckSet();
        HashSet<Integer>[] colCheck = emptyCheckSet();
        HashSet<Integer>[] squareCheck = emptyCheckSet();
        //fill sets from board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                rowCheck[row].add(board[row][col]);
                colCheck[col].add(board[row][col]);
                //doesn't cancel because integer division
                int squareIndex = (row / 3) * 3 + col / 3;
                squareCheck[squareIndex].add(board[row][col]);
                //filters out invalid numbers
                if (board[row][col] < 1 || board[row][col] > 9) {
                    valid = false;
                }
            }
        }
        //any sets with sizes not equal to 9 means the board was invalid
        for (int i = 0; i < 9; i++) {
            boolean invalidRow = rowCheck[i].size() != 9;
            boolean invalidCol = colCheck[i].size() != 9;
            boolean invalidSquare = squareCheck[i].size() != 9;
            if (invalidRow || invalidCol || invalidSquare) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Creates an empty array of 9 HashSet<Integer> objects for checking board
     * validity.
     *
     * @return the empty HashSet array to check
     */
    HashSet<Integer>[] emptyCheckSet() {
        HashSet<Integer>[] checker = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            checker[i] = new HashSet<>();
        }
        return checker;
    }

    /**
     * Checks if the passed in value at the passed in location will still allow
     * for a valid board.
     *
     * @param value the value to check
     * @param row the row of the location to check
     * @param col the column of the location to check
     * @return true if board will still be valid, false if board will be invalid
     */
    boolean isValidTile(int value, int row, int col) {
        boolean valid = true;
        //row conflict check
        for (int col2 = 0; col2 < 9; col2++) {
            if (board[row][col2] == value) {
                valid = false;
            }
        }
        //col conflict check
        for (int row2 = 0; row2 < 9; row2++) {
            if (board[row2][col] == value) {
                valid = false;
            }
        }
        //square conflict check
        for (int row2 = (row / 3) * 3; row2 < ((row / 3) + 1) * 3; row2++) {
            for (int col2 = (col / 3) * 3; col2 < ((col / 3) + 1) * 3; col2++) {
                if (board[row2][col2] == value) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Solve the incomplete Sudoku board and return true if a valid solution was
     * found.
     *
     * @return true if valid solution was found, false if no or invalid solution
     */
    public boolean solve() {
        SudokuBoard solution = new SudokuBoard();
        copy(solution);
        //catches duplicate numbers that cause stack overflow
        try {
            solve(solution, 1, 0, 0, false);
        } catch (StackOverflowError err) {
            return false;
        }
        board = solution.getArray();
        return isValidBoard();
    }

    /**
     * Copies the current incomplete board onto a new solution board.
     *
     * @param solution the initially empty board to store the solution in
     */
    void copy(SudokuBoard solution) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solution.set(board[row][col], row, col);
            }
        }
    }

    /**
     * Recursively fills in the Sudoku board solution.
     *
     * @param solution the SudokuBoard that stores the solution to this board.
     * @param value the current value to insert into the next location
     * @param row the row to insert the new value
     * @param col the column to insert the new value
     * @param backtrack true if the method is currently backtracking
     * @return true if a solution was found, false if forced to backtrack beyond
     * the first tile.
     */
    boolean solve(SudokuBoard solution, int value, int row, int col,
            boolean backtrack) {
        //handle location
        if (row == 9) {
            return true;
        } else if (row < 0) {
            return false;
        } else if (col == 9) {
            return solve(solution, value, row + 1, 0, backtrack);
        } else if (col < 0) {
            return solve(solution, value, row - 1, 8, backtrack);
        }
        //skip over original given values
        if (board[row][col] != 0 && backtrack) {
            return solve(solution, value, row, col - 1, backtrack);
        } else if (board[row][col] != 0 && !backtrack) {
            solution.set(board[row][col], row, col);
            return solve(solution, value, row, col + 1, backtrack);
        }
        //handle backtrack
        if (value > 9 || (backtrack && solution.get(row, col) == 9)) {
            solution.set(0, row, col);
            return solve(solution, 0, row, col - 1, true);
        } else if (backtrack) {
            value = solution.get(row, col);
            return solve(solution, value + 1, row, col, false);
        }
        //handle insertion
        if (solution.isValidTile(value, row, col)) {
            solution.set(value, row, col);
            return solve(solution, 1, row, col + 1, backtrack);
        } else {
            return solve(solution, value + 1, row, col, backtrack);
        }
    }

}
