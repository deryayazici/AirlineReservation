package techelevator.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Flight {
    private int flightId;
    private int airplaneId;
    private Date departureDate;
    private String departureAirportName;
    private String destinationAirportName;
    private Date arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Flight(){

    }

    public Flight(int flightId, int airplaneId, Date departureDate, String departureAirportName, String destinationAirportName, Date arrivalDate) {
        this.flightId = flightId;
        this.airplaneId = airplaneId;
        this.departureDate = departureDate;
        this.departureAirportName = departureAirportName;
        this.destinationAirportName = destinationAirportName;
        this.arrivalDate = arrivalDate;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getDestinationAirportName() {
        return destinationAirportName;
    }

    public void setDestinationAirportName(String destinationAirportName) {
        this.destinationAirportName = destinationAirportName;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String toString(){
      String  flightString ="FlightId: " +flightId + " " + "AirplaneId: "+airplaneId + " " +"Departure Date: "+ departureDate + " " +"Departure Time: "+ departureTime + " " +"Arrival Date: "+ arrivalDate + " "+" Arrival Time: " + arrivalTime;
      return  flightString;
    }
}
