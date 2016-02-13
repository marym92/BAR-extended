/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

/**
 *
 * @author mary
 */

public class BarSignupData {
    public String userID;
    public String pseudonym;
    public String pk;

    public BarSignupData(String userID, String pseudonym, String pk) {
        this.userID = userID;
        this.pseudonym = pseudonym;
        this.pk = pk;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
    
}
