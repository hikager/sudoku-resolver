/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.stage.Stage;
import model.PopUpMSG;


/**
 * FXML Controller class
 *
 * I've tried to clean all the code from this class which was not from
 * controller. This makes me create as many classes as functions I see to manage
 * this. The reason why I do this is because this controller was with >800 lines
 * of code and it smells like an endless spaghetti here...
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
