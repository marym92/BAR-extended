/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARsystemParams;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
public class WS_SystemParams {
    
    private static final String spURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.barsystemparams/getSystemParameters";
    
    public static ArrayList<BARsystemParams> getSystemParams() {
        ArrayList<BARsystemParams> sp = new ArrayList<>();
        
        try {
            URL url = new URL(spURL);
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
                
                BARsystemParams systemParam = new BARsystemParams();
                systemParam.setType(jsonParam.getString("type"));
                systemParam.setParam(jsonParam.getString("param"));
                
                sp.add(systemParam);
            }
            
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(WS_SystemParams.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sp;
    }
    
}