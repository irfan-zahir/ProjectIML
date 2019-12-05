package com.example.rentaloka;

public class OrderDetails {

    private String userEmail;
    private String plateNumber;
    private String usageDuration;
    private String totalPayment;
    private String orderDate;
    private String carReturned;

    public OrderDetails(){

    }

    public OrderDetails(String userEmail, String plateNumber, String usageDuration, String totalPayment, String orderDate,String carReturned) {
        this.userEmail = userEmail;
        this.plateNumber = plateNumber;
        this.usageDuration = usageDuration;
        this.totalPayment = totalPayment;
        this.orderDate = orderDate;
        this.carReturned = carReturned;
    }

    public String getCarReturned() {
        return carReturned;
    }

    public void setCarReturned(String carReturned) {
        this.carReturned = carReturned;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUsageDuration() {
        return usageDuration;
    }

    public void setUsageDuration(String usageDuration) {
        this.usageDuration = usageDuration;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

}
