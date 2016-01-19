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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@Entity
@Table(name = "BAR_loginAttempts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARloginAttempts.findAll", query = "SELECT b FROM BARloginAttempts b"),
    @NamedQuery(name = "BARloginAttempts.findByUserID", query = "SELECT b FROM BARloginAttempts b WHERE b.userID = :userID"),
    @NamedQuery(name = "BARloginAttempts.findByAttempts", query = "SELECT b FROM BARloginAttempts b WHERE b.attempts = :attempts")})
public class BARloginAttempts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attempts")
    private int attempts;

    public BARloginAttempts() {
    }

    public BARloginAttempts(Integer userID) {
        this.userID = userID;
    }

    public BARloginAttempts(Integer userID, int attempts) {
        this.userID = userID;
        this.attempts = attempts;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
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
        if (!(object instanceof BARloginAttempts)) {
            return false;
        }
        BARloginAttempts other = (BARloginAttempts) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARloginAttempts[ userID=" + userID + " ]";
    }
    
}
