/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Alert;

/**
 * Per separar més la capa de Model i conroller. D'aquesta forma podem
 * personalitzar tant com volem els pop-ups o msg per la app.
 *
 * @author LuisDAM
 *
 */
public class PopUpMSG {

    private String error;
    private String infoMSG;
    private String OKmsg;

    public void errorMSG() {

        Alert alert = new Alert(Alert.AlertType.ERROR); //Cal generar el constructor de classe Alert

        alert.setHeaderText(null); //No vull afegir cap capçalera, per això poso null

        alert.setTitle("Error"); //Poso un títol al pop up

        alert.setContentText(error); //Missatge que vull mostrar

        alert.showAndWait(); //Mostrar el pop up i esperar a que s'apreti aceptar
    }

    public void InfoMSG() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION); //Cal generar el constructor de classe Alert

        alert.setHeaderText(null); //No vull afegir cap capçalera, per això poso null

        alert.setTitle("Information"); //Poso un títol al pop up

        alert.setContentText(infoMSG); //Missatge que vull mostrar

        alert.showAndWait(); //Mostrar el pop up i esperar a que s'apreti aceptar
    }

    public void setinfoMSG(String infoMSG) {
        this.infoMSG = infoMSG;
    }

    public void setOKmsg(String OKmsg) {
        this.OKmsg = OKmsg;
    }

    public void setError(String error) {
        this.error = error;
    }

    public enum PopUpType {
        ERROR('e'), INFO('i'), OK('o');

        char value;

        PopUpType(char value) {
            this.value = value;
        }
    }
}
