package model;

import java.util.concurrent.Semaphore;
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

    private PopUpMSG popUpMSG;
    private Semaphore semaphore;

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
            //start resolving it (set the boolean matrix and input values
            prepareMatrices();
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
        //method to fill boolean matrix
        //method to fill integer matrix
    }

}
