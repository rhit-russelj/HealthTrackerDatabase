import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DatabaseCommunication {
		private String user;
		private HealthTrackerDBConnection con;
	public DatabaseCommunication(String user, HealthTrackerDBConnection conn) {
		this.user=user;
		this.con=conn;
	}
	
	
	public ArrayList<String> getExercises(){
		String query="Select Name From [Default_Exercise]";
		ArrayList<String> exercises=new ArrayList<String>();
		exercises.add("Choose");
		
		try {
			Statement stmt= con.getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()) {
				exercises.add(rs.getString("Name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went wrong with the connection.");
		}
		return exercises;
	}
	
	public int addStats(int uID, int h, int BMI, int w) {
		CallableStatement cs = null;
		int userID = getUser(this.user);
		if (uID != userID) {
			System.out.println("Incorrect UserID");
			return(2); 
		}
		try {
			cs = this.con.getConnection().prepareCall("{? = CALL AddStats( ?, ?, ?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, userID);
			cs.setInt(3, h);
			cs.setInt(4, BMI);
			cs.setInt(5, w);
			cs.execute();
			Main.conn.getConnection().commit();
			System.out.println("Result of add user stats is: " + cs.getInt(1));
			return cs.getInt(1);
		} catch (Exception e) {
			System.out.println("Parsing Issue");
			e.printStackTrace();
			return(6);
		}
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
			Main.conn.getConnection().commit();
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
public String getName() {
	String query="Select Top 1 name from HealthUser where email='"+user+"'";
	String result = user;
	try {
		Statement stmt=	Main.conn.getConnection().createStatement();
		ResultSet rs=stmt.executeQuery(query);
		while(rs.next()) {
			result=rs.getString("name");
		}
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Something went wrong getting users name");
	}
	return result;
	}


public HashMap<String, String> getUserStats() {
	String query="Select Top 1 Height, BMI, Weight from UserStatsView where email=?";
	HashMap<String, String> result = new HashMap<String, String>();
	result.put("Height", "No Data on Height");
	result.put("BMI", "No Data on BMI");
	result.put("Weight", "No Data on Weight");
	
	try {
		PreparedStatement stmt=	Main.conn.getConnection().prepareStatement(query);
		stmt.setString(1, user);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()) {
			result.put("Height", rs.getString("Height")+" ft");
			result.put("BMI",Integer.toString(rs.getInt("BMI")));
			result.put("Weight", Integer.toString(rs.getInt("Weight"))+" lbs");
			return result;
		}
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Something went wrong getting users stats");
	}
	return result;
	}


public int addGoal(String exerciseName, int reps, int sets, int time, int weight, String date) {
	CallableStatement cs = null;
	SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
	SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
	Date parsedDate;
	int userID=getUser(this.user);
	try {
		java.util.Date unformatetedDob=unformattedDobString.parse(date);
		String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
		parsedDate=Date.valueOf(parsedDateString);
		cs = this.con.getConnection().prepareCall("{ ? = CALL addGoal( ? , ?, ?, ?, ?, ?, ? )}");
		cs.registerOutParameter(1, Types.INTEGER);
		cs.setInt(2, userID);
		cs.setString(3, exerciseName);
		cs.setInt(4, reps);
		cs.setInt(5, sets);
		cs.setInt(6, time);
		cs.setInt(7, weight);
		cs.setDate(8, parsedDate);
		cs.execute();
		Main.conn.getConnection().commit();
		return cs.getInt(1);
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Parsing issue");
		e.printStackTrace();
		return 6;
	}
<<<<<<< HEAD
}

public int modifyAccount(String email,String name,String sex,String dob,String pass) {
	CallableStatement cs = null;
	SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
	SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
	Date parsedDate;
	int userID=getUser(this.user);
	try {
		java.util.Date unformatetedDob=unformattedDobString.parse(dob);
		String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
		parsedDate=Date.valueOf(parsedDateString);
		cs = this.con.getConnection().prepareCall("{ ? = CALL Edit_Account( ? , ? , ?, ?, ?, ? )}");
		cs.registerOutParameter(1, Types.INTEGER);
		cs.setInt(2, userID);
		cs.setString(3, email);
		cs.setString(4, name);
		cs.setString(5, sex);
		cs.setDate(6, parsedDate);
		cs.setString(7, pass);
		cs.execute();
		Main.conn.getConnection().commit();
		return cs.getInt(1);
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Parsing issue");
		e.printStackTrace();
		return 6;
	}
}


public byte[] getSalt(String user2) {
	// TODO Auto-generated method stub
	String query="Select PasswordSalt From HealthUser where email=?";
	byte[] result = null;
	try {
		PreparedStatement stmt=	Main.conn.getConnection().prepareStatement(query);
		stmt.setString(1, user2);
		ResultSet rs=stmt.executeQuery();
		result=rs.getBytes("PasswordSalt");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Something went wrong with the connection.");
	}
	return result;
	
}
=======
	
	public int modifyAccount(String email,String name,String sex,String dob,String pass) {
		CallableStatement cs = null;
		SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date parsedDate;
		int userID=getUser(this.user);
		try {
			java.util.Date unformatetedDob=unformattedDobString.parse(dob);
			String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
			parsedDate=Date.valueOf(parsedDateString);
			cs = this.con.getConnection().prepareCall("{ ? = CALL Edit_Account( ? , ? , ?, ?, ?, ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, userID);
			cs.setString(3, email);
			cs.setString(4, name);
			cs.setString(5, sex);
			cs.setDate(6, parsedDate);
			cs.setString(7, pass);
			cs.execute();
			Main.conn.getConnection().commit();
			return cs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Parsing issue");
			e.printStackTrace();
			return 6;
		}
	}

	public byte[] getSalt(String user2) {
		// TODO Auto-generated method stub
		String query="Select PasswordSalt From HealthUser where email=?";
		byte[] result = null;
		try {
			PreparedStatement stmt=	Main.conn.getConnection().prepareStatement(query);
			stmt.setString(1, user2);
			ResultSet rs=stmt.executeQuery();
			result=rs.getBytes("PasswordSalt");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went wrong with the connection.");
		}
		return result;	
		}

	public int checkGoalStat(String exerciseName) {
		CallableStatement cs = null;
		int result=-1;
		int userID=getUser(this.user);
		if(userID<=0) {
			System.out.println("Problem authorizing user credentials");
			return -1;
		}
	
		try {
			cs = this.con.getConnection().prepareCall("{ ? = CALL GoalCompleted( ? , ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, userID);
			cs.setString(3, exerciseName);
			cs.execute();
			Main.conn.getConnection().commit();
			result= cs.getInt(1);			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Goal Completed caught an error");
			e.printStackTrace();
			return -1;
		}
		if(result>=2&&result<=4) {
			System.out.println("Invalid input on checkGoalStat");
			return -1;
		}
		if(result==7) {
			System.out.println("Goal has not been reached");
			return 7;
		}
		if(result==1) {
			System.out.println("Goal has been achieved");
			return 1;
		}
		else {
			System.out.println("Caught some other number in GoalCheck");
			return -1;
		}
	}

	public int deleteGoal(String exercise) {
		CallableStatement cs = null;
		int userID=getUser(this.user);
		try {
			cs = this.con.getConnection().prepareCall("{ ? = CALL RemoveGoal( ? , ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, userID);
			cs.setString(3, exercise);
			cs.execute();
			Main.conn.getConnection().commit();
			return cs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("Parsing issue on delete");
			return 6;
		}
	}	
	
	public String formatAttribute(String attributeName) {
		if(attributeName.equals("rep")) return "Rep";
		if(attributeName.equals("sets")) return "Sets";
		if(attributeName.equals("time")) return "Time";
		if(attributeName.equals("weight")) return "Weight";
		if(attributeName.equals("calories")) return "Calories";
		return attributeName;
		
	}
	
	public ArrayList<Integer> getCords(String attributeName, String exerciseName){
		String attr=formatAttribute(attributeName);
		String query="Select "+attr+", DateOfExercise from RecordedExercise where UserID = ? and ";
		//Date[] xVals=new Date[]();
		//Integer[] yValues=new Integer[];
		return null;
		
	}
	
	public ArrayList<Integer> getGoalCords(String attributeName, String exerciseName){
		String attr=formatAttribute(attributeName);

		return null;
		
	}


	public HashMap<String, String> getCurrentGoalStats(String exercise) {
		// TODO Auto-generated method stub
		HashMap<String, String> attr=new HashMap<String, String>();
		attr.put("exercise", exercise);
		String query="Select reps, sets, time, weightGoal, goalDate from Goals where userID = ? and ExerciseName = ?";
		int userID=getUser(this.user);

		try {
			PreparedStatement stmt=	Main.conn.getConnection().prepareStatement(query);
			stmt.setInt(1, userID);
			stmt.setString(2, exercise);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				attr.put("reps", Integer.toString(rs.getInt("reps")));
				attr.put("sets",Integer.toString(rs.getInt("sets")));
				attr.put("time", Integer.toString(rs.getInt("time")));
				attr.put("weight", Integer.toString(rs.getInt("weightGoal")));
				attr.put("date", rs.getDate("goalDate").toString());
				return attr;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went wrong getting goal stats");
		}
		
		return null;
	}


	public int modGoal(String exerciseName, int reps, int sets, int time, int weight, String date) {
		CallableStatement cs = null;
		SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date parsedDate;
		int userID=getUser(this.user);
		try {
			java.util.Date unformatetedDob=unformattedDobString.parse(date);
			String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
			parsedDate=Date.valueOf(parsedDateString);
			cs = this.con.getConnection().prepareCall("{ ? = CALL ModifyGoal( ? , ?, ?, ?, ?, ?, ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, userID);
			cs.setString(3, exerciseName);
			cs.setInt(4, reps);
			cs.setInt(5, sets);
			cs.setInt(6, time);
			cs.setInt(7, weight);
			cs.setDate(8, parsedDate);
			cs.execute();
			Main.conn.getConnection().commit();
			return cs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Parsing issue");
			e.printStackTrace();
			return 6;
		}	}



>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
}







