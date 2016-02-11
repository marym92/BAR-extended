/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.control;

import gr.unipi.webdev.barapp.entities.LoginData;
import gr.unipi.webdev.barapp.entities.SignupData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            
            // Create JSON object
            String json = "";
 
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", ld.getUsername());
            jsonObject.accumulate("password", ld.getPassword());
            jsonObject.accumulate("bridgedPk", ld.getBridgedPk());
            jsonObject.accumulate("ip", ld.getIp());
            jsonObject.accumulate("serverNo", ld.getServerNo());
            jsonObject.accumulate("clusterNo", ld.getClusterNo());
            
            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                result = "-102";
                return result;
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
    
    public static String signup(SignupData sd) {
        String result = "";
        
        String reformDate;
        SimpleDateFormat oldFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            reformDate = newFormat.format(oldFormat.parse(sd.birthdate));
            
            try {
                URL url = new URL(usersURL + "/register");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");

                // Create JSON object
                String json = "";

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("username", sd.getUsername());
                jsonObject.accumulate("password", sd.getPassword());
                jsonObject.accumulate("passwordVer", sd.getPasswordVer());
                jsonObject.accumulate("email", sd.getEmail());
                jsonObject.accumulate("birthdate", reformDate);
                jsonObject.accumulate("cID", "" + sd.getCaptcha().getcID());
                jsonObject.accumulate("captcha", sd.getCaptcha().getCaptcha());

                // convert JSONObject to JSON to String
                json = jsonObject.toString();

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    result = "-208";
                    return result;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                String bufferString = "";

                while ((line = br.readLine()) != null) {
                    bufferString += line;
                }
                
                result = bufferString;
                conn.disconnect();

            } catch (Exception ex) {
                Logger.getLogger(WS_Users.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(WS_Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static String getCoordiKey() {
        String result = "";
        
        try {
            URL url = new URL(usersURL + "/getCoordiKey");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            
            if (conn.getResponseCode() != 200) {
                result = "-504";
                return result;
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line;
            String bufferString = "";
            
            while ((line = br.readLine()) != null) {
                bufferString += line;
            }
            
            result = bufferString;
            
            conn.disconnect();
                    
        } catch (Exception ex) {
            Logger.getLogger(WS_SystemParams.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
}
