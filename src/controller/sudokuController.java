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
import model.PopUpMSG;

/**
 * FXML Controller class for Sudoku view
 *
 * @author LuisDAM
 */
public class sudokuController implements Initializable {

    //Per mostrar errors o altres
    private PopUpMSG popUpMsg;

    @FXML
    private Button btnCloseAll;

    /**
     *
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("HEY!");
    }

}
