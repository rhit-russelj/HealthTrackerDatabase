

import java.awt.image.SampleModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HealthTrackerDBConnection {

	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public HealthTrackerDBConnection(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
        String conString=SampleURL.replace("${dbServer}", serverName);
        conString=conString.replace("${dbName}", databaseName);
        conString =conString.replace("${user}", user);
        conString=conString.replace("${pass}", pass);
        try{
            System.out.println(conString);

        	connection=DriverManager.getConnection(conString);
        	return true;
        }catch (Exception e) {
        	System.out.println("Failed to Connect to server and caught in connect");
        	return false;
		}
        	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
			try {
				System.out.println("Closing Connection");
				connection.close();
			} catch (SQLException e) {
				System.out.println("Failed closing connection");
			}
	}

}
