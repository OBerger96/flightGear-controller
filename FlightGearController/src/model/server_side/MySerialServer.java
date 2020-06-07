package model.server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	// Data members.
	private int port;
	private ClientHandler clientHandler;
	private volatile boolean stop;
	
	// CTOR.
	public MySerialServer() { this.stop = false; }

	// Opens the server.
	@Override
	public void open(int port,ClientHandler clientHandler) {
		this.port = port;
		this.clientHandler = clientHandler;
		new Thread(() -> {
			try {
 				runServer();
			} catch (Exception e) { e.printStackTrace(); }
		}).start();
	}

	// Stops the server.
	@Override
	public void stop() { stop = true; }

	// After the server is opened, runs the server.
	private void runServer()throws Exception {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server is open. waiting for clients...");
		server.setSoTimeout(300000000);
		
		while(!stop){
			try{
				Socket aClient=server.accept(); // Blocking call.
				System.out.println("Client has connected...");
				try {
					clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					aClient.close();
				} catch (IOException e) { System.out.println("invalid input2-output..."); e.printStackTrace(); }
			} catch(SocketTimeoutException e) { System.out.println("Time Out..."); e.printStackTrace(); }
		}
		
		server.close();
	}
}