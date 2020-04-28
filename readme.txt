|----------------------------------- Instruction --------------------------------------|

1. Extracting zip file

(make sure java installed in your machine)

2. Run server first
	Open another command line and run command below:
	
		java -jar server.jar {yourserverport}	

3. Run client second
	Open your command line and run command below:

		java -jar client.jar {yourserveripaddress} {yourserverport}

4. You should be able to see some words below in client command line:

yourname>java -jar client.jar {yourserveripaddress} {yourserverport}
Enter text:  //please enter the message you want to send to server here

5. Both server and client program will exit after client received response