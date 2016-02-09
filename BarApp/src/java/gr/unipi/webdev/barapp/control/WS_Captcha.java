/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.BARcaptcha;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author mary
 */
public class WS_Captcha {
    private static final String cURL = "http://83.212.114.35:8080/BAR_RestWS/webresources/gr.unipi.webdev.bar.entities.barcaptcha";
    
    public static BARcaptcha getCaptcha() {
        
        BARcaptcha captcha = new BARcaptcha();
        
        try {
            URL url = new URL(cURL + "/getRandomCaptcha");
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
            
            // convert string to JSONObject
            JSONObject jsonCaptcha = new JSONObject(bufferString);
            
            captcha.setcID(jsonCaptcha.getInt("CID"));
            captcha.setCaptcha(jsonCaptcha.getString("captcha"));
            
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(WS_SystemParams.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return captcha;
    }
}
