package com.chat.client;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatServer {
    private static boolean started = false;
    private static ServerSocket ss =null;
    private static Socket s = null;
    private static DataInputStream dis = null;
	public static void main(String[] args) {
    
    	   try{
    	       ss = new ServerSocket(8888);
    	   }catch (BindException e){
    		   	System.out.println("port is using.....");
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
    	      }
			} catch(EOFException e){
				System.out.println("client closed!");
			}catch (Exception e) {
				
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
