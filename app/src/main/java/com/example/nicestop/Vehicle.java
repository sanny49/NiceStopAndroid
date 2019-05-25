package com.example.nicestop;

public class Vehicle {
    private String plate_number;
    private String make;
    private String color;
    private String owner;
    private String address;
    public Vehicle(){}

    public Vehicle(String plate_number, String make, String color, String owner, String address) {
        this.plate_number = plate_number;
        this.make = make;
        this.color = color;
        this.owner = owner;
        this.address = address;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
