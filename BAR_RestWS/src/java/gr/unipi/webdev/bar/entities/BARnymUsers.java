/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@Entity
@Table(name = "BAR_nymUsers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARnymUsers.findAll", query = "SELECT b FROM BARnymUsers b"),
    @NamedQuery(name = "BARnymUsers.findByUserID", query = "SELECT b FROM BARnymUsers b WHERE b.userID = :userID"),
    @NamedQuery(name = "BARnymUsers.findByPseudonym", query = "SELECT b FROM BARnymUsers b WHERE b.pseudonym = :pseudonym"),
    @NamedQuery(name = "BARnymUsers.findByPk", query = "SELECT b FROM BARnymUsers b WHERE b.pk = :pk")})
public class BARnymUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pseudonym")
    private String pseudonym;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pk")
    private String pk;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sig")
    private byte[] sig;

    public BARnymUsers() {
    }

    public BARnymUsers(Integer userID) {
        this.userID = userID;
    }

    public BARnymUsers(Integer userID, String pseudonym, String pk, byte[] sig) {
        this.userID = userID;
        this.pseudonym = pseudonym;
        this.pk = pk;
        this.sig = sig;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
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

    public byte[] getSig() {
        return sig;
    }

    public void setSig(byte[] sig) {
        this.sig = sig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARnymUsers)) {
            return false;
        }
        BARnymUsers other = (BARnymUsers) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARnymUsers[ userID=" + userID + " ]";
    }
    
}
