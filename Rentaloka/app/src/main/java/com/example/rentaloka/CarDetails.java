package com.example.rentaloka;

public class CarDetails {
    private String plateNumber;
    private String carName;
    private int carSeats;
    private double pricepHour;
    private String status;

    public CarDetails() {
    }

    public CarDetails(String plateNumber, String carName, int carSeats, double pricepHour, String status) {
        this.plateNumber = plateNumber;
        this.carName = carName;
        this.carSeats = carSeats;
        this.pricepHour = pricepHour;
        this.status = status;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarSeats() {
        return carSeats;
    }

    public void setCarSeats(int carSeats) {
        this.carSeats = carSeats;
    }

    public double getPricepHour() {
        return pricepHour;
    }

    public void setPricepHour(double pricepHour) {
        this.pricepHour = pricepHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
