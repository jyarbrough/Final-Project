package models;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerModel {

    private String firstName;
    private String lastName;
    private String addressOne;
    private String addressTwo;
    private String phoneNumber;
    private String state;
    private String zipCode;
    private String city;

    private HashMap<String, CustomerModel> customerProfile;

    public CustomerModel(String firstName, String lastName, String addressOne, String addressTwo, String phoneNumber,
                         String state, String zipCode, String city) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.zipCode = zipCode;
        this.city = city;

    }

    public HashMap<String, CustomerModel> getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(HashMap<String, CustomerModel> customerProfile) { this.customerProfile = customerProfile; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

