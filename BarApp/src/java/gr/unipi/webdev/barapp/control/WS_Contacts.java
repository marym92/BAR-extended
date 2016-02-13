/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARcontacts;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mary
 */
public class WS_Contacts {
    
    private static final String cURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.barcontacts";
    
    public static boolean addToContacts(String encData) {
        boolean success = false;
        
        try {
            URL url = new URL(cURL + "/addToContacts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            
            // Create JSON object
            String json = "";
 
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("encData", encData);
            
            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                success = false;
                return success;
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            String bufferString = "";
            
            while ((line = br.readLine()) != null) {
                bufferString += line;
            }
            
            if (bufferString.equals("0")) {
                success = true;
            } else {
                success = false;
            }
            
            conn.disconnect();
            
        } catch (Exception ex) {
            Logger.getLogger(WS_Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public static ArrayList<BARcontacts> getContacts() {
        ArrayList<BARcontacts> contacts = new ArrayList<>();
        
        try {
            URL url = new URL(cURL + "/getContacts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode()); 
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            String bufferString = "";
            
            while ((line = br.readLine()) != null) {
                bufferString += line;
            }
            
            // convert string to JSONArray
            JSONArray jsonArray = new JSONArray(bufferString);
            int arrayNum = jsonArray.length();
            
            // JSONArray to ArrayList
            for (int i=0; i<arrayNum; i++) {
                JSONObject jsonParam = jsonArray.getJSONObject(i);
                
                BARcontacts contact = new BARcontacts();
                contact.setPseudonym("pseudonym");
                contact.setData("data");
                
                contacts.add(contact);
            }
            
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(WS_SystemParams.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contacts;
    }
}
