/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author mary
 */

/*
  -----------------------------------------------------------------
    This class is used to describe the communication of nodes in 
    order to construct the Onion Path Routing to the coordinator.

    OutgoingSocket class describes the role of a client for a 
    Client-Server communication.
    When the connection is opened, the client sends the encrypted 
    data to another user that plays at that time the role of the 
    server.
  -----------------------------------------------------------------
*/

public class OutgoingSocket {
    static final int PORT = 4444;
    
    public static boolean sendEncData(String encData, String nextIp) {
        boolean success = false;
        
        Socket clSocket;
        
        DataInputStream in;
        PrintWriter out;
        
        // Check if encrypted data is not null. If it is, return success=false
        if (encData.equals("") || encData.isEmpty()) {
            success = false;
            return success;
        }
        
        try {
            
            /* Make the connection. If sommething fails during the process,
            it prints an error message and end. */
            clSocket = new Socket(nextIp, PORT);
            in = new DataInputStream(clSocket.getInputStream());
            out = new PrintWriter(clSocket.getOutputStream());
            
            out.println(encData);
            out.flush();
            
            // Read and process the server's response to the command.
            DataInputStream dataIn = new DataInputStream(clSocket.getInputStream());
            
            String message = dataIn.readUTF();
            if (!message.equals("ok")) {
                success = false;
                return success;
            }
            
            success = true;
            
        } catch (IOException ex) {
            Logger.getLogger(OutgoingSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
        
    }
}
