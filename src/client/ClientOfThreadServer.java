package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class of ClientOfMultiThreadServer used for client side socket program
 * @author Shiqi Luo
 */
public class ClientOfThreadServer {

	private Socket socket;
	private DataInputStream inputFromServer = null;
	private DataOutputStream output = null;
	private BufferedReader input = null;
	
	/**
	 * Constructor of ClientOfMultiThreadServer
	 * @param address ip address of server
	 * @param port port of server
	 * @throws UnknownHostException, IOException
	 * */
	public ClientOfThreadServer(String address, int port) throws UnknownHostException, IOException{
		this.socket = new Socket(address, port);
		this.inputFromServer = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.input = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Run method of ClientOfMultiThreadServer, listen to command line message, 
	 * send it to server and print response string
	 * @throws IOException
	 */
	public void run() throws IOException {
		String inputText = "";
		String serverResponse = "";

		System.out.print("Enter text: ");
		
		inputText = input.readLine();
		output.writeUTF(inputText);
		serverResponse = inputFromServer.readUTF();
		System.out.println("Response from server: " + serverResponse);

		// close streams and sockets after received response
		input.close();
		output.close();
		inputFromServer.close();
		socket.close();
	}
	
	public static void main(String[] args) {
		
		try {
			if(args.length != 2){
				throw new IllegalArgumentException("Only accept 2 arguments: ip address and port!\n");
			}
			ClientOfThreadServer client = new ClientOfThreadServer(args[0], Integer.valueOf(args[1]));
			client.run();
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}

