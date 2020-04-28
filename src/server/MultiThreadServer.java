/**
 * 
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class of MultiThreadServer used for server side socket program
 * @author Shiqi Luo
 */
public class MultiThreadServer {

	private Socket client = null;
	private ServerSocket server = null;
	
	/**
	 * Constructor of MultiThreadServer
	 * @param port port of server
	 * @throws IOException
	 * */
	public MultiThreadServer(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	/**
	 * Run method of SocketServer, listen to client message, 
	 * send the capitalization reversed message back to client
	 * @throws IOException
	 */
	public void run() throws IOException{

		while(true){
			try{
				this.client = server.accept();
				
				DataInputStream input = new DataInputStream(client.getInputStream());
				DataOutputStream output = new DataOutputStream(client.getOutputStream());
				
				Thread worker = new Thread(new ResponseServiceThread(this.client, input, output));
				
				worker.start();
				
			} catch (Exception e) {
				this.client.close();
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(args.length != 1){
				throw new IllegalArgumentException("Only accept 1 argument: port\n");
			}
			MultiThreadServer server = new MultiThreadServer(Integer.valueOf(args[0]));
			server.run();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}

}
