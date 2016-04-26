package com.chat.client;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {
    private static boolean started = false;
    static ServerSocket ss =null;
    static Socket s = null;
    static DataInputStream dis = null;
	public static void main(String[] args) {
    
    	   try{
    	       ss = new ServerSocket(8888);
    	   }catch (IOException e){
    		   e.printStackTrace();
    	   }
    	   
    	   try{
	    	   started = true;
	    	   while(started){
			       boolean bconnected = false;
	    		   s  = ss.accept();
			       System.out.println("a client connected");
			       bconnected = true;
			        dis = new DataInputStream(s.getInputStream());
			       while(bconnected){
				       String str = dis.readUTF();
				       System.out.println(str);
	
			       }
			    	dis.close();   
    	      }
			} catch (Exception e) {
				
				//e.printStackTrace();
				System.out.println("client close");
			}finally{
				try{
					if(dis!=null)
						dis.close();
					if(s!=null)
					   s.close();
					
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
	}

}
