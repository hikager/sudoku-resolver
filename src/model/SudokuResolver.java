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
public class SudokuResolver extends Sudoku {

    private int sudokuM[][];
    private boolean sudokuViewM[][];

    public SudokuResolver(int rowLen, int colLen) {
        super(rowLen, colLen);
    }

    public SudokuResolver() {
        super();
        sudokuM = super.getSudokuM();
        sudokuViewM = super.getSudokuViewM();
    }
}
