package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class of SingleThreadServer used for server side socket program
 * @author Shiqi Luo
 */
public class SingleThreadServer implements Runnable{

	private Socket client = null;
	private ServerSocket server = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	/**
	 * Constructor of SingleThreadServer
	 * @param port port of server
	 * @throws IOException
	 * */
	public SingleThreadServer(int port) throws IOException{
		this.server = new ServerSocket(port);
		
	}
	
	/**
	 * Run method of SingleThreadServer, listen to client message, 
	 * send the capitalization reversed message back to client
	 * @throws IOException
	 */
	public void run() {
		String line = "";

		while(true){
			try{
				this.client = this.server.accept();
				this.input = new DataInputStream(client.getInputStream());
				this.output = new DataOutputStream(client.getOutputStream());
				
				line = this.input.readUTF();
				String reverse = this.reverse(line);
				this.output.writeUTF(reverse);
		
				// close streams and sockets after received response
				this.output.close();
				this.input.close();
				this.client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
			SingleThreadServer server = new SingleThreadServer(Integer.valueOf(args[0]));
			new Thread(server).start();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}
}

