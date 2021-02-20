package model;

import java.util.concurrent.Semaphore;

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
 * @see model.Sudoku
 * @see model.viewsudoku.ViewSudoku
 * @author LuisDAM
 */
public class SudokuResolver extends Sudoku {

    private int sudokuMatrix[][];
    private boolean sudokuViewM[][];
    private Semaphore semaphore;
    //private final int MATRIX_DEFAULT_LEN = 3;

    public SudokuResolver(int rowLen, int colLen, Semaphore semaphore) {
        super(rowLen, colLen, true);
        this.semaphore = semaphore;
    }

    /**
     * Default constructor for Sudoku 3x3 of matrices 3z3
     *
     * @param semaphore to handle the threads for the resolving.
     */
    public SudokuResolver(Semaphore semaphore) {
        super(3, 3, false);
        this.semaphore = semaphore;
    }

    public SudokuResolver() {
        super();
        sudokuMatrix = super.getSudokuM();
        sudokuViewM = super.getSudokuViewM();
    }

    //TODO Esta classe es quien "manage" la resolucion (esta inicia los hilos y demas)
}
