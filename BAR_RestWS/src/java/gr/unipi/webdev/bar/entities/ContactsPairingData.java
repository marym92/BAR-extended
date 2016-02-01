/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mary
 */
@XmlRootElement
public class ContactsPairingData {
    @XmlElement(name="pseudonym")
    public String pseudonym;
    @XmlElement(name="data")
    public String data;
}
