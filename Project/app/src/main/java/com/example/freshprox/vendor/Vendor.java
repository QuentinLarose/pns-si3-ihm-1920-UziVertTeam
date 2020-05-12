package com.example.freshprox.vendor;

import java.util.List;

public class Vendor {

    public enum PraticedPrice{
        low, moderate, high
    }

    public enum Product{
        fruitLegumes, viandes, poissons, fromage
    }

    private String name;
    private Address address;
    private PraticedPrice practicedPrice;
    private List<Product> salesProduct;
    private int picture;
    private String phoneNumber;
    private double latitude;
    private double longitude;

    public Vendor(String name, Address address, PraticedPrice practicedPrice, List<Product> salesProduct, int picture, String phoneNumber, double lat, double lng){
        this.name = name;
        this.address = address;
        this.practicedPrice = practicedPrice;
        this.salesProduct = salesProduct;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.latitude = lat;
        this.longitude = lng;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Address getAddress() {
        return address;
    }

    public PraticedPrice getPracticedPrice() {
        return practicedPrice;
    }

    public List<Product> getSalesProduct() {
        return salesProduct;
    }

    public int getPicture() {
        return picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
