/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
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
    
    public static PublicKey decryptPk(String encCoordiKey) {
        PublicKey pubKey = null;
        
        try {
            byte[] encodedPublicKey = DatatypeConverter.parseHexBinary(encCoordiKey);
            
            // Generate Public Key
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            pubKey = keyFactory.generatePublic(publicKeySpec);
            
        } catch (Exception ex) {
            Logger.getLogger(RSAencrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pubKey;
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
}
