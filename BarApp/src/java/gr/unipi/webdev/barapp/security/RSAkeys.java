/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.security;

import gr.unipi.webdev.barapp.db.DBinfo;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author mary
 */
public class RSAkeys {
    private static String algo = "RSA";
    
    public static void createKeys(String nym) {
        RSAkeys rsakey = new RSAkeys();
        
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algo);

            keyGen.initialize(2048);
            KeyPair generatedKeyPair = keyGen.genKeyPair();

            // -------------------- Connect to DB --------------------
            DBinfo.dbConnect();
            
            rsakey.SaveKeyPair(generatedKeyPair, nym);
            
            // -------------------- Close DB connection --------------------
            DBinfo.dbClose();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void SaveKeyPair(KeyPair keyPair, String nym) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        
        // Create Public Key.
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                        publicKey.getEncoded());
        String pk = getHexString(x509EncodedKeySpec.getEncoded());
        
        // Create Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                        privateKey.getEncoded());
        String sk = getHexString(pkcs8EncodedKeySpec.getEncoded());
        
        // Store Keys to DB
        if (nym.equals("")) {  
            /* Session Keys are created */ 
            DBinfo.dbKeysInsert(pk, sk);
        } else { 
            /* User's Key Pair are created */ 
            DBinfo.dbInfoInsert(nym, pk, sk);
        }
    }
    
    private String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
