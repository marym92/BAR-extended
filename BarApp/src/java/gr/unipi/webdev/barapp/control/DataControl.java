/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARactiveUsers;
import gr.unipi.webdev.barapp.entities.BarSignupData;
import gr.unipi.webdev.barapp.security.RSAencrypt;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *
 * @author mary
 */
public class DataControl {
    
    private static int onionNodes = 3;
    private static int endNode = 0;
    
    /*
    * In the future, this function will call AESencrypt.AESenc
    * before RSAencrypt.RSAenc(encString, coordiPubKey)
    */
    public static String encryptDataToCoordi(String encCoordiKey, BarSignupData bsd) {
        String encString = "";
        
        PublicKey coordiPubKey = RSAencrypt.decryptPk(encCoordiKey);
        
        String plainText = bsd.toString();
        
        byte[] encData = RSAencrypt.RSAenc(plainText, coordiPubKey);
        encString = new String(encData, StandardCharsets.UTF_8);
        
        return encString;
    }
    
    public static String encryptDataToAU(ArrayList<BARactiveUsers> rndAU, String encString) {
        
        PublicKey pubKey;
        byte[] encData;
        
        for (int node=0; node<onionNodes; node++) {
            
            if (node == endNode) {
                encString = "coordi:" + encString;
            }
            else {
                encString = rndAU.get(node-1).getIp() + ":" + encString;
            }
            
            pubKey = RSAencrypt.decryptPk(rndAU.get(node).getBridgedPk());
            
            encData = RSAencrypt.RSAenc(encString, pubKey);
            encString = new String(encData, StandardCharsets.UTF_8);
            
        }
        
        return encString;
    }
    
}
