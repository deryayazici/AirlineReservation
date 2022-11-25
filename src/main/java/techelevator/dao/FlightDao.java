package techelevator.dao;

import techelevator.model.Airplane;
import techelevator.model.Flight;
import techelevator.model.Passenger;

import java.time.LocalDate;
import java.util.List;


public interface FlightDao {
    List<Flight> getFlights(String departureCity, String arrivalCity, LocalDate departureDate);

    Flight getFlight(int flightId);



}
