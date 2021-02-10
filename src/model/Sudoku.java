/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LuisDAM
 */
public abstract class Sudoku  {

    private final int ROW_LENTGH;
    private final int COL_LENTGH;

    private int sudokuM[][];
    private boolean sudokuViewM[][];

    public Sudoku(int ROW_LENTGH, int COL_LENTGH) {
        this.ROW_LENTGH = ROW_LENTGH;
        this.COL_LENTGH = COL_LENTGH;
    }

    public Sudoku() {
        this.ROW_LENTGH = 9;
        this.COL_LENTGH = 9;
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
    
   

}
