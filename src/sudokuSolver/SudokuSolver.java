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
        Button btnCheck = new Button("Check");
        Button btnSolve = new Button("Solve");
        menu.getChildren().add(btnCheck);
        menu.getChildren().add(btnSolve);
        VBox.setMargin(btnCheck, new Insets(10, 0, 0, 0));
        VBox.setMargin(btnSolve, new Insets(10, 0, 0, 0));
        btnCheck.setFont(Font.font(15));
        btnSolve.setFont(Font.font(15));
        //button actions
        //PLACEHOLDER TODO
        return menu;
    }
    
}
