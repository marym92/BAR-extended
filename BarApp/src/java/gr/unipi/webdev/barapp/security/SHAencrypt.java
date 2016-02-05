/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.security;

import java.security.MessageDigest;

/**
 *
 * @author mary
 */
public class SHAencrypt {
    
    public static String SHA256encrypt (String h) throws Exception {
        String hash = "";
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(h.getBytes("UTF-8"));
        
        byte[] byteData = md.digest();
        
        // convert the byte to hex format
        StringBuilder hashHex = new StringBuilder();
        for (int i=0; i<byteData.length; i++) {
            hash = Integer.toHexString(0xff & byteData[i]);

            if (hash.length() == 1)
                hashHex.append('0');

            hashHex.append(hash);
        }
        
        return hashHex.toString();
        
    }
}
