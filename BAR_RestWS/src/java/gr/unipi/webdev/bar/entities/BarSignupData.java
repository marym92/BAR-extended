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
public class BarSignupData {
    @XmlElement(name="userID")
    public String userID;
    @XmlElement(name="pseudonym")
    public String pseudonym;
    @XmlElement(name="pk")
    public String pk;
}