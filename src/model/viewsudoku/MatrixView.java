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

    private int checkedNumbers[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

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

    /**
     * Counter of inputs from the user to insert into the Sudoku matrix
     */
    private int inputNumberCount;

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
     *
     * It checks;
     *
     * 1.If the matrix is initialized
     *
     * 2. If it has invalid inputs (no numbers o other stuff)
     *
     * 3. Last but not least, whether the numbers are in a correct position for
     * a Sudoku
     *
     * @return If the user inputs are valid before solving the Sudoku
     */
    public boolean isValidSudoku() {
        return (this.isIniti && hasValidInputs());
    }

    /**
     * It checks the view matrix, element by element if they're numbers.
     *
     * @return
     */
    private boolean hasValidInputs() {
        boolean isValidMatrix = true;
        inputNumberCount = 0;
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
                            } else {
                                /*
                                  If input is a valid number is time to check
                                  that number is valid for the sudoku context
                                 */
                                boolean hasValidNumber;
                                ++inputNumberCount;
                                //If I have time:
                                /**
                                 * This method below it repeats too much so I
                                 * need to "invalidate" those rows that are
                                 * already check (modify the for's with a
                                 * variable)
                                 *
                                 * Why? The repeatitions are made for each
                                 * submatrix. If it finds numbers in the three
                                 * submatrix it will make 3 times the check (3
                                 * times the same row!!)
                                 */
                                hasValidNumber = hasValidNumbers(row, col, subRow, subCol);

                                if (!hasValidNumber) {

                                    return hasValidNumber;
                                }
                            }
                        }
                    }
                }
            }

        }
        System.out.println("VALID");
        return isValidMatrix;
    }

    /**
     * It only checks whether the numbers are well-positioned
     *
     * This check use three checks
     *
     * 1.checkOnRowsConstant
     *
     * 2.checkOnColumnsConstant
     *
     * 3.checkOnSubmatrix
     *
     * If the input passes all of them, is a valid number in a Sudoku context.
     *
     * @return If the matrix is good-like Sudoku
     */
    private boolean hasValidNumbers(int row, int col, int rowSub, int colSub) {
        return checkOnRowsConstant(row, rowSub)
                && checkOnColumnsConstant(col, colSub)
                && checkOnSubmatrix(row, col);
    }

    /**
     * It checks on the matrixView each row whether it has repeated numbers
     *
     * This method iterates over the matrixView with a constant value of rows
     * because if we check all the matrix it will be too much calculus.
     *
     * For this reason, It is only checked those columns which affects a
     * collision or could have a repeated number (Understanding Sudoku's rules)
     *
     * @param rowCt a constant value for Matrix rows
     *
     * @param rowSubCt a constant value for SubMatrix rows
     * @return Whether it has repeated numbers (false) or not (true)
     */
    private boolean checkOnRowsConstant(int rowCt, int rowSubCt) {
        System.out.printf("\nChecking row [%d] and subRow[%d] \n", rowCt, rowSubCt);
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        //  for (int row = 0; row < LENGTH; row++) {
        for (int col = 0; col < LENGTH; col++) {
            // for (int subRow = 0; subRow < LENGTH; subRow++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                String inputStrNumber
                        = this.matrixView[rowCt][col][rowSubCt][subCol].getText();
                // System.out.println(inputStrNumber);
                //check if a number is repeated

                if (!inputStrNumber.equals("-") && inputStrNumber.length() > 0
                        && inputStrNumber.matches("[1-9]")) {
                    int number = Integer.parseInt(inputStrNumber);

                    for (int i = 0; i < LENGTH * LENGTH; i++) {
                        if (checkedNumbers[i] == number) {

                            System.out.println("rep!: " + number);
                            return noRepetitions = false;
                        }
                    }
                    checkedNumbers[colCount] = number;
                    printCheckArray();
                    ++colCount;
                }

            }
            //System.out.println("");
            //}
        }
        //  }
        System.out.printf("\nValid numbers in both  row [%d] and subRow [%d] \n\n", rowCt, rowSubCt);
        return noRepetitions;
    }

    /**
     * It checks on the matrixView each column whether it has repeated numbers.
     *
     * This method iterates over the matrixView with a constant value of columns
     * because if we check all the matrix it will be too much calculus.
     *
     * For this reason, It is only checked those columns which affects a
     * collision or could have a repeated number (Understanding Sudoku's rules)
     *
     *
     * @param colCt a constant value for Matrix columns
     * @param SubcolCt a constant value for SubMatrix columns
     * @return Whether it has repeated numbers (false) or not (true)in columns
     */
    private boolean checkOnColumnsConstant(int colCt, int SubcolCt) {
        System.out.printf("\nChecking col [%d] and subCol[%d] \n", colCt, SubcolCt);
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;

        for (int row = 0; row < LENGTH; row++) {

            for (int subRow = 0; subRow < LENGTH; subRow++) {
                String inputStrNumber
                        = this.matrixView[row][colCt][subRow][SubcolCt].getText();
                //System.out.println(inputStrNumber);
                //check if a number is repeated
                if (!inputStrNumber.equals("-")
                        && inputStrNumber.length() > 0
                        && inputStrNumber.matches("[1-9]")) {

                    int number = Integer.parseInt(inputStrNumber);

                    if (isRepeated(number)) {
                        return noRepetitions = false;
                    }
                    checkedNumbers[colCount] = number;
                    printCheckArray();
                    ++colCount;
                }
            }
            // System.out.println("");
        }
        System.out.printf("\nValid numbers in both  col  [%d] and subCol [%d] \n\n", colCt, SubcolCt);
        return noRepetitions;
    }

    /**
     * It checks on the matrixView one subMatrix (a 3x3 field within MatrixView)
     *
     * This method iterates over the matrixView with a constant value of columns
     * and rows because if we check all the subMatrices it will be too much
     * calculus.
     *
     * For this reason, It is only checked one SubMatrix at a time
     *
     *
     * @param rowCt Matrix row position (Sudoku matrix)
     * @param colCt Matrix column position (Sudoku matrix)
     * @return If the subMatrix has or not repeated numbers
     */
    private boolean checkOnSubmatrix(int rowCt, int colCt) {
        System.out.printf("\nChecking submatrix [%d,%d] \n", rowCt, colCt);
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        for (int subRow = 0; subRow < LENGTH; subRow++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                String inputStrNumber
                        = this.matrixView[rowCt][colCt][subRow][subCol].getText();
                //System.out.println(inputStrNumber);
                //check if a number is repeated
                if (!inputStrNumber.equals("-")
                        && inputStrNumber.length() > 0
                        && inputStrNumber.matches("[1-9]")) {

                    int number = Integer.parseInt(inputStrNumber);

                    if (isRepeated(number)) {
                        printCheckArray();
                        System.out.println("submatrix-check-repeated-found: " + number);
                        System.out.println("position: " + this.matrixView[rowCt][colCt][subRow][colCt].getId());
                        return noRepetitions = false;
                    }

                    checkedNumbers[colCount] = number;
                    printCheckArray();
                    ++colCount;
                }
            }
        }
        System.out.printf("\nValid numbers in subMatrix [%d,%d] \n\n", rowCt, colCt);
        return noRepetitions;
    }

    /**
     * Check from an array whether a number is repeated or not.
     *
     * Goal: This method goes along checking the input values from matrixView.
     *
     *
     * @param number The integer number to be checked
     * @return if there's some repeated number
     */
    private boolean isRepeated(int number) {
        boolean isRepeated = false;
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            if (checkedNumbers[i] == number) {
                System.out.println("rep!: " + number);
                return isRepeated = true;
            }
        }
        return isRepeated;
    }

    private void printCheckArray() {
        int totalLen = LENGTH * LENGTH;
        System.out.printf("-Array-Cheking-State: [");
        for (int i = 0; i < totalLen; i++) {
            if (i < totalLen - 1) {
                System.out.printf("%d, ", this.checkedNumbers[i]);
            } else {
                System.out.printf("%d] ", this.checkedNumbers[i]);
            }
        }
        System.out.println("");
    }

    /**
     * Reset the checking array to 0.
     *
     * Why: Because when a checking field is ended and start another, we need to
     * clean the numbers used previously.
     */
    private void resetCheckArray() {
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            this.checkedNumbers[i] = 0;
        }
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
