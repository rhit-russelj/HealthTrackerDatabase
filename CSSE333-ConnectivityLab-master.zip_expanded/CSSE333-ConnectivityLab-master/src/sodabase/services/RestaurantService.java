package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
		//Done: Task 5
		CallableStatement cs = null;
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = CALL AddRestaurant( ? , ? , ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, restName);
			cs.setString(3, addr);
			cs.setString(4, contact);
			cs.execute();
			int returnValue=cs.getInt(1);
			
			if(returnValue==1) {
				 JOptionPane.showMessageDialog(null, "Not all required parameters were filled with valid input");
				 return false;
			}
			if(returnValue==2) {
				 JOptionPane.showMessageDialog(null, "This restraunt already exists! There cannot be duplicate restraunt.");
				 return false;
			}
			
			return true;
		} catch (SQLException e) {
			System.out.println("The system ran into a problem.");
			e.printStackTrace();
			return false;
		}
		
	}
	

	public ArrayList<String> getRestaurants() {	
		//Done: Task 2 
		Statement stmt=null;
		String query="SELECT Name FROM Rest";
		ArrayList<String> rests = new ArrayList<String>();
	
		if(this.dbService!=null) {
	        try {
				stmt = this.dbService.getConnection().createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	rests.add(rs.getString("Name"));
		        }

			} catch (SQLException e) {
				e.printStackTrace();
			}
		    } 
		
		return rests;
	}
}
