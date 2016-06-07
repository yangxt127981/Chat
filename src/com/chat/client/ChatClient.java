package com.chat.client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame{
   
	private TextField tf = new TextField();
    private TextArea ta = new TextArea();
    private Socket s = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private boolean bConnected = false;
    
    Thread tRecv = new Thread(new RecvThread());
	public static void main(String[] args) {
       new ChatClient().lanuchFrame();
       
	}
	
	public void start(){
		RecvThread c = new RecvThread();
		new Thread(c).start();
	}
	
	public void lanuchFrame(){
		this.setLocation(400,500);
		this.setSize(300,300);
		add(tf,BorderLayout.SOUTH);
		add(ta,BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0){
				disconnect();
				System.exit(0);
			}
		});
		tf.addActionListener(new TfListener());
		this.setVisible(true);
		
		connect();
		tRecv.start();
	}
	
	public void connect(){
		try {
		    s = new Socket("127.0.0.1",8888);
			System.out.println("connect to server");
			bConnected = true;
			dos= new DataOutputStream(s.getOutputStream());
			dis= new DataInputStream(s.getInputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
//		try {
//			bConnected = false;
//			tRecv.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally{
//			try {
//				dos.close();
//				dis.close();
//				s.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		}
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class TfListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tf.getText().trim();
			tf.setText("");
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}
		
	}
	
   private class RecvThread implements Runnable{
    	   @Override
    	   public void run() {
    		try{   
    		   while(bConnected){
    			 String str = dis.readUTF();
    			 ta.setText(ta.getText() + str + '\n');
    		   }
    		} catch (SocketException e){
    			System.out.println("exit,bye");
    			
    		} catch (EOFException e){
    			e.printStackTrace();
            }catch (IOException e){
    			e.printStackTrace();
    		}
    	}
    
    }

}
