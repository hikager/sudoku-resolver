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

    private final int MATRIX_ROW;
    private final int MATRIX_COL;

    /**
     * Random generator object for thread-safe usage (because of multithreatinh)
     */
    private int boundedRandomValue = ThreadLocalRandom.current().nextInt(1, 9);

    public SudokuResolverSubMatrix(int sudokuSubMatrix[][],
            boolean sudokuSubMatrixView[][],
            int MATRIX_ROW,
            int MATRIX_COL,
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
        this.MATRIX_ROW = MATRIX_ROW;
        this.MATRIX_COL = MATRIX_COL;
        this.LENGTH = sudokuSubMatrix.length;

    }

    public boolean canInsertAgain;

    @Override
    public void run() {
        while (running.get()) {
            try {
                System.out.println("len: " + semaphore.getQueueLength());
                semaphore.acquire();
                //Thread.sleep(1000);
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
            System.out.println("num=" + randomNum + ", subMRow=" + subMatrixRow + ", subMCol" + subMatrixCol);
            //Check if the number is valid in its position (in the sudoku)
            if (checkValidNumber(randomNum, subMatrixRow, subMatrixCol)) {
                System.out.println("valid!");
                System.out.printf("Randoms (@%s):: [%d,%d]\n", this.subMatrixName, subMatrixRow, subMatrixCol);
                if (!sudokuSubMatrixView[subMatrixRow][subMatrixCol]) {
                    sudokuSubMatrix[subMatrixRow][subMatrixCol] = randomNum;
                    canInsert = true;
                    canInsertAgain = false;
                    //System.out.println("SALE!!!");
                }
            } else {
                System.out.println("non-valid!");
            }

        }
    }

    public boolean checkValidNumber(int numberToCheck, int subRowRnd, int subColRnd) {
        boolean validNumber = true;
        for (int subRow = 0; subRow < LENGTH; subRow++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                return isValidNumberChecker(numberToCheck, subRow, subCol);
            }
        }
        return validNumber;
    }

    private boolean isValidNumberChecker(int numberToCheck, int rowSub, int colSub) {
        return checkOnRowsConstant(numberToCheck, MATRIX_ROW, rowSub)
                && checkOnColumnsConstant(numberToCheck, MATRIX_COL, colSub)
                && checkOnSubmatrix(numberToCheck, MATRIX_ROW, MATRIX_COL);
    }

    private boolean checkOnRowsConstant(int numberToCheck, int ctRow, int ctSubRow) {
        System.out.println("\nChecking on rows constant:\n");
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        for (int col = 0; col < LENGTH; col++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                int num = this.matrixSudoku[ctRow][col][ctSubRow][subCol];
                // if (num != 0) {
                if (num == numberToCheck) {
                    printCheckArray();
                    System.out.printf(
                            "Thread (%s) found repeated number: %d\n",
                            this.getSubMatrixName(), num);
                    System.out.printf(
                            "position: mat[%d][%d]-sub[%d][%d]\n",
                            ctRow, col, ctSubRow, subCol);
                    return noRepetitions = false;
                }
                System.out.println("adddN");
                checkedNumbers[colCount] = num;
                printCheckArray();
                ++colCount;
                //}

            }
        }
        return noRepetitions;
    }

    private boolean checkOnColumnsConstant(int numberToCheck, int ctCol, int ctSubCol) {
        System.out.println("\nChecking on columns constant:\n");
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        for (int row = 0; row < LENGTH; row++) {
            for (int subRow = 0; subRow < LENGTH; subRow++) {
                int num = this.matrixSudoku[row][ctCol][subRow][ctSubCol];
                //    if (num != 0) {
                if (num == numberToCheck) {
                    printCheckArray();
                    System.out.printf(
                            "Thread (%s) found repeated number: %d\n",
                            this.getSubMatrixName(), num);
                    System.out.printf(
                            "position: mat[%d][%d]-sub[%d][%d]\n",
                            row, ctCol, subRow, ctSubCol);
                    return noRepetitions = false;
                }
                checkedNumbers[colCount] = num;
                printCheckArray();
                ++colCount;
                //       }
            }
        }
        return noRepetitions;
    }

    private boolean checkOnSubmatrix(int numberToCheck, int ctRow, int ctCol) {
        System.out.printf("\nChecking submatrix [%d,%d] \n", ctRow, ctCol);
        boolean noRepetitions = true;
        resetCheckArray();
        int colCount = 0;
        for (int subRow = 0; subRow < LENGTH; subRow++) {
            for (int subCol = 0; subCol < LENGTH; subCol++) {
                int num = this.matrixSudoku[ctRow][ctCol][subRow][subCol];
                //   if (num != 0) {
                if (num == numberToCheck) {
                    printCheckArray();
                    System.out.printf(
                            "Thread (%s) found repeated number: %d\n",
                            this.getSubMatrixName(), num);
                    System.out.printf(
                            "position: mat[%d][%d]-sub[%d][%d]\n",
                            ctRow, ctCol, subRow, subCol);
                    return noRepetitions = false;
                }
                checkedNumbers[colCount] = num;
                printCheckArray();
                ++colCount;
                //     }
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
