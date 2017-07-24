package jp.ac.ynu.pp2.gh.progdung.util;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
	
	public static Socket mySocket;
	public static final String SERVER_ADDR = "localhost";
	public static final int SERVER_PORT = 9999;
	
	public void init() {
		try {
			mySocket = new Socket(SERVER_ADDR, SERVER_PORT);
			System.out.println("socket was created");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendObject(Object pObject) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			oos.writeObject(pObject);
				} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
	
	public Object receiveObject() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(mySocket.getInputStream());
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
		
	}
	
}
