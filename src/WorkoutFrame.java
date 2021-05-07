import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class WorkoutFrame extends JFrame{

	
	public WorkoutFrame() {
		JButton closeButton = new JButton("Close");
		this.add(closeButton);
		this.pack();
		
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
