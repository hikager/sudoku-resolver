/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis ML
 */
public class SudokuResolverSubMatrix implements Runnable {

    private final int LENGTH;
    /**
     * SubMatrix from the Sudoku
     *
     * @see model.Sudoku#complexSudokuM
     */
    private int sudokuSubMatrix[][];

    /**
     * SubMatrix from the Sudoku - sudokuUserInputsM
     *
     * @see model.Sudoku#sudokuUserInputsM
     */
    private boolean sudokuSubMatrixView[][];//if all matrix is True, then the solver ends because it solve all

    /**
     * Semaphore to control each thread while resolving a subMatrix
     *
     * @see model.SudokuResolver#semaphore
     */
    private Semaphore semaphore;

    /**
     * Boolean object for thread-safe usage
     */
    private AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Random generator object for thread-safe usage (because of multithreatinh)
     */
    private int boundedRandomValue = ThreadLocalRandom.current().nextInt(1, 9);

    public SudokuResolverSubMatrix(int sudokuSubMatrix[][], boolean sudokuSubMatrixView[][], Semaphore semaphore) {
        this.sudokuSubMatrix = sudokuSubMatrix;
        this.sudokuSubMatrixView = sudokuSubMatrixView;
        this.semaphore = semaphore;

        LENGTH = sudokuSubMatrix.length;
    }

    @Override
    public void run() {
        while (running.get()) {
            try {
                semaphore.acquire();
                addingNumber();
                semaphore.release();
            } catch (InterruptedException ex) {
                System.out.println("SudokuResolverSubMatrix.class - run()");
                Logger.getLogger(SudokuResolverSubMatrix.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void addingNumber() {
        boolean canInsert = false;
        while (!canInsert) {
            int subMatrixRow = ThreadLocalRandom.current().nextInt(0, 2);
            int subMatrixCol = ThreadLocalRandom.current().nextInt(0, 2);
            System.out.printf("Randoms: [%d,%d]\n", subMatrixRow, subMatrixCol);
            if (!sudokuSubMatrixView[subMatrixRow][subMatrixCol]) {
                sudokuSubMatrix[subMatrixRow][subMatrixCol] = boundedRandomValue;
                canInsert = true;
            }
        }
    }

    public synchronized void stop() {
        this.running.set(false);
    }

}
