/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@Entity
@Table(name = "BAR_contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARcontacts.findAll", query = "SELECT b FROM BARcontacts b"),
    @NamedQuery(name = "BARcontacts.findByPseudonym", query = "SELECT b FROM BARcontacts b WHERE b.bARcontactsPK.pseudonym = :pseudonym"),
    @NamedQuery(name = "BARcontacts.findByData", query = "SELECT b FROM BARcontacts b WHERE b.bARcontactsPK.data = :data")})
public class BARcontacts implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BARcontactsPK bARcontactsPK;

    public BARcontacts() {
    }

    public BARcontacts(BARcontactsPK bARcontactsPK) {
        this.bARcontactsPK = bARcontactsPK;
    }

    public BARcontacts(String pseudonym, String data) {
        this.bARcontactsPK = new BARcontactsPK(pseudonym, data);
    }

    public BARcontactsPK getBARcontactsPK() {
        return bARcontactsPK;
    }

    public void setBARcontactsPK(BARcontactsPK bARcontactsPK) {
        this.bARcontactsPK = bARcontactsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bARcontactsPK != null ? bARcontactsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARcontacts)) {
            return false;
        }
        BARcontacts other = (BARcontacts) object;
        if ((this.bARcontactsPK == null && other.bARcontactsPK != null) || (this.bARcontactsPK != null && !this.bARcontactsPK.equals(other.bARcontactsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARcontacts[ bARcontactsPK=" + bARcontactsPK + " ]";
    }
    
}
