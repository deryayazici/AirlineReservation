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
    @Override
    public Passenger createPassenger(Passenger passenger) {
        String sql = "INSERT INTO passenger (email, last_name, first_name, phone_number) " +
                "VALUES (?, ?, ?, ?) RETURNING passenger_id;";
        Integer passengerId = jdbcTemplate.queryForObject(sql, Integer.class,
                passenger.getEmail(),passenger.getLastName(), passenger.getFirstName(), passenger.getPhoneNumber());

        Passenger thePassenger =getPassenger(passengerId);
        return thePassenger;
    }
    public Passenger addNewPassenger() {
        Passenger newPassenger = promptForNewPassengerData();
        newPassenger=createPassenger(newPassenger);
        System.out.println("\nConfirm your reservation!");
        return newPassenger;
    }

    @Override
    public void deletePassenger(int passengerId) {
        String sql = "Delete From passenger Where passengerId = ?;";

        jdbcTemplate.update(sql,passengerId);

    }

    public Passenger promptForNewPassengerData() {
        Passenger passenger = new Passenger();


        String email = "";
        while (email.isBlank()) {
            email =promptForString("Email: ");
        }
        passenger.setEmail(email);


        String firstName = "";
        while (firstName.isBlank()) {
            firstName = promptForString("First Name: ");
        }
        passenger.setFirstName(firstName);

        String lastName = "";
        while (lastName.isBlank()) {
            lastName = promptForString("Last Name: ");
        }
        passenger.setLastName(lastName);

        String phoneNumber = "";
        while (phoneNumber.isBlank()) {
            phoneNumber = promptForString("Phone Number: ");
        }
        passenger.setPhoneNumber(phoneNumber);
        return passenger;

    }

    private Passenger mapRowToPassenger(SqlRowSet rowSet) {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(rowSet.getInt("passenger_id"));
        passenger.setEmail(rowSet.getString("email"));
        passenger.setFirstName(rowSet.getString("first_name"));
        passenger.setLastName(rowSet.getString("last_name"));
        passenger.setPhoneNumber(rowSet.getString("phone_number"));

        return passenger;
    }
    private String promptForString(String prompt) {
        System.out.print(prompt);
        return userInput.nextLine();
    }

}
