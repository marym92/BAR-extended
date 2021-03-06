/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mary
 */
public class DBinfo {
    private static Connection c = null;
    private static Statement stmt = null;
    private static String sql;
    
    private static String tableInfo = "BAR_UserInfo";
    private static String tableTempKeys = "BAR_TempKeys";
    private static String tableTempBarID = "BAR_TempBarID";
    
    public static void dbConnect() {
        try {
            // ----------------- Connect to DB -----------------
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:barapp.db");
            c.setAutoCommit(false);

            // ------------------ Create Table ------------------
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS " + tableInfo + " " +
                         "(nym               TEXT       NOT NULL   PRIMARY KEY , " +
                         " pk                TEXT       NOT NULL   UNIQUE , " + 
                         " sk                TEXT       NOT NULL   UNIQUE);"; 
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS " + tableTempKeys + " " +
                         "(sessionPk         TEXT       NOT NULL   UNIQUE , " + 
                         " sessionSk         TEXT       NOT NULL   UNIQUE);"; 
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS " + tableTempBarID + " " +
                         "(userBarID         INTEGER    NOT NULL   PRIMARY KEY);"; 
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static void dbInfoInsert(String nym, String pk, String sk) {
        try {
            // ----------------- Insert Values -----------------
            sql = "INSERT INTO " + tableInfo + " (nym, pk, sk) VALUES "
                    + "('" + nym + "','" + pk + "','" + sk + "');";
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static void dbKeysInsert(String sessionPk, String sessionSk) {
        try {
            sql = "DELETE FROM " + tableTempKeys + ";";
            stmt.executeUpdate(sql);
            
            sql = "INSERT INTO " + tableTempKeys + " (sessionPk, sessionSk) VALUES "
                    + "('" + sessionPk + "','" + sessionSk + "');";
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static void dbBarIDInsert(int userBarID) {
        try {
            sql = "DELETE FROM " + tableTempBarID + ";";
            stmt.executeUpdate(sql);
            
            sql = "INSERT INTO " + tableTempBarID + " (userBarID) VALUES "
                    + "(" + userBarID + ");";
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static ResultSet dbSelect(String table) {
        ResultSet rs = null;
        
        try {
            if (table.equals("tableInfo")) {
                sql = "SELECT * FROM " + tableInfo + ";";
            } else if (table.equals("tableKeys")) {
                sql = "SELECT * FROM " + tableTempKeys + ";";
            } else {
                sql = "SELECT * FROM " + tableTempBarID + ";";
            }
            
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        return rs;
    }
    
    public static void dbDelete() {
        try {
            sql = "DELETE FROM " + tableTempBarID + ";";
            stmt.executeUpdate(sql);
            
            sql = "DELETE FROM " + tableTempKeys + ";";
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
