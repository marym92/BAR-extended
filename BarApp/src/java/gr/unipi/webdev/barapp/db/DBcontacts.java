/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.db;

import gr.unipi.webdev.barapp.entities.BARcontacts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mary
 */
public class DBcontacts {
    private static Connection c = null;
    private static Statement stmt = null;
    private static String sql;
    
    private static String tableContacts = "BAR_Contacts";
    
    public static void dbConnect() {
        try {
            // ----------------- Connect to DB -----------------
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:barapp.db");
            c.setAutoCommit(false);

            // ------------------ Create Table ------------------
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS " + tableContacts + " " +
                         "(nym               TEXT       NOT NULL   PRIMARY KEY , " +
                         " pk                TEXT       NOT NULL   UNIQUE , " + 
                         " sharedKey         TEXT       NOT NULL   UNIQUE , " +
                         " label             TEXT       NOT NULL   UNIQUE , " +
                         " oldLabel          TEXT                  UNIQUE );"; 
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static void dbContactInsert(String nym, String pk, String sharedKey, String label, String oldLabel) {
        try {
            // ----------------- Insert Values -----------------
            sql = "INSERT INTO " + tableContacts + " (nym, pk, sharedKey, label, oldLabel) VALUES "
                    + "('" + nym + "','" + pk + "','" + sharedKey + "','" + label + "','" + oldLabel + "');";
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static ArrayList<String> dbSelect() {
        ResultSet rs = null;
        ArrayList<String> cNym = new ArrayList<>();
        
        try {
            sql = "SELECT * FROM " + tableContacts + ";";
            
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                cNym.add(rs.getString("nym"));
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        return cNym;
    }
    
    public static void dbDelete() {
        try {
            sql = "DELETE FROM " + tableContacts + ";";
            stmt.executeUpdate(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBinfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void dbClose() {
        try {
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
