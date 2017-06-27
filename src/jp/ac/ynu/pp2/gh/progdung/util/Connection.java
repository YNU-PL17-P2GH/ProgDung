package jp.ac.ynu.pp2.gh.progdung.util;

import java.io.IOException;
import java.net.Socket;

public class Connection {
	
	public static Socket mySocket;
	public static final String SERVER_ADDR = "localhost";
	public static final int SERVER_PORT = 50000;
	
	public static void init() {
		try {
			mySocket = new Socket(SERVER_ADDR, SERVER_PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendObject(Object pObject) {
		
	}
	
	public static void receiveObject(Object pObject) {
		
	}

}
