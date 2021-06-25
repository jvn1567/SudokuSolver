package sudokuSolver;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author John
 */
public class SudokuBoard {
    
    private int[][] board;
    
    public SudokuBoard() {
        board = new int[9][9];
    }
    
    int get(int row, int col) {
        return board[row][col];
    }
    
    int[][] getArray() {
        return board;
    }
    
    void set(int num, int row, int col) {
        board[row][col] = num;
    }
    
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
    
    HashSet<Integer>[] emptyCheckSet() {
        HashSet<Integer>[] checker = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            checker[i] = new HashSet<>();
        }
        return checker;
    }
    
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
    
    public boolean solve() {
        SudokuBoard solution = new SudokuBoard();
        copy(solution);
        solve(solution, 1, 0, 0, false);
        board = solution.getArray();
        return isValidBoard();
    }
    
    void copy(SudokuBoard solution) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solution.set(board[row][col], row, col);
            }
        }
    }
    
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
