package group.project;

//imports 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Group 16
 * @version 1.0
 *
 * This class is a model of an Observatory that keep track of occurrences of Galamsey(Illegal mining).
 *
 * It holds details such as the name, date of commencement, all observed and recorded galamsey, as well
 * as some statistical information about the work of the observatory
 */
public class Observatory implements java.io.Serializable{
    /**
     * Identification used when deserializing to a Observatory object to confirm the class
     */
    private static final long serialVersionUID = 7978364738809170861L;

	//Definition of Instance Variables
    /**
     * This field stores the name of the observatory
     */
    private String observatoryName;

    /**
     * This field stores the name of the country in which the observatory is located
     */
    private String countryName;

    /**
     * This field store the year the work of the observatory was started.
     *
     * Note: The year value is store as a string and not a number. Type convertions should
     * be performed when necessary.
     */
    private String yearOfCommencement;

    /**
     * This is the area in square kilometers covered by the observatory
     */
    private double areaCoveredByObservatory;

    /**
     * This field stores a reference to the list of all galamsey(illegal mining) occurences
     *  that have been recorded by this observatory
     */
    private ArrayList<Galamsey> recordedEvents;

    //Statistical variables
    /**
     * This field stores the largest color value that has been recorded by the observatory
     */
    private int largestColorValue = 0;

    /**
     * This field stores the average colour value of all recorded galamsey(illegal mining) occurences
     * recorded by the observatory
     */
    private double averageColourValue = 0;


    //Constructor

    /**
     * Primary Constructor
     *
     * @param observatoryName   This is the name of the observatory
     * @param countryName   This is the country in which the observatory is located
     * @param yearOfCommencement    This is the year the observatory started work. It is stored as a string
     * @param areaCoveredByObservatory  This is the total area the observatory covers
     *
     * @throws IllegalArgumentException     This exception is thrown if a user specified an invalid string argument
     * as the date the observatory's work begun
     */
    public Observatory(String observatoryName, String countryName, String yearOfCommencement,
                       double areaCoveredByObservatory) throws IllegalArgumentException{
        //Throw Exception if the yearOfCommencement parsed is illegal
        if (!validYear(yearOfCommencement))
            throw new IllegalArgumentException("Sorry. An invalid year was specified. Try again!");

        this.observatoryName = observatoryName;
        this.countryName = countryName;
        this.yearOfCommencement = yearOfCommencement;
        this.areaCoveredByObservatory = areaCoveredByObservatory;
    }
	
	//Getters

    /**
     * @return the name of the observatory
     */
	public String getObservatoryName() {
		return observatoryName;
	}

    /**
     *
     * @return the name of the country the observary is located in
     */
    public String getCountryName() {
		return countryName;
	}

    /**
     *
     * @return the year the work of the observertory begun/commenced
     */
    public String getYearOfCommencement() {
		return yearOfCommencement;
	}

	public double getAreaCoveredByObservatory() {
		return areaCoveredByObservatory;
	}

	public ArrayList<Galamsey> getRecordedEvents() {
		return recordedEvents;
	}

	//Setters

    /**
     * This method replaces the existing observatory name with a specified new name
     *
     * @param observatoryName this is the new name of the observatory
     */
    public void setObservatoryName(String observatoryName) {
        this.observatoryName = observatoryName;
    }

    /**
     * This method replaces the existing country name with a specified new name
     *
     * @param countryName this is the new country name of the observatory
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    /**
     * This method update the size of the area covered by the observatory with a specified new size.
     * The area is measure in squared kilometers.
     *
     * @param areaCoveredByObservatory new area covered by observatory in square kilometers
     */
    public void setAreaCoveredByObservatory(double areaCoveredByObservatory) {
        this.areaCoveredByObservatory = areaCoveredByObservatory;
    }


	//CLASS METHODS

    /**
     *
     * @return the larges color value recorded for all observed galamsey(illegal mining) occurrences
     */
    public int getLargestGalamseyColor(){return largestColorValue;}

    /**
     *
     * @return the average color value for all galamsey occurrences recored by observatory. green = 1, yellow = 2, brown = 3
     */
    public int getAverageGalamseyColor(){
        int colorSumAccumulator = 0;
        int galamseyCount = 0;

        for (Galamsey currentGalamseyInstance : recordedEvents){
            colorSumAccumulator += currentGalamseyInstance.getColorValue();
            //increment count
            galamseyCount++;
        }
        return colorSumAccumulator / galamseyCount;
    }

    /**
     * This method return an interable list that contains all recorded galamsey occurrences that have a
     * color value greater than a specified base value
     * @param baseColorValue is the base value to be used to determine the galamsey occurrences to add to search result
     * @return search result with galamsey occurrences as an iterable object
     */
    public List<Galamsey> getGalamseyWithColValOver(int baseColorValue){
        ArrayList<Galamsey> searchResult = new ArrayList<>();           //container to fill up
        //loop through all recorded Galamsey
        for (Galamsey galamseyInstance : recordedEvents){
            //add to list if colorValue is greater than baseColorValue
            if (galamseyInstance.getColorValue() > baseColorValue)
                searchResult.add(galamseyInstance);
        }
        return searchResult;
    }

    /**
     *
     * @param colorValue
     * @param latitude
     * @param longitude
     * @param year
     *
     * @throws IllegalArgumentException when an invalid value (especially year) is parsed as argument to the function
     */
    public void recordGalamsey(String colorValue, double latitude, double longitude, String year)
            throws IllegalArgumentException{
        //Create Galamsey Instance
        Galamsey newGalamseyOccurrence =
                new Galamsey(colorValue, latitude, longitude, year);
        //update largestGalamseyColor if color value of new occurrence is larges so far
        if (newGalamseyOccurrence.getColorValue() > largestColorValue)
            largestColorValue = newGalamseyOccurrence.getColorValue();
        //add to list of galamsey observations
        recordedEvents.add(newGalamseyOccurrence);
    }



    //-------------------------------Start of auxiliary methods-------------------------------



    //----------------------------------End of auxiliary methods-------------------------------



    //------------------------Define helper methods----------------------------
    /**
     *
     * @author William Kyei (Group 16 member)
     * @param year
     * @return
     */
    private boolean validYear(String year) {
        /**
         * Temporary calendar object created to obtain current year
         */
        Calendar tempCalObj = null;
        try {
            tempCalObj = new Calendar.Builder().build();
            tempCalObj.setTime(new Date());
            int currentYear = tempCalObj.get(Calendar.YEAR);
            int givenYear = Integer.parseInt(year);

            //remove later
            System.out.println(tempCalObj.get(Calendar.YEAR));
            //remove ^ later

            return (givenYear >= 0) && (givenYear <= currentYear);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //---------------------------End of helper methods--------------------------

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
