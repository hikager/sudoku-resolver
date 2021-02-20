package model;

import model.viewsudoku.MatrixView;

/**
 * It creates a matrix for a Sudoku, so we can thing as a Sudoku data structure.
 * We can define the rows and columns length o just setting as default Sudoku
 * which has 9x9 structure.
 *
 * As an abstract class it cannot be instantiated but for this project this one
 * works a "data-structure" for its SudokuResolver inherited class.
 *
 * This class only creates the data-structure to use in the Sudoku solving
 *
 *
 * <h3>Explanation: </h3>
 * This class "prepares" the sheet to write on and the SudokuResolver class is
 * the pencil or pen which will use to solve the Sudoku itself.
 *
 *
 * @see model.SudokuResolver
 * @author LuisML
 */
public abstract class Sudoku {

    private final int ROW_LENTGH;
    private final int COL_LENTGH;

    private int sudokuM[][];
    /**
     * Class to generate the Sudoku view
     */
    private MatrixView matrixView;
    /**
     * Matrix to treat the solution of the Sudoku
     */
    private int complexSudokuM[][][][];

    /**
     * It manage the fix values which the user inserted first and then if
     * they're valid. This matrix will be useful for the resolving part.
     */
    private boolean sudokuUserInputsM[][][][];

    public Sudoku(int ROW_LENTGH, int COL_LENTGH, boolean simpleSudokuStructure) {
        this.ROW_LENTGH = ROW_LENTGH;
        this.COL_LENTGH = COL_LENTGH;
        if (simpleSudokuStructure) {
            initSudokuMatrix();
        } else {
            initComplexSudokuMatrix();
            initSudokuInputValuesMatrix();
        }
    }

    public Sudoku() {
        this.ROW_LENTGH = 9;
        this.COL_LENTGH = 9;
        initSudokuMatrix();
    }

    public int getROW_LENTGH() {
        return ROW_LENTGH;
    }

    public int getCOL_LENTGH() {
        return COL_LENTGH;
    }

    public int[][] getSudokuM() {
        return sudokuM;
    }

    public int[][][][] getComplexSudokuM() {
        return complexSudokuM;
    }

    public void setComplexSudokuM(int[][][][] complexSudokuM) {
        this.complexSudokuM = complexSudokuM;
    }

    public MatrixView getMatrixView() {
        return matrixView;
    }

    public void setMatrixView(MatrixView matrixView) {
        this.matrixView = matrixView;
    }

    public boolean[][][][] getSudokuUserInputsM() {
        return sudokuUserInputsM;
    }

    public void setSudokuUserInputsM(boolean[][][][] sudokuUserInputsM) {
        this.sudokuUserInputsM = sudokuUserInputsM;
    }

    /**
     * It initializes the matrix which will be used for resolving the Sudoku.
     *
     * The matrix complexSudokuM will store the values which the user inserts
     * and they're valid.
     *
     *
     */
    private void initComplexSudokuMatrix() {
        complexSudokuM = new int[ROW_LENTGH][COL_LENTGH][COL_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                //Creating a new 3x3 matrix  System.out.println("INIT COMPLEX");
                complexSudokuM[row][col] = new int[ROW_LENTGH][ROW_LENTGH];
                for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                    for (int colSubMatrix = 0; colSubMatrix < ROW_LENTGH; colSubMatrix++) {
                        //init the values within the submatrix
                        complexSudokuM[row][col][rowSubMatrix][colSubMatrix] = 0;//l;
                        // l++;
                    }
                }
            }
        }
        //  System.out.printf("=>" + l);
    }

    private void initSudokuInputValuesMatrix() {

        sudokuUserInputsM = new boolean[ROW_LENTGH][COL_LENTGH][COL_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                //Creating a new 3x3 matrix  System.out.println("INIT COMPLEX");
                sudokuUserInputsM[row][col] = new boolean[ROW_LENTGH][ROW_LENTGH];
                for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                    for (int colSubMatrix = 0; colSubMatrix < ROW_LENTGH; colSubMatrix++) {
                        //init the values within the submatrix
                        sudokuUserInputsM[row][col][rowSubMatrix][colSubMatrix] = false;//l;
                        // l++;
                    }
                }
            }
        }
        //  System.out.printf("=>" + l);
    }

    private void initSudokuMatrix() {
        sudokuM = new int[ROW_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                sudokuM[row][col] = 0;
            }
        }
    }

    public String getSimpleSudokuState() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-----------------------------------[Showing sudoku state]------------------------------------------\n");
        try {
            int rowLen = sudokuM.length;
            int colLen = sudokuM[0].length;
            for (int r = 0; r < rowLen; r++) {
                for (int c = 0; c < colLen; c++) {
                    sb.append("\t" + sudokuM[r][c]);
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error while reading sudoku matrix: " + e.getMessage());
        }
        sb.append("---------------------------------------------------------------------------------------------------");
        return sb.toString();
    }

    public String getComplexSudokuState() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------[Showing complex sudoku state]-------\n");
        try {
            for (int row = 0; row < ROW_LENTGH; row++) {
                for (int col = 0; col < COL_LENTGH; col++) {
                    for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                        for (int colSubMatrix = 0; colSubMatrix < COL_LENTGH; colSubMatrix++) {
                            sb.append(" " + complexSudokuM[row][col][rowSubMatrix][colSubMatrix]);
                        }
                        sb.append("\n");
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error while reading complex sudoku matrix: " + e.getMessage());
        }
        sb.append("------------------------------------------");
        return sb.toString();
    }

    public String getComplexSudokuStateFormatted() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------[Showing complex sudoku state]-------\n");
        try {
            for (int row = 0; row < ROW_LENTGH; row++) {
                for (int col = 0; col < COL_LENTGH; col++) {
                    for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                        for (int colSubMatrix = 0; colSubMatrix < COL_LENTGH; colSubMatrix++) {
                            sb.append(String.format("%3d", complexSudokuM[row][rowSubMatrix][col][colSubMatrix]));
                        }
                        sb.append(String.format("%2s", " "));
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error while reading complex sudoku matrix: " + e.getMessage());
        }
        sb.append("------------------------------------------");
        return sb.toString();
    }

    /**
     * F-> fixed value: The value which the user inserted and is valid to the
     * Sudoku matrix. Then these values cannot change when the Sudoku is being
     * resolved.
     *
     * NF-> Not-fixed value: Those values that will be changing while the Sudoku
     * is being solved
     *
     *
     * @return String with the current matrix state
     */
    public String getUserSudokuInputMatrixState() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------[Showing user matrix view  state]-------\n");
        try {
            sb.append("[f]  stands for Fixed value\n");
            sb.append("[nf] stands for Not-Fixed value\n\n");
            for (int row = 0; row < ROW_LENTGH; row++) {
                for (int col = 0; col < COL_LENTGH; col++) {
                    for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                        for (int colSubMatrix = 0; colSubMatrix < COL_LENTGH; colSubMatrix++) {
                            if (sudokuUserInputsM[row][rowSubMatrix][col][colSubMatrix]) {
                                sb.append(String.format("%3s ", "f"));
                                // sb.append(String.format("%3b ", sudokuUserInputsM[row][rowSubMatrix][col][colSubMatrix]));
                            } else {
                                sb.append(String.format("%3s ", "nf"));
                            }
                        }
                        sb.append(String.format("%2s ", " "));
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error while reading User sudoku matrix: " + e.getMessage());
        }
        sb.append("------------------------------------------");
        return sb.toString();
    }
}
