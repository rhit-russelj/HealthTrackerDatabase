import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.HintTextField;

public class LoginPanel extends JPanel{
	private HintTextField email;
	private HintTextField pass;
	private JButton logB;
	private JButton regB;
	
	public LoginPanel() {
		this.setSize(400,400);
		this.setLayout(null);
		
		JLabel lgn = new JLabel("Sign In");
		lgn.setFont(lgn.getFont().deriveFont(32.0f));

//		JLabel emL = new JLabel("Email:");
		email = new HintTextField("Email: i.e. JohnSmith@gmail.com");
		
		//JLabel psL = new JLabel("Password");
		pass= new HintTextField("Password");
		logB=new JButton("Login");
		JLabel regL = new JLabel("Dont have an account? Register now!\n");
		regB=new JButton("Register");
		
		
		lgn.setBounds(this.getWidth()/2-70, 20, 140, 50);
		email.setBounds(this.getWidth()/2-70, lgn.getY()+60, 140, 35);
		pass.setBounds(this.getWidth()/2-70, email.getY()+60, 140, 35);
		logB.setBounds(this.getWidth()/2-35, pass.getY()+40, 70, 40);
		regL.setBounds(this.getWidth()/2-110, logB.getY()+60, 220, 35);
		regB.setBounds(this.getWidth()/2-50, regL.getY()+25, 100, 20);

		
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
					Main.loggedUser =email.getText();
					user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				reg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
