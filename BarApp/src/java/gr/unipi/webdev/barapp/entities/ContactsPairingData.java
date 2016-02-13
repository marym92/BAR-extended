/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.entities;

/**
 *
 * @author mary
 */
public class ContactsPairingData {
    public String nym;
    public String pki;
    public String pkj;
    public String sharedKey;
    public String label;

    public ContactsPairingData() {
    }

    public ContactsPairingData(String nym, String pki, String pkj, String sharedKey, String label) {
        this.nym = nym;
        this.pki = pki;
        this.pkj = pkj;
        this.sharedKey = sharedKey;
        this.label = label;
    }

    public String getNym() {
        return nym;
    }

    public void setNym(String nym) {
        this.nym = nym;
    }

    public String getPki() {
        return pki;
    }

    public void setPki(String pki) {
        this.pki = pki;
    }

    public String getPkj() {
        return pkj;
    }

    public void setPkj(String pkj) {
        this.pkj = pkj;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
