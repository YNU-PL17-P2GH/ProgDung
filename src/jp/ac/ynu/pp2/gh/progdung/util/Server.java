package jp.ac.ynu.pp2.gh.progdung.util;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	private static int port=10500; // サーバの待ち受けポート
	
	public Server(int port){
		Server.port = port; //待ち受けポートを渡す
	}
	
	public static void main(String[] args){
		File userDir = new File("user");
		if (!userDir.exists()) {
			userDir.mkdirs();
		}
		
		File dataDir = new File("savedata");
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Server Ready");
			while(true){						//常にクライアントとの新規接続を待つ
				Socket socket = serverSocket.accept();
				new Receiver(socket).start();	//socketスレッドを起動
				System.out.println("thread start");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally {
		      try {
		          if (serverSocket != null) {
		            serverSocket.close();
		          }
		        } catch (IOException e) {}
		}
	}
	
}

