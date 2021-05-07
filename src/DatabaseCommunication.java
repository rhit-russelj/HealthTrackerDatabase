import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.sql.CallableStatement;
import java.sql.Date;

public class DatabaseCommunication {
		private String user;
		private HealthTrackerDBConnection con;
	public DatabaseCommunication(String user, HealthTrackerDBConnection conn) {
		this.user=user;
		this.con=conn;
	}
	
	
	public ArrayList<String> getExercises(){
		String query="Select Name From [Default_Exercise]";
		ArrayList<String> excersices=new ArrayList<String>();
		excersices.add("Choose");
		
		try {
			Statement stmt= con.getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				excersices.add(rs.getString("Name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went wrong with the connection.");
		}
		return excersices;
		
	}
	
	public int addWorkout(String exerciseName, int reps, int sets, int time, int weight, int cal, String date) {
		CallableStatement cs = null;
		SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date parsedDate;
		int userID=getUser(this.user);
		try {
			java.util.Date unformatetedDob=unformattedDobString.parse(date);
			String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
			parsedDate=Date.valueOf(parsedDateString);
			cs = this.con.getConnection().prepareCall("{ ? = CALL Add_Workout( ? , ?, ?, ?, ?, ?, ?, ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, exerciseName);
			cs.setInt(3, reps);
			cs.setInt(4, sets);
			cs.setInt(5, time);
			cs.setInt(6, weight);
			cs.setInt(7, cal);
			cs.setInt(8, userID);
			cs.setDate(9, parsedDate);
			cs.execute();
			return cs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Parsing issue");
			e.printStackTrace();
			return 6;
		}
	}

	
	public int getUser(String text) {
		// TODO Validate Email is valid text and injection attack
		String query="Select Top 1 id from HealthUser where email='"+text+"'";
		int result = -1;
		try {
			Statement stmt=	Main.conn.getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				result=rs.getInt("id");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("User is logged in as "+result);
		return result;
	}
}