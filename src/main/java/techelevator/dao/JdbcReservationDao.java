package techelevator.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import techelevator.model.Airplane;
import techelevator.model.Flight;
import techelevator.model.Passenger;
import techelevator.model.Reservation;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class JdbcReservationDao implements ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    JdbcAirplaneDao airplaneDao;
    JdbcPassengerDao passengerDao;
    JdbcFlightDao dao;

    Scanner userInput = new Scanner(System.in);


    public JdbcReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcFlightDao(jdbcTemplate);

        passengerDao = new JdbcPassengerDao(jdbcTemplate);
        airplaneDao = new JdbcAirplaneDao(jdbcTemplate);
    }
    public void bookingMenu() {
        boolean hasChosen = true;

        while (hasChosen) {
            System.out.println("-----------------------------------------");
            System.out.println("|  Welcome to the Booking Menu  |");
            System.out.println("-----------------------------------------");

            System.out.println("(1)Departure City: ");
            String departureCity = userInput.nextLine();

            System.out.println("(2)Arrival City: ");
            String arrivalCity = userInput.nextLine();

            System.out.println("(3)Departure Date: ");
            String departureDate = userInput.nextLine();
            LocalDate date = LocalDate.parse(departureDate);


            if (dao.getFlights(departureCity, arrivalCity, date).size() == 0) {
                System.out.println("No flights!");
            } else {
                List<Flight> flightList = dao.getFlights(departureCity, arrivalCity, date);
                System.out.println(flightList);
              //  reservationMenu();
                hasChosen = false;

            }

        }

    }
    @Override
    public Double getTotalPrice(int reservationId) {
        double totalPrice = 0;

        if (getReservation(reservationId) != null) {
            if (getReservation(reservationId).getTypeOfSeats().equalsIgnoreCase("F")) {

                totalPrice = airplaneDao.getAirplane(getReservation(reservationId).getFlightId()).getFirstClassSeatPrice() * (getReservation(reservationId).getNumberOfSeats());
            }
            if (getReservation(reservationId).getTypeOfSeats().equalsIgnoreCase("B")) {

                totalPrice = airplaneDao.getAirplane(getReservation(reservationId).getFlightId()).getBusinessClassSeatPrice() * (getReservation(reservationId).getNumberOfSeats());
            }
            if (getReservation(reservationId).getTypeOfSeats().equalsIgnoreCase("E")) {

                totalPrice = airplaneDao.getAirplane(getReservation(reservationId).getFlightId()).getEconomyClassSeatPrice() * (getReservation(reservationId).getNumberOfSeats());
            }

            System.out.println("Total price: " + totalPrice);


        }
        return totalPrice;
    }

    @Override
    public Reservation getReservation(int reservationId) {
        Reservation reservation = null;

        String sql = "" +
                "SELECT reservation_id, flight_id, email,type_of_seats,number_of_seats " +
                "FROM reservation " +
                "WHERE reservation_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reservationId);

        if (results.next()) {
            reservation = mapRowToReservation(results);
        }
        return reservation;
    }
    @Override
    public Reservation makeReservation(Reservation reservation) {

        String sql = "" +
                "INSERT INTO reservation (flight_id,email,type_of_seats,number_of_seats) " +
                "VALUES (?, ?,?,?) " +
                "RETURNING reservation_id;";

        Integer reservationId = jdbcTemplate.queryForObject(sql, Integer.class, reservation.getFlightId(),reservation.getEmail(),reservation.getTypeOfSeats(),reservation.getNumberOfSeats());


        Reservation theReservation = getReservation(reservationId);

        return theReservation;
    }

    private Reservation mapRowToReservation(SqlRowSet rowSet) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rowSet.getInt("reservation_id"));
        reservation.setFlightId(rowSet.getInt("flight_id"));
        reservation.setEmail(rowSet.getString("email"));
        reservation.setTypeOfSeats(rowSet.getString("type_of_seats"));
        reservation.setNumberOfSeats(rowSet.getInt("number_of_seats"));

        return reservation;
    }


}

