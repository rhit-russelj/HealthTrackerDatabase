package sodabase.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;

public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		JOptionPane.showMessageDialog(null, "Add Soda by Restaurant not implemented.");
		return false;
	}

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price,
			boolean useGreaterThanEqual) {
		//TODO: Task 3 and Task 4
		Double checkedPrice=null;
		if(price.length()==0) {
			price=null;
		}else {
			checkedPrice = Double.valueOf(price);
		}
		int i=1;
		String query = buildParameterizedSqlStatementString(rest, soda, checkedPrice, useGreaterThanEqual);
		System.out.println(query);
		try {
			PreparedStatement statement = null;
			try {
				this.dbService.getConnection().setAutoCommit(false);
				statement = this.dbService.getConnection().prepareStatement(query);
				if(rest!=null) {
					statement.setString(i, rest);
					i++;
				}
				if(soda!=null) {
					statement.setString(i, soda);
					i++;
				}
				if (checkedPrice != null && price.length() != 0) {
						statement.setDouble(i, checkedPrice);
				}
				ResultSet rs=statement.executeQuery();
				this.dbService.getConnection().commit();
				return parseResults(rs);
				
				} catch (SQLException e) {
					e.printStackTrace();
					if (this.dbService.getConnection() != null) {
						try {
							System.err.print("The input was faulty. Please try again.");
							this.dbService.getConnection().rollback();
						} catch (SQLException excep) {
							excep.printStackTrace();
							;
						}
					}
				} finally {
					if (statement != null) {
						statement.close();
					}
					this.dbService.getConnection().setAutoCommit(true);
				}
			

		}catch(SQLException ex){
		JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
		ex.printStackTrace();
		return new ArrayList<SodaByRestaurant>();
	}
		return new ArrayList<SodaByRestaurant>();

	}
	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price, boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (rest != null) {
			wheresToAdd.add("Restaurant = ?");
		}
		if (soda != null) {
			wheresToAdd.add("Soda = ?");
		}
		if (price != null) {
			if (useGreaterThanEqual) {
				wheresToAdd.add("Price >= ?");
			} else {
				wheresToAdd.add("Price <= ?");
			}
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			System.out.println(sodasByRestaurants.size());
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}


}
