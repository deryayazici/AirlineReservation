package techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import techelevator.model.Passenger;

import javax.sql.DataSource;
import java.util.Scanner;

public class JdbcPassengerDao implements PassengerDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcPassengerDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }
    JdbcFlightDao dao;
    JdbcReservationDao  rsvDao;
    Scanner userInput = new Scanner(System.in);

    public JdbcPassengerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcFlightDao(jdbcTemplate);
        rsvDao = new JdbcReservationDao(jdbcTemplate);
    }


    @Override
    public Passenger getPassenger(Integer passengerId) {
        Passenger passenger = null;

        String sql = "" +
                "SELECT * FROM passenger " +
                "WHERE passenger_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, passengerId);

        if (results.next()) {
            passenger = mapRowToPassenger(results);
        }
        return passenger;

    }

    private Passenger mapRowToPassenger(SqlRowSet rowSet) {
        Passenger passenger = new Passenger();
        passenger.setEmail(rowSet.getString("email"));
        passenger.setFirstName(rowSet.getString("first_name"));
        passenger.setLastName(rowSet.getString("last_name"));
        passenger.setPhoneNumber(rowSet.getString("phone_number"));

        return passenger;
    }

}
