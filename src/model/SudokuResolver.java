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
