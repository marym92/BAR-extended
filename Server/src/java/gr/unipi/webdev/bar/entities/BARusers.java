/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@Entity
@Table(name = "BAR_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARusers.findAll", query = "SELECT b FROM BARusers b"),
    @NamedQuery(name = "BARusers.findByUserID", query = "SELECT b FROM BARusers b WHERE b.userID = :userID"),
    @NamedQuery(name = "BARusers.findByUsername", query = "SELECT b FROM BARusers b WHERE b.username = :username"),
    @NamedQuery(name = "BARusers.findByPassword", query = "SELECT b FROM BARusers b WHERE b.password = :password"),
    @NamedQuery(name = "BARusers.findByEncryptSalt", query = "SELECT b FROM BARusers b WHERE b.encryptSalt = :encryptSalt"),
    @NamedQuery(name = "BARusers.findByEmail", query = "SELECT b FROM BARusers b WHERE b.email = :email"),
    @NamedQuery(name = "BARusers.findByBirthdate", query = "SELECT b FROM BARusers b WHERE b.birthdate = :birthdate"),
    @NamedQuery(name = "BARusers.findByLocked", query = "SELECT b FROM BARusers b WHERE b.locked = :locked")})
public class BARusers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "encryptSalt")
    private String encryptSalt;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "locked")
    private boolean locked;

    public BARusers() {
    }

    public BARusers(Integer userID) {
        this.userID = userID;
    }

    public BARusers(String username, String password, String encryptSalt, String email, Date birthdate, boolean locked) {
        this.username = username;
        this.password = password;
        this.encryptSalt = encryptSalt;
        this.email = email;
        this.birthdate = birthdate;
        this.locked = locked;
    }
    
    public BARusers(Integer userID, String username, String password, String encryptSalt, String email, Date birthdate, boolean locked) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.encryptSalt = encryptSalt;
        this.email = email;
        this.birthdate = birthdate;
        this.locked = locked;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptSalt() {
        return encryptSalt;
    }

    public void setEncryptSalt(String encryptSalt) {
        this.encryptSalt = encryptSalt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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
        if (!(object instanceof BARusers)) {
            return false;
        }
        BARusers other = (BARusers) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARusers[ userID=" + userID + " ]";
    }
    
}
