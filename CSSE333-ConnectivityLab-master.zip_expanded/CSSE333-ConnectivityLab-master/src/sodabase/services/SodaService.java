package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;
	
	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		//DONE: Task 
		CallableStatement cs = null;
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = CALL AddSoda( ? , ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, sodaName);
			cs.setString(3, manf);
			cs.execute();
			int returnValue=cs.getInt(1);
			
			if(returnValue==1) {
				 JOptionPane.showMessageDialog(null, "Not all required parameters were filled with valid input");
				 return false;
			}
			if(returnValue==2) {
				 JOptionPane.showMessageDialog(null, "This soda already exists! There cannot be duplicate sodas.");
				 return false;
			}
			
			return true;
		} catch (SQLException e) {
			System.out.println("The system ran into a problem.");
			e.printStackTrace();
			return false;
		}
		}
	
	public ArrayList<String> getSodas() {
		//DONE: Task 2
		String query="Select name from Soda";
		ArrayList<String> sodas = new ArrayList<String>();

		if(this.dbService!=null) {
	        try {
				Statement stmt = this.dbService.getConnection().createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	sodas.add(rs.getString("name"));
		        }

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sodas;
	}
}

