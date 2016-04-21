package com.chat.client;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {
    private static boolean started = false;
	public static void main(String[] args) {
       try {
    	   ServerSocket ss = new ServerSocket(8888);
    	   started = true;
    	   while(started){
		       boolean bconnected = false;
    		   Socket s  = ss.accept();
		       System.out.println("a client connected");
		       bconnected = true;
		       DataInputStream dis = new DataInputStream(s.getInputStream());
		       while(bconnected){
			       String str = dis.readUTF();
			       System.out.println(str);

		       }
		    	dis.close();   
    	   }
    	   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
