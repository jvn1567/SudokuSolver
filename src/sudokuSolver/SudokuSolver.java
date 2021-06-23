package sudokuSolver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class SudokuSolver extends Application {
    
    TextField[][] textFields;
    Label lblFeedback;
    
    @Override
    public void start(Stage primaryStage) {
        //panes
        GridPane boardPane = createGridPane();
        VBox menuPane = createMenuPane();
        //parent pane
        BorderPane parent = new BorderPane();
        parent.setCenter(boardPane);
        parent.setRight(menuPane);
        //scene
        Scene scene = new Scene(parent, 800, 600);
        //primary stage
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public GridPane createGridPane() {
        //board GridPane setup
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setBackground(new Background(new BackgroundFill(
                Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        //TextFields for input
        textFields = new TextField[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setMinSize(40, 40);
                textFields[row][col].setMaxSize(40, 40);
                textFields[row][col].setFont(Font.font(20));
                board.add(textFields[row][col], col, row);
                //separates 3x3 sections
                if (row % 3 == 0 && col % 3 == 0) {
                    GridPane.setMargin(textFields[row][col],
                            new Insets(2, 0, 0, 2));
                } else if (row % 3 == 0) {
                    GridPane.setMargin(textFields[row][col],
                            new Insets(2, 0, 0, 0));
                } else if (col % 3 == 0) {
                    GridPane.setMargin(textFields[row][col],
                            new Insets(0, 0, 0, 2));
                }
            }
        }
        return board;
    }
    
    public VBox createMenuPane() {
        //menu VBox setup
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        //welcome label
        Label lblWelcome = new Label("Welcome to Sudoku Solver!");
        lblWelcome.setFont(Font.font(20));
        VBox.setMargin(lblWelcome, new Insets(0, 20, 0, 20));
        menu.getChildren().add(lblWelcome);
        //buttons
        Button[] buttons = new Button[3];
        String[] buttonText = {"Check", "Solve", "Clear"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            VBox.setMargin(buttons[i], new Insets(10, 0, 0, 0));
            buttons[i].setFont(Font.font(15));
            buttons[i].setText(buttonText[i]);
            menu.getChildren().add(buttons[i]);
        }
        //button action events
        buttons[0].setOnAction(e -> checkBoard());
        buttons[1].setOnAction(e -> solveBoard());
        buttons[2].setOnAction(e -> clearTextFields());
        //label for feedback and error messages
        lblFeedback = new Label();
        lblFeedback.setFont(Font.font(15));
        VBox.setMargin(lblFeedback, new Insets(10, 0, 0, 0));
        menu.getChildren().add(lblFeedback);
        return menu;
    }
    
    public void checkBoard() {
        SudokuBoard board = new SudokuBoard();
        if (setStartingBoard(board)) {
            if (board.checkFullBoard()) {
                lblFeedback.setText("The puzzle is correct!");
            } else {
                lblFeedback.setText("The puzzle contains mistakes!");
            }
        } else {
            lblFeedback.setText("Invalid input!");
        }
    }
    
    public void solveBoard() {
        
    }
    
    public void clearTextFields() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col].setText("");
            }
        }
    }
    public boolean setStartingBoard(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String rawInput = textFields[row][col].getText();
                if (rawInput.length() > 0) {
                    try {
                        int input = Integer.parseInt(rawInput);
                        board.set(input, row, col);
                    } catch (NumberFormatException ex) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
}
