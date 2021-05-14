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
		JPanel labelPanel=new JPanel(); 
		JPanel inputPanel=new JPanel();
		
		JLabel exerciseL = new JLabel("Exercise |");
		JLabel repL = new JLabel("    Reps |");
		JLabel setL = new JLabel("Sets |");
		JLabel timeL = new JLabel("    Time     |");
		JLabel weightL = new JLabel("    Weight    |");
		JLabel calL = new JLabel("Calories |");
		JLabel dateL = new JLabel("Workout Date");
		DatabaseCommunication c=new DatabaseCommunication(Main.loggedUser, Main.conn);
		labelPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		JComboBox exercisesChoice=new JComboBox(c.getExercises().toArray());
		AutoCompletion.enable(exercisesChoice);
		
		HintTextField reps =new HintTextField("i.e. 3");
		HintTextField sets =new HintTextField("i.e. 8");
		HintTextField time =new HintTextField("i.e. 3 (in seconds)");
		HintTextField weight =new HintTextField("recorded in lbs.");
		HintTextField cal =new HintTextField("i.e. 300");
		DateTextField workoutDate=new DateTextField();
		
		JButton submitButton= new JButton("Submit");
		JButton closeButton = new JButton("Close");
		
		
		labelPanel.add(exerciseL);
		labelPanel.add(repL);
		labelPanel.add(setL);
		labelPanel.add(timeL);
		labelPanel.add(weightL);
		labelPanel.add(calL);
		labelPanel.add(dateL);
		
		inputPanel.add(exercisesChoice);
		inputPanel.add(reps);
		inputPanel.add(sets);
		inputPanel.add(time);
		inputPanel.add(weight);
		inputPanel.add(cal);
		inputPanel.add(workoutDate);
		this.add(labelPanel);
		this.add(inputPanel);
		this.add(submitButton);
		this.add(closeButton);
		
		labelPanel.setBounds(0, 40, this.getWidth(), 40);
		inputPanel.setBounds(0, 80, this.getWidth(), 40);
		submitButton.setBounds(this.getWidth()/2-40, inputPanel.getY()+40, 80, 30);
		closeButton.setBounds(this.getWidth()/2-40, submitButton.getY()+40, 80, 30);
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(reps.getText().equals("i.e. 3")) {reps.setText("0");}
					if(sets.getText().equals("i.e. 8")) {sets.setText("0");}
					if(time.getText().equals("i.e. 3 (in seconds)")) {time.setText("0");}
					if(weight.getText().equals("recorded in lbs.")) {weight.setText("0");}
					if(cal.getText().equals("i.e. 300")) {cal.setText("0");}
					int i=c.addWorkout(
							exercisesChoice.getSelectedItem().toString(), 
							Integer.parseInt(reps.getText()), 
							Integer.parseInt(sets.getText()),
							Integer.parseInt(time.getText()),
							Integer.parseInt(weight.getText()),
							Integer.parseInt(cal.getText()),
							workoutDate.getText());
					
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
	
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
