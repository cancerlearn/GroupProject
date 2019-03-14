/**
 * 
 */
package group.project;
import group.project.Observatory;
import group.project.Galamsey;
import group.project.Monitoring;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * @author Group 16
 * @version 1.0
 * 
 * This class provides a console UI for users to utilize the Galamsey, Monitoring, and Observatory classes created.
 * 
 */
public class MonitoringIO {
	/**
	 * Monitoring object created to have access to all observatories.
	 */
	private static Monitoring m1 = new Monitoring();
	
	/**
	 * This method is the first to run in this class.
	 * It reads the file to load data stored from the previous session.
	 * This data can then be manipulated or maintained by the user's following commands.
	 */
	private static void loadStoredData() {
		
		ObjectInputStream objRead = null;
		
		try {
			if (new File("GalamseyRecords.txt").length() != 0) {
				
				objRead = new ObjectInputStream(new FileInputStream("GalamseyRecords.txt"));
				m1 = (Monitoring) objRead.readObject();
				System.out.println(m1.getObservatories());
				/*for (Observatory o: m1.getObservatories()) {
					
					System.out.println(o+"\n");
					
				}*/
				
			}
			
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} catch(ClassNotFoundException cnfe) {
			
			cnfe.printStackTrace();
			
		} finally {
			
			try {
				if(objRead != null) objRead.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * This method handles all user interactions when creating an observatory.
	 */
	private static void observatoryData() {
	
		Observatory o1 = new Observatory();
		
		System.out.println("\n\nWelcome to 'Create an Observatory'!\n\n"+
		"Input the following information correctly to create an observatory.\nInput 'done' to end this process.\n\n");
		
		Scanner takeInput = new Scanner(System.in);
		
		String userCommand = "";
		while(true) {
			
			System.out.println("---> Observatory name: ");
			userCommand = takeInput.nextLine();
			if (!userCommand.matches("\\s+")) o1.setObservatoryName(userCommand);
			else {System.out.println("Incorect format for name. Try again.\n\n\n\n"); continue;}
			
			System.out.println("\n---> Country name: ");
			userCommand = takeInput.nextLine();
			if (!userCommand.matches("\\s+")) o1.setCountryName(userCommand);
			else {System.out.println("Incorect format for country. Try again.\n\n\n\n"); continue;}
			
			System.out.println("\n---> Year of Commencement: ");
			userCommand = takeInput.nextLine();
			if (o1.validYear(userCommand)) o1.setYearOfCommencement(userCommand);
			else { System.out.println("Invalid year! Try again.\n\n\n\n"); continue;};
			
			System.out.println("\n---> Area covered by observatory in km: ");
			userCommand = takeInput.nextLine();
			try{
				o1.setAreaCoveredByObservatory(Double.parseDouble(userCommand));
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if (!userCommand.equals("")) {
				
				ArrayList<Observatory> temp = m1.getObservatories();
				temp.add(o1);
				m1.setObservatories(temp);
				saveToFile();
				
			}
			
			System.out.println("\n\nObservatory created.\nInput 'done' to end this process or 'again' to add another Observatory.");
			userCommand = takeInput.nextLine();
			if (userCommand.equals("done")) {
				
				System.out.println("Menu\n\n"+
						"--> Enter observatory data\n"+
						"--> Enter galamsey data\n"+
						"--> View Statistics\n\n"+
						"--> Exit"+
						"\n\n*Type 'eod' to enter observatory data, 'egd' to enter galamsey data, and 'stats' "
						+ "to view statistics.\nEnter 'exit' to end application.");
				
				break;
			}
			if (userCommand.equals("again")) continue;
			
		}
		
	}
	
	/**
	 * This method handles all user interactions when a galamsey event is recorded
	 */
	private static void galamseyData() {
		
		Galamsey g1 = new Galamsey();
		
		System.out.println("\n\nWelcome to 'Add Galamsey Event'!\n\n");
		
		Scanner takeInput = new Scanner(System.in);
		
		String userCommand = "";
		
		ArrayList<Observatory> obs = m1.getObservatories();
		
		String observatoryFound = "";
		String errorOccurred = "";
		
		while (true) {
			
			System.out.println("Enter the name of an Observatory for which you wish to record this event:");
			//Taking input for existing Observatory name
			userCommand = takeInput.nextLine();
		
			for (Observatory ob: obs) {
			
				if (ob.getObservatoryName().equals(userCommand)) {
				
					//Set observatoryFound string to 'yes'
					observatoryFound = "yes";
					//Set errorOccurred string to 'no'
					errorOccurred = "no";
					
					//Updated observatory object used
					Observatory updatedObs = ob;
				
					System.out.println("---> ColorValue (Options: 'ONE'=Green/Healthy vegetation with no galamsey prescence,"
							+ "'TWO'=Yellow/Fair vegetation with potential prescence,"
							+ "'THREE'=Green/Healthy vegetation with certain galamsey prescence)\nValue: ");
					
					userCommand = takeInput.nextLine();
					if (!userCommand.equals("ONE") && !userCommand.equals("TWO") && !userCommand.equals("THREE")) {
						System.out.println("\nInvalid option provided. Try again.\n\n\n\n");
						errorOccurred = "yes";
						break;
					} 
					g1.setColorValue(userCommand);
					
					System.out.println("\n---> Position of Galamsey event (Enter in the format 'Latitude(between -90\u00B0 and "
							+ "90\u00B0; negative=south, positive=north), Longitude(between -180\u00B0 and 180\u00B0; negative=west, positive=east)'): ");
					userCommand = takeInput.nextLine();
					try {
						String latitude = userCommand.split(",")[0];
						String longitude = userCommand.split(",")[1].replaceAll("\\s+", "");
						g1.setPosition(Double.parseDouble(latitude), Double.parseDouble(longitude));
					
					} catch(Exception e) {
						//e.printStackTrace();
						System.out.println("Invalid format for latitude. Try Again.");
						errorOccurred = "yes";
						break;
					
					}
					
					System.out.println("\n---> Year Recorded: ");
					userCommand = takeInput.nextLine();
					if (updatedObs.validYear(userCommand)) g1.setYear(userCommand);
					else {
						
						System.out.println("Invalid year! Try again.\n\n\n\n");
						errorOccurred = "yes";
						break;
						
					}
					
					updatedObs.recordGalamsey(g1);
					m1.replaceObservatory(updatedObs);
					saveToFile();
					
				} else {errorOccurred = "yes";}
				
			}
			
			if(!observatoryFound.equals("yes")) {
				
				System.out.println("\nNo such observatory exists. Try again.\nThis is the list of observatories: " + obs.toString() + "\n\n");
				continue;
			
			}
			
			
			
			if (!errorOccurred.equals("yes")) System.out.println("\n\nGalamsey event added.\nInput 'done' to end this "
					+ "process or 'again' to add another.");
			else {System.out.println("\nInput 'done' to end this process or 'again' to add another.");}
			userCommand = takeInput.nextLine();
			
			if (userCommand.equals("again")) continue;
			
			if (userCommand.equals("done")) {
				
				System.out.println("Menu\n\n"+
						"--> Enter observatory data\n"+
						"--> Enter galamsey data\n"+
						"--> View Statistics\n\n"+
						"--> Exit"+
						"\n\n*Type 'eod' to enter observatory data, 'egd' to enter galamsey data, and 'stats' "
						+ "to view statistics.\nEnter 'exit' to end application.");
				
				break;
			}
			
			
			/*System.out.println("Input (Enter 'done' to end this process or 'again' to try again): ");
			userCommand = takeInput.nextLine();
			if (userCommand.equals("again")) continue;
			break;*/
		}
		
		
	}
	
	/**
	 * 
	 */
	private static void statsMenu() {
		
		System.out.println("\n\nWelcome to 'ViewStats'!\n\n"+
				"Select one of the many options to view general monitoring statistics on the observatories "
				+ "and the galamsey events they record.\nInput the corresponging number to view the stat and 'done' to end this process.\n\n");
				
		Scanner takeInput = new Scanner(System.in);
				
		String userCommand = "";
		
		while(true) {
			
			System.out.println("1. View observatory with largest average galamsey\n2. View largest galamsey\n"
					+ "3. View all galamsey with color value over given number.\n\n");
			
			userCommand = takeInput.nextLine();
			if (userCommand.equals("1")) System.out.println("\nObservatory with largest average galamsey: "+
			m1.getObsevatoryWithHighestAverageCV().getObservatoryName());
			
			if (userCommand.equals("2")) System.out.println("\nLargest galamsey (color value) recorded: "+
			m1.getLargestGalamseyColor());
			
			if (userCommand.equals("3")) {
				
				System.out.println("\nGive threshold for color value: ");
				userCommand = takeInput.nextLine();
				
				try {
					
					ArrayList<Galamsey> galAboveThreshold= m1.getGalamseyWithColValOver(Integer.parseInt(userCommand));
					ArrayList<String> galamseyThresh = new ArrayList<String>();
					for (Galamsey g: galAboveThreshold) {
						galamseyThresh.add(g.toString());
					}
					System.out.println("All galamsey with color value over"+userCommand+": "+galamseyThresh);
				} catch(Exception e) {
					e.printStackTrace();
				}
			
			}
			
			if (!userCommand.equals("done"))System.out.println("\n\nStat shown.\nInput 'done' to end this process or 'again' to view another stat.\n");
			//userCommand = takeInput.nextLine();
			if (userCommand.equals("done")) {
				
				System.out.println("Menu\n\n"+
						"--> Enter observatory data\n"+
						"--> Enter galamsey data\n"+
						"--> View Statistics\n\n"+
						"--> Exit"+
						"\n\n*Type 'eod' to enter observatory data, 'egd' to enter galamsey data, and 'stats' "
						+ "to view statistics.\nEnter 'exit' to end application.");
				
				break;
			}
			if (userCommand.equals("again")) continue;
			
		}
		
		
	}
	
	/**
	 * This method saves changes made to the file.
	 * 
	 * @param obj
	 */
	private static void saveToFile() {
		
		ObjectOutputStream objWrite = null;
		
		try {
			
			objWrite = new ObjectOutputStream(new FileOutputStream("GalamseyRecords.txt"));
			System.out.println(m1.getObservatories().toString());
			objWrite.writeObject(m1);
			
		} catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			System.out.println("File does not exist.");
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
			
		} finally {
			
			try {
				if(objWrite != null) objWrite.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
	}

	public static void main(String [] args) {
		
		loadStoredData();
		
		System.out.println("Menu\n\n"+
		"--> Enter observatory data\n"+
		"--> Enter galamsey data\n"+
		"--> View Statistics\n\n"+
		"--> Exit"+
		"\n\n*Type 'eod' to enter observatory data, 'egd' to enter galamsey data, and 'stats' to view statistics.\nEnter 'exit' to end application.");
		
		Scanner input = new Scanner(System.in);
		
		String command = "";
		while(true) {
			
			System.out.println("\n\nInput: ");
			command = input.nextLine();
			
			if (command.equals("eod")) observatoryData();
			

			if (command.equals("egd")) galamseyData();
			
			
			if (command.equals("stats")) statsMenu();
			
			
			if (command.equals("exit")) break;
			
		}
		
		input.close();
	}
	
}
