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
		mainP.setLayout(null);
		JLabel mainL=new JLabel("Successfully Logged In. This Page will be the main point of travel on this application"
				+ ". There are limited options at the moment, but it will expand in time. Hope you enjoy!");
		
		JButton addWorkout=new JButton("Add Workout Session");
		JButton updateStats=new JButton("Update Body Stats");
		JButton signOut = new JButton("Log Out");
		
		mainL.setBounds(0, 20, this.getWidth(), 40);
		addWorkout.setBounds(this.getWidth()/3-this.getWidth()/8, this.getHeight()/2, this.getWidth()/4, 60);
		updateStats.setBounds(this.getWidth()*2/3-this.getWidth()/8, this.getHeight()/2, this.getWidth()/4, 60);
		signOut.setBounds(this.getWidth()/2-70, this.getHeight()*3/4, 140, 40);

		
		mainP.add(mainL);
		mainP.add(addWorkout);
		mainP.add(updateStats);
		mainP.add(signOut);
		this.add(mainP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds Action to add workout for a Health User. Opens a new JFrame to add information
		addWorkout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WorkoutFrame workoutPopUp = new WorkoutFrame();
				workoutPopUp.setVisible(true);
				workoutPopUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
			}
		});
		
		
		//Adds Action to Update a Health Users Stats. Opens a new JFrame to change information
		updateStats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserStatsFrame popUp = new UserStatsFrame();
				popUp.setVisible(true);
				popUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		
		signOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.conn.closeConnection();
				closeFrame();
				System.exit(EXIT_ON_CLOSE);
			}
		});
		
	}

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}