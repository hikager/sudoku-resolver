/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.viewsudoku;

import java.util.concurrent.Semaphore;
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

    private Semaphore semaphore;

    private double lenTxt = (SUDOKU_PANE_WIDTH / 9); //Width / number of columns
    private double lenY = (SUDOKU_PANE_HEIGHT / 9); // Width / number of rows

    private int TEXTBOX_WIDTH = 30;
    /**
     * Matrix wrapper for each text-field which is created from
     * initTextBoxesMatrix;
     */
    private TextField[][] textFMatrix;

    private AnchorPane sudokuPane;

    public ViewSudoku(AnchorPane sudokuPane) {
        this.sudokuPane = sudokuPane;
        textFMatrix = new TextField[9][9];
        semaphore = new Semaphore(1);
    }

    public ViewSudoku(AnchorPane sudokuPane, int semaphoreSlotLimit) {
        this.sudokuPane = sudokuPane;
        textFMatrix = new TextField[9][9];
        semaphore = new Semaphore(semaphoreSlotLimit);
    }

    public void initTextBoxesMatrix() {

        //To separate each record (text-field) I decied to set a constant width and height
        //from the SudokuView.fxml (view) sudokuPane object.
        //int separatorSubMatrix = 0; //each 3 columns restarts
        //Position for each sudoku-record-input
        double posX = 0, posY = 0;
        for (int i = 0; i < 9; i++) {
            System.out.println("Row number: " + (i + 1));
            posX = lenTxt * i;
            for (int j = 0; j < 9; j++) {
                TextField txtF = new TextField("" + 0);
                textFMatrix[i][j] = setProperties(i, j, posX, posY);
            }
            System.out.println("");
        }
        System.out.println("\nMatrix of text-boxes generated in the view...");
    }

    private TextField setProperties(int i, int j, double posX, double posY) {

        posX += 35;
        System.out.println("J=" + j);
        posY = lenY * j;
        posY += 10;

        TextField txtF = new TextField("" + 0);
        txtF.setStyle("-fx-font-size:15px;");
        //id format : textInput_rowNumber_colNumber
        txtF.setId("textInput_" + i + "_" + j);
        System.out.printf("New textBox at (%f,%f) with id: %s\n", posX, posY, "textInput_" + i + "_" + j);
        txtF.setLayoutX(posX);
        txtF.setLayoutY(posY);
        txtF.setPrefWidth(TEXTBOX_WIDTH);

        txtF.setOnMouseClicked((event) -> {
            // String textFieldId = event.getPickResult().getIntersectedNode().getId();
            System.out.println("Element id selected: textInput_" + i + "_" + j);
        });
        sudokuPane.getChildren().add(txtF);

        return txtF;

        //We store our custom text field into the matrix
    }

}
