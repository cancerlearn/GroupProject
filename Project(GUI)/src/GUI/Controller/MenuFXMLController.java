/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import foundation.classes.Galamsey;
import GUI.Model.MonitoringGUI_Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class for menu
 *
 * @author Albert Kyei
 */
public class MenuFXMLController implements Initializable {
    
    @FXML
    private GridPane obsPane, galPane;
    
    @FXML
    private JFXTabPane statsPane;
    
    @FXML
    private Label vegColorTxt;
    
    @FXML
    private JFXButton obsData, galData, stats, addGal_btn, addObs_btn;
    
    @FXML
    private JFXTextField pos_Latitude, pos_Longitude, yearField, galObsName, 
            obsNameField, obsCountryField, obsYearField, obsAreaField;
    
    @FXML
    private ChoiceBox<String> galObsName_CB;
    
    @FXML
    private JFXRadioButton cv_ONE, cv_TWO, cv_THREE;
    
    /**
     * Model object needed for controller.
     */
    MonitoringGUI_Model guiModel = new MonitoringGUI_Model();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setChoiceBox();
        
    }
    
    /**
     * This method creates an alert box with the needed information taken as parameters.
     * 
     * @param title
     * @param header
     * @param context 
     */
    private void callAlert(String title, String header, String context){
        
        Alert alert = new Alert(AlertType.INFORMATION);
        
        alert.setTitle(title);
        
        alert.setHeaderText(header);
        
        alert.setContentText(context);
 
        alert.showAndWait();
    }
    
    @FXML
    private void controlMenuButtonAction(ActionEvent ae){
        
        if (obsData == ae.getSource()){
            obsPane.toFront();
        }
        else if (galData == ae.getSource()){
            galPane.toFront();
        }
        else if (stats == ae.getSource()){
            statsPane.toFront();
        }
        
    }
    
    @FXML
    private void controlRadioButtons(ActionEvent ae){
        
        if (cv_ONE == ae.getSource() && !cv_TWO.isSelected() && !cv_THREE.isSelected()){
            
            vegColorTxt.setText("Vegetation color is green.");        //Set text showing corresponding vegetation color
                
            vegColorTxt.setTextFill(Color.web("#000000"));          //Set color of text showing corresponding vegetation color
            
        }
        
        else if (!cv_ONE.isSelected() && cv_TWO == ae.getSource() && !cv_THREE.isSelected()){
            
            vegColorTxt.setText("Vegetation color is yellow.");
                
            vegColorTxt.setTextFill(Color.web("#000000"));
            
        }
        
        else if (!cv_ONE.isSelected() && !cv_TWO.isSelected() && cv_THREE == ae.getSource()){
            
            vegColorTxt.setText("Vegetation color is brown.");
                
            vegColorTxt.setTextFill(Color.web("#000000"));
            
        }
        else {
            
            vegColorTxt.setText("Color of vegetation due to color-value assigned.");
                
            vegColorTxt.setTextFill(Color.web("grey"));
            
        }
        
    }
    
    @FXML
    private void controlObservatoryButtonAction(ActionEvent ae){
        
        String obsName = null;
        String obsCountry = null;
        String obsYear = null;
        Double obsArea = null;
        
        if (addObs_btn == ae.getSource()){
            
            boolean emptyFields = (obsNameField.getText() == null || obsNameField.getText().trim().isEmpty()) || 
                    (obsCountryField.getText() == null || obsCountryField.getText().trim().isEmpty()) || 
                    (obsYearField.getText() == null || obsYearField.getText().trim().isEmpty()) || 
                    (obsAreaField.getText() == null || obsAreaField.getText().trim().isEmpty());
            
            System.out.println("emptyFields: "+emptyFields);
            
            if (emptyFields){
                callAlert("Input Error", "Info: ", "One or more empty fields.");
            }
            
            //Handling year input
            if (Galamsey.validYear(obsYearField.getText())) obsYear = obsYearField.getText();
                
            else{
                callAlert("Input Error!", "Info: ", "Enter a valid year!");
                return;
            }
            //Verifying area given for observatory
            try{
               obsArea = Double.parseDouble(obsAreaField.getText());
            } catch (NumberFormatException e){
                return;
            }
            
            obsName = obsNameField.getText();
            obsCountry = obsCountryField.getText();
            
            if (guiModel.enterObservatoryData(obsName, obsCountry, obsYear, obsArea)){
                
                callAlert("Succesful Input", "Info", "New Observatory "+obsNameField.getText()+" added.");
                
                obsNameField.setText(""); obsCountryField.setText(""); obsYearField.setText(""); obsAreaField.setText("");
            }
        }
    }
    
    @FXML
    private void controlAddGalamseyButtonAction(ActionEvent ae){
        
        String colorValue = null;
        String vegColor = null;
        double latitude;
        double longitude;
        String year;
        
        if (addGal_btn == ae.getSource()){
            
            //Handling selection of color value
            if (cv_ONE.isSelected() && !cv_TWO.isSelected() && !cv_THREE.isSelected()){
                
                //Set colorValue
                colorValue = "ONE";
                
                vegColor = "Green";
            }
            else if (!cv_ONE.isSelected() && cv_TWO.isSelected() && !cv_THREE.isSelected()){
                
                colorValue = "TWO";
                
                vegColor = "Yellow";
            
            }
            else if (!cv_ONE.isSelected() && !cv_TWO.isSelected() && cv_THREE.isSelected()){
                
                colorValue = "THREE";
                
                vegColor = "Brown";
            
            }
            else{
                
                callAlert("Input Error!", "Info:", "Select one ColorValue only!");
                
                return;
            
            }
            
            //Handling latitude and longitude input
            try {
                
                latitude = Double.parseDouble(pos_Latitude.getText());
                
                longitude = Double.parseDouble(pos_Longitude.getText());
            
            } catch(NumberFormatException e){
                
                callAlert("Input Error!", "Info: ", "Enter a valid angle!");
                
                return;
            
            }
            
            //Handling year input
            if (Galamsey.validYear(yearField.getText())) year = yearField.getText();
                
            else {
                callAlert("Input Error!", "Info: ", "Enter a valid year!");
                return;
            }
            
            //Handling observatory name input
            if (!galObsName_CB.getSelectionModel().isEmpty()){
                
                if (guiModel.enterGalamseyData(colorValue, vegColor, latitude, longitude, year, galObsName_CB.getValue()))
                    callAlert("Succesful Input", "Info", "New Galamsey added to "+galObsName_CB.getValue());
                
            }
            
            
//            if (guiModel.verifyObservatory(galObsName.getText())) {
//                
//                if (guiModel.enterGalamseyData(colorValue, vegColor, latitude, longitude, year, galObsName.getText())) 
//                    callAlert("Succesful Input", "Info", "New Galamsey added to "+galObsName.getText());
//                
//            }
//            else {
//                callAlert("Input Error!", "Info: ", "Observatory does not exit. Enter a valid observatory name.");
//            }
        }
        
    }
    
    @FXML
    private void setChoiceBox(){
        
        ObservableList<String> cursors = FXCollections.observableArrayList(guiModel.getObservatories());
        
        galObsName_CB.setItems(cursors);
        
    }
    
}
