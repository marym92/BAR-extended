/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.security;

import java.nio.charset.Charset;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mary
 */
public class AESencrypt {
    
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] keyValue = null;

    public KeyGenerator keygenerator;
    public SecretKey AESkey;

    public IvParameterSpec ivSpec;

    public String AESenc(String data) throws Exception 
    {
        // Generate the key
        Key key = generateKey();
        // Create the Cipher
        Cipher c = Cipher.getInstance(ALGORITHM);

        ivSpec = new IvParameterSpec(new byte[16]);

        // Initialize the cipher for encryption
        c.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encrData = c.doFinal(data.getBytes());
        String encryptedData = DatatypeConverter.printBase64Binary(encrData);

        return encryptedData;
    }
    
    public String AESdec(String encryptedData, String encryptIV) throws Exception {
        // Generate the key
        Key key = generateKey();
        // Create the Cipher
        Cipher c = Cipher.getInstance(ALGORITHM);

        ivSpec = new IvParameterSpec(DatatypeConverter.parseBase64Binary(encryptIV));

        // Initialize the cipher for decryption
        c.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decordedData = DatatypeConverter.parseBase64Binary(encryptedData);
        byte[] decrData = c.doFinal(decordedData);
        String decryptedData = new String(decrData);
        
        return decryptedData;
    }
    
    public void getKey(String hintText) throws Exception {
		
        byte[] b = hintText.getBytes(Charset.forName("UTF-8"));
        keyValue = new byte[b.length/2];

        for (int i=0; i<keyValue.length; i++) {
                keyValue[i] = (byte) (b[i] ^ b[i + keyValue.length]);
        }

    }

    private static Key generateKey() {
        Key key = new SecretKeySpec(keyValue, "AES");
        return key;
    }
    
}
