package sudokuSolver;

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
    
    public void set(int num, int row, int col) {
        board[row][col] = num;
    }
    
    public boolean checkFullBoard() {
        boolean valid = true;
        HashSet<Integer>[] rowCheck = emptyCheckSet();
        HashSet<Integer>[] colCheck = emptyCheckSet();
        HashSet<Integer>[] squareCheck = emptyCheckSet();
        //fill sets from board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                rowCheck[row].add(board[row][col]);
                colCheck[col].add(board[row][col]);
                int squareRow = row / 3;
                int squareCol = col / 3;
                //* 3 doesn't cancel because row / 3 is integer division
                int squareIndex = squareRow * 3 + squareCol;
                squareCheck[squareIndex].add(board[row][col]);
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
        System.out.println("TEST");
        return valid;
    }
    
    public HashSet<Integer>[] emptyCheckSet() {
        HashSet<Integer>[] checker = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            checker[i] = new HashSet<>();
        }
        return checker;
    }
}
