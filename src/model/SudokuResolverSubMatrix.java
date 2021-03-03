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
public class SudokuResolverSubMatrix extends Thread {

    /**
     * Array for storing valid numbers while checking the Sudoku
     */
    private int checkedNumbers[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    /**
     * Amount of wrong numbers in the subMatrix from the point of view of the
     * Main Matrix (Sudoku)
     */
    private int collisions;

    private String subMatrixName;

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

    private AtomicBoolean insertAgain = new AtomicBoolean(true);

    private int matrixSudoku[][][][];

    private final int MatrixRow;
    private final int MatrixCol;

    /**
     * Random generator object for thread-safe usage (because of multithreatinh)
     */
    private int boundedRandomValue = ThreadLocalRandom.current().nextInt(1, 9);

    public SudokuResolverSubMatrix(int sudokuSubMatrix[][],
            boolean sudokuSubMatrixView[][],
            int MatrixRow,
            int MatrixCol,
            Semaphore semaphore,
            String subMatrixName,
            AtomicBoolean insertAgain,
            int[][][][] matrixSudoku) {
        this.sudokuSubMatrix = sudokuSubMatrix;
        this.sudokuSubMatrixView = sudokuSubMatrixView;
        this.semaphore = semaphore;
        this.subMatrixName = subMatrixName;
        this.insertAgain = insertAgain;
        this.matrixSudoku = matrixSudoku;
        this.MatrixCol = MatrixCol;
        this.MatrixRow = MatrixRow;
        this.LENGTH = sudokuSubMatrix.length;

    }

    public boolean canInsertAgain;

    @Override
    public void run() {
        while (running.get()) {
            try {
                System.out.println("len: " + semaphore.getQueueLength());
                semaphore.acquire();
                Thread.sleep(1000);
                addingNumber();

                // while (!canInsertAgain) {
                //This removes a bug I cannot solve yet
                semaphore.release();

                //}
                // System.out.println(this.subMatrixName + " -  is free");
            } catch (InterruptedException ex) {
                System.out.println("SudokuResolverSubMatrix.class - run()");
                Logger.getLogger(SudokuResolverSubMatrix.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void addingNumber() {

        boolean canInsert = false;
        while (!canInsert) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
            //If it's valid the number, insert it

            int subMatrixRow = ThreadLocalRandom.current().nextInt(0, 3);
            int subMatrixCol = ThreadLocalRandom.current().nextInt(0, 3);

            //Check if the number is valid in its position (in the sudoku)
            checkValidNumber(randomNum, subMatrixRow, subMatrixCol);

            System.out.printf("Randoms (@%s):: [%d,%d]\n", this.subMatrixName, subMatrixRow, subMatrixCol);
            if (!sudokuSubMatrixView[subMatrixRow][subMatrixCol]) {
                sudokuSubMatrix[subMatrixRow][subMatrixCol] = randomNum;
                canInsert = true;
                canInsertAgain = false;
                //System.out.println("SALE!!!");
            }

        }
    }

    public boolean checkValidNumber(int numberToCheck, int subRowRnd, int subColRnd) {

        return true;
    }

    private boolean isValidNumberChecker(int row, int col, int rowSub, int colSub) {
        return checkOnRowsConstant(row, rowSub)
                && checkOnColumnsConstant(col, colSub)
                && checkOnSubmatrix(row, col);
    }

    private boolean checkOnRowsConstant(int ctRow, int ctSubRow) {

        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        for (int col = 0; col < LENGTH; col++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                int num = this.matrixSudoku[ctRow][col][ctSubRow][subCol];
                if (isRepeated(num)) {
                    printCheckArray();
                    System.out.printf("Thread (%s) found repeated number: %d\n", this.getSubMatrixName(), num);
                    System.out.printf("position: mat[%d][%d]-sub[%d][%d]\n", ctRow, col, ctSubRow, subCol);
                    return noRepetitions = false;
                }

                checkedNumbers[colCount] = num;
                printCheckArray();
                ++colCount;
            }
        }
        return noRepetitions;
    }

    /**
     * It shows up pretty the array-state for each Sudoku's field check
     */
    private void printCheckArray() {
        int totalLen = LENGTH * LENGTH;
        System.out.printf("-Array-Cheking-State: [");
        for (int i = 0; i < totalLen; i++) {
            if (i < totalLen - 1) {
                System.out.printf("%d, ", this.checkedNumbers[i]);
            } else {
                System.out.printf("%d] ", this.checkedNumbers[i]);
            }
        }
        System.out.println("");
    }

    private boolean checkOnColumnsConstant(int ctCol, int ctSubCol) {
        resetCheckArray();
        return true;
    }

    private boolean checkOnSubmatrix(int ctRow, int ctCol) {
        System.out.printf("\nChecking submatrix [%d,%d] \n", ctRow, ctCol);
        resetCheckArray();
        return true;
    }

    private boolean isRepeated(int number) {
        boolean isRepeated = false;
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            if (checkedNumbers[i] == number) {
                System.out.println("rep!: " + number);
                return isRepeated = true;
            }
        }
        return isRepeated;
    }

    /**
     * Reset the checking array to 0.
     *
     * Why: Because when a checking field is ended and start another, we need to
     * clean the numbers used previously.
     */
    private void resetCheckArray() {
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            this.checkedNumbers[i] = 0;
        }
    }

    public String getSubMatrixName() {
        return subMatrixName;
    }

    public void setSubMatrixName(String subMatrixName) {
        this.subMatrixName = subMatrixName;
    }

    public int[][] getSudokuSubMatrix() {
        return sudokuSubMatrix;
    }

    public void setSudokuSubMatrix(int[][] sudokuSubMatrix) {
        this.sudokuSubMatrix = sudokuSubMatrix;
    }

    public boolean[][] getSudokuSubMatrixView() {
        return sudokuSubMatrixView;
    }

    public void setSudokuSubMatrixView(boolean[][] sudokuSubMatrixView) {
        this.sudokuSubMatrixView = sudokuSubMatrixView;
    }

    public int getCollisions() {
        return collisions;
    }

}
