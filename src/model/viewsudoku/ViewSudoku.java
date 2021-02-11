/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.viewsudoku;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author LuisDAM
 */
public class ViewSudoku {

    //Si es modifiquès el Pane aquest hauria de
    //canviarlo manualment
    private int SUDOKU_PANE_WIDTH = 854;
    private int SUDOKU_PANE_HEIGHT = 459;

    public void initTextBoxesMatrix(AnchorPane sudokuPane) {

        //To separate each record (text-field) I decied to set a constant width and height
        //from the SudokuView.fxml (view) sudokuPane object.
        double lenTxt = (SUDOKU_PANE_WIDTH / 9); //Width / number of columns
        double lenY = (SUDOKU_PANE_HEIGHT / 9); // Width / number of rows
        int TEXTBOX_WIDTH = 30;

        //int separatorSubMatrix = 0; //each 3 columns restarts
        //Position for each sudoku-record-input
        double posX, posY;
        for (int i = 0; i < 9; i++) {
            System.out.println("Row number: " + (i + 1));
            posX = lenTxt * i;
            for (int j = 0; j < 9; j++) {
                //Setting manually some padding
                if (j == 0) {
                    posX += 30;
                }
                posY = lenY * j;
                posY += 10;

                TextField txtF = new TextField("" + 0);
                txtF.setStyle("-fx-font-size:15px;");
                System.out.printf("New textBox at (%f,%f)\n", posX, posY);
                txtF.setLayoutX(posX);
                txtF.setLayoutY(posY);
                txtF.setPrefWidth(TEXTBOX_WIDTH);

                sudokuPane.getChildren().add(txtF);
            }

            System.out.println("");
        }
        System.out.println("\nMatrix of text-boxes generated...");
    }
}
