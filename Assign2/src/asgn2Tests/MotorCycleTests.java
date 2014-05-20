/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.*;
import asgn2Vehicles.*;

/**
 * @author hogan
 * 
 */
public class MotorCycleTests {

	private MotorCycle motorCycle;
	private static final int VALID_DURATION_STAY = 20;
	private static final int VALID_ARRIVAL_TIME = 2;
	private static final int INVALID_ARRIVAL_TIME = -1;
	private static final int VALID_PARKING_TIME = 2;
	private static final int INVALID_PARKING_TIME = -1;
	private static final int INVALID_DURATION_STAY = 0;
	private static final int ACTUAL_DEPARTURE_TIME = 60;
	private static final int VALID_EXIT_TIME = 5;
	private static final int LARGE_PARKING_TIME = 5;
	private static final int SMALL_EXIT_TIME = 1;
	private static final int SMALL_DEP_TIME = 1;
	private static final int LARGE_EXIT_TIME = 30;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		motorCycle = new MotorCycle("MC1", VALID_ARRIVAL_TIME);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test valid motorcycle constructor
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testMotorCycleConstructor() throws VehicleException {
		motorCycle = new MotorCycle("MC2", VALID_ARRIVAL_TIME);
	}

	/**
	 * Test invalid arrival time for motorcycle
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testMotorCycleConstructor_InvalidArrivalTime()
			throws VehicleException {
		new MotorCycle("MC2", INVALID_ARRIVAL_TIME);
	}

	/**
	 * Test entering a MotorCycle into a parking state
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testEnterParkState() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(motorCycle.isParked());
	}

	/**
	 * Test entering a MotorCycle into a parking state with invalid parkingTime
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testEnterParkState_InvalidParkingTime() throws VehicleException {
		motorCycle.enterParkedState(INVALID_PARKING_TIME, VALID_DURATION_STAY);
	}

	/**
	 * Test entering a MotorCycle into a parking state with invalid duration
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testEnterParkState_InvalidDuration() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, INVALID_DURATION_STAY);
	}

	/**
	 * Test entering a MotorCycle into a queued state
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException {
		motorCycle.enterQueuedState();
		assertTrue(motorCycle.isQueued());
	}

	/**
	 * Test entering a parked MotorCycle into a queued state
	 */
	@Test(expected = VehicleException.class)
	public void testEnterQueuedState_Parked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.enterQueuedState();
	}

	/**
	 * Test entering a queued MotorCycle into a queued state
	 */
	@Test(expected = VehicleException.class)
	public void testEnterQueuedState_Queued() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.enterQueuedState();
	}

	/**
	 * Test exiting a MotorCycle from parking
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testExitParking() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertTrue(motorCycle.wasParked());
	}

	/**
	 * Test exiting a queued MotorCycle from parking
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitParking_Queued() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
	}

	/**
	 * Test exiting a queued MotorCycle from stateless
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitParking_NotParked() throws VehicleException {
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
	}

	/**
	 * Test exiting a parked MotorCycle where departure time is less than
	 * parking time.
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitParking_InvalidDepTime() throws VehicleException {
		motorCycle.enterParkedState(LARGE_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitParkedState(SMALL_DEP_TIME);
	}

	/**
	 * Test exiting a MotorCycle from queue
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testExitQueue() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitQueuedState(VALID_EXIT_TIME);
		assertTrue(motorCycle.wasQueued());
	}

	/**
	 * Test exiting a MotorCycle from queue
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitQueue_Parked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitQueuedState(VALID_EXIT_TIME);
	}

	/**
	 * Test exiting a MotorCycle from stateless
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitQueue_NotQueued() throws VehicleException {
		motorCycle.exitQueuedState(VALID_EXIT_TIME);
	}

	/**
	 * Test exiting a MotorCycle from stateless
	 * 
	 * @throws VehicleException
	 */
	@Test(expected = VehicleException.class)
	public void testExitQueue_InvalidExitTime() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitQueuedState(SMALL_EXIT_TIME);
	}

	@Test
	public void testGetArrTime() {
		assertEquals(motorCycle.getArrivalTime(), VALID_ARRIVAL_TIME);
	}

	/**
	 * Test getting departureTime, in this case the MotorCycle has not parked
	 */
	@Test
	public void testGetDepTime() {
		assertEquals(motorCycle.getDepartureTime(), 0);
	}

	/**
	 * Test getting departureTime, in this case the MotorCycle has parked
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testGetDepTime_Parked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertEquals(motorCycle.getDepartureTime(), ACTUAL_DEPARTURE_TIME);
	}

	/**
	 * Test getting parkingTime, in this case the MotorCycle has not parked
	 */
	@Test
	public void testGetParkTime() {
		assertEquals(motorCycle.getParkingTime(), 0);
	}

	/**
	 * Test getting parkingTime, in this case the MotorCycle has not parked
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testGetParkTime_Parked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertEquals(motorCycle.getParkingTime(), VALID_PARKING_TIME);
	}

	@Test
	public void testGetVehID() {
		assertEquals(motorCycle.getVehID(), "MC1");
	}

	@Test
	public void testIsParked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(motorCycle.isParked());
	}

	@Test
	public void testIsParked_False() throws VehicleException {
		assertFalse(motorCycle.isParked());
	}

	@Test
	public void testIsQueued() throws VehicleException {
		motorCycle.enterQueuedState();
		assertTrue(motorCycle.isQueued());
	}

	@Test
	public void testIsQueued_False() throws VehicleException {
		assertFalse(motorCycle.isQueued());
	}

	@Test
	public void testIsSatisfied() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(motorCycle.isSatisfied());
	}

	/**
	 * Test dissatifaction for waiting too long
	 * 
	 * @throws VehicleException
	 */
	@Test
	public void testIsSatisfied_LongQueue() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitQueuedState(LARGE_EXIT_TIME);
		assertFalse(motorCycle.isSatisfied());
	}

	@Test
	public void testIsSatisfied_notParked() {
		assertFalse(motorCycle.isSatisfied());
	}

	@Test
	public void testToString() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitQueuedState(VALID_EXIT_TIME);
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertTrue(motorCycle.toString().contains(motorCycle.getVehID()));
		assertTrue(motorCycle.toString().contains(
				String.valueOf(VALID_ARRIVAL_TIME)));
		assertTrue(motorCycle.toString().contains(
				String.valueOf(VALID_EXIT_TIME)));
		assertTrue(motorCycle.toString().contains(
				String.valueOf(VALID_EXIT_TIME - VALID_ARRIVAL_TIME)));
		assertTrue(motorCycle.toString().contains(
				String.valueOf(VALID_PARKING_TIME)));
		assertTrue(motorCycle.toString().contains(
				String.valueOf(ACTUAL_DEPARTURE_TIME)));
	}

	@Test
	public void testWasParked() throws VehicleException {
		motorCycle.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		motorCycle.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertTrue(motorCycle.wasParked());
	}

	@Test
	public void testWasQueued() throws VehicleException {
		motorCycle.enterQueuedState();
		motorCycle.exitQueuedState(VALID_EXIT_TIME);
		assert (motorCycle.wasQueued());
	}
}
