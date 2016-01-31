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
@Table(name = "BAR_systemParams")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BARsystemParams.findAll", query = "SELECT b FROM BARsystemParams b"),
    @NamedQuery(name = "BARsystemParams.findByType", query = "SELECT b FROM BARsystemParams b WHERE b.type = :type"),
    @NamedQuery(name = "BARsystemParams.findByParam", query = "SELECT b FROM BARsystemParams b WHERE b.param = :param")})
public class BARsystemParams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "param")
    private String param;

    public BARsystemParams() {
    }

    public BARsystemParams(String type) {
        this.type = type;
    }

    public BARsystemParams(String type, String param) {
        this.type = type;
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BARsystemParams)) {
            return false;
        }
        BARsystemParams other = (BARsystemParams) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.unipi.webdev.bar.entities.BARsystemParams[ type=" + type + " ]";
    }
    
}
