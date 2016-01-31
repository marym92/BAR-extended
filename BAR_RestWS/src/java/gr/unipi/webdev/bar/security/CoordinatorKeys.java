/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.security;

import static gr.unipi.webdev.bar.security.CoordinatorDB.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mary
 */
public class CoordinatorKeys {
    private static String algo = "RSA";
    
    public static PublicKey getCoordiPK() throws Exception {
        byte[] encodedPublicKey = null;
        
        dbConnect();
        
        ResultSet rs = dbSelect("pub");
        while (rs.next()) {
            // Read Public Key.
            encodedPublicKey = DatatypeConverter.parseHexBinary(rs.getString(1));
        }
       
        // Generate Public Key
        KeyFactory keyFactory = KeyFactory.getInstance(algo);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        dbClose();
        
        return publicKey;
    }
    
    public static PrivateKey getCoordiSK() throws Exception {
        byte[] encodedPrivateKey = null;
        
        dbConnect();
        
        ResultSet rs = dbSelect("priv");
        while (rs.next()) {
            // Read Private Key.
            encodedPrivateKey = DatatypeConverter.parseHexBinary(rs.getString(1));
        }
       
        // Generate Private Key
        KeyFactory keyFactory = KeyFactory.getInstance(algo);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        dbClose();
        
        return privateKey;
    }
    
}
