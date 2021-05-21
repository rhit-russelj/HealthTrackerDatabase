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
<<<<<<< HEAD
		labelPanel.add(calL);
=======
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
		
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat unformattedDobString = new SimpleDateFormat("yyyy-mm-dd");
		Date unformatetedDob = null;
		try {
			unformatetedDob = unformattedDobString.parse(attr.get("date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String parsedDateString=parsedDateStringFormat.format(unformatetedDob);		
		Date parsedDate = null;
		try {
			parsedDate = parsedDateStringFormat.parse(parsedDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workoutDate=new DateTextField(parsedDate);
		
		
		labelPanel.add(exerciseL);
		labelPanel.add(repL);
		labelPanel.add(setL);
		labelPanel.add(timeL);
		labelPanel.add(weightL);
<<<<<<< HEAD
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
=======
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
		labelPanel.add(dateL);
		
		inputPanel.add(exercisesChoice);
		inputPanel.add(reps);
		inputPanel.add(sets);
		inputPanel.add(time);
		inputPanel.add(weight);
<<<<<<< HEAD
<<<<<<< HEAD
		inputPanel.add(cal);
=======
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
=======
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
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
<<<<<<< HEAD
<<<<<<< HEAD
		return (reps.getText().equals("i.e. 300")) ? "0" : reps.getText();
=======
		return (cal.getText().equals("i.e. 300")) ? "0" : reps.getText();
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
=======
		return (cal.getText().equals("i.e. 300")) ? "0" : reps.getText();
>>>>>>> parent of d76524f (implemented a lot of stuff mainly around modifying existing data)
	}
	
	public String getDate() {
		return workoutDate.getText();
	}
	
}
