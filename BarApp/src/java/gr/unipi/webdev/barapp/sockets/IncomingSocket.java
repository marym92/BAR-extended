/* 
 * Copyright (C) 2016, BAR protocol.  All rights reserved. 
 *            Mary M. — University of Piraeus
 */
package gr.unipi.webdev.barapp.sockets;

import gr.unipi.webdev.barapp.control.DataControl;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author mary
 */

/*
  ---------------------------------------------------------------------
    This class is used to describe the communication of nodes in 
    order to construct the Onion Path Routing to the coordinator.

    IncomingSocket class describes the role of a Multi-Threaded 
    server for a Client-Server communication. Each "server" plays 
    the role of a node in onion path routing, which means that it
    receives an encrypted string, decrypt it and pass it to another
    node.
    When the connection is opened, the client sends the encrypted 
    data to another user that plays at that time the role of the 
    server. The server replies with a message, either "ok" or "error".
  ---------------------------------------------------------------------
*/

public class IncomingSocket {
    
    static final int PORT = 4444;
    static String encData;
    
    public static void recvEncData() {
        
        Socket clientSocket;
        ServerSocket serverSocket = null;
        
        boolean listening = true;
        
        encData = "";
        
        try {
            serverSocket = new ServerSocket(PORT);
            
            while(listening) {
                Connection con = new Connection(serverSocket.accept());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(IncomingSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(IncomingSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static class Connection extends Thread {
        Socket clientSocket;
        DataInputStream in;
        PrintWriter out;
        
        Connection(Socket clientSocket) {
            this.clientSocket = clientSocket;
            
            start();
        }
        
        void sendResult() throws IOException {
            /* This is called by the run() method. */
            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
            
            // Check if encrypted data is not null. If it is, return success=false
            if (encData.equals("") || encData.isEmpty()) {
                dataOut.writeUTF("error");
            }
            else {
                dataOut.writeUTF("ok");
            }
            
            dataOut.close();
        }
        
        @Override
        public void run() {
            /*  It creates streams for communicating with the client, reads 
                encData from the client.  */
            try {
                
                in = new DataInputStream(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream());
                encData = in.readLine();
                
                sendResult();
                DataControl.recvData(encData);
                
                out.close();
                in.close();
                clientSocket.close();
                
            } catch (IOException ex) {
                Logger.getLogger(IncomingSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
}
