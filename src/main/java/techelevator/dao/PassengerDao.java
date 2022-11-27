package techelevator.dao;

import techelevator.model.Passenger;

public interface PassengerDao {

    Passenger createPassenger(Passenger passenger);
    Passenger getPassenger (Integer passengerId);
    Passenger addNewPassenger();

}
