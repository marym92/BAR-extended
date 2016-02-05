/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import static gr.unipi.webdev.barapp.db.DBinfo.dbClose;
import static gr.unipi.webdev.barapp.db.DBinfo.dbConnect;
import static gr.unipi.webdev.barapp.db.DBinfo.dbSelect;
import java.net.InetAddress;
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
}
