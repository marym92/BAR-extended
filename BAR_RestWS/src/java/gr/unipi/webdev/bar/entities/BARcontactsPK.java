/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mary
 */
@Embeddable
public class BARcontactsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "pseudonym")
    private String pseudonym;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "data")
    private String data;

    public BARcontactsPK() {
    }

    public BARcontactsPK(String pseudonym, String data) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pseudonym != null ? pseudonym.hashCode() : 0);
        hash += (data != null ? data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARcontactsPK)) {
            return false;
        }
        BARcontactsPK other = (BARcontactsPK) object;
        if ((this.pseudonym == null && other.pseudonym != null) || (this.pseudonym != null && !this.pseudonym.equals(other.pseudonym))) {
            return false;
        }
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARcontactsPK[ pseudonym=" + pseudonym + ", data=" + data + " ]";
    }
    
}
