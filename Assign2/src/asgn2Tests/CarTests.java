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



import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;

/**
 * @author hogan
 *
 */
public class CarTests {
	
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
	private Car smallCar;
	private Car generalCar;
	private MotorCycle motorCycle;
	private String str;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		smallCar = new Car("C1", VALID_ARRIVAL_TIME, true);
		generalCar = new Car("C1", VALID_ARRIVAL_TIME, false);
		motorCycle = new MotorCycle("MC1", VALID_ARRIVAL_TIME);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	
	/**
	 * Test valid small car constructor, and that it is small
	 * @throws VehicleException
	 */
	@Test
	public void testCarConstructor() throws VehicleException {
		smallCar = new Car("C1", VALID_ARRIVAL_TIME, true);
		assertTrue(smallCar.isSmall());
	}
	
	/**
	 * Test valid general car constructor, and that it is not small
	 * @throws VehicleException
	 */
	@Test
	public void testGeneralCarConstructor() throws VehicleException {
		generalCar = new Car("C2", VALID_ARRIVAL_TIME, false);
		assertFalse(generalCar.isSmall());
	}
	
	/**
	 * Test invalid arrival time for car
	 * @throws VehicleException
	 */
	@Test(expected=VehicleException.class)
	public void testCarConstructor_InvalidArrivalTime() throws VehicleException {
		new Car("C1", INVALID_ARRIVAL_TIME, true);
	}
	
	/**
	 * Test valid motorcycle constructor
	 * @throws VehicleException
	 */
	@Test
	public void testMCConstructor() throws VehicleException {
		motorCycle = new MotorCycle("MC1", VALID_ARRIVAL_TIME);
	}
	
	/**
	 * Test invalid arrival time for motorcycle
	 * @throws VehicleException
	 */
	@Test(expected=VehicleException.class)
	public void testMCConstructor_InvalidArrivalTime() throws VehicleException {
		new MotorCycle("MC1", INVALID_ARRIVAL_TIME);
	}
	
	/**
	 * Test entering a small car into a parking state
	 * @throws VehicleException 
	 */
	@Test
	public void testEnterParkState() throws VehicleException{
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(smallCar.isParked());
	}
	
	/**
	 * Test entering a small car into a parking state with invalid parkingTime
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testEnterParkState_InvalidParkingTime() throws VehicleException{
		smallCar.enterParkedState(INVALID_PARKING_TIME, VALID_DURATION_STAY);
	}
	
	/**
	 * Test entering a small car into a parking state with invalid duration
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testEnterParkState_InvalidDuration() throws VehicleException{
		smallCar.enterParkedState(VALID_PARKING_TIME, INVALID_DURATION_STAY);
	}
	
	/**
	 * Test entering a small car into a queued state
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException{
		smallCar.enterQueuedState();
		assertTrue(smallCar.isQueued());
	}
	
	/**
	 * Test entering a parked small car into a queued state 
	 */
	@Test(expected=VehicleException.class)
	public void testEnterQueuedState_Parked() throws VehicleException{
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.enterQueuedState();
	}
	
	/**
	 * Test entering a queued small car into a queued state 
	 */
	@Test(expected=VehicleException.class)
	public void testEnterQueuedState_Queued() throws VehicleException{
		smallCar.enterQueuedState();
		smallCar.enterQueuedState();
	}
	
	/**
	 * Test exiting a small car from parking
	 * @throws VehicleException 
	 */
	@Test
	public void testExitParking() throws VehicleException{
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertTrue(smallCar.wasParked());
	}
	
	/**
	 * Test exiting a queued small car from parking
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitParking_Queued() throws VehicleException{
		smallCar.enterQueuedState();
		smallCar.exitParkedState(ACTUAL_DEPARTURE_TIME);
	}
	
	/**
	 * Test exiting a queued small car from stateless
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitParking_NotParked() throws VehicleException{
		smallCar.exitParkedState(ACTUAL_DEPARTURE_TIME);
	}
	
	/**
	 * Test exiting a parked small car where departure time is less than parking
	 * time.
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitParking_InvalidDepTime() throws VehicleException{
		smallCar.enterParkedState(LARGE_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.exitParkedState(SMALL_DEP_TIME);
	}
	
	/**
	 * Test exiting a small car from queue
	 * @throws VehicleException 
	 */
	@Test
	public void testExitQueue() throws VehicleException{
		smallCar.enterQueuedState();
		smallCar.exitQueuedState(VALID_EXIT_TIME);
		assertTrue(smallCar.wasQueued());
	}
	
