import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.AutoCompletion;
import util.DateTextField;
import util.HintTextField;

public class WorkoutInputPanel extends JPanel{

	private JComboBox exercisesChoice;
	private HintTextField reps;
	private HintTextField sets;
	private HintTextField time;
	private HintTextField weight;
	private HintTextField cal;
	private DateTextField workoutDate;
	
	
	public WorkoutInputPanel(String GoalOrWeight) {
		JPanel labelPanel=new JPanel(); 
		JPanel inputPanel=new JPanel();
		
		JLabel exerciseL = new JLabel("Exercise |");
		JLabel repL = new JLabel("    Reps   |");
		JLabel setL = new JLabel("Sets |");
		JLabel timeL = new JLabel("    Time     |");
		JLabel weightL = new JLabel("    Weight    |");
		JLabel calL = new JLabel("Calories |");
		JLabel dateL = new JLabel(GoalOrWeight);
				
		labelPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		exercisesChoice=new JComboBox(new DatabaseCommunication(Main.loggedUser, Main.conn).getExercises().toArray());
		AutoCompletion.enable(exercisesChoice);
		
		reps =new HintTextField("i.e. 3");
		sets =new HintTextField("i.e. 8");
		time =new HintTextField("i.e. 3 (in seconds)");
		weight =new HintTextField("recorded in lbs.");
		cal =new HintTextField("i.e. 300");
		workoutDate=new DateTextField();
		
		
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
		
		labelPanel.setBounds(0, 40, this.getWidth(), 40);
		inputPanel.setBounds(0, 80, this.getWidth(), 40);
	}
	
	public String getExercise() {
		return exercisesChoice.getSelectedItem().toString();
	}
	
	public String getReps() {
		return (reps.getText().equals("i.e. 3")) ? "0" : reps.getText();
	}
	
	public String getSets() {
		return (sets.getText().equals("i.e. 8")) ? "0" : sets.getText();
	}
	
	public String getTime() {
		return (time.getText().equals("i.e. 3 (in seconds)")) ? "0" : reps.getText();
	}
	
	public String getWeight() {
		return (weight.getText().equals("recorded in lbs.")) ? "0" : weight.getText();
	}
	
	public String getCal() {
		return (reps.getText().equals("i.e. 300")) ? "0" : reps.getText();
	}
	
	public String getDate() {
		return workoutDate.getText();
	}
	
}
