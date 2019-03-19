/**
 * 
 */
package foundation.classes;
import java.sql.*;


/**
 * Description
 * 
 * @author Group 16
 * @version 1.0
 */
public class Database {
	
	//TODO make sure db works on all machines. user and password may not work.
        
        /**
	 * Monitoring object created to have access to all observatories.
	 */
	private static Monitoring m1 = new Monitoring();
	
	/**
	 * Default constructor of Database class.
         * 
         * A connection is made to the database, if it does not exist, it is created.
	 */
	public Database() {
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
		 
            try (Connection dbConnect = DriverManager.getConnection(url, user, password); Statement Stmt = dbConnect.createStatement();) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                //Creation of new database if it does not exist
                try {

                        Stmt.execute("CREATE DATABASE icp_groupproject");

                        Stmt.execute("CREATE TABLE `galamsey` (\r\n" + 
                                        "  `GalamseyID` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
                                        "  `ColorValue` int(11) DEFAULT NULL,\r\n" + 
                                        "  `VegetationColor` varchar(45) DEFAULT NULL,\r\n" + 
                                        "  `Latitude` int(11) DEFAULT NULL,\r\n" + 
                                        "  `Longitude` int(11) DEFAULT NULL,\r\n" + 
                                        "  `Year` varchar(45) DEFAULT NULL,\r\n" + 
                                        "  `ObservatoryID` int(11) DEFAULT NULL,\r\n" + 
                                        "  PRIMARY KEY (`GalamseyID`),\r\n" + 
                                        "  KEY `observatoryID_idx` (`ObservatoryID`),\r\n" + 
                                        "  CONSTRAINT `ObservatoryID` FOREIGN KEY (`ObservatoryID`) REFERENCES `observatory` (`ObservatoryID`) ON DELETE NO ACTION ON UPDATE NO ACTION\r\n" + 
                                        ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;\r\n" + 
                                        "");

                        Stmt.execute("CREATE TABLE `observatory` (\r\n" + 
                                        "  `ObservatoryID` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
                                        "  `Name` varchar(45) DEFAULT NULL,\r\n" + 
                                        "  `Country` varchar(45) DEFAULT NULL,\r\n" + 
                                        "  `CommencementYear` varchar(45) DEFAULT NULL,\r\n" + 
                                        "  `AreaOfObservatory` float DEFAULT NULL,\r\n" + 
                                        "  PRIMARY KEY (`ObservatoryID`)\r\n" + 
                                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\r\n" + 
                                        "");

                        dbConnect.commit(); // now the database physically exists

                        System.out.println("Database did not exist, new database created.");

                        }catch (SQLException exception) {

                                //we are here if database exists

                        }

                    System.out.println("Working connection with database established.");
                    dbConnect.close();

            } catch (ClassNotFoundException | SQLException e) {

                    System.out.println(e);
                    System.out.println("Working connection with database may not have established.");
            }		
		
	}
	
	public ResultSet executeQuery(String instructions) {
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
		
            try (Connection dbConnect = DriverManager.getConnection(url, user, password); Statement stmnt = dbConnect.createStatement();) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                
                return stmnt.executeQuery(instructions);

            } catch (Exception e) {

                System.out.println(e);

            }

            return null;
		
	}
	
	public String executeUpdate(String instructions) {
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
            
		
            try (Connection dbConnect = DriverManager.getConnection(url, user, password); Statement stmnt = dbConnect.createStatement();) {
                
                Class.forName("com.mysql.cj.jdbc.Driver");

                stmnt.executeUpdate(instructions);
                
                return "Successful Input";

            } catch (Exception e) {

                System.out.println(e); 

                return "Unsuccessful Input";

            }
		
	}
	
	/**
	 * This method is the first to run in this class.
	 * It reads the file to load data stored from the previous session.
	 * This data can then be updated, modified, or maintained by the user's following commands.
	 */
	protected static void loadStoredData() {
            
            Database d1 = new Database();
		
            try (ResultSet rs = d1.executeQuery("SELECT * FROM observatory"); ResultSet rs1 = d1.executeQuery("SELECT * FROM galamsey");) {
                
                    while (rs.next()) {

                            //Collect Observatory attributes from database
                            String name = rs.getString("Name");
                            String country = rs.getString("Country");
                            String year = rs.getString("CommencementYear");
                            double area = (double) rs.getFloat("AreaOfObservatory");
                            String observatoryID = rs.getString("ObservatoryID");

                            //Create observatory object
                            Observatory o1 = new Observatory(name, country, year, area);

                            //Iterate through all Galamsey and add the appropriate events to the observatory
                            while (rs1.next()) {

                                    //Collect Observatory attributes from database
                                    String colorValue = rs1.getString("ColorValue");
                                    double latitude = rs1.getDouble("Latitude");
                                    double longitude = rs1.getDouble("Longitude");
                                    String gYear = rs1.getString("Year");

                                    String gObservatoryID = rs1.getString("ObservatoryID");

                                    if (observatoryID.equals(gObservatoryID)) {

                                            Galamsey g1 = new Galamsey(colorValue, latitude, longitude, gYear);
                                            o1.recordGalamsey(g1);

                                    }

                            }

                            m1.addObservatory(o1);

                    }

                    System.out.println("Observatories in Monitoring: "+m1.getObservatories());

            } catch (SQLException e) {
            }
		
	}
	
	
	/*public static void main(String args[]) {
		
		Database d1 = new Database();
		
	}*/

}
