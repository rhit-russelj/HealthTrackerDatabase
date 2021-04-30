import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JFrame{

	public HomePage(String text, HealthTrackerDBConnection conn) {
		// TODO Auto-generated constructor stub
		this.setSize(1000,1000);
		JPanel mainP=new JPanel();
		JLabel mainL=new JLabel("Successfully Logged In. This Page will be the main point of travel on this application"
				+ ". There are limited options at the moment, but it will expand in time. Hope you enjoy!");
		JButton signOut = new JButton("Log Out");
		mainP.add(mainL);
		mainP.add(signOut);
		this.add(mainP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		signOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.conn.closeConnection();
				closeFrame();
			}
		});
		
	}

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
