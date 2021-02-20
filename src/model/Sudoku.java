package model;

/**
 * It creates a matrix for a Sudoku, so we can thing as a Sudoku data structure.
 * We can define the rows and columns length o just setting as default Sudoku
 * which has 9x9 structure.
 *
 * As an abstract class it cannot be instantiated but for this project this one
 * works a "data-structure" for its SudokuResolver inherited class.
 *
 * @see model.SudokuResolver
 * @author LuisDAM
 */
public abstract class Sudoku {

    private final int ROW_LENTGH;
    private final int COL_LENTGH;

    private int sudokuM[][];
    private int complexSudokuM[][][][];
    private boolean sudokuViewM[][];

    public Sudoku(int ROW_LENTGH, int COL_LENTGH, boolean simpleSudokuStructure) {
        this.ROW_LENTGH = ROW_LENTGH;
        this.COL_LENTGH = COL_LENTGH;
        if (simpleSudokuStructure) {
            initSudokuMatrix();
        } else {
            initComplexSudokuMatrix();
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

    public boolean[][] getSudokuViewM() {
        return sudokuViewM;
    }

    private void initComplexSudokuMatrix() {
        System.out.println("INIT COMPLEX");
        int l = 0;
        complexSudokuM = new int[ROW_LENTGH][COL_LENTGH][COL_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                //Creating a new 3x3 matrix  System.out.println("INIT COMPLEX");
                complexSudokuM[row][col] = new int[ROW_LENTGH][ROW_LENTGH];
                for (int rowSubMatrix = 0; rowSubMatrix < ROW_LENTGH; rowSubMatrix++) {
                    for (int colSubMatrix = 0; colSubMatrix < ROW_LENTGH; colSubMatrix++) {
                        //init the values within the submatrix
                        complexSudokuM[row][col][rowSubMatrix][colSubMatrix] = l;
                        l++;
                    }
                }
            }
        }
        System.out.printf("=>" + l);
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
}
