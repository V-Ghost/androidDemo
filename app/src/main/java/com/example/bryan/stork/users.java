package com.example.bryan.stork;

/**
 * Created by Bryan on 11/3/2018.
 */

public class users {
   public String username;
    public String email;
    public String password;
    public String payment_number;
    public String pin;



    public users() {
    }
    public users(String username, String email, String password, String payment_number, String pin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.payment_number = payment_number;
        this.pin = pin;
    }


}
