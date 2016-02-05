/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.LoginData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author mary
 */
public class WS_Users {
    
    private static final String usersURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.barusers";
    
    public static String login(LoginData ld) {
        String result = "";
        
        try {
            URL url = new URL(usersURL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            
            // Create JSON object
            String json = "";
 
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", ld.getUsername());
            jsonObject.accumulate("password", ld.getPassword());
            jsonObject.accumulate("bridgedPk", ld.getBridgedPk());
            jsonObject.accumulate("ip", ld.getUsername());
            jsonObject.accumulate("serverNo", ld.getServerNo());
            jsonObject.accumulate("clusterNo", ld.getClusterNo());
            
            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            conn.disconnect();
            
        } catch (Exception ex) {
            Logger.getLogger(WS_Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
