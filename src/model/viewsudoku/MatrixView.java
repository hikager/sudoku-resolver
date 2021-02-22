/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.viewsudoku;

import java.util.concurrent.Semaphore;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.PopUpMSG;

/**
 *
 * @author Luis ML
 */
public class MatrixView {
    //Si es modifiquès el Pane aquest hauria de
    //canviarlo manualment

    private PopUpMSG popUpMSG;
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
     * I use manually the Anchor pain tag with the prefWidth=854
     *
     * This is the constant wrapper for the pane's width
     */
    private int SUDOKU_PANE_WIDTH = 854;

    /**
     * Extract from the view package : SudokuView.fxml and id=sudokuPane
     *
     * I use manually the Anchor pain tag with the prefHeight==854
     *
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

    /**
     * This is the object pane to add each text-field into the view (to set
     * visible each text-field)
     */
    private AnchorPane sudokuPane;

    /**
     * Object to handle the threads which is going to use an object from this
     * class
     */
    private Semaphore semaphore;

    /**
     * If the matrix view is already initialized
     */
    private boolean isIniti = false;
    
    public MatrixView(AnchorPane sudokuPane) {
        this.sudokuPane = sudokuPane;
        matrixView = new TextField[LENGTH][LENGTH][LENGTH][LENGTH];
        semaphore = new Semaphore(1);
        popUpMSG = new PopUpMSG();
        
    }
    
    public MatrixView(AnchorPane sudokuPane, int semaphoreSlotLimit) {
        this.sudokuPane = sudokuPane;
        matrixView = new TextField[LENGTH][LENGTH][LENGTH][LENGTH];
        semaphore = new Semaphore(semaphoreSlotLimit);
        popUpMSG = new PopUpMSG();
        
    }

    /**
     * This function use a complex build.
     *
     * <br>
     * 1. First it creates an object of subMatrixViewClass this object handles
     * the text-fields and its position in the pane.
     * <br>
     * <ol>
     *
     * <li>
     * The constructor of subMatrixViewClass gets the pane to set each
     * text-field the <b>posX</b> and <b>posY</b> to arrange each text in the
     * pane
     * </li>
     * <li>
     * Then I pass the lenX and LenY which are the total length of the
     * sudokuPane divided by 3
     * </li>
     * <li>
     * And the i,j works as the offsets (which sub-matrix are we talking) which
     * will makes the offset (<i>desplazamiento</i>) from each subMatrix
     * </li>
     * </ol>
     *
     * <h3>Matrix representation and information: (matrixView)</h3>
     * <br><br>
     *
     * {@code matrix of sub-matrices
     *
     *  | s00 | s01 | s02|
     *  | s10 | s11 | s12|
     *  | s10 | s11 | s12|
     *
     * }
     * <br> <br>
     * Each literal {@code s01} stands for s = subMatrix
     * <br>
     * The {@code offset in sub-matrix} stands for the i,j in this class
     *
     * <br>
     * <br>
     * 2. Once the subMatrixViewClass.getSubMatrix() makes the sub-matrix we add
     * it to the Matrix matrixView
     *
     * <br>
     * <br>
     *
     * matrixView [rowA][colA][rowAA][colAA] => multidimensional matrix.<br>
     *
     * <ul>
     * <li>rowA-> matrixView ROW</li>
     * <li>colA-> matrixView COLUMN</li>
     * <li>rowAA-> sub-matrix index for its ROW</li>
     * <li>colAA-> sub-matrix index for its COLUMNS</li>
     * </ul>
     *
     *
     * @see model.viewsudoku.SubMatrixView
     */
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
                                lenX / LENGTH, lenY / LENGTH,
                                i, j);
                
                this.matrixView[i][j] = subMatrixViewClass.getSubMatrix();
                posX += lenX;
            }
            posY += lenY;
            System.out.println("");
        }
        this.isIniti = true;
        System.out.println("\nMatrix of text-boxes generated in the view...");
    }

    
    /**
     * It checks whether the inputs from the user are valid (non-numbers,
     * spaces, empty, negative values...)
     *
     * @return If the user inputs are valid before solving the Sudoku
     */
    public boolean isValidSudoku() {
        boolean isValid = false;
        if (this.isIniti) {
            isValid = isValidMatrix();
        }
        return isValid;
    }

    /**
     * It checks the view matrix, element by element.
     *
     * @return
     */
    private boolean isValidMatrix() {
        boolean isValidMatrix = true;
        for (int row = 0; row < LENGTH; row++) {
            for (int col = 0; col < LENGTH; col++) {
                for (int subRow = 0; subRow < LENGTH; subRow++) {
                    for (int subCol = 0; subCol < LENGTH; subCol++) {
                        String inputText = this.matrixView[row][col][subRow][subCol].getText();
                        //Those textfield with "-" were inserted while the initialition
                        //so we need to ignored when we check the input values
                        if (!inputText.equals("-")) {
                            if (!(inputText != null
                                    && inputText.length() > 0
                                    && inputText.matches("[1-9]"))) {
                                popUpMSG.setError(inputText + " is not a valid number for a Sudoku!\n"
                                        + "this Sudoku will be reset!");
                                popUpMSG.errorMSG();
                                System.out.println("<" + inputText + "> is not a valid number for a Sudoku!\n "
                                        + " The first error were found in"
                                        + this.matrixView[row][col][subRow][subCol].getId());
                                return isValidMatrix = false;
                            }
                        }
                    }
                }
            }
            
        }
        System.out.println("VALID");
        return isValidMatrix;
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
