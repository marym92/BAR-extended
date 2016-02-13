/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import static gr.unipi.webdev.barapp.db.DBinfo.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mary
 */
public class UserControl {
    private static String algo = "RSA";
    
    public static String getNym() throws Exception {
        String nym = null;
        
        dbConnect();
        
        ResultSet rs = dbSelect("tableInfo");
        while (rs.next()) {
            nym = rs.getString("nym");
        }
        dbClose();
        
        return nym;
    }
    
    public static PublicKey getPK(boolean session) throws Exception {
        byte[] encodedPublicKey = null;
        
        dbConnect();
        
        if (session) {
            // Get Session Key 
            ResultSet rs = dbSelect("tableTempKeys");
            while (rs.next()) {
                // Read Public Key.
                encodedPublicKey = DatatypeConverter.parseHexBinary(rs.getString("sessionPk"));
            }
        }
        else {
            ResultSet rs = dbSelect("tableInfo");
            while (rs.next()) {
                // Read Public Key.
                encodedPublicKey = DatatypeConverter.parseHexBinary(rs.getString("pk"));
            }
        }
        
        if(encodedPublicKey == null) {
            return null;
        }
        
        // Generate Public Key
        KeyFactory keyFactory = KeyFactory.getInstance(algo);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        dbClose();
        
        return publicKey;
    }
    
    public static PrivateKey getSK() throws Exception {
        byte[] encodedPrivateKey = null;
        
        dbConnect();
        
        ResultSet rs = dbSelect("tableInfo");
        while (rs.next()) {
            // Read Private Key.
            encodedPrivateKey = DatatypeConverter.parseHexBinary(rs.getString("sk"));
        }
       
        // Generate Private Key
        KeyFactory keyFactory = KeyFactory.getInstance(algo);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        dbClose();
        
        return privateKey;
    }
    
    public static int setBarID(int userBarID) throws Exception {
        dbConnect();
        
        dbBarIDInsert(userBarID);
        
        dbClose();
        
        return userBarID;
    }
    
    public static int getBarID() throws Exception {
        int userBarID = 0;
        
        dbConnect();
        
        ResultSet rs = dbSelect("tableBarID");
        while (rs.next()) {
            userBarID = rs.getInt("userBarID");
        }
        dbClose();
        
        return userBarID;
    }
    
    public static String getIP() {
        InetAddress ip;
        String ipString = "";
        
        try {
            ip = InetAddress.getLocalHost();
            ipString = ip.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ipString;
    }
    
    public static void logout() throws Exception {
        int userBarID = getBarID();
        String result = "";
        
        String auURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.baractiveusers";
        try {
            URL url = new URL(auURL + "/logout/" + userBarID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode()); 
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            String bufferString = "";

            while ((line = br.readLine()) != null) {
                bufferString += line;
            }

            // convert string to JSONObject
            result = bufferString;
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (result.equals("0")) {
            dbConnect();
            dbDelete();
            dbClose();
        }
    }
}
