package model;

import static java.lang.System.exit;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;

/**
 * This class extends from Sudoku and it inherits both the Sudoku matrix and
 * Sudoku View Matrix (to check fixed positions)
 *
 * It actually done what its name says, It will solve a Sudoku matrix.
 *
 * For this project this class will use or will be "bind" with the numbers in
 * the Sudoku which is made from JavaFx. These two classes will exchange each
 * other the numbers in the Sudoku.
 *
 *
 *
 *
 * <h3>Explanation </h3>
 *
 * The class which inherits this class will use to solve the Sudoku, the
 * complexSudokuM matrix (which is inherited too).
 *
 * Then, I'm summing up the resolving of the Sudoku in the following paragraph.
 *
 * 1. This matrix (complexSudokuM) will be set in 0 all the values <br>
 *
 * 2. This matrix will be used to set the first values to the ViewMatrix of the
 * Sudoku (all the 0s will be inserted into the matrixView from the class
 * MatrixView).<br>
 *
 * 3.The user will insert the values, if they're valid, the Boolean matrix will
 * set true those valid positions<br>
 *
 * 4. This matrix (complexSudokuM) will use the Boolean matrix to insert the
 * first values to start the first state of the Sudoku.So the Boolean matrix
 * will be used for set those positions fixed. Then the matrixView will be used
 * to retrieve the input values from the user. From this point we already have
 * all the data to start the next and last part.<br>
 *
 * 5.Once these values are inserted in the matrix complexSudokuM, the resolving
 * part will start.<br>
 *
 *
 * @see model.Sudoku
 * @see model.viewsudoku.ViewSudoku
 * @author LuisML
 */
public class SudokuResolver extends Sudoku {

    private SudokuResolverSubMatrix[] subMatrixRunnables = new SudokuResolverSubMatrix[9];
    private Thread[] subMatricesTh = new Thread[9];
    private PopUpMSG popUpMSG;
    private Semaphore semaphore;
    private AtomicBoolean insertAgain = new AtomicBoolean(false);

    public SudokuResolver(int rowLen, int colLen, Semaphore semaphore, AnchorPane sudokuPane) {
        super(rowLen, colLen, true, sudokuPane);
        this.semaphore = semaphore;

    }

    /**
     * Default constructor for Sudoku 3x3 of matrices 3x3
     *
     * @param semaphore to handle the threads for the resolving.
     */
    public SudokuResolver(Semaphore semaphore, AnchorPane sudokuPane) {
        super(3, 3, false, sudokuPane);
        this.semaphore = semaphore;
        popUpMSG = new PopUpMSG();
    }

    public SudokuResolver() {
        super();
        popUpMSG = new PopUpMSG();

    }

    //TODO Esta classe es quien "manage" la resolucion (esta inicia los hilos y demas)
    /**
     * It check whether the a matrix is valid for the Sudoku or not. (Integer
     * matrix)
     *
     * @return Whether a matrix is a valid Sudoku
     */
    public void startResolving() {
        boolean isValidSudoku = getMatrixView().isValidSudoku();
        if (isValidSudoku) {
            //resolve the matrix here!
            //start filling matrices (set the boolean matrix and input values
            prepareMatrices();

            start();
            //method to start the calculus/resolving-part
        } else {
            //We warn the user he/she just inserted numbers on invalid position
            popUpMSG.setError("This is not a valid Saudoku to resolve...\n"
                    + "It's going to clean the Sudoku!!!");
            popUpMSG.errorMSG();
            //reset the whole matrix view
            getMatrixView().initTextBoxesMatrix();
            /*
            If there's time, an upgrade when an input is invalid:
            
            TODO:
            For each wrong input color in red the text and disable the button,
            this will just avoid user to insert invalid inputs and set the
            resolve button
             */
        }
    }

    /**
     * It sets up the Boolean matrix and integer.
     *
     * In Boolean matrix it will set true to those input values where user
     * inserted
     *
     * In integer matrix the values from the text-field to its mirror position
     * (same matrix pos)
     */
    private void prepareMatrices() {
        //method to fill integer matrix
        System.out.println("Importing numbers....");
        importSudoku();
        //method to fill boolean matrix
        System.out.println("Setting up fixed values...");
        importBooleanSudoku();
    }

    /**
     * It creates an array of threads to store each runnable of the sub-matrix
     * resolver object.
     */
    private void initSubMatricesThreads() {
        int len = super.getMatrixView().LENGTH;
        int th = 0;
        //int totalLen = len * len;
        for (int row = 0; row < len; row++) {
            for (int col = 0; col < len; col++) {
                subMatrixRunnables[th]
                        = new SudokuResolverSubMatrix(this.getComplexSudokuM()[row][col],
                                this.getSudokuUserInputsM()[row][col],
                                row,
                                col,
                                this.semaphore,
                                "subMatrix[" + row + "," + col + "]",
                                insertAgain,
                                this.getComplexSudokuM());
                try {

                    /* System.out.println(subMatrixRunnables[th].getSubMatrixName());
                    this.subMatricesTh[th] = new Thread(
                            subMatrixRunnables[th],
                            subMatrixRunnables[th].getSubMatrixName());*/
                    ++th;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * It starts each thread from sub-matrix array
     */
    private void startSubMatricesThreads() {
        int len = super.getMatrixView().LENGTH;
        System.out.println("----------------------------");
        for (int i = 0; i < len * len; i++) {
            subMatrixRunnables[i].start();
            System.out.println("thread starting... " + subMatrixRunnables[i].getName());
        }
        System.out.println("----------------------------");
    }

    private void checkSudoku() {
        while (true) {
            try {
                Thread.sleep(1000);

                this.semaphore.acquire();
                System.out.println("Check sudoku state on resolving:");
                System.out.println(this.getComplexSudokuStateFormatted());
                this.semaphore.release();
                System.exit(0);
                restartSubMatricesThreads();

            } catch (InterruptedException ex) {
                Logger.getLogger(SudokuResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        initSubMatricesThreads();
        startSubMatricesThreads();
        checkSudoku();
    }

    private void restartSubMatricesThreads() {
        int len = super.getMatrixView().LENGTH;
        for (int i = 0; i < len * len; i++) {
            this.subMatrixRunnables[i].canInsertAgain = true;
            System.out.println("thread starting... "
                    + this.subMatrixRunnables[i].getSubMatrixName());
        }
    }

    private void checkCurrentMatrix() {

    }

    private boolean hasValidNumbers(int row, int col, int rowSub, int colSub) {
        return checkOnRowsConstant(row, rowSub)
                && checkOnColumnsConstant(col, colSub)
                && checkOnSubmatrix(row, col);
    }

    private boolean checkOnRowsConstant(int ctRow, int ctSubRow) {
        return true;
    }

    private boolean checkOnColumnsConstant(int ctCol, int ctSubCol) {
        return true;
    }

    private boolean checkOnSubmatrix(int ctRow, int ctCol) {
        return true;
    }

}
