package group.project;

import java.util.ArrayList;

/**
 * @author Group 16
 * @version 1.0
 * 
 */
public class Monitoring implements java.io.Serializable{
    /**
     * Identification used when deserializing to a Monitoring object to confirm the class
     */
    private static final long serialVersionUID = -571008035669649445L;


    //Instance variables
    /**
     * This field stores a reference to a list of all observatories
     */
    private ArrayList<Observatory> observatories = new ArrayList<Observatory>();
    
    //Additional methods to add and remove observatories from Monitoring class
    /**
	 * @param observatories the observatories to set/add
	 */
	public void addObservatory(Observatory observatory) {
		observatories.add(observatory);
	}
	
	/**
	 * This method replaces observatories in the ArrayList of observatories and returns the old object
	 * 
	 * @param obs
	 * @return
	 */
	public Observatory replaceObservatory(Observatory obs) {
		
		Observatory oldObservatory = null;
		
		if(observatories.contains(obs)) {
			//Old observatory is stored
			oldObservatory = observatories.get(observatories.indexOf(obs));
			//Observatory is replaced
			observatories.set(observatories.indexOf(obs), obs);
		}
		
		return oldObservatory;
	}
	
	//Getter & Setter
	/**
	 * @return the observatories
	 */
	public ArrayList<Observatory> getObservatories() {
		return this.observatories;
	}

	/**
	 * @param observatories the observatories to set
	 */
	public void setObservatories(ArrayList<Observatory> observatories) {
		this.observatories = observatories;
	}
	
	

	//General class methods
	/**
     * This method returns the observatory with the largest average color value of any of the observatories monitored as a double
     *
     * @return observatory with highest average color value (CV)
     */
    public Observatory getObsevatoryWithHighestAverageCV(){
        //return null if no observatories have been stored
        if (observatories.isEmpty())
            return null;

        //define temporary variable to store current largest
        Observatory highestAverageGalamseyCV = observatories.get(0);     //set to first observation

        for (Observatory currentInstance : observatories){
            //set instance as current highest if it has an higher average
            //color value
            if (currentInstance.getAverageGalamseyColor() > highestAverageGalamseyCV.getAverageGalamseyColor())
                highestAverageGalamseyCV = currentInstance;
        }
        return highestAverageGalamseyCV;
    }

    /**
     *
     * @return the highest galamsey color value of all recorded galamsey occurrences (across observatories)
     */
    public int getLargestGalamseyColor(){
        int currentLargestGalamseyColor = 0;            //temporary value

        for (Observatory currentObservatory : observatories){
            if (currentObservatory.getLargestGalamseyColor() > currentLargestGalamseyColor)
                currentLargestGalamseyColor = currentObservatory.getLargestGalamseyColor();
        }
        return currentLargestGalamseyColor;
    }


    /**
     * This method returns an iterable list that contains all recorded galamsey occurrences that have a
     * color value greater than a specified base value
     * @param baseColorValue is the base value to be used to determine the galamsey occurrences to add to search result
     * @return search result with galamsey occurrences as an iterable object
     */
    public ArrayList<Galamsey> getGalamseyWithColValOver(int baseColorValue){
        ArrayList<Galamsey> searchResult = new ArrayList<>();           //container to fill up
        //loop through all recorded Observatories
        for (Observatory currentObservatory : observatories){
            //for every observatory, get all galamsey with higher color values and add to search result
            searchResult.addAll(currentObservatory.getGalamseyWithColValOver(baseColorValue));
        }
        return searchResult;
    }
}
