/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author chinj
 */
public class Address implements Comparable<Address>{
    private String streetAddress;
    private String city;
    private String postalCode;
    private String state;

    public Address(String streetAddress, String city, String postalCode, String state) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    @Override
    public int compareTo(Address anotherAddress){
        int cmpStreetAddress = streetAddress.compareTo(anotherAddress.streetAddress);
        if (cmpStreetAddress != 0 || streetAddress != null)
            return cmpStreetAddress;
        
        int cmpCity = city.compareTo(anotherAddress.city);
        if (cmpCity != 0 || city != null)
            return cmpCity;
        
        int cmpPostalCode = postalCode.compareTo(anotherAddress.postalCode);
        if (cmpPostalCode != 0 || postalCode != null)
            return cmpPostalCode;
        
        int cmpState = state.compareTo(anotherAddress.state);
        if (cmpState != 0 || state != null)
            return cmpState;
        
        return 0;
    }
    
    @Override
    public String toString() {
        return streetAddress + ", " + postalCode + ", " + city + ", " + state;
    }
}
