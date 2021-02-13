/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data_structure;

/**
 * A data-structure made for managing our Sudoku resolver, which needs to get
 * each 3x3 sub-matrix from the Sudoku (divide and conquer) .
 *
 * @author Luis ML
 */
public class SubMatrixSelection {

    /**
     * The number of sum-matrices it results from the splitting the matrix
     */
    private int numberOfSubMatrices;

    /**
     * Number of rows which has each sub-matrix
     */
    private int subMatrixRows;

    /**
     * Number of columns which has each sub-matrix
     */
    private int subMatrixCols;

    /**
     * Matrix to be divided in subMatrices
     */
    private int matrix[][];

    /**
     * Array of sub-matrices 3x3 (or multidimensional array)
     *
     * From 0 to 8 as we read a Sudoku (from left to right and following the
     * next row)
     */
    private int[][][] subMatrixArray;

    /**
     * It builds the data-structure
     *
     * @param matrix - matrix to be segmented into sub-matrices
     */
    public SubMatrixSelection(int matrix[][]) {
        this.matrix = matrix;

        if (matrix != null) {
            subMatrixRows = matrix.length;
            subMatrixCols = matrix[0].length;
            //It sets [index-array] [rows submatrix] [columns submatrix]
            if (subMatrixRows == subMatrixCols) {
                numberOfSubMatrices = subMatrixRows; //As rows==columns no matter which we pick
                subMatrixArray = new int[numberOfSubMatrices][subMatrixRows / 3][subMatrixCols / 3];
            } else {
                System.out.println("Your matrix rows number are different to columns!!! I need they're same");
            }
        } else {
            System.out.println("I need a  not null matrix to make your division of matrices :0");
        }

    }

    public void fillArraySubMatrices2() {
        for (int subMatrixIndex = 0; subMatrixIndex < this.numberOfSubMatrices; subMatrixIndex++) {
            for (int subMatrixRow = 0; subMatrixRow < this.subMatrixRows; subMatrixRow++) {
                for (int subMatrixCol = 0; subMatrixCol < this.subMatrixCols; subMatrixCol++) {
                    //subMatrixArray[subMatrixIndex][subMatrixRow][subMatrixCol] = this.matrix[][]
                }
            }
        }
    }

    private void fillArraySubMatrices() {
        int matrixRows = matrix.length;
        int matrixCols = matrix[0].length;
        int matrixXcount = 0;
        int matrixYcount = 0;
        int arrayCount = 0;
        
     
        
        for (int i = 0; i < matrixCols; i++) {
            if (matrixXcount == 2) {
                matrixXcount = 0;
            }
            for (int j = 0; j < matrixCols; j++) {

                if (matrixYcount == 2) {
                    ++arrayCount;
                    j -= 2;
                }

            }

            ++matrixXcount;

        }

    }

    public void showSubMatrices() {
        for (int subMatrixIndex = 0; subMatrixIndex < this.numberOfSubMatrices; subMatrixIndex++) {
            System.out.println("Sub-Matrix: " + subMatrixIndex + "\n");
            for (int subMatrixRow = 0; subMatrixRow < this.subMatrixRows; subMatrixRow++) {
                for (int subMatrixCol = 0; subMatrixCol < this.subMatrixCols; subMatrixCol++) {
                    System.out.printf("%d ", subMatrixArray[subMatrixIndex][subMatrixRow][subMatrixCol]);
                }
                System.out.println("");
            }
        }
    }

}
