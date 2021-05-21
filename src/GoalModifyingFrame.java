import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GoalModifyingFrame extends JFrame{

	public GoalModifyingFrame(String exercise) {
		this.setSize(700, 400);
		this.setLayout(null);
		
		DatabaseCommunication c= new DatabaseCommunication(Main.loggedUser, Main.conn);
		
		WorkoutInputPanel workoutInputPanel=new WorkoutInputPanel("Accomplish By Date", c.getCurrentGoalStats(exercise));
		
		JButton deleteButton= new JButton("Delete");
		JButton submitButton= new JButton("Submit");
		JButton closeButton = new JButton("Close");
		
		this.add(workoutInputPanel);
		this.add(deleteButton);
		this.add(submitButton);
		this.add(closeButton);
		
		workoutInputPanel.setBounds(0, 80, this.getWidth(), 80);
		workoutInputPanel.setVisible(true);
		deleteButton.setBounds(this.getWidth()/2-40, workoutInputPanel.getY()+80, 80, 30);
		submitButton.setBounds(this.getWidth()/2-40, deleteButton.getY()+40, 80, 30);
		closeButton.setBounds(this.getWidth()/2-40, submitButton.getY()+40, 80, 30);
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=c.deleteGoal(workoutInputPanel.getExercise());
				if(i<=0) {
					closeFrame();
				}
				if(i==1) {
					JOptionPane.showMessageDialog(null, "Please Select a Exercise to record.");
				}
				if(i==2) {
					JOptionPane.showMessageDialog(null, "No Such Exercise Exists in the database.");
				}
				if(i==3||i==4) {
					JOptionPane.showMessageDialog(null, "There was a problem with user Authentication.");
				}
				if(i==5) {
					JOptionPane.showMessageDialog(null, "The Goal you wish to delete does not exist.");
				}
				if(i==6) {
					JOptionPane.showMessageDialog(null, "There was a parsing issue when trying to delete the goal");
				}
			}
		});
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int i=c.modGoal(
							workoutInputPanel.getExercise(), 
							Integer.parseInt(workoutInputPanel.getReps()), 
							Integer.parseInt(workoutInputPanel.getSets()),
							Integer.parseInt(workoutInputPanel.getTime()),
							Integer.parseInt(workoutInputPanel.getWeight()),
							workoutInputPanel.getDate());
					
					if(i<=0) {
						closeFrame();
					}
					if(i==1) {
						JOptionPane.showMessageDialog(null, "Please Select a Exercise to record.");
					}
					if(i==2) {
						JOptionPane.showMessageDialog(null, "No Such Exercise Exists in the database.");
					}
					if(i==3||i==4) {
						JOptionPane.showMessageDialog(null, "There was a problem with user Authentication.");
					}
					if(i==6) {
						JOptionPane.showMessageDialog(null, "There was a problem parsing data on the method sent to the db.");
					}
					if(i==7) {
						JOptionPane.showMessageDialog(null, "Set the goal in the future");
					}
					if(i==9) {
						JOptionPane.showMessageDialog(null, "Only POSITIVE numbers are valid inputs for reps, sets, time, weight, and calories.");
					}
					System.out.println(i);
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Parsing int issue");
					JOptionPane.showMessageDialog(null, "Only numbers can be valid inputs for reps, sets, time, weight, and calories.");

				}
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFrame();
			}
		});
	}
	
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}