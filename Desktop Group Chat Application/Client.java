//package chatApp;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import javax.swing.WindowConstants;
//
//public class Client implements ActionListener, Runnable, KeyListener {
//	
//	// Declare socket and I/O streams
//	Socket SocketClient;
//	DataOutputStream writer;
//	DataInputStream reader;
//	
//	// Declare user name
//	String UserName;
//	
//	// Declare GUI Components
//	JFrame screen;
//	JPanel top_area;
//	JLabel l3; 
//	JLabel active_count;
//	JTextArea all_msg;
//	JTextField temp_msg;
//	JButton sendFile;
//	JButton send;
//	File file;
//	
//	// Constructor for the client class
//	Client(String name, int port, String ip){
//		try {
//			 // Initialize socket and I/O streams
//			SocketClient = new Socket(ip,port);
//			writer = new DataOutputStream(SocketClient.getOutputStream());
//			reader = new DataInputStream(SocketClient.getInputStream());
//		}catch(Exception e) {
//			JOptionPane.showMessageDialog(null, "Unable to connect to Server. Try Again!!");
//			System.exit(0);
//		}
//		
//		// Initialize user name
//		UserName = "[" + name + "]";
//		
//		 // Setup the main screen
//		screen = new JFrame("Chat App");
//		
//		// Setup the top area panel
//		top_area = new JPanel();
//		top_area.setLayout(null);
//		top_area.setBackground(new Color(7,	94, 84));
//		top_area.setBounds(0,0,450,100);
//		screen.add(top_area);
//		
//		 // Setup the user name label
//		l3 = new JLabel(name, SwingConstants.CENTER);
//		l3.setFont(new Font("SAN_SERIF",Font.BOLD,22));
//		l3.setForeground(Color.WHITE);
//		l3.setBounds(165,10,129,25);
//		top_area.add(l3);
//		
//		// Setup the active user count label
//		active_count = new JLabel("Active User : 0");
//		active_count.setFont(new Font("SAN_SERIF",Font.BOLD,12));
//		active_count.setForeground(Color.WHITE);
//		active_count.setBounds(180,40,100,18);
//		top_area.add(active_count);
//		
//		// Setup the message area
//		all_msg = new JTextArea();
//		all_msg.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
//		all_msg.setEditable(false);
//		all_msg.setLineWrap(true);
//		all_msg.setWrapStyleWord(true);
//		// Add a scroll pane to the message area
//		JScrollPane sp = new JScrollPane(all_msg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		sp.setBounds(5,75,440,570);
//		sp.setBorder(BorderFactory.createEmptyBorder());
//        sp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
//            }
//        });
//        screen.add(sp);
//        
//        // Setup the input message text field
//        temp_msg = new JTextField();
//        temp_msg.setBounds(5,655,310,40);
//        temp_msg.addKeyListener(new KeyListener(){
//        	public void keyTyped(KeyEvent e){
//        		// Not used
//        	}
//        	
//        	public void keyPressed(KeyEvent e) {
//        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//        			String str = UserName + temp_msg.getText();
//        			if(str.length() == UserName.length())
//        			{
//        				try {
//        					writer.writeInt(1);
//        					writer.writeUTF(str);
//        					writer.flush();
//        				}catch(Exception e2) {
//        					temp_msg.setText("");
//        				}
//        			}
//        		}
//        	}
//        	 public void keyReleased(KeyEvent e) {
//        		// Not used
//             }
//        });
//        temp_msg.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
//        screen.add(temp_msg);
//        
//        // Setup the send file button
//        sendFile = new JButton("Send File");
//        sendFile.setBounds(320,675,123,30);
//        sendFile.setBackground(new Color(7,94,84));
//        sendFile.setForeground(Color.WHITE);
//        sendFile.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
//        sendFile.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		
//        		JFileChooser send_file = new JFileChooser();
//        		send_file.setDialogTitle("Choose a file to send");
//        		if(send_file.showOpenDialog(null) == send_file.APPROVE_OPTION)
//        		{
//        			file=send_file.getSelectedFile();
//        			try {
//        				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
//        				byte[] fileContentbytes = new byte[(int) file.length()];
//        				fis.read(fileContentbytes);
//        				writer.writeInt(2);
//        				writer.writeUTF(file.getName());
//        				writer.writeInt(fileContentbytes.length);
//        				writer.write(fileContentbytes);
//        				writer.flush();
//        			}catch(FileNotFoundException ex) {
//        				ex.printStackTrace();        			
//        		}catch(IOException ex) {
//        			ex.printStackTrace();
//        		}
//        	}
//        }
//        });
//        screen.add(sendFile);
//		
//        // Setup the send message button
//        send = new JButton("Send");
//        send.setBounds(320, 650, 123, 30);
//        send.setBackground(new Color(7, 94, 84));
//        send.setForeground(Color.WHITE);
//        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
//        send.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		String str = UserName + temp_msg.getText();
//        		if(str.length() == UserName.length()) return;
//                try{
//                    writer.writeInt(1);
//                    writer.writeUTF(str);
//                    writer.flush();
//                }catch(Exception e2){}
//                temp_msg.setText("");
//            }
//        });
//        screen.add(send);
//        
//        // Setup the main screen properties
//        screen.getContentPane().setBackground(Color.WHITE);
//        screen.setLayout(null);
//        screen.setSize(460,750);
//        screen.setLocation(300,50);
//        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        screen.setResizable(false);
//        screen.setVisible(true);
//	}
//	
//	// Runnable interface method to handle incoming messages
//	public void run() {
//		while(true) {
//			int flag=0;
//			try {
//				flag=reader.readInt();
//				if(flag == 2)
//				{
//					String file_name=reader.readUTF();
//					
//					int fileContentlen = 0;
//					fileContentlen = reader.readInt();
//					
//					byte[] fb = new byte[fileContentlen];
//					
//					reader.readFully(fb, 0, fileContentlen);
//					File download = new File(file_name);
//					FileOutputStream fos = new FileOutputStream(download);
//					fos.write(fb);
//					JOptionPane.showMessageDialog(screen, "New File Recieved");
//					fos.close();
//				}else {
//					String msg = "";
//					msg = reader.readUTF();
//					all_msg.append(msg+ "\n");
//					
//				}
//				active_count.setText("Active User  : "+reader.readInt());
//			}catch(Exception ee){}
//		}
//	}
//	
//	// KeyListener interface methods (not used)
//	@Override
//	public void keyTyped(KeyEvent e) {}
//	@Override
//	public void keyPressed(KeyEvent e) {}
//	@Override
//	public void keyReleased(KeyEvent e) {}
//
//	// ActionListener interface method (not used)
//	@Override
//	public void actionPerformed(ActionEvent e) {}
//}

package chatApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Client implements ActionListener, Runnable, KeyListener {
	
	// Declare socket and I/O streams
	Socket SocketClient;
	DataOutputStream writer;
	DataInputStream reader;
	
	// Declare user name
	String UserName;
	
	// Declare GUI Components
	JFrame screen;
	JPanel top_area;
	JLabel l3; 
	JLabel active_count;
	JTextArea all_msg;
	JTextField temp_msg;
	JButton sendFile;
	JButton send;
	File file;
	
	// Constructor for the client class
	Client(String name, int port, String ip){
		try {
			 // Initialize socket and I/O streams
			SocketClient = new Socket(ip,port);
			writer = new DataOutputStream(SocketClient.getOutputStream());
			reader = new DataInputStream(SocketClient.getInputStream());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to Server. Try Again!!");
			System.exit(0);
		}
		
		// Initialize user name
		UserName = "[" + name + "]";
		
		 // Setup the main screen
		screen = new JFrame("Chat App");
		
		// Setup the top area panel
		top_area = new JPanel();
		top_area.setLayout(null);
		top_area.setBackground(new Color(7,	94, 84));
		top_area.setBounds(0,0,400,80); // Adjusted height and width
		screen.add(top_area);
		
		 // Setup the user name label
		l3 = new JLabel(name, SwingConstants.CENTER);
		l3.setFont(new Font("SAN_SERIF",Font.BOLD,20)); // Adjusted font size
		l3.setForeground(Color.WHITE);
		l3.setBounds(120,10,120,25); // Adjusted bounds
		top_area.add(l3);
		
		// Setup the active user count label
		active_count = new JLabel("Active User : 0");
		active_count.setFont(new Font("SAN_SERIF",Font.BOLD,10)); // Adjusted font size
		active_count.setForeground(Color.WHITE);
		active_count.setBounds(150,40,100,18); // Adjusted bounds
		top_area.add(active_count);
		
		// Setup the message area
		all_msg = new JTextArea();
		all_msg.setFont(new Font("SAN_SERIF", Font.PLAIN, 14)); // Adjusted font size
		all_msg.setEditable(false);
		all_msg.setLineWrap(true);
		all_msg.setWrapStyleWord(true);
		// Add a scroll pane to the message area
		JScrollPane sp = new JScrollPane(all_msg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(5,75,390,470); // Adjusted bounds
		sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        screen.add(sp);
        
        // Setup the input message text field
        temp_msg = new JTextField();
        temp_msg.setBounds(5,575,260,35); // Adjusted bounds
        temp_msg.addKeyListener(new KeyListener(){
        	public void keyTyped(KeyEvent e){
        		// Not used
        	}
        	
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        			String str = UserName + temp_msg.getText();
        			if(str.length() == UserName.length())
        			{
        				try {
        					writer.writeInt(1);
        					writer.writeUTF(str);
        					writer.flush();
        				}catch(Exception e2) {
        					temp_msg.setText("");
        				}
        			}
        		}
        	}
        	 public void keyReleased(KeyEvent e) {
        		// Not used
             }
        });
        temp_msg.setFont(new Font("SAN_SERIF", Font.PLAIN, 14)); // Adjusted font size
        screen.add(temp_msg);
        
        // Setup the send file button
        sendFile = new JButton("Send File");
        sendFile.setBounds(275,580,100,30); // Adjusted bounds
        sendFile.setBackground(new Color(7,94,84));
        sendFile.setForeground(Color.WHITE);
        sendFile.setFont(new Font("SAN_SERIF",Font.PLAIN,14)); // Adjusted font size
        sendFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		JFileChooser send_file = new JFileChooser();
        		send_file.setDialogTitle("Choose a file to send");
        		if(send_file.showOpenDialog(null) == send_file.APPROVE_OPTION)
        		{
        			file=send_file.getSelectedFile();
        			try {
        				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        				byte[] fileContentbytes = new byte[(int) file.length()];
        				fis.read(fileContentbytes);
        				writer.writeInt(2);
        				writer.writeUTF(file.getName());
        				writer.writeInt(fileContentbytes.length);
        				writer.write(fileContentbytes);
        				writer.flush();
        			}catch(FileNotFoundException ex) {
        				ex.printStackTrace();        			
        		}catch(IOException ex) {
        			ex.printStackTrace();
        		}
        	}
        }
        });
        screen.add(sendFile);
		
        // Setup the send message button
        send = new JButton("Send");
        send.setBounds(275, 547, 100, 30); // Adjusted bounds
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 14)); // Adjusted font size
        send.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String str = UserName + temp_msg.getText();
        		if(str.length() == UserName.length()) return;
                try{
                    writer.writeInt(1);
                    writer.writeUTF(str);
                    writer.flush();
                }catch(Exception e2){}
                temp_msg.setText("");
            }
        });
        screen.add(send);
        
        // Setup the main screen properties
        screen.getContentPane().setBackground(Color.WHITE);
        screen.setLayout(null);
        screen.setSize(400, 650); // Adjusted screen size
        screen.setLocation(300,50);
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.setResizable(false);
        screen.setVisible(true);
	}
	
	// Runnable interface method to handle incoming messages
	public void run() {
		while(true) {
			int flag=0;
			try {
				flag=reader.readInt();
				if(flag == 2)
				{
					String file_name=reader.readUTF();
					
					int fileContentlen = 0;
					fileContentlen = reader.readInt();
					
					byte[] fb = new byte[fileContentlen];
					
					reader.readFully(fb, 0, fileContentlen);
					File download = new File(file_name);
					FileOutputStream fos = new FileOutputStream(download);
					fos.write(fb);
					JOptionPane.showMessageDialog(screen, "New File Received");
					fos.close();
				}else {
					String msg = "";
					msg = reader.readUTF();
					all_msg.append(msg+ "\n");
					
				}
				active_count.setText("Active User  : "+reader.readInt());
			}catch(Exception ee){}
		}
	}
	
	// KeyListener interface methods (not used)
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	// ActionListener interface method (not used)
	@Override
	public void actionPerformed(ActionEvent e) {}
}

