package com.example.client_restaurant;

public class TempUser {
     private String dni ="";
    private static TempUser ini;

    private TempUser(){

    }

    public static TempUser Singleton(){
        if(ini== null){
            ini = new TempUser();

        }
        return ini;
    }

    public String getDni(){
        return dni;
    }
    public void setDni(String dni){
        this.dni = dni;
    }
}
