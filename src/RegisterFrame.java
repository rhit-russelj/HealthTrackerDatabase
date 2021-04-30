import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class RegisterFrame extends JFrame{
	private JTextPane userName;
	private JTextPane sex;
	private JTextPane dob;
	private JTextPane email;
	private JTextPane password;
	private JButton registerButton;
	
	public RegisterFrame() {
		this.setSize(400,400);
		
		JPanel body=new JPanel();
		JLabel l2=new JLabel("Register");
		

		userName = new JTextPane();
		userName.setText("UserName");

		sex = new JTextPane();
		sex.setText("Sex");
		
		dob = new JTextPane();
		dob.setText("Date Of Birth");
		
		email = new JTextPane();
		email.setText("Email");
		
		password = new JTextPane();
		password.setText("Password");
		
		
		registerButton=new JButton("Finish registration");
	
		
		body.add(l2);
		body.add(userName);
		body.add(sex);
		body.add(dob);
		body.add(email);
		body.add(password);
		body.add(registerButton);
		
		this.add(body);

		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Login");
				if(AttemptRegister(userName.getText(), sex.getText(), dob.getText(), email.getText(), password.getText())) {
					HomePage user=new HomePage(email.getText(), Main.conn);
					Main.loggedUser = email.getText();
					user.setVisible(true);
					closeFrame();
					
				}
			}
		});
		
		
	}	
	
	private boolean AttemptRegister(String uname, String sex, String dob, String email, String pass) {
		return AppLogin.register(uname, sex, Date.valueOf("2000-12-12"), email, pass);
	}
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}

}
