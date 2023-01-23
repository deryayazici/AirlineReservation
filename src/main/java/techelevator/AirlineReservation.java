package techelevator;

import org.apache.commons.dbcp2.BasicDataSource;
import techelevator.dao.*;

import javax.sql.DataSource;
import java.util.Scanner;



public class AirlineReservation {
    private final Scanner userInput = new Scanner(System.in);

    private final FlightDao flightDao;
    private final JdbcReservationDao reservationDao;
    public AirlineReservation(DataSource dataSource) {
        flightDao = new JdbcFlightDao(dataSource);
        reservationDao=new JdbcReservationDao(dataSource);
    }

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/flightreservation");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        AirlineReservation application = new AirlineReservation(dataSource);
        application.run();
    }

    public void run() {
        displayBanner();
        boolean running = true;
        while (running) {
            displayMenu();
            int selection = promptForInt("Please select an option: ");


            if (selection == 1) {
                reservationDao.bookingMenu();
                displayFlights();

            }
            if (selection == 2) {
                reservationDao.cancellationMenu();
            }

            if (selection == 3) {
                System.out.println("Thank you for choosing us!");

            } else {
                displayError("Invalid option selected.");
            }

        }
    }

    private void displayBanner() {
        System.out.println("-----------------------------------------");
        System.out.println("|  Airline Booking Application  |");
        System.out.println("-----------------------------------------");
    }
    private void displayMenu() {
        System.out.println("1. Book a Flight");
        System.out.println("2. Cancel the Flight");
        System.out.println("3. Exit");
    }


    private void displayFlights() {
        //System.out.println((flightDao.getFlights()));
       // System.out.format("FlightId " +flightDao.);
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
