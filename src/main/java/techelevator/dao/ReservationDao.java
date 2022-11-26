package techelevator.dao;

import techelevator.model.Airplane;
import techelevator.model.Passenger;
import techelevator.model.Reservation;

public interface ReservationDao {

     Reservation getReservation (int reservationId);
     Reservation makeReservation(Reservation reservation);

     Double getTotalPrice(int reservationId);


}
