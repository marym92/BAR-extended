/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.bar.security;

import gr.unipi.webdev.bar.entities.BarSignupData;
import gr.unipi.webdev.bar.entities.ContactsPairingData;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author mary
 */
public class RSAdecrypt {
    
    private static final String ALGORITHM = "RSA";
        
    public static BarSignupData RSAdecSignup(String ciphertext) throws Exception {
        BarSignupData bsd = null;
        
        PrivateKey coordiSK = CoordinatorKeys.getCoordiSK();
        
        String decryptedText = "";
        byte[] decryptedBytes = null;
        
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance(ALGORITHM);
          // encrypt the plain text using the public key
          cipher.init(Cipher.DECRYPT_MODE, coordiSK);
          decryptedBytes = cipher.doFinal(ciphertext.getBytes(StandardCharsets.UTF_8));
          
          decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
          
          String[] parts = decryptedText.split("-");
          bsd = new BarSignupData(parts[0], parts[1], parts[2]);
          
        } catch (Exception ex) {
          Logger.getLogger(RSAdecrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bsd;
    }
    
    public static ContactsPairingData RSAdecContacts(String ciphertext) throws Exception {
        ContactsPairingData cpd = null;
        
        PrivateKey coordiSK = CoordinatorKeys.getCoordiSK();
        
        String decryptedText = "";
        byte[] decryptedBytes = null;
        
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance(ALGORITHM);
          // encrypt the plain text using the public key
          cipher.init(Cipher.DECRYPT_MODE, coordiSK);
          decryptedBytes = cipher.doFinal(ciphertext.getBytes(StandardCharsets.UTF_8));
          
          decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
          
          String[] parts = decryptedText.split("-");
          cpd = new ContactsPairingData(parts[0], parts[1]);
          
        } catch (Exception ex) {
          Logger.getLogger(RSAdecrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cpd;
    }
}
