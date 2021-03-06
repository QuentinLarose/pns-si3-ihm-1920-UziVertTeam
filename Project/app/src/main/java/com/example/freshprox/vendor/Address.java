package com.example.freshprox.vendor;

import androidx.annotation.NonNull;

public class Address {
    private String adr;
    private String postalCode;
    private String city;

    public Address(String adr, String postalCode, String city){
        this.adr = adr;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NonNull
    @Override
    public String toString() {
        return adr + "\n" + postalCode + ", " + city;
    }
}
