/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.entities;

/**
 *
 * @author mary
 */
public class BARactiveUsers {
    private int userBarID;
    private String ip;
    private String bridgedPk;
    
    public BARactiveUsers() {
    }
    
    public BARactiveUsers(Integer userBarID, String ip, String bridgedPk) {
        this.userBarID = userBarID;
        this.ip = ip;
        this.bridgedPk = bridgedPk;
    }

    public int getUserBarID() {
        return userBarID;
    }

    public void setUserBarID(int userBarID) {
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
   
}
