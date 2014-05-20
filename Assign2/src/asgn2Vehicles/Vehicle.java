/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 19/04/2014
 * 
 */
package asgn2Vehicles;

import asgn2Exceptions.*;
import asgn2Simulators.*;

/**
 * Vehicle is an abstract class specifying the basic state of a vehicle and the
 * methods used to set and access that state. A vehicle is created upon arrival,
 * at which point it must either enter the car park to take a vacant space or
 * become part of the queue. If the queue is full, then the vehicle must leave
 * and never enters the car park. The vehicle cannot be both parked and queued
 * at once and both the constructor and the parking and queuing state transition
 * methods must respect this constraint.
 * 
 * Vehicles are created in a neutral state. If the vehicle is unable to park or
 * queue, then no changes are needed if the vehicle leaves the carpark
 * immediately. Vehicles that remain and can't park enter a queued state via
 * {@link #enterQueuedState() enterQueuedState} and leave the queued state via
 * {@link #exitQueuedState(int) exitQueuedState}. Note that an exception is
 * thrown if an attempt is made to join a queue when the vehicle is already in
 * the queued state, or to leave a queue when it is not.
 * 
 * Vehicles are parked using the {@link #enterParkedState(int, int)
 * enterParkedState} method and depart using {@link #exitParkedState(int)
 * exitParkedState}
 * 
 * Note again that exceptions are thrown if the state is inappropriate: vehicles
 * cannot be parked or exit the car park from a queued state.
 * 
 * The method javadoc below indicates the constraints on the time and other
 * parameters. Other time parameters may vary from simulation to simulation and
 * so are not constrained here.
 * 
 * @author hogan
 * 
 */
public abstract class Vehicle extends Object {

	private String vehID = "";
	private int arrivalTime = 0;
	private int departureTime = 0;
	private String vehicleState = "";
	private int parkingTime = 0;
	private int exitTime = 0;
	private int queueTime = 0;

	/**
	 * Vehicle Constructor
	 * 
	 * @param vehID
	 *            String identification number or plate of the vehicle
	 * @param arrivalTime
	 *            int time (minutes) at which the vehicle arrives and is either
	 *            queued, given entry to the car park or forced to leave
	 * @throws VehicleException
	 *             if arrivalTime is <= 0
	 * @author Ali Almuhanna - 8965048
	 */
	public Vehicle(String vehID, int arrivalTime) throws VehicleException {

		if (arrivalTime <= 0) {
			throw new VehicleException("arrival time is less than 0");
		}

		else {
			this.vehID = vehID;
			this.arrivalTime = arrivalTime;
		}

	}

	/**
	 * Transition vehicle to parked state (mutator) Parking starts on arrival or
	 * on exit from the queue, but time is set here
	 * 
	 * @param parkingTime
	 *            int time (minutes) at which the vehicle was able to park
	 * @param intendedDuration
	 *            int time (minutes) for which the vehicle is intended to remain
	 *            in the car park. Note that the parkingTime + intendedDuration
	 *            yields the departureTime
	 * @throws VehicleException
	 *             if the vehicle is already in a parked or queued state, if
	 *             parkingTime <= 0, or if intendedDuration is less than the
	 *             minimum prescribed in asgnSimulators.Constants
	 * @author Ali Almuhanna - 8965048
	 */
	public void enterParkedState(int parkingTime, int intendedDuration)
			throws VehicleException {

		if (vehicleState == "P" || vehicleState == "Q" || parkingTime <= 0
				|| intendedDuration < Constants.MINIMUM_STAY) {

			throw new VehicleException("vehicle already parked or queued or"
					+ "invalid parking time or invalid intended duration and "
					+ "given");
		}

		else {
			this.parkingTime = parkingTime;
			departureTime = parkingTime + intendedDuration;
			vehicleState = "P";
		}

	}

	/**
	 * Transition vehicle to queued state (mutator) Queuing formally starts on
	 * arrival and ceases with a call to {@link #exitQueuedState(int)
	 * exitQueuedState}
	 * 
	 * @throws VehicleException
	 *             if the vehicle is already in a queued or parked state
	 * @author Ali Almuhanna - 8965048
	 */
	public void enterQueuedState() throws VehicleException {

		if (vehicleState == "P" || vehicleState == "Q") {
			throw new VehicleException("vehicle is already queued or parked");
		} else {
			vehicleState = "Q";
		}

	}

	/**
	 * Transition vehicle from parked state (mutator)
	 * 
	 * @param departureTime
	 *            int holding the actual departure time
	 * @throws VehicleException
	 *             if the vehicle is not in a parked state, is in a queued state
	 *             or if the revised departureTime < parkingTime
	 * @author Ali Almuhanna - 8965048
	 */
	public void exitParkedState(int departureTime) throws VehicleException {

		if (vehicleState != "P" || vehicleState == "Q"
				|| departureTime < parkingTime) {
			throw new VehicleException("departureTime < parkingTime");
		}

		else {
			this.departureTime = departureTime;
			vehicleState = "";
		}

	}

