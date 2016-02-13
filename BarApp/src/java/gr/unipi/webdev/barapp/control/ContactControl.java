/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.db.DBcontacts;
import gr.unipi.webdev.barapp.entities.BARactiveUsers;
import gr.unipi.webdev.barapp.entities.BARcontacts;
import gr.unipi.webdev.barapp.entities.BARnymUsers;
import gr.unipi.webdev.barapp.entities.ContactsPairingData;
import gr.unipi.webdev.barapp.security.RSAencrypt;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mary
 */
public class ContactControl {
    
    private static int onionNodes = 3;
    
    private static String signAlgo = "SHA256withRSA";
    private static String hashAlgo = "SHA-256";
    
    public static boolean keyExchangeProtocol_send(String pseudonym) throws Exception {
        boolean success = false;
        byte[] labelBytes, keyBytes;
        String label, sharedKey;
        
        /* ----- (a) WS -- Get all BAR users (nymUsers) ----- */
        ArrayList<BARnymUsers> nymUsers = WS_NymUsers.getNymUsers();
        if (nymUsers.isEmpty()) {
            success = false;
            return success;
        }
        
        /* ----- (b) For each entry: ----- */
        for (BARnymUsers u:nymUsers) {
            /* ----- (b1) Choose a random label ----- */
            Random r = new Random();
            labelBytes = new byte[16];
            r.nextBytes(labelBytes);
            
            label = getHexString(labelBytes);
            
            /* ----- (b2) Choose a random shared key ----- */
            r = new Random();
            keyBytes = new byte[16];
            r.nextBytes(keyBytes);
            
            sharedKey = getHexString(keyBytes);
            
            /* ----- (b3) Sign data ----- */
            PublicKey pubKey = RSAencrypt.decryptOwnPk();
            ContactsPairingData cpd = new ContactsPairingData(u.pseudonym, pubKey.toString(), u.pk, sharedKey, label);
            byte[] sig = signData(cpd);
            
            /* ----- (b4) Get userj pk ----- */
            PublicKey pkj = RSAencrypt.decryptPk(u.pk);
            
            /* ----- (b5) Generate encodedData ----- */
            String encData = pseudonym + "-" + pubKey.toString() + "-" + sharedKey + "-" + label + "-" + new String(sig, StandardCharsets.UTF_8);
            byte[] encDataBytes = RSAencrypt.RSAenc(encData, pkj);
            encData = new String(encDataBytes, StandardCharsets.UTF_8);
            
            /* ----- (c) Create onion routing path to Coordinator ----- */
            
            /* ----- (c1) WS -- Get Active Users ----- */
            ArrayList<BARactiveUsers> activeUsers = WS_ActiveUsers.getActiveUsers();

            /* ----- (c2) Check if activeUsers>3 to set onion path routing ----- */
            if (activeUsers.size() < onionNodes) {
                success = false;
                return success; 
            }
            
            /* ----- (c3) Choose randomly 3 active users ----- */
            ArrayList<BARactiveUsers> rndAU = WS_ActiveUsers.getRndActiveUsers(activeUsers);
                                        
            /* ----- (c4) Encrypt data with Coordi PK ----- */
            String encCoordiKey = WS_Users.getCoordiKey();
            encData = DataControl.encryptDataToCoordi(encCoordiKey, encData);
            if (encData.equals("")) { 
                success = false;
                return success; 
            } 
                                            
            /* ----- (c5) Encrypt data with each of the 3 random users' PKs ----- */
            encData = DataControl.encryptDataToAU(false, rndAU, encData);
                                                
            if (encData.equals("")) { 
                success = false;
                return success;
            }
            
            /* ----- (c6) Onion path routing to send enc{KeyExchangeData} ----- */
            success = DataControl.sendData(encData, rndAU.get(rndAU.size()-1).getIp());

            if (success) {
                /* ----- (c7) If success, add contact to db ----- */
                DBcontacts.dbContactInsert(u.getPseudonym(), u.getPk(), sharedKey, label, null);
            }
        }
        
        return success;
    }
    
