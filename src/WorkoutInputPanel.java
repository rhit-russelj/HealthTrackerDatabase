import java.awt.FlowLayout;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
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
	private HashMap<String, String> attr=null;
	
	public WorkoutInputPanel(String GoalOrWeight) {
		JPanel labelPanel=new JPanel(); 
		JPanel inputPanel=new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel exerciseL = new JLabel("Exercise |");
		JLabel repL = new JLabel("    Reps   |");
		JLabel setL = new JLabel("Sets |");
		JLabel timeL = new JLabel("    Time     |");
		JLabel weightL = new JLabel("    Weight    |");
		JLabel calL=new JLabel();
		if(GoalOrWeight.equals("Workout Date"))	calL = new JLabel("Calories |");
		JLabel dateL = new JLabel(GoalOrWeight);
				
		labelPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		exercisesChoice=new JComboBox(new DatabaseCommunication(Main.loggedUser, Main.conn).getExercises().toArray());
		AutoCompletion.enable(exercisesChoice);
		
		reps =new HintTextField("i.e. 3");
		sets =new HintTextField("i.e. 8");
		time =new HintTextField("i.e. 3 (in seconds)");
		weight =new HintTextField("recorded in lbs.");
		if(GoalOrWeight.equals("Workout Date"))	cal =new HintTextField("i.e. 300");
		workoutDate=new DateTextField();
		
		
		labelPanel.add(exerciseL);
		labelPanel.add(repL);
		labelPanel.add(setL);
		labelPanel.add(timeL);
		labelPanel.add(weightL);
		if(GoalOrWeight.equals("Workout Date"))	labelPanel.add(calL);
		labelPanel.add(dateL);
		
		inputPanel.add(exercisesChoice);
		inputPanel.add(reps);
		inputPanel.add(sets);
		inputPanel.add(time);
		inputPanel.add(weight);
		if(GoalOrWeight.equals("Workout Date"))	inputPanel.add(cal);
		inputPanel.add(workoutDate);
		this.add(labelPanel);
		this.add(inputPanel);
		
		labelPanel.setBounds(0, 40, this.getWidth(), 40);
		inputPanel.setBounds(0, 80, this.getWidth(), 40);
	}
	
	public WorkoutInputPanel(String exOrG, HashMap<String, String> attributes) {
		this.attr=attributes;
		JPanel labelPanel=new JPanel(); 
		JPanel inputPanel=new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel exerciseL = new JLabel("Exercise |");
		JLabel repL = new JLabel("    Reps   |");
		JLabel setL = new JLabel("Sets |");
		JLabel timeL = new JLabel("    Time     |");
		JLabel weightL = new JLabel("    Weight    |");
		JLabel calL=new JLabel();
		if(exOrG.equals("Workout Date")) calL = new JLabel("Calories |");
		JLabel dateL = new JLabel(exOrG);
				
		labelPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		String[] inputEx=new String[] {attr.get("exercise")};
		exercisesChoice=new JComboBox(inputEx);
		AutoCompletion.enable(exercisesChoice);
		
		for(String i: attr.keySet()) {
			System.out.println(attr.get(i));
		}
		reps =new HintTextField(attr.get("reps"));
		sets =new HintTextField(attr.get("sets"));
		time =new HintTextField(attr.get("time"));
		weight =new HintTextField(attr.get("weight"));		
		if(exOrG.equals("Workout Date"))	cal =new HintTextField(attr.get("cal"));
		
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date parsedDate = null;
		try {
			parsedDate = parsedDateStringFormat.parse(attr.get("date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workoutDate=new DateTextField(parsedDate);
		workoutDate.setEditable(false);
		
		
		labelPanel.add(exerciseL);
		labelPanel.add(repL);
		labelPanel.add(setL);
		labelPanel.add(timeL);
		labelPanel.add(weightL);
		if(exOrG.equals("Workout Date"))	labelPanel.add(calL);
		labelPanel.add(dateL);
		
		inputPanel.add(exercisesChoice);
		inputPanel.add(reps);
		inputPanel.add(sets);
		inputPanel.add(time);
		inputPanel.add(weight);
		if(exOrG.equals("Workout Date"))	inputPanel.add(cal);
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
		String rs=reps.getText();
		if(rs.equals("i.e. 3")) {
			rs="0";
		}
		if(rs.equals("") && attr!=null) {
			rs=attr.get("reps");
		}
		return rs;
	}
	
	public String getSets() {
		String rs1=sets.getText();
		if(rs1.equals("i.e. 8")) {
			rs1="0";
		}
		if(rs1.equals("") && attr!=null) {
			rs1=attr.get("sets");
		}
		return rs1;
	}
	
	public String getTime() {
		String rs2=time.getText();
		if(rs2.equals("i.e. 3 (in seconds)")) {
			rs2="0";
		}
		if(rs2.equals("") && attr!=null) {
			rs2=attr.get("time");
		}
		return rs2;
	}
	
	public String getWeight() {
		String rs3=weight.getText();
		if(rs3.equals("recorded in lbs.")) {
			rs3="0";
		}
		if(rs3.equals("") && attr!=null) {
			rs3=attr.get("weight");
		}
		return rs3;
	}
	
	public String getCal() {
		String rs4=cal.getText();
		if(rs4.equals("i.e. 300")) {
			rs4="0";
		}
		if(rs4.equals("") && attr!=null) {
			rs4=attr.get("cal");
		}
		return rs4;
	}
	
	public String getDate() {
		return workoutDate.getText();
	}
}
