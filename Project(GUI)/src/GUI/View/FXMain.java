/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.View;

import GUI.Controller.MenuFXMLController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author Albert Kyei
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            
            //Create the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            
            Parent root = FXMLLoader.load(getClass().getResource("GUI_FXML.fxml"));
            
            Scene scene = new Scene(root, 973, 614);
            
            primaryStage.setTitle("Galamsey");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
