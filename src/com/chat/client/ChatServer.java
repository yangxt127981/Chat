package com.chat.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
boolean started = false;
ServerSocket ss = null;
List<Client> clients = new ArrayList<Client>();

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
  		   clients.add(c);
		   System.out.println("a client connected");
	      }
		} catch(IOException e){
          e.printStackTrace();
       }finally{
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
	
class Client implements Runnable{
   private Socket s;
   private DataInputStream dis =null;
   private DataOutputStream dos = null;
   private boolean bconnected = false;
   
   
   public Client(Socket s){
	   this.s = s;
	   try {
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());
		bconnected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   public void send(String str){
	   try {
		   dos.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   @Override
   public void run() {
		try {
	       while(bconnected){
				String str = dis.readUTF();
			    System.out.println(str);
			    for(int i=0;i<clients.size();i++){
			    	Client c = clients.get(i);
			    	c.send(str);
			    }
//			    for(Iterator<Client> it=clients.iterator();it.hasNext();){
//			    	Client c = it.next();
//			    	c.send(str);
//			    }
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
				if(dos!=null)
				   dos.close();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		} 
	 }
  }
}
