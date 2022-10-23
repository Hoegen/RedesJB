package main;


public class Main {

	public static void main(String[] args) {
		
		//new ClientWindow();
		
		//new ServidorFake();
		
		server.ServidorFake.main();
		new Thread(()-> new client.ClientWindow()).start();
	}

}