	/**
	 * Test exiting a small car from queue
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitQueue_Parked() throws VehicleException{
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.exitQueuedState(VALID_EXIT_TIME);
	}
	
	/**
	 * Test exiting a small car from stateless
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitQueue_NotQueued() throws VehicleException{
		smallCar.exitQueuedState(VALID_EXIT_TIME);
	}

	/**
	 * Test exiting a small car from stateless
	 * @throws VehicleException 
	 */
	@Test(expected=VehicleException.class)
	public void testExitQueue_InvalidExitTime() throws VehicleException{
		smallCar.enterQueuedState();
		smallCar.exitQueuedState(SMALL_EXIT_TIME);
	}
	
	@Test
	public void testGetArrTime() {
		assertEquals(smallCar.getArrivalTime(), VALID_ARRIVAL_TIME);
	}
	
	/**
	 * Test getting departureTime, in this case the car has not parked
	 */
	@Test
	public void testGetDepTime(){
		assertEquals(smallCar.getDepartureTime(), 0);
	}
	
	/**
	 * Test getting departureTime, in this case the car has parked
	 * @throws VehicleException
	 */
	@Test
	public void testGetDepTime_Parked() throws VehicleException {
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertEquals(smallCar.getDepartureTime(), ACTUAL_DEPARTURE_TIME);
	}
	
	/**
	 * Test getting parkingTime, in this case the car has not parked
	 */
	@Test
	public void testGetParkTime() {
		assertEquals(smallCar.getParkingTime(), 0);
	}
	
	/**
	 * Test getting parkingTime, in this case the car has not parked
	 * @throws VehicleException 
	 */
	@Test
	public void testGetParkTime_Parked() throws VehicleException {
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertEquals(smallCar.getParkingTime(), VALID_PARKING_TIME);
	}
	
	@Test
	public void testGetVehID() {
		assertEquals(smallCar.getVehID(), "C1");
	}
	
	@Test
	public void testIsParked() throws VehicleException {
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(smallCar.isParked());
	}
	
	@Test
	public void testIsParked_False() throws VehicleException {
		assertFalse(smallCar.isParked());
	}
	
	@Test
	public void testIsQueued() throws VehicleException {
		smallCar.enterQueuedState();
		assertTrue(smallCar.isQueued());
	}
	
	@Test
	public void testIsQueued_False() throws VehicleException {
		assertFalse(smallCar.isQueued());
	}
	
	@Test
	public void testIsSatisfied() throws VehicleException {
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		assertTrue(smallCar.isSatisfied());
	}
	
	/**
	 * Test dissatifaction for waiting too long
	 * @throws VehicleException
	 */
	@Test
	public void testIsSatisfied_LongQueue() throws VehicleException {
		smallCar.enterQueuedState();
		smallCar.exitQueuedState(LARGE_EXIT_TIME);
		assertFalse(smallCar.isSatisfied());
	}
	
	@Test
	public void testIsSatisfied_notParked() {
		assertFalse(smallCar.isSatisfied());
	}
	
	@Test 
	public void testToString() throws VehicleException {
		smallCar.enterQueuedState();
		smallCar.exitQueuedState(VALID_EXIT_TIME);
		smallCar.enterParkedState(VALID_PARKING_TIME, VALID_DURATION_STAY);
		smallCar.exitParkedState(ACTUAL_DEPARTURE_TIME);
		assertTrue(smallCar.toString().contains(smallCar.getVehID()));
		assertTrue(smallCar.toString().contains(String.valueOf(VALID_ARRIVAL_TIME)));
		assertTrue(smallCar.toString().contains(String.valueOf(VALID_EXIT_TIME)));
		assertTrue(smallCar.toString().contains(String.valueOf(VALID_EXIT_TIME-VALID_ARRIVAL_TIME)));
	}
	
}
