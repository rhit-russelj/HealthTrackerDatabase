import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import util.HintTextField;

public class HomePage extends JFrame{
	public HomePage(String user, HealthTrackerDBConnection conn) {
		// TODO Auto-generated constructor stub
		this.setSize(1000,1000);
		DatabaseCommunication c=new DatabaseCommunication(user, conn);
		
		JPanel mainP=new JPanel();
		mainP.setLayout(null);
		JLabel mainL=new JLabel("Welcome "+c.getName()+ "!");
		mainL.setFont(new Font("Verdana", Font.BOLD, 40));
		mainL.setAlignmentX(CENTER_ALIGNMENT);

		
		JPanel statsPanel=new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		HashMap<String, String>userStats=new HashMap<String, String>();
		userStats=c.getUserStats();
		System.out.println("Welcome "+user);
		System.out.println(userStats.get("Height")+userStats.get("BMI")+userStats.get("Weight"));
		JLabel statsLabel=new JLabel("User Body Stats:");
		JLabel heightL=new JLabel("Height: "+userStats.get("Height"));
		JLabel bmiL=new JLabel("Body Mass Index (BMI): "+userStats.get("BMI"));
		JLabel weightL=new JLabel("Weight: "+userStats.get("Weight"));
		statsPanel.add(statsLabel);
		statsPanel.add(heightL);
		statsPanel.add(bmiL);
		statsPanel.add(weightL);
		for(Component j: statsPanel.getComponents()) {
			j.setFont(new Font("Verdana", Font.BOLD, 17));
		}
		
		
		JButton addWorkout=new JButton("Add Workout Session");
		
		ArrayList<String> exerL=new ArrayList<String>();
		try {
			exerL=c.getExercises();
		} catch (Exception e) {
			System.out.println("Problem Retrieving Exercise");
		}
		JComboBox exerCombo=new JComboBox(exerL.toArray());
		
		JFreeChart chart = createChart(c.getCords("Back Extension"));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);

		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		JButton updateStats=new JButton("Update Body Stats");
		JButton addGoalButton = new JButton("Add Goal");
		JButton modGoalButton= new JButton("Modify Goal");
		JButton editAccButton = new JButton("Edit Account");
		JButton editWorkoutButton = new JButton("Edit Previous Workout");
		JButton signOut = new JButton("Log Out");
		mainL.setBounds(0, 20, this.getWidth(), 40);		
		addWorkout.setBounds(2*this.getWidth()/3-this.getWidth()/8, this.getHeight()/4, this.getWidth()/4, 100);
		statsPanel.setBounds(0, this.getHeight()/4, this.getWidth()/2, 200);
		buttonPanel.setBounds(0, this.getHeight()/2, this.getWidth()/2, 300);
		
		exerCombo.setBounds(this.getWidth()/3+30, this.getHeight()/2-110, 200, 70/2);
		panel.setBounds(this.getWidth()/3+30, this.getHeight()/2-70, this.getWidth()/2, this.getHeight()/2);
		
		
		buttonPanel.add(addGoalButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonPanel.add(modGoalButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonPanel.add(editWorkoutButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonPanel.add(updateStats);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonPanel.add(editAccButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonPanel.add(signOut);
		for(Component j: buttonPanel.getComponents()) {
			j.setFont(new Font("Verdana", Font.BOLD, 17));
		}
		
		mainP.add(exerCombo);
		mainP.add(panel);
		mainP.add(mainL);
		mainP.add(addWorkout);
		mainP.add(statsPanel);
		mainP.add(buttonPanel);
		this.add(mainP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds Action to add workout for a Health User. Opens a new JFrame to add information
		addWorkout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WorkoutFrame workoutPopUp = new WorkoutFrame();
				workoutPopUp.setVisible(true);
				workoutPopUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
			}
		});
		
		modGoalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame=new JFrame("Choose Goal");
				ArrayList<String> gls=new ArrayList<String>();
				try {
					gls=c.getGoals();
				} catch (Exception e) {
					System.out.println("Problem Retrieving goals");
				}
				if(gls.size()==0) {
					JOptionPane.showMessageDialog(null, "You currently do not have any goals! Try setting one by clicking the Add Goal Button!");
					return;
				}
				JComboBox gCombo=new JComboBox(gls.toArray());
				frame.add(gCombo);
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

				gCombo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String exerciseN=(String) gCombo.getSelectedItem();
						GoalModifyingFrame gmF=new GoalModifyingFrame(exerciseN);
						gmF.setVisible(true);
						gmF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						frame.setVisible(false);
					}
				});
			}
		});
		
editWorkoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame exerciseFrame=new JFrame("Choose Exercise");
				ArrayList<String> recEs=new ArrayList<String>();
				try {
					recEs=c.getRecExercises();
				} catch (Exception e) {
					System.out.println("Problem Retrieving Recorded Exercises");
				}
				if(recEs.size()==0) {
					JOptionPane.showMessageDialog(null, "You currently do not have any Recorded Workout Sessions! Try creating one by clicking the Add Workout Button!");
					return;
				}
				JComboBox exCombo=new JComboBox(recEs.toArray());
				exerciseFrame.add(exCombo);
				exerciseFrame.pack();
				exerciseFrame.setVisible(true);
				exerciseFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				
				exCombo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg1) {
						JFrame dFrame=new JFrame("Choose Exercise");
						ArrayList<String> recDateList=new ArrayList<String>();
						String exerciseN=(String) exCombo.getSelectedItem();

						try {
							recDateList=c.getRecExercises(exerciseN);
						} catch (Exception e) {
							System.out.println("Problem Retrieving Recorded Exercises");
						}
						if(recDateList.size()==0) {
							JOptionPane.showMessageDialog(null, "You currently do not have any Recorded Workout Sessions of this Exercise! Try creating one by clicking the Add Workout Button!");
							return;
						}
						JComboBox dCombo=new JComboBox(recDateList.toArray());
						dFrame.add(dCombo);
						dFrame.pack();
						dFrame.setVisible(true);
						dFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
						exerciseFrame.setVisible(false);

						dCombo.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								String inputDate=(String) dCombo.getSelectedItem();
								WorkoutFrame mdF=new WorkoutFrame(exerciseN, inputDate);
								mdF.setVisible(true);
								mdF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
								dFrame.setVisible(false);
							}
						});
					}
				});				
			}
		});
		editAccButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame checkFrame=new JFrame();
				JPanel checkP=new JPanel();
				checkP.setLayout(new BoxLayout(checkP, BoxLayout.X_AXIS));
				JLabel passL=new JLabel("Password: ");
				HintTextField passF=new HintTextField("Password"); 
				JButton confirmB=new JButton("Confirm Password");
				
				checkP.add(passL);
				checkP.add(passF);
				checkP.add(confirmB);
				checkFrame.add(checkP);
				checkFrame.pack();
				checkFrame.setVisible(true);
				checkFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
				confirmB.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						boolean secInt=AppLogin.login(user, passF.getText());
						if(secInt) {
							AccountEditFrame accF=new AccountEditFrame(user, conn);
							accF.setVisible(true);
							accF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							checkFrame.setVisible(false);
						} else {
							checkFrame.dispose();
						}
					}
				});
			}
		});
		
		//Adds Action to Update a Health Users Stats. Opens a new JFrame to change information
		updateStats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserStatsFrame popUp = new UserStatsFrame();
				popUp.setVisible(true);
				popUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		
		addGoalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GoalFrame goalPopUp = new GoalFrame();
				goalPopUp.setVisible(true);
				goalPopUp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		
		signOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.conn.closeConnection();
				closeFrame();
				System.exit(EXIT_ON_CLOSE);
			}
		});
		
	}
	
	private static JFreeChart createChart(XYDataset dataset) {

	    JFreeChart chart = ChartFactory.createTimeSeriesChart(
	        "Exercise Diagram",  // title
	        "Date",             // x-axis label
	        "Recorded Value",   // y-axis label
	        dataset,            // data
	        true,               // create legend?
	        true,               // generate tooltips?
	        false               // generate URLs?
	    );

	    chart.setBackgroundPaint(Color.white);

	    XYPlot plot = (XYPlot) chart.getPlot();
	    plot.setBackgroundPaint(Color.lightGray);
	    plot.setDomainGridlinePaint(Color.white);
	    plot.setRangeGridlinePaint(Color.white);
	    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	    plot.setDomainCrosshairVisible(true);
	    plot.setRangeCrosshairVisible(true);

	    XYItemRenderer r = plot.getRenderer();
	    if (r instanceof XYLineAndShapeRenderer) {
	        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
	        renderer.setBaseShapesVisible(true);
	        renderer.setBaseShapesFilled(true);
	        renderer.setDrawSeriesLineAsPath(true);
	    }

	    DateAxis axis = (DateAxis) plot.getDomainAxis();
	    axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

	    return chart;

	}

	private void closeFrame() {
		//closest I can find atm to remove the frame without affecting other aspects of code
		this.setVisible(false);
	}
}