	/**
	 * Transition vehicle from queued state (mutator) Queuing formally starts on
	 * arrival with a call to {@link #enterQueuedState() enterQueuedState} Here
	 * we exit and set the time at which the vehicle left the queue
	 * 
	 * @param exitTime
	 *            int holding the time at which the vehicle left the queue
	 * @throws VehicleException
	 *             if the vehicle is in a parked state or not in a queued state,
	 *             or if exitTime is not later than arrivalTime for this vehicle
	 * @author Ali Almuhanna - 8965048
	 */
	public void exitQueuedState(int exitTime) throws VehicleException {

		if (vehicleState == "P" || vehicleState != "Q"
				|| exitTime < arrivalTime) {
			throw new VehicleException("vehicle is parked or not queued, or "
					+ "exit time is not later than arrival time");
		} else {
			this.exitTime = exitTime;
			vehicleState = "";
		}

	}

	/**
	 * Simple getter for the arrival time
	 * 
	 * @return the arrivalTime
	 * @author Ali Almuhanna - 8965048
	 */
	public int getArrivalTime() {

		return arrivalTime;

	}

	/**
	 * Simple getter for the departure time from the car park Note: result may
	 * be 0 before parking, show intended departure time while parked; and
	 * actual when archived
	 * 
	 * @return the departureTime
	 * @author Ali Almuhanna - 8965048
	 */
	public int getDepartureTime() {

		return departureTime;

	}

	/**
	 * Simple getter for the parking time Note: result may be 0 before parking
	 * 
	 * @return the parkingTime
	 * @author Ali Almuhanna - 8965048
	 */
	public int getParkingTime() {

		return parkingTime;

	}

	/**
	 * Simple getter for the vehicle ID
	 * 
	 * @return the vehID
	 * @author Ali Almuhanna - 8965048
	 */
	public String getVehID() {

		return vehID;

	}

	/**
	 * Boolean status indicating whether vehicle is currently parked
	 * 
	 * @return true if the vehicle is in a parked state; false otherwise
	 * @author Ali Almuhanna - 8965048
	 */
	public boolean isParked() {

		return vehicleState == "P";

	}

	/**
	 * Boolean status indicating whether vehicle is currently queued
	 * 
	 * @return true if vehicle is in a queued state, false otherwise
	 * @author Ali Almuhanna - 8965048
	 */
	public boolean isQueued() {

		return vehicleState == "Q";

	}

	/**
	 * Boolean status indicating whether customer is satisfied or not Satisfied
	 * if they park; dissatisfied if turned away, or queuing for too long Note
	 * that calls to this method may not reflect final status
	 * 
	 * @return true if satisfied, false if never in parked state or if queuing
	 *         time exceeds max allowable
	 * @author Ali Almuhanna - 8965048
	 */
	public boolean isSatisfied() {

		boolean satisfied = true;

		if (!this.wasParked()
				|| exitTime - arrivalTime > Constants.MAXIMUM_QUEUE_TIME) {
			satisfied = false;
		}

		else {
			satisfied = true;
		}

		return satisfied;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @author Ali Almuhanna - 8965048
	 */
	@Override
	public String toString() {

		StringBuilder result = new StringBuilder();
		String NEW_LINE = "\n";

		result.append("Vehicle vehID: " + vehID + NEW_LINE);
		result.append("Arrival Time: " + arrivalTime + NEW_LINE);

		if (this.wasQueued()) {
			queueTime = exitTime - arrivalTime;
			result.append("Vehicle was queued" + NEW_LINE);
			result.append("Exit from queue: " + exitTime + NEW_LINE);
			result.append("Queue time: " + queueTime + NEW_LINE);
		} else {
			result.append("Vehicle was not queued" + NEW_LINE);
		}

		if (this.wasParked()) {
			result.append("Vehicle was parked" + NEW_LINE);
			result.append("Entry to Car Park: " + parkingTime + NEW_LINE);
			result.append("Parking Time: " + departureTime + NEW_LINE);
		} else {
			result.append("Vehicle was not parked" + NEW_LINE);
		}

		if (this.isSatisfied()) {
			result.append("Customer was satisfied" + NEW_LINE);
		} else {
			result.append("Customer was not satisfied" + NEW_LINE);
		}

		return result.toString();
	}

	/**
	 * Boolean status indicating whether vehicle was ever parked Will return
	 * false for vehicles in queue or turned away
	 * 
	 * @return true if vehicle was or is in a parked state, false otherwise
	 * @author Ali Almuhanna - 8965048
	 */
	public boolean wasParked() {

		if (vehicleState == "P" || parkingTime > 0) {
			return true;
		}

		return false;

	}

	/**
	 * Boolean status indicating whether vehicle was ever queued
	 * 
	 * @return true if vehicle was or is in a queued state, false otherwise
	 * @author Ali Almuhanna - 8965048
	 */
	public boolean wasQueued() {

		if (exitTime > 0 || vehicleState == "Q") {
			return true;
		}

		return false;

	}
}
