package techelevator.model;

public class Reservation {
    private int reservationId;
    private int flightId;
    private String email;
    private String typeOfSeats;
    private int numberOfSeats;

    public String getTypeOfSeats() {
        return typeOfSeats;
    }

    public void setTypeOfSeats(String typeOfSeats) {
        this.typeOfSeats = typeOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        String  reservationString ="ReservationId: " +reservationId + " " + "FlightId: "+flightId + " " +"Email: "+ email + " " +"Seat Type: "+ typeOfSeats + " " +"Passenger Number: "+ numberOfSeats ;
        return  reservationString;
    }
}
