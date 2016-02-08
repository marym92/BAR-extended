/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.entities;

/**
 *
 * @author mary
 */
public class SignupData {
    public String username;
    public String password;
    public String passwordVer;
    public String email;
    public String birthdate;
    public BARcaptcha captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVer() {
        return passwordVer;
    }

    public void setPasswordVer(String passwordVer) {
        this.passwordVer = passwordVer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public BARcaptcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(BARcaptcha captcha) {
        this.captcha = captcha;
    }
    
    
}
