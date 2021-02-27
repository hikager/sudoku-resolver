package controller;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @see
 * <a href="https://github.com/hikager/sudoku-resolver/commits?author=hikager">Registre
 * de commits</a>
 * 
 * @author Luis ML
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            // primaryStage.initStyle(StageStyle.UNDECORATED);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/SudokuView.fxml"));
            Pane ventana = (Pane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(ventana);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
