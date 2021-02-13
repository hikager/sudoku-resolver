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
    private boolean sudokuViewM[][];

    public Sudoku(int ROW_LENTGH, int COL_LENTGH) {
        this.ROW_LENTGH = ROW_LENTGH;
        this.COL_LENTGH = COL_LENTGH;
        initSudoku();
    }

    public Sudoku() {
        this.ROW_LENTGH = 9;
        this.COL_LENTGH = 9;
        initSudoku();
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

    private void initSudoku() {
        initSudokuMatrix();
        initSudokuViewMatrix();
    }

    private void initSudokuMatrix() {
        sudokuM = new int[ROW_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                sudokuM[row][col] = 0;
            }
        }
    }

    private void initSudokuViewMatrix() {
        sudokuViewM = new boolean[ROW_LENTGH][COL_LENTGH];
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                sudokuViewM[row][col] = false;
            }
        }
    }

    public String getSudokuState() {
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
}
