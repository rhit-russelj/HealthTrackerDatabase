import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

public class Main {
	
	static HealthTrackerDBConnection conn=null;
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
			// ex.printStackTrace();
		}
		conn = new HealthTrackerDBConnection(server, name);
		System.out.println(conn.connect(uname, pass) ? "Connected!" : "Failed to Connect.");
	}
	public static void main(String[] args) {
		init();
		
		mainScreen=new JFrame("Home Page for Health Tracker");
		mainScreen.setSize(400,400);
		LoginPanel login=new LoginPanel();
		
		
		mainScreen.add(login);
		
		mainScreen.setAutoRequestFocus(false);
		mainScreen.repaint();
		mainScreen.setVisible(true);
		mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
	
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		mainScreen.setVisible(false);
	}


}
