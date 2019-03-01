/**
 * 
 */
package group.project;
import group.project.Position;
import java.util.Date;
import java.util.Calendar;

/**
 * @author Group 16
 * @version 1.0
 * 
 * This class is a model of Galamsey (Illegal mining).
 * 
 * It holds the details of this occurrence such as vegetation color,location, and the year it occurred.
 */
public class Galamsey {
	
	/**
	 * @author group 16
	 *
	 * This enum consists of the fixed set of values for the field "colorValue"
	 */
	private enum cValue{
		
		ONE("Green"), TWO("Yellow"), THREE("Brown");
		
		private String vegColour;
		
		cValue(String vc){
			this.vegColour = vc;
		}
		
		String getColor() {
			return this.vegColour;
		}

	}
	
	/**
	 * This field represents the measure of the a vegetation's "health" or status, and the likeliness of galamsey around the vegetation.
	 * 
	 * The value "1" depicts green and healthy vegetation identified with no �galamsey� presence.
	 * The value "2" depicts yellow vegetation identified to have fair vegetation with potential �galamsey� presence.
	 * The value "3" depicts brown and poor vegetation identified with certain �galamsey� presence.
	 */
	private cValue colorValue;
	
	/**
	 * This field represents the color of vegetation around the galamsey.
	 * 
	 * It is related to and restricted by the "colorValue" field. Each colorValue has its own vegetation color.
	 */
	private String veg_Color;
	
	/**
	 * This field represents the location of the galamsey using latitude and longitude in the "Position" class.
	 */
	private Position position;

	/**
	 * The year the galamsey took place.
	 */
	private String year;
	
	/**
	 * Primary constructor
	 * 
	 * @param colorValue
	 * @param latitude
	 * @param longitude
	 * @param year
	 */
	public Galamsey(String colorValue, double latitude, double longitude, String year) {
		
		this.colorValue = cValue.valueOf(colorValue);
		
		veg_Color = this.colorValue.getColor();
		
		this.position = (new Position(latitude, longitude));
		
		if(validYear(year)) this.year = year;
		
	}
	
	/**
	 * 
	 * 
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colorValue == null) ? 0 : colorValue.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((veg_Color == null) ? 0 : veg_Color.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Galamsey other = (Galamsey) obj;
		if (colorValue != other.colorValue) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		if (veg_Color == null) {
			if (other.veg_Color != null) {
				return false;
			}
		} else if (!veg_Color.equals(other.veg_Color)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the colorValue
	 */
	public String getColorValue() {
		if (this.veg_Color == "Green") return "1";
		if (this.veg_Color == "Yellow") return "2";
		else return "3";
	}

	/**
	 * @param colorValue the colorValue to set
	 */
	public void setColorValue(String colorValue) {
		this.colorValue = cValue.valueOf(colorValue);
		this.veg_Color = this.colorValue.getColor();
	}

	/**
	 * @return the veg_Color
	 */
	public String getVeg_Color() {
		return veg_Color;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position.toString();
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(double latitude, double longitude) {
		this.position = new Position(latitude, longitude);
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		if (validYear(year)) this.year = year;
	}
	
	//Used to test.
	//Remember to remove when submitting!!
	/*
	public static void main(String []args) {
		Galamsey g1 = new Galamsey("ONE", 40, 30.244, "2019");
		
		System.out.println(g1.getColorValue());
		System.out.println(g1.getVeg_Color());
		System.out.println(g1.getPosition());
		System.out.println(g1.getYear());
	}*/
	
}