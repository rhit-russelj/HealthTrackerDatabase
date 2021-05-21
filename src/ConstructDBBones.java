
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Properties;

import javax.swing.JFrame;

public class ConstructDBBones {

	static HealthTrackerDBConnection conn = null;
	public static String loggedUser = "";
	public static JFrame mainScreen;

	public static void init() {
		String uname = "", pass = "", name = "", server = "";
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			name = prop.getProperty("db.name");
			uname = prop.getProperty("db.user");
			pass = prop.getProperty("db.pass");
			server = prop.getProperty("db.server");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		conn = new HealthTrackerDBConnection(server, name);
		System.out.println(conn.connect(uname, pass) ? "Connected!" : "Failed to Connect.");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
		int result=loadDB("rootExerciseFile.csv");
		System.out.println((result<0) ? "Sucessfully imported File!": "Failed to import file.");
	}

	public static int loadDB(String filePath) {

		String line = "";
		String splitBy = ",";
		String tempName;
		String tempMuscle;
		String tempCategory;
		String tempVidURL;
		String tempRep;
		String tempSet;
		String tempTime;
		String tempWeight;
		int intRep=-1;
		int intSet=-1;
		int intTime=-1;
		int intWeight=-1;
		try {
			// parsing a CSV file into BufferedReader class constructor
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] row = line.split(splitBy); // use comma as separator
				if (row.length != 8)
					continue; // I continue here because exercises are not self dependent and I handle any
								// updates in the system
				tempName = row[0];
				tempMuscle = row[1];
				tempCategory = row[2];
				tempVidURL = row[3];
				tempRep = row[4];
				tempSet = row[5];
				tempTime = row[6];
				tempWeight = row[7];
				System.out.println("Row [ExerciseName=" + tempName + ", MuscleUsed=" + tempMuscle
										+ ", Category=" + tempCategory + ", URL=" + tempVidURL + ", rep= " + tempRep
										+ ", set= " + tempSet + ", time= " + tempTime + ", weight= " + tempWeight + "]");
				try {
					intRep=Integer.valueOf(tempRep);
					intSet=Integer.valueOf(tempSet);
					intTime=Integer.valueOf(tempTime);
					intWeight=Integer.valueOf(tempWeight);
					URI test=new URL(tempVidURL).toURI();
				} catch (Exception e) {
					System.out.println("Unable to parse necessary columns. Line not inserted.");
					//e.printStackTrace();
					continue;
				}
				if(intRep==-1) continue;
				int i=loadExercise(tempName, tempMuscle, tempCategory, tempVidURL, intRep, intSet, intTime, intWeight);
				if(i==1) {
					System.out.println("Null Values Present. Line Not inserted.");
					continue;
				}
				if(i==2||i==3||i==4||i==5) {
					System.out.println("Non-boolean insertion attempted for uses attribute. Line Not inserted.");
					continue;
				}
				if(i==6) {
					System.out.println("Something Went Very Wrong");
					return 0;
				}
				if(i<0) {
					System.out.println("loaded row");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}
	
	public static int loadExercise(String name, String muscle, String category, String url, int usesreps, int usesSets, int usestime, int usesweight) {
		CallableStatement cs = null; 
		try {
			cs = conn.getConnection().prepareCall("{? = CALL AddExercise( ?, ?, ?, ?, ?, ?, ?, ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, muscle);
			cs.setString(4, category);
			cs.setString(5, url);
			cs.setInt(6, usesreps);
			cs.setInt(7, usesSets);
			cs.setInt(8, usestime);
			cs.setInt(9, usesweight);
			cs.execute();
			conn.getConnection().commit();
			//System.out.println("Result of add Exercise is: " + cs.getInt(1));
			return cs.getInt(1);
		} catch (Exception e) {
			System.out.println("Parsing Issue");
			//e.printStackTrace();
			return(6);
		}
	}
}