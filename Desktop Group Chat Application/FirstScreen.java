package chatApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


/**
 * FirstScreen class manages the initial GUI for user input to connect to the chat room.
 */
public class FirstScreen implements ActionListener {
	
	static JFrame screen;
	
	JLabel txt1;
	JLabel txt2;
	JLabel txt3;
	
	JTextField UserName;
	JTextField Ip;
	JTextField Port;
	
	JButton connect;
	
	
	/**
	 * Constructor for initializing the FirstScreen GUI.
	 */
	FirstScreen(){
		screen = new JFrame("Chat App");
		
		txt1 = new JLabel("Enter your name");
		txt1.setBounds(20,20,100,20);
		screen.add(txt1);
		
		UserName = new JTextField();
		UserName.setBounds(20,50,200,30);
		screen.add(UserName);
		
		txt2 = new JLabel("Enter Server Ip Address");
		txt2.setBounds(20,90,150,20);
		screen.add(txt2);
		
		Ip = new JTextField();
		Ip.setBounds(20,110,200,30);
		screen.add(Ip);
		
		txt3 = new JLabel("Enter Port Number");
		txt3.setBounds(20,150,120,20);
		screen.add(txt3);
		
		Port = new JTextField();
		Port.setBounds(20,170,200,30);
		screen.add(Port);
		
		
		
		connect = new JButton("Connect to Chat Room");
		connect.setBounds(40,220,170,50);
		connect.addActionListener(this);
		screen.add(connect);
		
		screen.setLayout(null);
		screen.setSize(260,350);
		screen.setLocation(500,200);
		screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		screen.setVisible(true);
		
		}
	
	
	/**
	 * Entry point for the application.
	 */
	public static void main(String[] args) {
		new FirstScreen();
	}
	
	/**
	 * ActionListener implementation for handling button click events.
	 * e ActionEvent object representing the event triggered.
	 */
	public void actionPerformed(ActionEvent e) {
		String Name = UserName.getText();
		String P = Port.getText();
		String IP = Ip.getText();
		
		// Validate user input
		if(Name.length()== 0) {
			JOptionPane.showMessageDialog(screen, "Username Missing!!!");
		}
		else if(IP.length() == 0) {
			JOptionPane.showMessageDialog(screen, "Server Ip Address Missing!!!");
		}
		else if(P.length() == 0){
			JOptionPane.showMessageDialog(screen, "Port Number Missing!!!");
		}
		else {
			// Hide the current screen and initiate client connection
			screen.setVisible(false);
			int pp = Integer.parseInt(P);
			Client one = new Client(Name, pp , IP);
			Thread t1 = new Thread(one);
			t1.start();
		}
	}
	
}
