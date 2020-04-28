package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class of StreamServer used for server side socket program
 * @author Shiqi Luo
 */
public class StreamServer {

	private Socket client = null;
	private ServerSocket server = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	/**
	 * Constructor of StreamServer
	 * @param port port of server
	 * @throws IOException
	 * */
	public StreamServer(int port) throws IOException{
		this.server = new ServerSocket(port);
		this.client = this.server.accept();
		this.input = new DataInputStream(client.getInputStream());
		this.output = new DataOutputStream(client.getOutputStream());
	}
	
	/**
	 * Run method of StreamServer, listen to client message, 
	 * send the capitalization reversed message back to client
	 * @throws IOException
	 */
	public void run() throws IOException{
		String line = "";

		line = this.input.readUTF();

		List<Character> list = new ArrayList<>();
		for(int i = 0; i<line.length(); i++){
			list.add(line.charAt(i));
		}
		
		list = list.stream().sorted().map(ch -> toUpperCase(ch)).collect(Collectors.toList());
		
		this.output.writeUTF(list.toString());

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
	private char toUpperCase(char ch){
		return Character.toUpperCase(ch);
	}
	
	public static void main(String[] args) {
		
		try {
			if(args.length != 1){
				throw new IllegalArgumentException("Only accept 1 argument: port\n");
			}
			StreamServer server = new StreamServer(Integer.valueOf(args[0]));
			server.run();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
