package sodabase.services;

import java.awt.image.SampleModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		//TODO: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
        String conString=SampleURL.replace("${dbServer}", serverName);
        conString=conString.replace("${dbName}", databaseName);
        conString =conString.replace("${user}", user);
        conString=conString.replace("${pass}", pass);
        System.out.println(conString);
        try{
        	connection=DriverManager.getConnection(conString);
        	return true;
        }catch (Exception e) {
        	return false;
		}
        	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		//TODO: Task 1
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
