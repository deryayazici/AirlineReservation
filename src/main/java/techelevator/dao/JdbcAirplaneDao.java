package techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import techelevator.model.Airplane;

public class JdbcAirplaneDao implements AirplaneDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAirplaneDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Airplane getAirplane(int flightId) {

        Airplane airplane = null;
        String sql = "SELECT * FROM airplane JOIN flight USING (airplane_id) WHERE flight_id =?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, flightId);
        if (results.next()) {
            airplane = mapRowToAirplane(results);
        }
        return airplane;
    }

    @Override
    public int firstClassSeats(int flightId) {

        int firstClass =0;

        String sql = "SELECT first_class_seats FROM airplane JOIN flight USING (airplane_id) WHERE flight_id =?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, flightId);
        if (result.next()) {
            firstClass = result.getInt("first_class_seats");
        }
        return firstClass;
    }

    @Override
    public int businessClassSeats(int flightId) {
        int businessClass =0;

        String sql = "SELECT business_class_seats FROM airplane JOIN flight USING (airplane_id) WHERE flight_id =?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, flightId);
        if (result.next()) {
            businessClass = result.getInt("business_class_seats");
        }
        return businessClass;
    }

    @Override
    public int economyClassSeats(int flightId) {
        int economyClass =0;

        String sql = "SELECT economy_class_seats FROM airplane JOIN flight USING (airplane_id) WHERE flight_id =?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, flightId);
        if (result.next()) {
            economyClass = result.getInt("economy_class_seats");
        }
        return economyClass;
    }

    @Override
    public void reserveBusinessSeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  business_class_seats = business_class_seats - ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);

    }



    @Override
    public void reserveEconomySeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  economy_class_seats = economy_class_seats - ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);


    }

    @Override
    public void reserveFirstSeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  first_class_seats = first_class_seats - ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);

    }
    @Override
    public void returnBusinessSeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  business_class_seats = business_class_seats + ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);
    }

    @Override
    public void returnEconomySeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  economy_class_seats = economy_class_seats + ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);

    }

    @Override
    public void returnFirstSeats(int airplaneId, int numberOfPassengers) {
        String sql = "" +
                "UPDATE airplane " +
                "SET  first_class_seats = first_class_seats + ? " +
                "WHERE airplane_id = ? ;";

        jdbcTemplate.update(sql,numberOfPassengers,airplaneId);

    }


    private Airplane mapRowToAirplane(SqlRowSet rowSet) {
        Airplane airplane = new Airplane();
        airplane.setAirplaneId(rowSet.getInt("airplane_id"));
        airplane.setPlaneName(rowSet.getString("plane_type"));
        airplane.setBusinessClassSeat(rowSet.getInt("business_class_seats"));
        airplane.setBusinessClassSeatPrice(rowSet.getDouble("business_class_price"));
        airplane.setEconomyClassSeat(rowSet.getInt("economy_class_seats"));
        airplane.setEconomyClassSeatPrice(rowSet.getDouble("economy_class_price"));
        airplane.setFirstClassSeat(rowSet.getInt("first_class_seats"));
        airplane.setFirstClassSeatPrice(rowSet.getDouble("first_class_price"));
        airplane.setAirlineId(rowSet.getInt("airline_id"));

        return airplane;

    }

}
