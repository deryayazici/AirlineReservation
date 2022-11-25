package techelevator.model;

public class Airplane {
    private int airplaneId ;
    private String planeName ;
    private int firstClassSeat;
    private double firstClassSeatPrice;
    private int businessClassSeat;
    private double businessClassSeatPrice;
    private int economyClassSeat;
    private double economyClassSeatPrice;
    private int airlineId;

    public double getFirstClassSeatPrice() {
        return firstClassSeatPrice;
    }

    public void setFirstClassSeatPrice(double firstClassSeatPrice) {
        this.firstClassSeatPrice = firstClassSeatPrice;
    }

    public double getBusinessClassSeatPrice() {
        return businessClassSeatPrice;
    }

    public void setBusinessClassSeatPrice(double businessClassSeatPrice) {
        this.businessClassSeatPrice = businessClassSeatPrice;
    }

    public double getEconomyClassSeatPrice() {
        return economyClassSeatPrice;
    }

    public void setEconomyClassSeatPrice(double economyClassSeatPrice) {
        this.economyClassSeatPrice = economyClassSeatPrice;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public int getFirstClassSeat() {
        return firstClassSeat;
    }

    public void setFirstClassSeat(int firstClassSeat) {
        this.firstClassSeat = firstClassSeat;
    }

    public int getBusinessClassSeat() {
        return businessClassSeat;
    }

    public void setBusinessClassSeat(int businessClassSeat) {
        this.businessClassSeat = businessClassSeat;
    }

    public int getEconomyClassSeat() {
        return economyClassSeat;
    }

    public void setEconomyClassSeat(int economyClassSeat) {
        this.economyClassSeat = economyClassSeat;
    }

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    public String toString(){
        String  airplaneString ="AirplaneId: " +airplaneId + " " + "Plane Name: "+planeName + " " +"Available First Class Seats: "+ firstClassSeat + " " +"Available Business Class Seats: "+ businessClassSeat + " " +"Available Economy Class Seats: "+ economyClassSeat;
        return  airplaneString;
    }
}
