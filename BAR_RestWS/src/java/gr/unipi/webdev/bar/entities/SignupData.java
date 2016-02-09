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
public class SignupData {
    @XmlElement(name="username")
    public String username;
    @XmlElement(name="password")
    public String password;
    @XmlElement(name="passwordVer")
    public String passwordVer;
    @XmlElement(name="email")
    public String email;
    @XmlElement(name="birthdate")
    public String birthdate;
    @XmlElement(name="cID")
    public int cID;
    @XmlElement(name="captcha")
    public String captcha;
}