    public static boolean keyExchangeProtocol_recv(String pseudonym) throws Exception {
        boolean success = false;
        
        /* ----- (a) WS -- Get Contacts containing userNym ----- */
        ArrayList<BARcontacts> contactsByNym = getContactsByNym(pseudonym);
        if (contactsByNym.isEmpty()) {
            success = false;
            return success;
        }
        
        /* ----- (b) Get existing contacts from db ----- */
        DBcontacts.dbConnect();
        ArrayList<String> existContactsNym = DBcontacts.dbSelect();
        DBcontacts.dbClose();
        if (existContactsNym.isEmpty()) {
            success = true;
            return success;
        }
        
        /* ----- (c) Check for new contacts ----- */
        ArrayList<BARcontacts> newContacts = new ArrayList<>();
        for (int i=0; i<contactsByNym.size(); i++) {
            for (int j=0; j<existContactsNym.size(); j++) {
                if (!existContactsNym.get(j).equalsIgnoreCase(contactsByNym.get(i).getPseudonym())) {
                    newContacts.add(contactsByNym.get(i));
                }
            }    
        }
        
        if (newContacts.isEmpty()) {
            success = true;
            return success;
        }
        
        /* ----- (d) Get private key ----- */
        PrivateKey ownSk = RSAencrypt.decryptOwnSk(); 
        
        /* ----- (e) For each contact: ----- */
        for (BARcontacts c:newContacts) {
            /* ----- (e1) Decrypt data ----- */
            String[] parts = RSAencrypt.RSAdec(false, c.getData(), ownSk);
        
            String nym = parts[0];
            String pk = parts[1];
            String sharedKey = parts[2];
            String label = parts[3];
            String sig = parts[4];
            
            /* ----- (e2) Verify sig ----- */
            PublicKey ownPk = RSAencrypt.decryptOwnPk();
            ContactsPairingData cpd = new ContactsPairingData(nym, pk, ownPk.toString(), sharedKey, label);
            
            success = verifySign(cpd, sig);
            if (success) {
                /* ----- (e3) If verified, add contact to db ----- */
                DBcontacts.dbContactInsert(nym, pk, sharedKey, label, null);
            }
        }
        
        return success;
    }
    
    private static ArrayList<BARcontacts> getContactsByNym(String nym) {
        ArrayList<BARcontacts> contactsByNym = new ArrayList<>();
        
        ArrayList<BARcontacts> allcontacts = WS_Contacts.getContacts();
        if (allcontacts.isEmpty()) {
            return null;
        }
        
        for (BARcontacts c:allcontacts) {
            if (c.pseudonym.equals(nym)) {
                contactsByNym.add(c);
            }
        }
        
        return contactsByNym;
    }
    
    private static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    
    private static byte[] signData(ContactsPairingData cpd) throws Exception {
        String signAlgo = "SHA256withRSA";
        String hashAlgo = "SHA-256";
        
        PrivateKey privateKey = RSAencrypt.decryptOwnSk();
       
        // initialize the signature with signature algorithm and private key 
        Signature signature = Signature.getInstance(signAlgo);
        signature.initSign(privateKey, new SecureRandom());
       
        // Get hash function of data
        MessageDigest md = MessageDigest.getInstance(hashAlgo);
        String data = cpd.nym + "-" + cpd.pki + "-" + cpd.pkj + "-" + cpd.sharedKey + "-" + cpd.label;

        md.update(data.getBytes("UTF-8"));
        byte[] digest = md.digest();
       
        // update signature with data to be signed 
        signature.update(digest);
       
        // sign the data
        byte[] signBytes = signature.sign();
       
        return signBytes;
    }
    
    private static boolean verifySign(ContactsPairingData cpd, String sig) throws Exception {
        boolean verified = false;
        
        PublicKey publicKey = RSAencrypt.decryptPk(cpd.pki);
        
        // Verifying the Signature
        Signature myVerifySign = Signature.getInstance(signAlgo);
        myVerifySign.initVerify(publicKey);
        
        // Get hash function of data
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String data = cpd.nym + "-" + cpd.pki + "-" + cpd.pkj + "-" + cpd.sharedKey + "-" + cpd.label;

        md.update(data.getBytes("UTF-8"));
        byte[] digest = md.digest();
        
        myVerifySign.update(digest);

        boolean verifySign = myVerifySign.verify(sig.getBytes(StandardCharsets.UTF_8));
        if (verifySign == false) {
            verified = false;
        }else {
            verified = true;
        }
        
        return verified;
    }
}
