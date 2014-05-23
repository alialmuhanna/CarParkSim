/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * @author hogan
 * 
 */
public class CarParkTests {

	// with default parameters
	private CarPark cp;

	private CarPark cp1;

	private Car car1;

	private Car car2;

	private Car car3;

	private MotorCycle mc;

	private Simulator sim;

	private static final int VALID_ARRIVAL_TIME = 2;

	private static final int ACTUAL_DEPARTURE_TIME = 60;

	private static final int VALID_DURATION_STAY = 20;

	private static final int EXIT_TIME = 5;

	private static final int LARGE_TIME = 100;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		cp = new CarPark();
		cp1 = new CarPark(2, 1, 1, 1);
		car1 = new Car("C1", VALID_ARRIVAL_TIME, false);
		car2 = new Car("C2", VALID_ARRIVAL_TIME, false);
		car3 = new Car("S2", VALID_ARRIVAL_TIME, true);
		mc = new MotorCycle("M1", VALID_ARRIVAL_TIME);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#archiveDepartingVehicles(int, boolean)}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testArchiveDepartingVehicles() throws SimulationException,
			VehicleException {
		fillCarParkAndQueue();
		cp1.archiveDepartingVehicles(car1.getDepartureTime(), false);
		assertEquals(cp1.getNumCars(), 2);
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#archiveNewVehicle(asgn2Vehicles.Vehicle)}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicle_VehicleQueued()
			throws SimulationException, VehicleException {
		fillCarParkAndQueue();
		cp1.archiveNewVehicle(car3);
	}
	
	/**
	 * 
	 * @throws SimulationException
	 * @throws VehicleException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicle_VehicleParked()
			throws SimulationException, VehicleException {
		fillCarParkAndQueue();
		cp1.archiveNewVehicle(car1);
	}

	/**
	 * Archive new vehicle because spaces and queue are full
	 * @throws SimulationException
	 * @throws VehicleException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testArchiveNewVehicle() throws SimulationException,
			VehicleException {
		fillCarParkAndQueue();
		Vehicle car4 = new Car("C4", VALID_ARRIVAL_TIME, false);
		cp1.archiveNewVehicle(car4);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveQueueFailures(int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testArchiveQueueFailures() throws SimulationException, VehicleException {
		fillCarParkAndQueue();
		cp1.archiveQueueFailures(LARGE_TIME);
		assertTrue(cp1.queueEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkEmpty()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testCarParkEmpty() throws SimulationException, VehicleException {
		cp.parkVehicle(car1, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		assertFalse(cp.carParkEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkFull()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testCarParkFull() throws SimulationException, VehicleException {
		fillCarPark();
		assertTrue(cp1.carParkFull());
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#enterQueue(asgn2Vehicles.Vehicle)}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testEnterQueue() throws SimulationException, VehicleException {
		cp1.enterQueue(car1);
		assertEquals(cp1.numVehiclesInQueue(), 1);
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#exitQueue(asgn2Vehicles.Vehicle, int)}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testExitQueue() throws SimulationException, VehicleException {
		cp1.enterQueue(car1);
		cp1.exitQueue(car1, EXIT_TIME);
		assertEquals(cp1.numVehiclesInQueue(), 0);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#finalState()}.
	 */
	@Test
	public void testFinalState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumCars()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testGetNumCars() throws SimulationException, VehicleException {
		fillCarPark();
		assertEquals(cp1.getNumCars(), 3);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumMotorCycles()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testGetNumMotorCycles() throws SimulationException,
			VehicleException {
		fillCarPark();
		assertEquals(cp1.getNumMotorCycles(), 1);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumSmallCars()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testGetNumSmallCars() throws SimulationException,
			VehicleException {
		fillCarPark();
		assertEquals(cp1.getNumSmallCars(), 1);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getStatus(int)}.
	 */
	@Test
	public void testGetStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#initialState()}.
	 */
	@Test
	public void testInitialState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#numVehiclesInQueue()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testNumVehiclesInQueue() throws SimulationException,
			VehicleException {
		fillCarParkAndQueue();
		assertEquals(cp1.numVehiclesInQueue(), 1);
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#parkVehicle(asgn2Vehicles.Vehicle, int, int)}
	 * .
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testParkVehicle() throws SimulationException, VehicleException {
		cp1.parkVehicle(car1, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		assertEquals(cp1.getNumCars(), 1);
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#processQueue(int, asgn2Simulators.Simulator)}
	 * .
	 */
	@Test
	public void testProcessQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueEmpty()}.
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testQueueEmpty() {
		assertTrue(cp1.queueEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueFull()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testQueueFull() throws SimulationException, VehicleException {
		fillCarParkAndQueue();
		assertTrue(cp1.queueFull());
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#spacesAvailable(asgn2Vehicles.Vehicle)}.
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testSpacesAvailable_MC() {
		assertTrue(cp1.spacesAvailable(mc));
	}

	/**
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testSpacesAvailable_GenCar() {
		assertTrue(cp1.spacesAvailable(car1));
	}

	/**
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testSpacesAvailable_SmallCar() {
		assertTrue(cp1.spacesAvailable(car3));
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#tryProcessNewVehicles(int, asgn2Simulators.Simulator)}
	 * .
	 * 
	 * @throws SimulationException
	 * @throws VehicleException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testTryProcessNewVehicles_Car() throws VehicleException,
			SimulationException {
		sim = new Simulator();
		cp1.tryProcessNewVehicles(VALID_ARRIVAL_TIME, sim);
		assertEquals(cp1.getNumCars(), 1);
	}
	
	/**
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testTryProcessNewVehicles_MotorCycle() throws VehicleException,
			SimulationException {
		sim = new Simulator();
		cp1.tryProcessNewVehicles(VALID_ARRIVAL_TIME, sim);
		assertEquals(cp1.getNumMotorCycles(), 1);
	}

	/**
	 * Test method for
	 * {@link asgn2CarParks.CarPark#unparkVehicle(asgn2Vehicles.Vehicle, int)}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 * @author Ali Almuhanna - 8965048
	 */
	@Test
	public void testUnparkVehicle() throws SimulationException,
			VehicleException {
		cp1.parkVehicle(car1, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.unparkVehicle(car1, ACTUAL_DEPARTURE_TIME);
		assertTrue(cp1.carParkEmpty());
	}

	/**
	 * Fill cp1
	 * 
	 * @throws SimulationException
	 * @throws VehicleException
	 * @author Ali Almuhanna - 8965048
	 */
	private void fillCarPark() throws SimulationException, VehicleException {
		cp1.parkVehicle(car1, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.parkVehicle(car2, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.parkVehicle(car3, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.parkVehicle(mc, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
	}

	/**
	 * Fill cp1 and queue
	 * 
	 * @throws SimulationException
	 * @throws VehicleException
	 * @author Ali Almuhanna - 8965048
	 */
	private void fillCarParkAndQueue() throws SimulationException,
			VehicleException {
		cp1.parkVehicle(car1, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.parkVehicle(car2, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.parkVehicle(mc, VALID_ARRIVAL_TIME, VALID_DURATION_STAY);
		cp1.enterQueue(car3);
	}

}
