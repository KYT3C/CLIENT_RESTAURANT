package com.example.client_restaurant;

public class Users {

    private String dni;
    private String firstName;
    private String surnames;
    private String phoneNumber;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    private int kind;

    public Users(String dni, String firstName, String surnames, String phoneNumber, int kind){
        this.dni = dni;
        this.firstName = firstName;
        this.surnames = surnames;
        this.phoneNumber = phoneNumber;
        this.kind = kind;
    }
}
