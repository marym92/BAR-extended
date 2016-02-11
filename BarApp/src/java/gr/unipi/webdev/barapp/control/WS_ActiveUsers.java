/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARactiveUsers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mary
 */
public class WS_ActiveUsers {
    
    private static final String auURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.baractiveusers";
    
    public static ArrayList<BARactiveUsers> getActiveUsers() {
        ArrayList<BARactiveUsers> au = new ArrayList<>();
        
        try {
            URL url = new URL(auURL + "/getActiveUsers");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
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
                
                BARactiveUsers activeUser = new BARactiveUsers();
                //--------------------------- CHECK IF INTEGER WORKS ------------------------
                activeUser.setUserBarID(jsonParam.getInt("userBarID"));
                activeUser.setIp(jsonParam.getString("ip"));
                activeUser.setBridgedPk(jsonParam.getString("bridgedPk"));
                
                au.add(activeUser);
            }
            
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(WS_SystemParams.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return au;
    }
    
    public static ArrayList<BARactiveUsers> getRndActiveUsers(ArrayList<BARactiveUsers> activeUsers) {
        ArrayList<BARactiveUsers> rndActiveUsers = new ArrayList<>();
        BARactiveUsers au;
        Random rnd;
        
        do {
            au = new BARactiveUsers();

            // Chooses a random active user
            rnd = new Random(System.nanoTime());

            au = activeUsers.get(rnd.nextInt(activeUsers.size()));
            if(!rndActiveUsers.contains(au)) {
                rndActiveUsers.add(au);
            }
            
        } while (rndActiveUsers.size() == 3);
        
        return rndActiveUsers;
    }
    
}
