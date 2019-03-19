/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class for menu
 *
 * @author Albert Kyei
 */
public class MenuFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private GridPane obsPane, galPane;
    
    @FXML
    private JFXTabPane statsPane;
    
    @FXML
    private JFXButton obsData, galData, stats, addGal_btn;
    
    @FXML
    private JFXRadioButton cv_ONE, cv_TWO, cv_THREE; 
    
    @FXML
    private void controlButtonAction(ActionEvent ae){
        
        if (obsData == ae.getSource()){
            obsPane.toFront();
        }
        else if (galData == ae.getSource()){
            galPane.toFront();
        }
        else if (stats == ae.getSource()){
            statsPane.toFront();
        }
        else if (addGal_btn == ae.getSource()){
            
            if (cv_ONE.isSelected() && !cv_TWO.isSelected() && !cv_THREE.isSelected()){
                
            }
            else if (!cv_ONE.isSelected() && cv_TWO.isSelected() && !cv_THREE.isSelected()){
                
            }
            else if (!cv_ONE.isSelected() && !cv_TWO.isSelected() && cv_THREE.isSelected()){
                
            }
            else{
                
            }
        }
        
    }
    
}
