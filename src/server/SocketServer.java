package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class of SocketServer used for server side socket program
 * @author Shiqi Luo
 */
public class SocketServer {

	private Socket client = null;
	private ServerSocket server = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	/**
	 * Constructor of SocketServer
	 * @param port port of server
	 * @throws IOException
	 * */
	public SocketServer(int port) throws IOException{
		this.server = new ServerSocket(port);
		this.client = this.server.accept();
		this.input = new DataInputStream(client.getInputStream());
		this.output = new DataOutputStream(client.getOutputStream());
	}
	
	/**
	 * Run method of SocketServer, listen to client message, 
	 * send the capitalization reversed message back to client
	 * @throws IOException
	 */
	public void run() throws IOException{
		String line = "";

		line = this.input.readUTF();
		String reverse = this.reverse(line);
		this.output.writeUTF(reverse);

		// close streams and sockets after received response
		this.output.close();
		this.input.close();
		this.client.close();
		this.server.close();
	}
	
	/**
	 * Reverse message characters and reverse capital
	 * @param message
	 * @return result string
	 */
	private String reverse(String message){
		StringBuilder res = new StringBuilder(message);
		
		for(int i = 0; i<res.length(); i++){
			char ch = res.charAt(i);
			if(Character.toUpperCase(ch) == ch){
				res.setCharAt(i, Character.toLowerCase(ch));
			}
			else{
				res.setCharAt(i, Character.toUpperCase(ch));
			}
		}
		
		return res.reverse().toString();
	}
	
	public static void main(String[] args) {
		
		try {
			if(args.length != 1){
				throw new IllegalArgumentException("Only accept 1 argument: port\n");
			}
			SocketServer server = new SocketServer(Integer.valueOf(args[0]));
			server.run();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
