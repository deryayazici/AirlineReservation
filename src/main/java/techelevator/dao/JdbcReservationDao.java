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

