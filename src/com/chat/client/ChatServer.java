package com.chat.client;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatServer {
boolean started = false;
ServerSocket ss = null;
public static void main(String[] args) {
	   new ChatServer().start();
}
public void start(){
	   started = true;
	   try{
	       ss = new ServerSocket(8888);
	   }catch (BindException e){
		   	System.out.println("port is using.....");
		   	System.exit(0);
       }catch (IOException e){
		   e.printStackTrace();
	   }
	   
	   try{
  	   while(started){
  		   Socket s  = ss.accept();
  		   Client c = new Client(s);
  		   new Thread(c).start();
		   System.out.println("a client connected");
	      }
		} catch(IOException e){
          e.printStackTrace();
       }finally{
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
	
class Client implements Runnable{
   private Socket s;
   private DataInputStream dis =null;
   private boolean bconnected = false;
   
   
   public Client(Socket s){
	   this.s = s;
	   try {
		dis = new DataInputStream(s.getInputStream());
		bconnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
	@Override
   public void run() {
		try {
	       while(bconnected){
				String str = dis.readUTF();
			    System.out.println(str);
				} 
		}catch (EOFException e) {
			System.out.println("client closed!");
		}catch (IOException e) {
			e.printStackTrace();
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
}
