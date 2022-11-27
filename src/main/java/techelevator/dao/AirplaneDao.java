package techelevator.dao;

import techelevator.model.Airplane;

public interface AirplaneDao {
    Airplane getAirplane(int flightId);
    int firstClassSeats(int flightId);

    int businessClassSeats(int flightId);

    int economyClassSeats(int flightId);
    void reserveBusinessSeats(int airplaneId, int numberOfPassengers);
    void reserveEconomySeats(int airplaneId, int numberOfPassengers);
    void reserveFirstSeats(int airplaneId, int numberOfPassengers);


}
