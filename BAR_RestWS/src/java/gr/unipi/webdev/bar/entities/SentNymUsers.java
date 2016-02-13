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
public class SentNymUsers {
    public String pseudonym;
    public String pk;
    //public byte[] sig;

    public SentNymUsers() {
    }
    
    public SentNymUsers(String pseudonym, String pk) {
        this.pseudonym = pseudonym;
        this.pk = pk;
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
