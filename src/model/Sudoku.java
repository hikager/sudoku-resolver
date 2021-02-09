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
public class Sudoku {

    private final int ROW_LENTGH = 9;
    private final int COL_LENTGH = 9;

    private int sudokuM[][];
    private boolean sudokuViewM[][];

    public Sudoku() {
        sudokuM = new int[ROW_LENTGH][COL_LENTGH];
        sudokuViewM = new boolean[ROW_LENTGH][COL_LENTGH];
    }

    private void initSudokuMatrix() {
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                sudokuM[row][col] = 0;
            }
        }
    }

    private void initSudokuViewMatrix() {
        for (int row = 0; row < ROW_LENTGH; row++) {
            for (int col = 0; col < COL_LENTGH; col++) {
                sudokuViewM[row][col] = false;
            }
        }
    }

}
