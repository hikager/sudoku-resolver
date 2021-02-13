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
 * @author Luis ML
 */
public class MatrixView {
    //Si es modifiquès el Pane aquest hauria de
    //canviarlo manualment

    /**
     * Matrix wrapper for each text-field which is created from
     * initTextBoxesMatrix
     *
     * This one will store for each position a sub-matrix of 3x3 (TextField
     * matrix)
     */
    private TextField[][] matrixView[][];

    /**
     * This class generates each sub-matrix for each matrixView position and
     * sets the position in the pane (sudokuPane)
     */
    private SubMatrixView subMatrixViewClass;

    /**
     * This is a symmetric matrix, so the both row-length and column-length are
     * the same value.
     */
    public final int LENGTH = 3;

    /**
     * Extract from the view package : SudokuView.fxml and id=sudokuPane
     *
     * This is the constant wrapper for the pane's width
     */
    private int SUDOKU_PANE_WIDTH = 854;

    /**
     * Extract from the view package : SudokuView.fxml and id=sudokuPane
     *
     * This is the constant wrapper for the pane's height
     */
    private int SUDOKU_PANE_HEIGHT = 459;

    /**
     * lenX is for each position from the X axis of the sub-matrices
     *
     * (Horizontal position)
     */
    private double lenX = (SUDOKU_PANE_WIDTH / LENGTH);
    /**
     * lenY is for each position from the Y axis of the sub-matrices
     *
     * (Vertical position)
     */
    private double lenY = (SUDOKU_PANE_HEIGHT / LENGTH);

    /**
     * The width which will have each text-field in the view (30 px)
     */
    private final int TEXTBOX_WIDTH = 30;
    /**
     * Matrix wrapper for each text-field which is created from
     * initTextBoxesMatrix;
     */
    @Deprecated
    private TextField[][] textFMatrix;

    private AnchorPane sudokuPane;

    private Semaphore semaphore;

    public MatrixView(AnchorPane sudokuPane) {
        this.sudokuPane = sudokuPane;
        matrixView = new TextField[LENGTH][LENGTH][LENGTH][LENGTH];
        semaphore = new Semaphore(1);

    }

    public MatrixView(AnchorPane sudokuPane, int semaphoreSlotLimit) {
        this.sudokuPane = sudokuPane;
        matrixView = new TextField[LENGTH][LENGTH][LENGTH][LENGTH];
        semaphore = new Semaphore(semaphoreSlotLimit);
    }

    public void initTextBoxesMatrix() {

        double posY = 20;
        for (int i = 0; i < LENGTH; i++) {
            System.out.println("Row number: " + (i + 1));
            double posX = 35;
            
            for (int j = 0; j < LENGTH; j++) {
                //posX = lenX * i + 35;
                this.subMatrixViewClass
                        = new SubMatrixView(this.sudokuPane,
                                posX, posY,
                                lenX/LENGTH, lenY/LENGTH,
                                i, j);

                this.matrixView[i][j] = subMatrixViewClass.getSubMatrix();
                posX += lenX;
            }
            posY += lenY;
            System.out.println("");
        }
        System.out.println("\nMatrix of text-boxes generated in the view...");
    }

    public TextField[][][][] getMatrixView() {
        return matrixView;
    }

    public void setMatrixView(TextField[][][][] matrixView) {
        this.matrixView = matrixView;
    }

    public SubMatrixView getSubMatrixViewClass() {
        return subMatrixViewClass;
    }

    public void setSubMatrixViewClass(SubMatrixView subMatrixViewClass) {
        this.subMatrixViewClass = subMatrixViewClass;
    }

    public TextField[][] getTextFMatrix() {
        return textFMatrix;
    }

    public void setTextFMatrix(TextField[][] textFMatrix) {
        this.textFMatrix = textFMatrix;
    }

    public AnchorPane getSudokuPane() {
        return sudokuPane;
    }

    public void setSudokuPane(AnchorPane sudokuPane) {
        this.sudokuPane = sudokuPane;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

}
