package jp.ac.ynu.pp2.gh.progdung.util;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

	public static Socket mySocket;
	public static final String SERVER_ADDR = "localhost";
	public static final int SERVER_PORT = 10500;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;

	public static void init() {
		try {
			mySocket = new Socket(SERVER_ADDR, SERVER_PORT);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			System.out.println("socket was created");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendObject(Object pObject) {
		try {
			oos.writeObject(pObject);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public static Object receiveObject() {
		try {
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;

	}

}
