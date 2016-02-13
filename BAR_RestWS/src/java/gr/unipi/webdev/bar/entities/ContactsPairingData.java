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
public class ContactsPairingData {
    public String pseudonym;
    public String data;

    public ContactsPairingData() {
    }
    
    public ContactsPairingData(String pseudonym, String data) {
        this.pseudonym = pseudonym;
        this.data = data;
    }
}
