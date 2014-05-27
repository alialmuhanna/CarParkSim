package asgn2Simulators;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class GUISimulator extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtMaxCarSpaces;
	private JTextField txtMaxSmallCars;
	private JTextField txtMaxMotorcycles;
	private JTextField txtMaxQueueSize;
	private JTextField txtRandomSeed;
	private JTextField txtMeanStay;
	private JTextField txtSdStay;
	private JTextField txtCarProbability;
	private JTextField txtSmallCarProb;
	private JTextField txtMotorcycleProb;
	private JButton btnNewButton;

	private Log log;
	private CarPark cp;
	private Simulator sim;
	private String[] args;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISimulator window = new GUISimulator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUISimulator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 620, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 268, 419);
		frame.getContentPane().add(panel);

		JLabel lblCarParkParameters = new JLabel("Car Park Parameters");
		panel.add(lblCarParkParameters);

		txtMaxCarSpaces = new JTextField();
		txtMaxCarSpaces.setToolTipText("Max car spaces");
		panel.add(txtMaxCarSpaces);
		txtMaxCarSpaces.setColumns(10);

		txtMaxSmallCars = new JTextField();
		txtMaxSmallCars.setToolTipText("Max small cars");
		panel.add(txtMaxSmallCars);
		txtMaxSmallCars.setColumns(10);

		txtMaxMotorcycles = new JTextField();
		txtMaxMotorcycles.setToolTipText("Max motorcycles");
		panel.add(txtMaxMotorcycles);
		txtMaxMotorcycles.setColumns(10);

		txtMaxQueueSize = new JTextField();
		txtMaxQueueSize.setToolTipText("Max queue size");
		panel.add(txtMaxQueueSize);
		txtMaxQueueSize.setColumns(10);

		txtRandomSeed = new JTextField();
		txtRandomSeed.setToolTipText("Random seed");
		panel.add(txtRandomSeed);
		txtRandomSeed.setColumns(10);

		txtMeanStay = new JTextField();
		txtMeanStay.setToolTipText("Mean stay");
		panel.add(txtMeanStay);
		txtMeanStay.setColumns(10);

		txtSdStay = new JTextField();
		txtSdStay.setToolTipText("sd stay");
		panel.add(txtSdStay);
		txtSdStay.setColumns(10);

		txtCarProbability = new JTextField();
		txtCarProbability.setToolTipText("Car probability");
		panel.add(txtCarProbability);
		txtCarProbability.setColumns(10);

		txtSmallCarProb = new JTextField();
		txtSmallCarProb.setToolTipText("Small car prob");
		panel.add(txtSmallCarProb);
		txtSmallCarProb.setColumns(10);

		txtMotorcycleProb = new JTextField();
		txtMotorcycleProb.setToolTipText("MotorCycle prob");
		panel.add(txtMotorcycleProb);
		txtMotorcycleProb.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(286, 6, 328, 378);
		frame.getContentPane().add(panel_1);

		JTextArea textArea = new JTextArea();
		panel_1.add(textArea);

		JTextArea txtrSimulation = new JTextArea();
		txtrSimulation.setText("Simulation will be displayed here");
		panel_1.add(txtrSimulation);

		btnNewButton = new JButton("Start Simulation");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// default values
				if (txtMaxCarSpaces.getText().isEmpty()
						&& txtMaxSmallCars.getText().isEmpty()
						&& txtMaxMotorcycles.getText().isEmpty()
						&& txtMaxQueueSize.getText().isEmpty()
						&& txtRandomSeed.getText().isEmpty()
						&& txtMeanStay.getText().isEmpty()
						&& txtSdStay.getText().isEmpty()
						&& txtCarProbability.getText().isEmpty()
						&& txtSmallCarProb.getText().isEmpty()
						&& txtMotorcycleProb.getText().isEmpty()) {

					txtMaxCarSpaces.setText(Integer
							.toString(Constants.DEFAULT_MAX_CAR_SPACES));
					txtMaxSmallCars.setText(Integer
							.toString(Constants.DEFAULT_MAX_SMALL_CAR_SPACES));
					txtMaxMotorcycles.setText(Integer
							.toString(Constants.DEFAULT_MAX_MOTORCYCLE_SPACES));
					txtMaxQueueSize.setText(Integer
							.toString(Constants.DEFAULT_MAX_QUEUE_SIZE));
					txtRandomSeed.setText(Integer
							.toString(Constants.DEFAULT_SEED));
					txtMeanStay.setText(Double
							.toString(Constants.DEFAULT_INTENDED_STAY_MEAN));
					txtSdStay.setText(Double
							.toString(Constants.DEFAULT_INTENDED_STAY_SD));
					txtCarProbability.setText(Double
							.toString(Constants.DEFAULT_CAR_PROB));
					txtSmallCarProb.setText(Double
							.toString(Constants.DEFAULT_SMALL_CAR_PROB));
					txtMotorcycleProb.setText(Double
							.toString(Constants.DEFAULT_MOTORCYCLE_PROB));

					args = new String[] { txtMaxCarSpaces.getText(),
							txtMaxSmallCars.getText(),
							txtMaxMotorcycles.getText(),
							txtMaxQueueSize.getText(),
							txtRandomSeed.getText(), txtMeanStay.getText(),
							txtSdStay.getText(),
							txtCarProbability.getText(),
							txtSmallCarProb.getText(),
							txtMotorcycleProb.getText() };
					
					
					
				}

				// users values
				else {
					args = new String[] { txtMaxCarSpaces.getText(),
							txtMaxSmallCars.getText(),
							txtMaxMotorcycles.getText(),
							txtMaxQueueSize.getText(), txtRandomSeed.getText(),
							txtMeanStay.getText(), txtSdStay.getText(),
							txtCarProbability.getText(),
							txtSmallCarProb.getText(),
							txtMotorcycleProb.getText() };
				}
				
				try {
					SimulationRunner.main(args);
				} catch (SimulationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton.setBounds(286, 396, 160, 29);
		frame.getContentPane().add(btnNewButton);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
