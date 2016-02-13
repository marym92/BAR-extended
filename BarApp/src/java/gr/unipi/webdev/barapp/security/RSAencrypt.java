/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.security;

import gr.unipi.webdev.barapp.db.DBinfo;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mary
 */
public class RSAencrypt {
    
    private static final String ALGORITHM = "RSA";
    
    public static PublicKey decryptPk(String encPubKey) {
        PublicKey pubKey = null;
        
        try {
            byte[] encodedPublicKey = DatatypeConverter.parseHexBinary(encPubKey);
            
            // Generate Public Key
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            pubKey = keyFactory.generatePublic(publicKeySpec);
            
        } catch (Exception ex) {
            Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pubKey;
    }
    
    public static PublicKey decryptOwnPk() {
        PublicKey pubKey = null;
            
        DBinfo.dbConnect();

        ResultSet rs = DBinfo.dbSelect("tableInfo");
        
        try {
            byte[] encodedPublicKey = null;
            
            while (rs.next()) {
                // Read Private Key.
                encodedPublicKey = DatatypeConverter.parseHexBinary(rs.getString(1));
            }
            
            // Generate Public Key
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            pubKey = keyFactory.generatePublic(publicKeySpec);
            
        } catch (Exception ex) {
            Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBinfo.dbClose();
            
        return pubKey;
    }
    
    public static PrivateKey decryptOwnSk() {
        PrivateKey privKey = null;
            
        DBinfo.dbConnect();

        ResultSet rs = DBinfo.dbSelect("tableInfo");
        
        try {
            byte[] encodedPrivateKey = null;
            
            while (rs.next()) {
                // Read Private Key.
                encodedPrivateKey = DatatypeConverter.parseHexBinary(rs.getString(2));
            }
            
            // Generate Private Key
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            privKey = keyFactory.generatePrivate(privateKeySpec);
            
        } catch (Exception ex) {
            Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBinfo.dbClose();
            
        return privKey;
    }
   
    public static byte[] RSAenc(String text, PublicKey key) {
        byte[] encDataBytes = null;
        
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance(ALGORITHM);
          // encrypt the plain text using the public key
          cipher.init(Cipher.ENCRYPT_MODE, key);
          encDataBytes = cipher.doFinal(text.getBytes());
          
        } catch (Exception ex) {
          Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return encDataBytes;
    }
    
    public static String[] RSAdec(boolean register, String text, PrivateKey key) {
        String decryptedText = "";
        byte[] decryptedBytes = null;
        String[] parts = null;
        
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

            if (register) {
                parts = decryptedText.split(":");
            } else {
                parts = decryptedText.split("-");
            }
          
        } catch (Exception ex) {
          Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return parts;
    }
}
