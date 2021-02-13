/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Luis ML
 */
public class SudokuResolverSubMatrix implements Runnable {

    private int sudokuM[][];
    private boolean sudokuViewM[][];//if all matrix is True, then the solver ends because it solve all
    private Semaphore semaphore;

    public SudokuResolverSubMatrix(int sudokuM[][], boolean sudokuViewM[][], Semaphore semaphore) {
        this.sudokuM = sudokuM;
        this.sudokuViewM = sudokuViewM;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

    }

    private void testAddOnePerRow() {

    }

}
