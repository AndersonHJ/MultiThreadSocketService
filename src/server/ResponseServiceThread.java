/**
 * 
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Class of ResponseServiceThread used for server side socket program
 * @author Shiqi Luo
 */
public class ResponseServiceThread implements Runnable {

	private Socket client = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	/**
	 * Constructor of ResponseServiceThread
	 * @param port port of server
	 * @throws IOException
	 * */
	public ResponseServiceThread(Socket client, DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
		this.client = client;
	}
	
	@Override
	public void run() {
		String line = "";

		try {
			line = this.input.readUTF();
		
			String reverse = this.reverse(line);
			System.out.println(Thread.currentThread().getName());
			this.output.writeUTF(reverse + Thread.currentThread().getId());
			
			this.input.close();
			this.output.close();
			this.client.close();

		} catch (IOException e) {
			e.printStackTrace();
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
