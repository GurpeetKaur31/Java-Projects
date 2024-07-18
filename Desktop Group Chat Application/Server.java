package chatApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
	
	// Socket for the client connection
	Socket socket;
	// Vector to store the DataOutputStreams of all connected clients
	static Vector<DataOutputStream> client_dout = new Vector<>();
	
	 // Constructor to initialize the socket
	public Server(Socket socket) {
		this.socket = socket;
	}
	
	// Runnable method to handle client communication
	public void run() {
		try {
			// Setting up input and output streams for communication
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			// Adding the client's DataOutputStream to the vector
			client_dout.add(dos);
			
			// Infinite loop to keep the server running and listening for messages
			while(true) {
				// Read the message type from the client
				int msg = dis.readInt();
				
				// Vector to store indices of clients to be removed
				Vector<Integer> to_remove = new Vector<>();
				int index_to_remove = 0;
				if(msg == 1) {
					// Text message
					String data = dis.readUTF();
					
					// Broadcast the message to all clients
					for(DataOutputStream BW : client_dout) {
						try {
							BW.writeInt(1);
							BW.writeUTF(data);
							BW.flush();
						}catch(IOException ee) {
							// Add index of failed client to remove vector
							to_remove.add(index_to_remove);
						}
						index_to_remove++;
					}
				}else {
					// File message
					String file_name = dis.readUTF();
					int fileContentlen = dis.readInt();
					byte [] fb = new byte[fileContentlen];
					dis.readFully(fb,0, fileContentlen);
					
					 // Broadcast the file to all clients
					for(DataOutputStream dt : client_dout) {
						try {
							dt.writeInt(2);
							dt.writeUTF(file_name);
							dt.writeInt(fileContentlen);
							dt.write(fb);;
						}catch(IOException ee) {
							// Add index of failed client to remove vector
							to_remove.add(index_to_remove);
						}
						index_to_remove++;
					}
				}
				
				 // Remove failed clients
				for(int i:to_remove) client_dout.remove(i);
				
				// Notify all clients about the updated client count
				for(DataOutputStream dt : client_dout) {
					dt.writeInt(client_dout.size());
				}
			}
		}catch(IOException e) {
			
		}
	}
	
	// Main method to start the server
	public static void main(String[] args)throws Exception{
		ServerSocket s = new ServerSocket(4444);
		System.out.println("Server is UP on port 4444");
		while(true) {
			// Accept new client connections
			Socket socket = s.accept();
			System.out.println("We have a new User");
			Server server = new Server(socket);
			
			 // Create a new thread for each client
			Thread thread = new Thread(server);
			thread.start();
		}
	}
}
