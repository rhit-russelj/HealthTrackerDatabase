import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class LoginPanel extends JPanel{
	private JTextPane email;
	private JTextPane pass;
	private JButton logB;
	private JButton regB;
	
	public LoginPanel() {
		this.setSize(200,200);
		
		JLabel lgn = new JLabel("Sign In\n");
		email = new JTextPane();
		email.setText("Email");
		email.setSize(30,100);
		pass= new JTextPane();
		pass.setText("Password");
		pass.setSize(30,100);
		logB=new JButton("Login");
		JLabel regL = new JLabel("Dont have an account? Register now!\n");
		regB=new JButton("Register");

		
		this.add(lgn);
		this.add(email);
		this.add(pass);
		this.add(logB);
		this.add(regL);
		this.add(regB);
		logB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Login");
				if(attemptLogin(email.getText(), pass.getText())) {
					HomePage user=new HomePage(email.getText(), Main.conn);
					Main.loggedUser = email.getText();
					user.setVisible(true);
					closeFrame();
					
				}
			}

		});
		regB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RegisterFrame reg=new RegisterFrame();
				reg.setVisible(true);
				closeFrame();
			}
		});
		
	}
	
	private boolean attemptLogin(String uname, String pass) {
		return AppLogin.login(uname, pass);
	}
	

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		Main.mainScreen.setVisible(false);
	}
	

	
}
