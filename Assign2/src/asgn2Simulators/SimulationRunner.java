/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 23/04/2014
 * 
 */
package asgn2Simulators;

import java.io.IOException;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

/**
 * Class to operate the simulation, taking parameters and utility methods from
 * the Simulator to control the CarPark, and using Log to provide a record of
 * operation.
 * 
 * @author hogan
 * 
 */
public class SimulationRunner {

	private CarPark carPark;
	private Simulator sim;
	private Log log;

	private static String[] args;

	private static int maxCarSpaces;
	private static int maxSmallCarSpaces;
	private static int maxMotorCycleSpaces;
	private static int maxQueueSize;
	private static int seed;
	private static double meanStay;
	private static double sdStay;
	private static double carProb;
	private static double smallCarProb;
	private static double mcProb;

	private static Simulator s;
	private static CarPark cp;

	/**
	 * Constructor just does initialisation
	 * 
	 * @param carPark
	 *            CarPark currently used
	 * @param sim
	 *            Simulator containing simulation parameters
	 * @param log
	 *            Log to provide logging services
	 */
	public SimulationRunner(CarPark carPark, Simulator sim, Log log) {
		this.carPark = carPark;
		this.sim = sim;
		this.log = log;
	}

	/**
	 * Method to run the simulation from start to finish. Exceptions are
	 * propagated upwards from Vehicle, Simulation and Log objects as necessary
	 * 
	 * @throws VehicleException
	 *             if Vehicle creation or operation constraints violated
	 * @throws SimulationException
	 *             if Simulation constraints are violated
	 * @throws IOException
	 *             on logging failures
	 */
	public void runSimulation() throws VehicleException, SimulationException,
			IOException {
		this.log.initialEntry(this.carPark, this.sim);
		for (int time = 0; time <= Constants.CLOSING_TIME; time++) {
			// queue elements exceed max waiting time
			if (!this.carPark.queueEmpty()) {
				this.carPark.archiveQueueFailures(time);
			}
			// vehicles whose time has expired
			if (!this.carPark.carParkEmpty()) {
				// force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				this.carPark.archiveDepartingVehicles(time, force);
			}
			// attempt to clear the queue
			if (!this.carPark.carParkFull()) {
				this.carPark.processQueue(time, this.sim);
			}
			// new vehicles from minute 1 until the last hour
			if (newVehiclesAllowed(time)) {
				this.carPark.tryProcessNewVehicles(time, this.sim);
			}
			// Log progress
			this.log.logEntry(time, this.carPark);
		}
		this.log.finalise(this.carPark);
	}

	/**
	 * Main program for the simulation
	 * 
	 * @param args
	 *            Arguments to the simulation
	 * @throws SimulationException
	 */
	public static void main(String[] args) throws SimulationException {

		// TODO: Implement Argument Processing

		if (args[0] == String.valueOf(Constants.DEFAULT_MAX_CAR_SPACES)
				&& args[1] == String
						.valueOf(Constants.DEFAULT_MAX_SMALL_CAR_SPACES)
				&& args[2] == String
						.valueOf(Constants.DEFAULT_MAX_MOTORCYCLE_SPACES)
				&& args[3] == String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE)
				&& args[4] == String.valueOf(Constants.DEFAULT_SEED)
				&& args[5] == String
						.valueOf(Constants.DEFAULT_INTENDED_STAY_MEAN)
				&& args[6] == String
						.valueOf(Constants.DEFAULT_INTENDED_STAY_SD)
				&& args[7] == String.valueOf(Constants.DEFAULT_CAR_PROB)
				&& args[8] == String.valueOf(Constants.DEFAULT_SMALL_CAR_PROB)
				&& args[9] == String.valueOf(Constants.DEFAULT_MOTORCYCLE_PROB)) {
			
			cp = new CarPark();
			s = null;
			
		}

		else {
			maxCarSpaces = Integer.parseInt(args[0]);
			maxSmallCarSpaces = Integer.parseInt(args[1]);
			maxMotorCycleSpaces = Integer.parseInt(args[2]);
			maxQueueSize = Integer.parseInt(args[3]);
			seed = Integer.parseInt(args[4]);
			meanStay = Double.parseDouble(args[5]);
			sdStay = Double.parseDouble(args[6]);
			carProb = Double.parseDouble(args[7]);
			smallCarProb = Double.parseDouble(args[8]);
			mcProb = Double.parseDouble(args[9]);

			cp = new CarPark(maxCarSpaces, maxSmallCarSpaces,
					maxMotorCycleSpaces, maxQueueSize);
			s = new Simulator(seed, meanStay, sdStay, carProb, smallCarProb,
					mcProb);
		}

		Log l = null;
		try {
			s = new Simulator();
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}

		// Run the simulation
		SimulationRunner sr = new SimulationRunner(cp, s, l);
		try {
			sr.runSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Helper method to determine if new vehicles are permitted
	 * 
	 * @param time
	 *            int holding current simulation time
	 * @return true if new vehicles permitted, false if not allowed due to
	 *         simulation constraints.
	 */
	private boolean newVehiclesAllowed(int time) {
		boolean allowed = (time >= 1);
		return allowed && (time <= (Constants.CLOSING_TIME - 60));
	}

}
