/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.PopUpMSG;
import model.SudokuResolver;
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

    @FXML
    private AnchorPane MainPain;

    @FXML
    private AnchorPane sudokuPane;

    /**
     *
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Creating a matrix of text-boxes
        this.viewSudoku = new ViewSudoku(sudokuPane);
        this.viewSudoku.initTextBoxesMatrix();

        SudokuResolver sr = new SudokuResolver();
        System.out.println(sr.getSudokuState());
        
        

    }

}
