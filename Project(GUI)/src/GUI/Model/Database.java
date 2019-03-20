/**
 * 
 */
package GUI.Model;
import foundation.classes.Galamsey;
import foundation.classes.Monitoring;
import foundation.classes.Observatory;
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
	public Database() {}
        
        public void initializeDBConnection (){
            
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
        
        
        public boolean executeObservatoryInsertion(String obsName, String obsCountry, String obsYear, double obsArea){
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
            
            String ins = "INSERT INTO observatory (Name, Country, CommencementYear, AreaOfObservatory) VALUES (?,?,?,?)";
		
            try (Connection dbConnect = DriverManager.getConnection(url, user, password); PreparedStatement pStmnt = dbConnect.prepareStatement(ins);) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                
                pStmnt.setString(1, obsName);
                pStmnt.setString(2, obsCountry);
                pStmnt.setString(3, obsYear);
                pStmnt.setDouble(4, obsArea);
                
                pStmnt.execute();
                        
            } catch (Exception e) {
                
                System.out.println(e);
                return false;
            }
            
            return true;
            
        }
        
        
        
        public boolean executeGalamseyInsertion(String colorValue, String vegColor, double latitude, double longitude, String year, int obsID){
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
            
            String ins = "INSERT INTO galamsey (ColorValue, VegetationColor, Latitude, Longitude, Year, ObservatoryID) VALUES (?,?,?,?,?,?)";
		
            try (Connection dbConnect = DriverManager.getConnection(url, user, password); PreparedStatement pStmnt = dbConnect.prepareStatement(ins);) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                
                pStmnt.setString(1, colorValue);
                pStmnt.setString(2, vegColor);
                pStmnt.setDouble(3, latitude);
                pStmnt.setDouble(4, longitude);
                pStmnt.setString(5, year);
                pStmnt.setInt(6, obsID);
                
                pStmnt.execute();
                        
            } catch (Exception e) {

                System.out.println(e);
                
                return false;

            }
            
            return true;
            
        }
        
        
	
        /**
         * 
         * @param instructions
         * @return 
         */
	public ResultSet executeQuery(String instructions) {
            
            String url = "jdbc:mysql://localhost:3306/icp_groupproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String password = "Complex908@mysql";
            
            Connection dbConnect = null;
		
            try {
                
                dbConnect = DriverManager.getConnection(url, user, password);
                
                Statement stmnt = dbConnect.createStatement();
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                return stmnt.executeQuery(instructions);

            } catch (Exception e) {

                System.out.println(e);

            }

            return null;
		
	}
	
        
        
        /**
         * 
         * @param instructions
         * @return 
         */
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
	 * This method is called second when the Database object is created elsewhere.
	 * It reads from the database to load data stored from the previous session.
	 * This data can then be updated, or maintained by the user's following commands.
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

                    System.out.println("Data loaded (printing from loadStoredData method): "+m1.getObservatories());

            } catch (SQLException e) {}
		
	}
	
        public Monitoring getMonitoring() {
            
            return m1;
            
        }

}
