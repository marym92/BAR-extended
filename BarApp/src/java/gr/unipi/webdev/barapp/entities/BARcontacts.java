/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.entities;

/**
 *
 * @author mary
 */
public class BARcontacts {
    public String pseudonym;
    public String data;

    public BARcontacts() {
    }
    
    public BARcontacts(String pseudonym, String data) {
        this.pseudonym = pseudonym;
        this.data = data;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
