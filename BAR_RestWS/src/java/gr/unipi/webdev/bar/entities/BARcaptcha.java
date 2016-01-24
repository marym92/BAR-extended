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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "BAR_captcha")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARcaptcha.findAll", query = "SELECT b FROM BARcaptcha b"),
    @NamedQuery(name = "BARcaptcha.findByCID", query = "SELECT b FROM BARcaptcha b WHERE b.cID = :cID"),
    @NamedQuery(name = "BARcaptcha.findByCaptcha", query = "SELECT b FROM BARcaptcha b WHERE b.captcha = :captcha")})
public class BARcaptcha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cID")
    private Integer cID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "captcha")
    private int captcha;

    public BARcaptcha() {
    }

    public BARcaptcha(Integer cID) {
        this.cID = cID;
    }

    public BARcaptcha(Integer cID, int captcha) {
        this.cID = cID;
        this.captcha = captcha;
    }

    public Integer getCID() {
        return cID;
    }

    public void setCID(Integer cID) {
        this.cID = cID;
    }

    public int getCaptcha() {
        return captcha;
    }

    public void setCaptcha(int captcha) {
        this.captcha = captcha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cID != null ? cID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARcaptcha)) {
            return false;
        }
        BARcaptcha other = (BARcaptcha) object;
        if ((this.cID == null && other.cID != null) || (this.cID != null && !this.cID.equals(other.cID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARcaptcha[ cID=" + cID + " ]";
    }
    
}
