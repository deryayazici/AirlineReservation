package techelevator.dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import techelevator.model.Airplane;
import techelevator.model.Flight;
import techelevator.model.Passenger;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcFlightDao implements FlightDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFlightDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcFlightDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Flight> getFlights(String departureCity, String arrivalCity, LocalDate departureDate) {

        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT flight_id,airplane_id,departure_date,departure_time,arrival_time,arrival_date,departure_airport_name,arrival_airport_name " +
                "FROM flight " +
                "JOIN airport AS departure_airport ON departure_airport.airport_id = flight.departure_airport_id " +
                "JOIN airport AS arrival_airport ON arrival_airport.airport_id = flight.arrival_airport_id " +
                "WHERE departure_airport.city_name = ? AND arrival_airport.city_name = ? AND departure_date = ? " +
                "ORDER BY flight_id; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, departureCity, arrivalCity, departureDate);
        while (results.next()) {
            flights.add(mapRowToFlight(results));
        }
        return flights;

    }

    @Override
    public Flight getFlight(int flightId) {
        Flight flight = null;
        String sql = "SELECT * FROM flight " +
                "WHERE flight_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, flightId);
        if (results.next()) {
            flight = mapRowToFlight(results);
        }
        return flight;
    }


    private Flight mapRowToFlight(SqlRowSet rowSet) {
        Flight flight = new Flight();
        flight.setFlightId(rowSet.getInt("flight_id"));
        flight.setAirplaneId(rowSet.getInt("airplane_id"));
        flight.setDepartureDate(rowSet.getDate("departure_date").toLocalDate());
        flight.setArrivalDate(rowSet.getDate("arrival_date").toLocalDate());
        flight.setDepartureAirportName(rowSet.getString("departure_airport_name"));
        flight.setDestinationAirportName(rowSet.getString("arrival_airport_name"));
        flight.setArrivalTime(rowSet.getTime("arrival_time").toLocalTime());
        flight.setDepartureTime(rowSet.getTime("departure_time").toLocalTime());
        return flight;


    }


}

