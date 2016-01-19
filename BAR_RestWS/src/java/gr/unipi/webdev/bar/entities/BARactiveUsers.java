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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@Entity
@Table(name = "BAR_activeUsers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARactiveUsers.findAll", query = "SELECT b FROM BARactiveUsers b"),
    @NamedQuery(name = "BARactiveUsers.findByUserBarID", query = "SELECT b FROM BARactiveUsers b WHERE b.userBarID = :userBarID"),
    @NamedQuery(name = "BARactiveUsers.findByIp", query = "SELECT b FROM BARactiveUsers b WHERE b.ip = :ip"),
    @NamedQuery(name = "BARactiveUsers.findByBridgedPk", query = "SELECT b FROM BARactiveUsers b WHERE b.bridgedPk = :bridgedPk")})
public class BARactiveUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "userBarID")
    private Integer userBarID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "bridgedPk")
    private String bridgedPk;

    public BARactiveUsers() {
    }

    public BARactiveUsers(Integer userBarID) {
        this.userBarID = userBarID;
    }

    public BARactiveUsers(Integer userBarID, String ip, String bridgedPk) {
        this.userBarID = userBarID;
        this.ip = ip;
        this.bridgedPk = bridgedPk;
    }

    public Integer getUserBarID() {
        return userBarID;
    }

    public void setUserBarID(Integer userBarID) {
        this.userBarID = userBarID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBridgedPk() {
        return bridgedPk;
    }

    public void setBridgedPk(String bridgedPk) {
        this.bridgedPk = bridgedPk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userBarID != null ? userBarID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARactiveUsers)) {
            return false;
        }
        BARactiveUsers other = (BARactiveUsers) object;
        if ((this.userBarID == null && other.userBarID != null) || (this.userBarID != null && !this.userBarID.equals(other.userBarID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARactiveUsers[ userBarID=" + userBarID + " ]";
    }
    
}
