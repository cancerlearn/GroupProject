/**
 * 
 */
package foundation.classes;
import java.lang.Math;

/**
 * @author Group 16
 * @version 1.0
 *
 * This class is modeled after the geographic coordinate system.
 * It consists of longitude and latitude to represent location.
 */
public class Position implements java.io.Serializable {
	
	/**
	 * Identification used when deserializing to a Position object to confirm the class
	 */
	private static final long serialVersionUID = -578272541301949445L;
	
	//Fields of latitude and longitude
	/**
	 * Latitude: measure of how far north or south of the equator a location is.
	 */
	private double latitude;
	/**
	 * Longitude: measure of how far east or west of the prime meridian a location is.
	 */
	private double longitude;
	
	
	/**
	 * Default constructor for this class.
	 * <p>
	 * Position is set to intersection of equator and prime meridian
	 * longitude = 0, latitude = 0.
	 * </p>
	 */
	public Position() {
		
		this.latitude = 0;
		
		this.longitude = 0;
		
	
	}
	
	/**
	 * Overloaded constructor for this class.
	 * <p>
	 * In this constructor latitude and longitude values are accepted as parameters in this constructor.
	 * </p>
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public Position(double latitude, double longitude){
		
		//The auxiliary method "inBoundary()" is used to determine if the values of latitude and longitude given are right.
		try {
			if(inBoundary(latitude, longitude)) {
			
				this.latitude = latitude;
		
				this.longitude = longitude;
			}
		} catch(IllegalArgumentException iae) {
				
				iae.printStackTrace();
				
			}
	}
	
	/**
	 * This is an auxiliary method used to verify the validity of values for latitude and longitude.
	 * <p>
	 * latitude should be between -90 degrees and 90 degrees, longitude should be between -180 degrees and 180 degrees.
	 * </p>
	 * 
	 * @param ltd
	 * @param lgtd
	 * @return The boolean value determining if latitude and longitude are right.
	 * @throws IllegalArgumentException
	 */
	private boolean inBoundary(double ltd, double lgtd) throws IllegalArgumentException {
		
		/**
		 * The boolean value determining if latitude is right.
		 */
		boolean rightLatitude = (ltd >= -90) && (ltd <= 90);
		
		/**
		 * The boolean value determining if longitude is right.
		 */
		boolean rightLongitude = (ltd >= -180) && (ltd <= 180);
		
		if (!(rightLatitude && rightLongitude)) throw new IllegalArgumentException();
		
		return rightLatitude && rightLongitude;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Position other = (Position) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		return true;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		if (this.latitude > 0 && this.longitude > 0) 
			return "Position(latitude, longitude): " + Math.abs(this.latitude) + "\u00B0 N, "+ Math.abs(this.longitude) + "\u00B0 E";
		
		if (this.latitude > 0 && this.longitude < 0) 
			return "Position(latitude, longitude): " + Math.abs(this.latitude) + "\u00B0 N, "+ Math.abs(this.longitude) + "\u00B0 W";
		
		if (this.latitude < 0 && this.longitude < 0) 
			return "Position(latitude, longitude): " + Math.abs(this.latitude) + "\u00B0 S, "+ Math.abs(this.longitude) + "\u00B0 W";
		
		if (this.latitude < 0 && this.longitude > 0) 
			return "Position(latitude, longitude): " + Math.abs(this.latitude) + "\u00B0 S, "+ Math.abs(this.longitude) + "\u00B0 E";
		
		else return "Position(latitude, longitude): " + Math.abs(this.latitude) + "\u00B0 N, "+ Math.abs(this.longitude) + "\u00B0 E";
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	//Used to test.
	//Remember to remove when submitting!!
	/*public static void main(String [] args) {
		Position p1 = new Position(-40.0, -30.244);
		System.out.println(p1);
	}*/
}
