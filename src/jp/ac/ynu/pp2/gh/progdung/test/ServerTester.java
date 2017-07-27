package jp.ac.ynu.pp2.gh.progdung.test;

import java.util.Scanner;

import jp.ac.ynu.pp2.gh.progdung.util.Connection;
import jp.ac.ynu.pp2.gh.progdung.util.SaveData;

public class ServerTester {

	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("client active");
		Connection.init();
		String str;
		
		System.out.println("client active2");
		
		SaveData data =new SaveData();
		data.setFlag("userName", "shintani");
		data.setFlag("passWord", "natsuo");
		Scanner scan=new Scanner(System.in);
		while(true){
			
			str=scan.next();
			data.setFlag("operation", str);	//サーバへのオペレーション指定
			
			switch(str){
			case "a":
				Connection.sendObject(data);
				data=(SaveData) Connection.receiveObject();
				
				System.out.println("sing up:"+data.getFlag("signup"));
				break;
			case "b":
				Connection.sendObject(data);
				data=(SaveData) Connection.receiveObject();
				
				System.out.println("sign in:"+data.getFlag("signin"));
				break;
			case "c":
				Connection.sendObject(data);
				data=(SaveData) Connection.receiveObject();
				
				System.out.println("save data:"+data.getFlag("savedata"));
				break;
			case "d":
				Connection.sendObject(data);
				data=(SaveData) Connection.receiveObject();
				System.out.println(data.getFlag("userName"));
				
				System.out.println("load data:"+data.getFlag("loaddata"));
				System.out.println("sing up:"+data.getFlag("signup"));
				System.out.println("sign in:"+data.getFlag("signin"));
				System.out.println("save data:"+data.getFlag("savedata"));
				System.out.println(data.getFlag("passWord"));
				break;
			case "e":
				Connection.sendObject(data);
				scan.close();
				System.exit(0);
				break;
				
			default: ;
			
				
			}
			//scan.close();
		}
		
	}
	
	
	

}
