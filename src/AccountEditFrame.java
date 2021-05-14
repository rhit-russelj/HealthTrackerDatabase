import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.DateTextField;
import util.HintTextField;

public class AccountEditFrame extends JFrame{

	public AccountEditFrame(String user, HealthTrackerDBConnection conn) {
	DatabaseCommunication c=new DatabaseCommunication(user, conn);
	JPanel mainP=new JPanel();
	mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
		
	JPanel emailP=new JPanel();
	emailP.setLayout(new BoxLayout(emailP, BoxLayout.X_AXIS));
	JLabel emailL=new JLabel("Email: ");
	HintTextField emailF=new HintTextField("Current Email will be inserted here");
	JButton emailB=new JButton("Change Email");
	emailP.add(emailL);
	emailP.add(emailF);
	emailP.add(emailB);
	
	JPanel nameP=new JPanel();
	nameP.setLayout(new BoxLayout(nameP, BoxLayout.X_AXIS));
	JLabel nameL=new JLabel("Name: ");
	HintTextField nameF=new HintTextField("Current Name will be inserted here");
	JButton nameB=new JButton("Change Name");
	nameP.add(nameL);
	nameP.add(nameF);
	nameP.add(nameB);
	
	JPanel sexP=new JPanel();
	sexP.setLayout(new BoxLayout(sexP, BoxLayout.X_AXIS));
	JLabel sexL=new JLabel("Sex: ");
	HintTextField sexF=new HintTextField("Current Sex will be inserted here");
	JButton sexB=new JButton("Change Sex");
	sexP.add(sexL);
	sexP.add(sexF);
	sexP.add(sexB);
	
	JPanel dobP=new JPanel();
	dobP.setLayout(new BoxLayout(dobP, BoxLayout.X_AXIS));
	JLabel dobL=new JLabel("Date of Birth: ");
	DateTextField dobF=new DateTextField();
	JButton dobB=new JButton("Change Date of Birth");
	dobP.add(dobL);
	dobP.add(dobF);
	dobP.add(dobB);
	
	JPanel passP=new JPanel();
	passP.setLayout(new BoxLayout(passP, BoxLayout.X_AXIS));
	JLabel passL=new JLabel("Password");
	HintTextField passF=new HintTextField("(Not shown)");
	JButton passB=new JButton("Change Password");
	passP.add(passL);
	passP.add(passF);
	passP.add(passB);
	
	
	
	mainP.add(emailP);
	mainP.add(nameP);
	mainP.add(sexP);
	mainP.add(dobP);
	mainP.add(passP);
	this.add(mainP);
	this.pack();
	
	emailB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int i=c.modifyAccount(emailF.getText(), null, null, dobF.getText(), null);
			handleErrors(i);
		}
	});
	
	nameB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int i=c.modifyAccount(null, nameF.getText(), null, dobF.getText(), null);
			handleErrors(i);
		}
	});
	
	sexB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int i=c.modifyAccount(null, null,sexF.getText(), dobF.getText(), null);
			handleErrors(i);
		}
	});
	
	dobB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int i=c.modifyAccount(null, null, null,dobF.getText(), null);
			handleErrors(i);
		}
	});
	
	passB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			byte[] salt=c.getSalt(user);
			int i=c.modifyAccount(null, null, null, dobF.getText(), AppLogin.hashPassword(salt, passF.getText()));
			handleErrors(i);
		}
	});
	
	}
	
	private void handleErrors(int error) {
		if(error==0) JOptionPane.showMessageDialog(null, "Success");
		if(error==1) JOptionPane.showMessageDialog(null, "Invalid input!");
		if(error==2) JOptionPane.showMessageDialog(null, "Account already in use!");
		if(error==3) JOptionPane.showMessageDialog(null, "Error 3");
		if(error>3 || error<0) JOptionPane.showMessageDialog(null, "Someting went wrong!");
	}
	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
