package com.example.nicestop;

public class Violator {
    private String Enforcer;
    private  String TVBNo;
    private  String type;
    private  String date;
    private  String time;
    private  String to;
    private  String licenseNo;
    private  String address1;
    private  String plateNo;
    private  String make;
    private  String color;
    private  String owner;
    private  String address2;
    private  String violation;
    private  String location;
    private  String Price;

    public Violator() {
    }

    public Violator(String Enforcer,String TVBNo,String type, String date, String time, String to, String licenseNo, String address1, String plateNo, String make, String color, String owner, String address2, String violation, String location, String Price) {
        this.Enforcer = Enforcer;
        this.type = type;
        this.date = date;
        this.time = time;
        this.to = to;
        this.licenseNo = licenseNo;
        this.address1 = address1;
        this.plateNo = plateNo;
        this.make = make;
        this.color = color;
        this.owner = owner;
        this.address2 = address2;
        this.violation = violation;
        this.TVBNo = TVBNo;
        this.location = location;
        this.Price = Price;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEnforcer() {
        return Enforcer;
    }

    public void setEnforcer(String enforcer) {
        Enforcer = enforcer;
    }

    public String getTVBNo() {
        return TVBNo;
    }

    public void setTVBNo(String TVBNo) {
        this.TVBNo = TVBNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }
}
