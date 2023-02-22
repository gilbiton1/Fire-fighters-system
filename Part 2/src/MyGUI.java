import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;

public class MyGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					MyGUI frame = new MyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(" Welcome To FatmaUnit Fire-Station");
		lblNewLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(87, 50, 571, 73);
		contentPane.add(lblNewLabel);
		
		JTextPane txtpnNumberOfEvent = new JTextPane();
		txtpnNumberOfEvent.setForeground(Color.BLUE);
		txtpnNumberOfEvent.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		txtpnNumberOfEvent.setText("number of fire trucks to add :");
		txtpnNumberOfEvent.setBounds(30, 311, 249, 33);
		contentPane.add(txtpnNumberOfEvent);
		
		JTextPane txtpnNumberOfPlanes = new JTextPane();
		txtpnNumberOfPlanes.setForeground(Color.BLUE);
		txtpnNumberOfPlanes.setText("number of planes to add:");
		txtpnNumberOfPlanes.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		txtpnNumberOfPlanes.setBounds(434, 311, 224, 33);
		contentPane.add(txtpnNumberOfPlanes);
		
		JTextPane txtpnNumberOfEvent_4 = new JTextPane();
		txtpnNumberOfEvent_4.setForeground(Color.BLUE);
		txtpnNumberOfEvent_4.setBackground(Color.WHITE);
		txtpnNumberOfEvent_4.setText("number of event commander to add :");
		txtpnNumberOfEvent_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		txtpnNumberOfEvent_4.setBounds(30, 180, 314, 33);
		contentPane.add(txtpnNumberOfEvent_4);
		
		JTextPane txtpnWorkTimeFor = new JTextPane();
		txtpnWorkTimeFor.setForeground(Color.BLUE);
		txtpnWorkTimeFor.setText("work time for station worker :");
		txtpnWorkTimeFor.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		txtpnWorkTimeFor.setBounds(434, 180, 249, 33);
		contentPane.add(txtpnWorkTimeFor);
		
		JSpinner trucksSpinner = new JSpinner();
		trucksSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null, 1.0));
		trucksSpinner.setBounds(68, 355, 152, 27);
		contentPane.add(trucksSpinner);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		btnNewButton.setForeground(Color.RED);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(482, 433, 127, 44);
		contentPane.add(btnNewButton);
		JSpinner workTime = new JSpinner();
		workTime.setModel(new SpinnerNumberModel(new Double(1), null, null,0.1));
		workTime.setBounds(482, 224, 142, 27);
		contentPane.add(workTime);
		JSpinner commandersSpinner = new JSpinner();
		commandersSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null,1));
		commandersSpinner.setBounds(68, 224, 152, 24);
	
		contentPane.add(commandersSpinner);
		
		JSpinner planes = new JSpinner();
		planes.setBounds(482, 355, 142, 27);
		contentPane.add(planes);
		
		JButton btnNewButton_1 = new JButton("Start");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int planesValue=(int) planes.getValue();
				double workTimeValue=(double)workTime.getValue();
				int commandNum= (int) commandersSpinner.getValue();
				int trucksValue=(int) trucksSpinner.getValue();
				if(commandNum>8) {
					commandNum=8;
					commandersSpinner.setValue(8);
				JOptionPane.showMessageDialog(null, "The number choosen is 8");
				}
				File file = new File("Configuration.txt");
				CallCenter a= new CallCenter(file,commandNum,workTimeValue,trucksValue,planesValue);
				
			}
		});
		btnNewButton_1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 22));
		btnNewButton_1.setForeground(Color.GREEN);
		btnNewButton_1.setBounds(124, 433, 131, 44);
		contentPane.add(btnNewButton_1);
		
	
		
	
	}
}
