/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARactiveUsers;
import gr.unipi.webdev.barapp.entities.BarSignupData;
import gr.unipi.webdev.barapp.security.RSAencrypt;
import gr.unipi.webdev.barapp.sockets.OutgoingSocket;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
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
    
    public static boolean sendData(String encData, String nextIp) {
        boolean success;
        
        /* ---------- Checks nextIp ----------
           if (nextIp = coordi) -> call WS
           if (nextIp = node) -> OutgoingSocket.sendData()
        */
        if (nextIp.equals("coordi")) {
            success = WS_NymUsers.registerBar(encData);
        } 
        else {
            success = OutgoingSocket.sendEncData(encData, nextIp);
        }
        return success;
    }
    
    public static void recvData(String encData) {
        PrivateKey privKey;
        String newEncData;
        
        privKey = RSAencrypt.decryptSk();
            
        String[] parts = RSAencrypt.RSAdec(encData, privKey);
        
        String nextIp = parts[0];
        newEncData = parts[1];
            
        sendData(newEncData, nextIp);
    }
    
}
