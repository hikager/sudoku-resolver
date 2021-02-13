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
 * @author Luis ML
 */
public class SubMatrixView {

    /**
     * Matrix wrapper for each text-field which is created from
     * initTextBoxesMatrix;
     */
    private TextField[][] subMatrix;

    /**
     * This is a symmetric matrix, so the both row-length and column-length are
     * the same value.
     */
    public final int LENGTH = 3;

    /**
     * Position of the textField in AXIS-X.
     */
    @Deprecated
    private double lenX;

    /**
     * Position of the textField in AXIS-Y.
     */
    @Deprecated
    private double lenY;

    /**
     * Position of the textField in AXIS-X.
     */
    private double positionX;

    /**
     * Position of the textField in AXIS-Y.
     */
    private double positionY;

    private int sudokuPainWidth;

    private int sudokuPainHeight;

    private int TEXTBOX_WIDTH = 30;

    /**
     * Sudoku wrapper or container (where each text-field will be in)
     */
    private AnchorPane sudokuPane;

    public SubMatrixView(AnchorPane sudokuPane,
            double positionX, double positionY,
            int sudokuPainWidth, int sudokuPainHeight) {

        this.sudokuPane = sudokuPane;
        subMatrix = new TextField[LENGTH][LENGTH];

        /*
        positionX = Matrix Length / 3 (because it has 3x3) but this
        submatrix needs to make the: 
            ((matrixLength)/3) /3 :: because we're setting a 9x9 elements
        then:
                1/3 first, 2/3 second, 3/3 and third element
         */
        this.positionX = positionX / LENGTH;
        this.positionY = positionY / LENGTH;

        this.sudokuPainWidth = sudokuPainWidth;
        this.sudokuPainHeight = sudokuPainHeight;
    }

    private void initSubMatrix() {
        double posX = 0, posY = 0;
        for (int i = 0; i < LENGTH; i++) {
            posX = positionX * i;
            for (int j = 0; j < LENGTH; j++) {
                TextField txtF = new TextField("" + 0);
                subMatrix[i][j] = setProperties(i, j, posX, posY);
            }
        }
    }

    private TextField setProperties(int i, int j, double posX, double posY) {

        //posX += 35;
        System.out.println("J=" + j);
        posY = positionY * j;
        //posY += 10;

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
