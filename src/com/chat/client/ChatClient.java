package com.chat.client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{
    private TextField tf = new TextField();
    private TextArea ta = new TextArea();
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       new ChatClient().lanuchFrame();
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
				System.exit(0);
			}
		});
		tf.addActionListener(new TfListener());
		this.setVisible(true);
		
	}
	
	private class TfListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = tf.getText().trim();
			ta.setText(s);
			tf.setText("");
		}
		
	}

}
