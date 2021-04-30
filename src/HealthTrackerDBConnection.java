

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
        	//connection=DriverManager.getConnection("jdbc:sqlserver://titan.csse.rose-hulman.edudatabaseName=SodaBaseUserbohnernj30;user=SodaBaseUserbohnernj30;password={c3OxKJo5CR}");
            System.out.println(conString);

        	connection=DriverManager.getConnection(conString);
        	System.out.println(connection);
        	return true;
        }catch (Exception e) {
			e.printStackTrace();

        	return false;
		}
        	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
