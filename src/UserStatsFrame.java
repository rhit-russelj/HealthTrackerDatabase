import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.HintTextField;

public class UserStatsFrame extends JFrame {

	public UserStatsFrame() {
		this.setSize(700, 400);
		this.setLayout(null);
		JPanel labelPanel = new JPanel();
		JPanel inputPanel = new JPanel();

	//	JLabel userIDL = new JLabel("UserID |");
		JLabel heightL = new JLabel("    Height |");
		JLabel BMIL = new JLabel("BMI |");
		JLabel weightL = new JLabel("    Weight    |");
		DatabaseCommunication c = new DatabaseCommunication(Main.loggedUser, Main.conn);
		labelPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());

	//	HintTextField UserID = new HintTextField("i.e. 1");
		HintTextField Height = new HintTextField("i.e. 5.5");
		HintTextField BMI = new HintTextField("20");
		HintTextField Weight = new HintTextField("Recorded in lbs.");

		JButton submitButton = new JButton("Submit");
		JButton closeButton = new JButton("Close");

	//	labelPanel.add(userIDL);
		labelPanel.add(heightL);
		labelPanel.add(BMIL);
		labelPanel.add(weightL);

	//	inputPanel.add(UserID);
		inputPanel.add(Height);
		inputPanel.add(BMI);
		inputPanel.add(Weight);
		this.add(labelPanel);
		this.add(inputPanel);
		this.add(submitButton);
		this.add(closeButton);

		labelPanel.setBounds(0, 40, this.getWidth(), 40);
		inputPanel.setBounds(0, 80, this.getWidth(), 40);
		submitButton.setBounds(this.getWidth() / 2 - 40, inputPanel.getY() + 40, 80, 30);
		closeButton.setBounds(this.getWidth() / 2 - 40, submitButton.getY() + 40, 80, 30);

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//if (UserID.getText().equals("i.e. 1")) {
					//	UserID.setText("0");
					//}
					if (Height.getText().equals("i.e. 5.5")) {
						Height.setText("0");
					}
					if (BMI.getText().equals("i.e. 3 (in seconds)")) {
						BMI.setText("0");
					}
					if (Weight.getText().equals("recorded in lbs.")) {
						Weight.setText("0");
					}
					int i = c.addStats(c.getUser(Main.loggedUser), Integer.parseInt(Height.getText()),
							Integer.parseInt(BMI.getText()), Integer.parseInt(Weight.getText()));

					if (i <= 0) {
						closeFrame();
					}
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "Please enter a valid input for UserID");
					}
					if (i == 2 || i == 3) {
						JOptionPane.showMessageDialog(null, "There was a problem with user Authentication.");
					}
					if (i == 4) {
						JOptionPane.showMessageDialog(null,
								"Only POSITIVE numbers are valid inputs for height, BMI, and weight.");
					}
					if (i == 5) {
						JOptionPane.showMessageDialog(null,
								"There was a problem parsing data on the method sent to the db.");
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Parsing int issue");
					JOptionPane.showMessageDialog(null,
							"Only numbers are valid inputs for height, BMI, and weight.");

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
		// closest I can find atm to remove the frame without affecting other aspects of
		// code
		this.setVisible(false);
	}

}
