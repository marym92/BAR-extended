/*  
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */ 
package gr.unipi.webdev.barapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author mary
 */
public class DBmenu {
    private static Connection c = null;
    private static Statement stmt = null;
    private static String sql;
    
    private static String table = "BAR_SiteMenu";
    
    public static void dbConnect() {
        try {
            // ----------------- Connect to DB -----------------
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:barapp.db");
            c.setAutoCommit(false);

            // ------------------ Create Table ------------------
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS " + table + " " +
                         "(entry             TEXT       NOT NULL   PRIMARY KEY, " +
                         " position          INTEGER    NOT NULL , " + 
                         " link              TEXT       NOT NULL , " +
                         " not_connected     INTEGER    NOT NULL   DEFAULT 0)"; 
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static void dbMenuInsert() {
        try {
            // ----------------- Insert Values -----------------
            sql = "DELETE FROM " + table + ";";
            stmt.executeUpdate(sql);
            
            sql = "INSERT INTO " + table + " (entry, position, link, not_connected) " +
                    "VALUES "
                    + "('Home', 1, './index.jsp', 0), "
                    + "('About', 2, './about.jsp', 0), "
                    + "('Login', 3, './login.jsp', 2), "
                    + "('My Account', 4, './account.jsp', 1), "
                    + "('Contact Us', 5, './contact.jsp', 0); ";
            stmt.executeUpdate(sql);
            
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static ResultSet dbSelect(boolean connected) {
        ResultSet rs = null;
        
        try {
            if (connected == false) {
                sql = "SELECT * FROM " + table + " WHERE not_connected != 1;";
            } else {
                sql = "SELECT * FROM " + table + " WHERE not_connected != 2;";
            }
            
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        return rs;
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
