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
                reservationMenu();
                hasChosen = false;

            }

        }

    }
    public void reservationMenu() {
        boolean hasReservation = true;

        while (hasReservation) {
            System.out.println("(1)Enter the FlightId number to select your flight: ");
            String flightId = userInput.nextLine();
            int flightNum = Integer.parseInt(flightId);

            System.out.println (airplaneDao.getAirplane(flightNum));
            hasReservation = false;


            System.out.println("(2)Select your seat type((F)irst Class, (B)usiness CLass, (E)conomy Class: ");
            String seatType = userInput.nextLine();

            System.out.println("(3) Enter number of passenger: ");
            String passengers = userInput.nextLine();
            int passengerNum = Integer.parseInt(passengers);

            if (seatType.equalsIgnoreCase("F")) {
                if (airplaneDao.firstClassSeats(flightNum) >= passengerNum) {
                    System.out.println("(1) Do you want to continue to make reservation ((Y)es,(N)o: ");
                    String confirmation = userInput.nextLine();
                    if (confirmation.equalsIgnoreCase("N")) {
                        break;
                    }
                    else if (confirmation.equalsIgnoreCase("Y")) {
                        passengerDao.addNewPassenger();
                        int airplaneId=airplaneDao.getAirplane(flightNum).getAirplaneId();
                        airplaneDao.reserveFirstSeats(airplaneId,passengerNum);
                        Reservation newReservation = addNewReservation();
                        System.out.println(getTotalPrice(newReservation.getReservationId()));
                    }
                    else {
                        System.out.println("Please enter (Y) for yes , (N) for no!");
                    }

                }
                else {
                    System.out.println("No available seat!");
                }

            }
            if (seatType.equalsIgnoreCase("B")) {
                if (airplaneDao.businessClassSeats(flightNum) >= passengerNum) {
                    System.out.println("(1) Do you want to continue to make reservation ((Y)es,(N)o: ");
                    String confirmation = userInput.nextLine();
                    if (confirmation.equalsIgnoreCase("N")) {
                        break;
                    }
                    else if (confirmation.equalsIgnoreCase("Y")) {
                        passengerDao.addNewPassenger();
                        int airplaneId=airplaneDao.getAirplane(flightNum).getAirplaneId();
                        airplaneDao.reserveBusinessSeats(airplaneId,passengerNum);
                        Reservation newReservation = addNewReservation();
                        System.out.println(getTotalPrice(newReservation.getReservationId()));

                    }
                    else {
                        System.out.println("Please enter (Y) for yes , (N) for no!");
                    }

                } else {
                    System.out.println("No available seat!");
                }

            }
            if (seatType.equalsIgnoreCase("E")) {
                if (airplaneDao.economyClassSeats(flightNum) >= passengerNum) {
                    System.out.println("(1) Do you want to continue to make reservation ((Y)es,(N)o: ");
                    String confirmation = userInput.nextLine();
                    if (confirmation.equalsIgnoreCase("N")) {
                        break;
                    }
                    else if (confirmation.equalsIgnoreCase("Y")) {
                        passengerDao.addNewPassenger();
                        int airplaneId=airplaneDao.getAirplane(flightNum).getAirplaneId();
                        airplaneDao.reserveEconomySeats(airplaneId,passengerNum);
                        Reservation newReservation = addNewReservation();
                        System.out.println(getTotalPrice(newReservation.getReservationId()));



                    }
                    else {
                        System.out.println("Please enter (Y) for yes , (N) for no!");
                    }

                }
                else {
                    System.out.println("No available seat!");
                }

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
    public void cancelReservation(int reservationId) {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?;";
        jdbcTemplate.update(sql, reservationId);

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

    private Reservation  addNewReservation() {
        Reservation newReservation = promptForNewReservationData();

        newReservation = makeReservation(newReservation);
        System.out.println(newReservation);

        return newReservation;
    }

    private Reservation promptForNewReservationData() {

        Reservation reservation = new Reservation();

        String email = "";
        while (email.isBlank()) {
            email =promptForString("Email: ");
        }
        reservation.setEmail(email);

        String flightId = "";
        while (flightId.isBlank()) {
            flightId =promptForString("FlightId: ");

        }
        int flightNum = Integer.parseInt(flightId);
        reservation.setFlightId(flightNum);


        String seatType="";
        while(seatType.isBlank()){
            seatType = promptForString("Seat Type((F)irst,(B)usiness,(E)conomy): ");
        }
        reservation.setTypeOfSeats(seatType);

        String passengerNum ="";

        while(passengerNum.isBlank()){
            passengerNum = promptForString("Number of passengers: ");
        }

        int passengerNumber = Integer.parseInt(passengerNum);
        reservation.setNumberOfSeats(passengerNumber);

        return reservation;
    }

    public void cancellationMenu() {
        boolean hasChosen = true;

        while (hasChosen) {
            System.out.println("-----------------------------------------");
            System.out.println("|  Cancel Your Reservation  |");
            System.out.println("-----------------------------------------");

            System.out.println("(1)Enter your passengerId: ");
            String passengerId = userInput.nextLine();

            Integer passengerIdNum = Integer.parseInt(passengerId);

            System.out.println("(2)Enter your reservationId: ");
            String reservationId = userInput.nextLine();
            Integer reservationIdNum = Integer.parseInt(reservationId);



            if (getReservation(reservationIdNum)==null || passengerDao.getPassenger(passengerIdNum)== null){
                System.out.println("Reservation has not found!");
            } else {
                System.out.println(" Enter (Y)es to confirm cancellation or (N)o to cancel:  ");
                String cancel = userInput.nextLine();
                if (cancel.equalsIgnoreCase("N")) {
                    break;
                }


                if (cancel.equalsIgnoreCase("N")) {
                    break;
                }
                else if (cancel.equalsIgnoreCase("Y")) {
                    passengerDao.deletePassenger(passengerIdNum);
                    String seatType = getReservation(reservationIdNum).getTypeOfSeats();
                    Integer numberOfPassengers = getReservation(reservationIdNum).getNumberOfSeats();
                    Integer airplaneId = dao.getFlight(getReservation(reservationIdNum).getFlightId()).getAirplaneId();
                    cancelReservation(reservationIdNum);
                    if (seatType=="F") {
                    airplaneDao.returnFirstSeats(airplaneId,numberOfPassengers);
                    }
                    if (seatType=="B") {
                        airplaneDao.returnBusinessSeats(airplaneId,numberOfPassengers);
                    }
                    if (seatType=="E") {
                        airplaneDao.returnEconomySeats(airplaneId,numberOfPassengers);
                    }
                }
            }
        }
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return userInput.nextLine();
    }
    private int promptForInt(String prompt) {
        return (int) promptForDouble(prompt);
    }

    private double promptForDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String response = userInput.nextLine();
            try {
                return Double.parseDouble(response);
            }  catch (NumberFormatException e) {
                if (response.isBlank()) {
                    return -1;
                } else {
                    displayError("Numbers only, please.");
                }
            }
        }
    }
    private void displayError(String message) {
        System.out.println();
        System.out.println("***" + message + "***");
        System.out.println();
    }



}

