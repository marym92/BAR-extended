/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unipi.webdev.bar.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author mary
 */
public class CoordinatorDB {
    private static Connection c = null;
    private static Statement stmt = null;
    private static String sql;
    
    public static void dbConnect() {
        try {
            // ----------------- Connect to DB -----------------
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/glassfish/Documents/BarDocs/db/coordibar.db");
            c.setAutoCommit(false);

        } catch (Exception e) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public static ResultSet dbSelect(String keytype) {
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            sql = "SELECT KEY FROM COORDIKEYS WHERE KEYTYPE='" + keytype + "';";
            
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
