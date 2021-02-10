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

/**
 * FXML Controller class for Sudoku view
 *
 * @author LuisDAM
 */
public class sudokuController implements Initializable {

    //Per mostrar errors o altres
    private PopUpMSG popUpMsg;

    private int SUDOKU_PANE_WIDTH = 854;
    private int SUDOKU_PANE_HEIGHT = 459;

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

        //TODO : Pass all this sudoku view maker into a class which will be called 'SudokuViewMaker'
        //To separate each record (text-field) I decied to set a constant width and height
        //from the SudokuView.fxml (view) sudokuPane object.
        double lenTxt = (SUDOKU_PANE_WIDTH / 9); //Width / number of columns
        double lenY = (SUDOKU_PANE_HEIGHT / 9); // Width / number of rows
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {
                TextField txtF = new TextField("" + 9);
                System.out.println(lenTxt * i);
                txtF.setLayoutX(lenTxt * i);
                txtF.setLayoutY(lenY * j);
                txtF.setPrefWidth(30);
                sudokuPane.getChildren().add(txtF);
            }

        }

        System.out.println("x lentgh: " + sudokuPane.widthProperty().get());
        System.out.println("HEY!");
        SudokuResolver sr = new SudokuResolver();
        System.out.println(sr.getSudokuState());

    }

}
