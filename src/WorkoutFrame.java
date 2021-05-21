import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.AutoCompletion;
import util.DateTextField;
import util.HintTextField;

public class WorkoutFrame extends JFrame{

	
	public WorkoutFrame() {
		this.setSize(700, 400);
		this.setLayout(null);
		
		DatabaseCommunication c= new DatabaseCommunication(Main.loggedUser, Main.conn);
		
		WorkoutInputPanel workoutInputPanel=new WorkoutInputPanel("Workout Date");
		
		JButton submitButton= new JButton("Submit");
		JButton closeButton = new JButton("Close");
		
		this.add(workoutInputPanel);
		this.add(submitButton);
		this.add(closeButton);
		
		workoutInputPanel.setBounds(0, 80, this.getWidth(), 80);
		workoutInputPanel.setVisible(true);
		submitButton.setBounds(this.getWidth()/2-40, workoutInputPanel.getY()+80, 80, 30);
		closeButton.setBounds(this.getWidth()/2-40, submitButton.getY()+40, 80, 30);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int i=c.addWorkout(
							workoutInputPanel.getExercise(), 
							Integer.parseInt(workoutInputPanel.getReps()), 
							Integer.parseInt(workoutInputPanel.getSets()),
							Integer.parseInt(workoutInputPanel.getTime()),
							Integer.parseInt(workoutInputPanel.getWeight()),
							Integer.parseInt(workoutInputPanel.getCal()),
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
					if(i==8) {
						closeFrame();
						JOptionPane.showMessageDialog(null, "CONGRATULATIONS! You have reached your goal for this Exercise! Modify your goal to reach greater heights!");
					}
					if(i==9) {
						JOptionPane.showMessageDialog(null, "Only POSITIVE numbers are valid inputs for reps, sets, time, weight, and calories.");
					}
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
	
	public WorkoutFrame(String exerciseN, String inputDate) {
		this.setSize(700, 400);
		this.setLayout(null);
		
		DatabaseCommunication c= new DatabaseCommunication(Main.loggedUser, Main.conn);
		
		WorkoutInputPanel workoutInputPanel=new WorkoutInputPanel("Workout Date", c.getCurrentRecStats(exerciseN, inputDate));
		
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
				// TODO Auto-generated method stub
				int i=c.deleteWorkout(exerciseN, inputDate);
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
					System.out.println("OK:::Panel"+workoutInputPanel);
					System.out.println(	"OK:::"+						workoutInputPanel.getExercise()+
							workoutInputPanel.getReps()+
							workoutInputPanel.getSets()+
							workoutInputPanel.getTime()+
							workoutInputPanel.getWeight()+
							workoutInputPanel.getCal()+
							workoutInputPanel.getDate());
					int i=c.modWorkout(
							workoutInputPanel.getExercise(), 
							Integer.parseInt(workoutInputPanel.getReps()), 
							Integer.parseInt(workoutInputPanel.getSets()),
							Integer.parseInt(workoutInputPanel.getTime()),
							Integer.parseInt(workoutInputPanel.getWeight()),
							Integer.parseInt(workoutInputPanel.getCal()),
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
					if(i==8) {
						closeFrame();
						JOptionPane.showMessageDialog(null, "CONGRATULATIONS! You have reached your goal for this Exercise! Modify your goal to reach greater heights!");
					}
					if(i==9) {
						JOptionPane.showMessageDialog(null, "Only POSITIVE numbers are valid inputs for reps, sets, time, weight, and calories.");
					}
					if(i==11) {
						JOptionPane.showMessageDialog(null, "Exercise record does not exist in the database");
					}
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Parsing issue from modW");
					JOptionPane.showMessageDialog(null, "Only numbers can be valid inputs for reps, sets, time, weight, and calories.");

				}
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFrame();
			}
		});	}

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
