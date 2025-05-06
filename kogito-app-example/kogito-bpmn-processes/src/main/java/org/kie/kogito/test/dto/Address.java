package org.kie.kogito.test.dto;

import java.io.Serializable;

public class Address implements Serializable {
    
    private String streetName;
    private String number;
    private String additionalData;
    private String country;
    private String city;
    private String zipCode;

    public Address() {
    }

    public Address(String streetName, String number, String additionalData, String country, String city,
            String zipCode) {
        this.streetName = streetName;
        this.number = number;
        this.additionalData = additionalData;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address [streetName=" + streetName + ", number=" + number + ", additionalData=" + additionalData
                + ", country=" + country + ", city=" + city + ", zipCode=" + zipCode + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((additionalData == null) ? 0 : additionalData.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (streetName == null) {
            if (other.streetName != null)
                return false;
        } else if (!streetName.equals(other.streetName))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (additionalData == null) {
            if (other.additionalData != null)
                return false;
        } else if (!additionalData.equals(other.additionalData))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (zipCode == null) {
            if (other.zipCode != null)
                return false;
        } else if (!zipCode.equals(other.zipCode))
            return false;
        return true;
    }

    
}
