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
    private double lenX;

    /**
     * Position of the textField in AXIS-Y.
     */
    private double lenY;

    /**
     * Position of the textField in AXIS-X.
     */
    private double positionX;

    /**
     * Position of the textField in AXIS-Y.
     */
    private double positionY;

    private int TEXTBOX_WIDTH = 30;

    /**
     * Sudoku wrapper or container (where each text-field will be in)
     */
    private AnchorPane sudokuPane;

    private int offsetX;
    private int offsetY;

    public SubMatrixView(AnchorPane sudokuPane,
            double positionX, double positionY,
            double lenX, double lenY,
            int offsetX, int offsetY) {

        this.lenX = lenX;
        this.lenY = lenY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.sudokuPane = sudokuPane;
        subMatrix = new TextField[LENGTH][LENGTH];

        /*
        positionX = Matrix Length / 3 (because it has 3x3) but this
        submatrix needs to make the: 
            ((matrixLength)/3) /3 :: because we're setting a 9x9 elements
        then:
                1/3 first, 2/3 second, 3/3 and third element
         */
        this.positionX = positionX;
        this.positionY = positionY;

        initSubMatrix();
    }

    private void initSubMatrix() {
        double posY = positionY;
        for (int i = 0; i < LENGTH; i++) { //filas -> y
            double posX = positionX;

            for (int j = 0; j < LENGTH; j++) { //columnas -> x

                TextField txtF = new TextField("" + 0);
                subMatrix[i][j] = setProperties(i, j, posX, posY);
                posX += lenX;//j * offsetX;
            }
            posY += lenY;
        }

        System.out.println("Submatrix created!");
    }

    private TextField setProperties(int i, int j, double posX, double posY) {

        System.out.println("J=" + j);
        // posY = positionY + j * offsetY;

        TextField txtF = new TextField("" + 0);
        txtF.setStyle("-fx-font-size:15px;");
        //id format : textInput_rowNumber_colNumber
        txtF.setId("textInput_" + i + "_" + j);
        System.out.printf("New textBox at (%f,%f) with id: %s\n", posX, posY, "textInput_" + offsetX + "_" + offsetY + "_" + i + "_" + j);

        txtF.setLayoutX(posX);
        txtF.setLayoutY(posY);
        txtF.setPrefWidth(TEXTBOX_WIDTH);

        txtF.setOnMouseClicked((event) -> {
            System.out.println("---------------------------[Data info for clicked text:]--------------------------------");
            System.out.println("Element id selected: textInput_" + i + "_" + j);
            System.out.println("matrix position: " + offsetX + "_" + offsetY);
            System.out.println("sub-matrix position: " + i + "_" + j);
            System.out.println("---------------------------------------------------------------------------------------");
        });
        sudokuPane.getChildren().add(txtF);

        return txtF;

        //We store our custom text field into the matrix
    }

    public TextField[][] getSubMatrix() {
        return subMatrix;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public int getTEXTBOX_WIDTH() {
        return TEXTBOX_WIDTH;
    }

    public AnchorPane getSudokuPane() {
        return sudokuPane;
    }

}
