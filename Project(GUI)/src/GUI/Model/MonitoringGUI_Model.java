/**
 * 
 */
package GUI.Model;
import foundation.classes.Observatory;
import foundation.classes.Galamsey;
import foundation.classes.Monitoring;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main model class used in this project.
 * It utilizes the database object to fulfill its purpose as the Model class.
 * 
 * @author Group 16
 * @version 1.0
 */
public class MonitoringGUI_Model {
	
	Database d1 = new Database();
        
        Monitoring m1 = null;
        
        /**
         * Constructor for MonitoringGUI_Model class
         */
        public MonitoringGUI_Model(){
            
            //DB connection is created
            d1.initializeDBConnection();
            
            //Stored data is converted into corresponding objects for easier data manipulation
            Database.loadStoredData();
            
            //Monitoring object is obtained from database class
            m1 = d1.getMonitoring();
            
        }
        
	/**
	 * This method handles all user interactions when creating an observatory.
	 */
	public boolean enterObservatoryData(String obsName, String obsCountry, String obsYear, double obsArea) {
            
            //New observatory is created
            Observatory o1 = new Observatory(obsName, obsCountry, obsYear, obsArea);
            
            //Observatory is now added to monitoring class
            m1.addObservatory(o1);

            //Returning boolean value determining success of insertion into database
            return d1.executeObservatoryInsertion(obsName, obsCountry, obsYear, obsArea);
		
	}
	
	/**
	 * This method stores a new galamsey event in the database
         * 
         * @param colorValue
         * @param vegColor
         * @param latitude
         * @param longitude
         * @param year
         * @param obsName
	 */
	public boolean enterGalamseyData(String colorValue, String vegColor, double latitude, double longitude, String year, String obsName) {
            
            Integer obsID = null;
            
            ResultSet getObservatory = d1.executeQuery("SELECT * FROM observatory");
            
            try {
                
                //Iterate through observatories in database
                while (getObservatory.next()) {                        
                    //If observatory name exists, find observatoryID
                    if (getObservatory.getString("Name").equals(obsName)) obsID = getObservatory.getInt("ObservatoryID");
                                
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(MonitoringGUI_Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Create galamsey object to insert into the right observatory in monitoring object
            Galamsey g1 = new Galamsey(colorValue, latitude, longitude, year);
            
            //Iterate through observatories in monitoring to find the right observatory
            for (Observatory obs: m1.getObservatories()){
                
                //If observatory found
                if (obs.getObservatoryName().equals(obsName)) {
                    
                    //boolean value determining success of insertion into database
                    boolean dbInsertionSuccess = d1.executeGalamseyInsertion(colorValue, vegColor, latitude, longitude, year, obsID);
                    
                    //If database insertion is valid, add new galamsey event to the right observatory
                    if (dbInsertionSuccess) {
                        
                        obs.recordGalamsey(g1);
                        
                        return true;
                    }
                
                }
            
            }
            
            return false;
            
	}
	
	public boolean verifyObservatory(String observatoryName) {
            
            for (Observatory obs: m1.getObservatories()){
                        
                if (obs.getObservatoryName().equals(observatoryName)) return true;
            }
            return false;
        }
        
        /**
         * Return ArrayList of names of all observatories
         * 
         * @return 
         */
        public ArrayList<String> getObservatories(){
            
            ArrayList<String> obsNames = new ArrayList<String>();
            
            for (Observatory obs: m1.getObservatories()){
                        
                obsNames.add(obs.getObservatoryName());
            }
            return obsNames;
            
        }
}
