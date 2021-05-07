import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import util.DateTextField;
import util.HintTextField;

public class RegisterFrame extends JFrame{
	private HintTextField userName;
	private JComboBox<String> sex;
	private DateTextField dob;
	private HintTextField email;
	private HintTextField password;
	private JButton registerButton;
	private final static int DEFAULT_WIDTH = 150;
	private final static int DEFAULT_HEIGHT = 35;
	
	public RegisterFrame() {
		
		
		this.setSize(400,400);
		JPanel body=new JPanel();
		body.setLayout(null);
		JLabel l2=new JLabel("Register");
		l2.setFont(l2.getFont().deriveFont(32.0f));


//		JLabel l2=new JLabel("Register");
		userName = new HintTextField("UserName");

		String[] choices = { "Male", "Female", "Other", "Prefer not to say"};
	//	JLabel l2=new JLabel("Register");
		sex = new JComboBox<String>(choices);
		
		JLabel date=new JLabel("Date of Birth");
		dob=new DateTextField();

		//JLabel l2=new JLabel("Register");
		email = new HintTextField("Email");

//		JLabel l2=new JLabel("Register");
		password = new HintTextField("Password");
		
		
		registerButton=new JButton("Finish registration");
		l2.setBounds(this.getWidth()/2-75, 20, 200, 50);
		userName.setBounds(this.getWidth()/2-75, l2.getY()+45, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		email.setBounds(this.getWidth()/2-75, userName.getY()+40, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		dob.setBounds(this.getWidth()/2-75, email.getY()+40, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		sex.setBounds(this.getWidth()/2-75, dob.getY()+40, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		password.setBounds(this.getWidth()/2-75, sex.getY()+40, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		registerButton.setBounds(this.getWidth()/2-70, password.getY()+40, 140, 40);



		
	
		body.add(l2);
		body.add(userName);
		body.add(sex);
		body.add(date);
		body.add(dob);
		body.add(email);
		body.add(password);
		body.add(registerButton);
		
		this.setBackground(Color.cyan);

		this.add(body);
		this.setAutoRequestFocus(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(AttemptRegister(userName.getText(), (String) sex.getSelectedItem(), dob.getText(), email.getText(), password.getText())) {
					HomePage user=new HomePage(email.getText(), Main.conn);
					Main.loggedUser = email.getText();
					user.setVisible(true);
					user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					closeFrame();	
				}
			}
		});
		
		
	}	
	
	private boolean AttemptRegister(String uname, String sex, String dob, String email, String pass) {
		return AppLogin.register(uname, sex, dob, email, pass);
	}
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}

}
