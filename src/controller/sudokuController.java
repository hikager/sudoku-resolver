/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.PopUpMSG;
import model.SudokuResolver;
import model.viewsudoku.MatrixView;
import model.viewsudoku.ViewSudoku;

/**
 * FXML Controller class for Sudoku view
 *
 * @author LuisDAM
 */
public class sudokuController implements Initializable {

    //Per mostrar errors o altres
    private PopUpMSG popUpMsg;

    private ViewSudoku viewSudoku;

    private SudokuResolver sudokuResolver;

    @FXML
    private AnchorPane MainPain;

    @FXML
    private AnchorPane sudokuPane;
    @FXML
    private Button btnResolve;

    /**
     *
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Creating a matrix of text-boxes

        //MatrixView matrixView = new MatrixView(sudokuPane);
        // matrixView.initTextBoxesMatrix();
        //this.viewSudoku = new ViewSudoku(sudokuPane);
        //this.viewSudoku.initTextBoxesMatrix();
        sudokuResolver = new SudokuResolver(new Semaphore(1), sudokuPane);
        System.out.println(sudokuResolver.getSimpleSudokuState());
        System.out.println(sudokuResolver.getComplexSudokuState());
        System.out.println(sudokuResolver.getComplexSudokuStateFormatted());
        System.out.println(sudokuResolver.getUserSudokuInputMatrixState());

    }

    @FXML
    private void onResolve(MouseEvent event) {
       sudokuResolver.startResolving();
    }

}
