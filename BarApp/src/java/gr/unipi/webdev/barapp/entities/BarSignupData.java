/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.entities;

/**
 *
 * @author mary
 */
public class BarSignupData {
    public String userID;
    public String pseudonym;
    public String pk;

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

    @Override
    public String toString() {
        return "" + userID + "-" + pseudonym + "-" + pk;
    }
    
}
