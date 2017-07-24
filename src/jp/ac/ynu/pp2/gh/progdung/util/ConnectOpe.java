package jp.ac.ynu.pp2.gh.progdung.util;

import java.util.Scanner;


public class ConnectOpe {

	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("cliant active");
		Connection con = new Connection();
		con.init();
		String str;
		
		System.out.println("cliant active2");
		
		SaveData data =new SaveData();
		data.setFlag("userName", "shintani");
		data.setFlag("passWord", "natsuo");
		Scanner scan=new Scanner(System.in);
		while(true){
			
			str=scan.next();
			data.setFlag("operation", str);	//サーバへのオペレーション指定
			
			switch(str){
			case "a":
				con.sendObject(data);
				data=(SaveData) con.receiveObject();
				
				System.out.println("sing up:"+data.getFlag("signup"));
				break;
			case "b":
				con.sendObject(data);
				data=(SaveData) con.receiveObject();
				
				System.out.println("sign in:"+data.getFlag("signin"));
				break;
			case "c":
				con.sendObject(data);
				data=(SaveData) con.receiveObject();
				
				System.out.println("save data:"+data.getFlag("savedata"));
				break;
			case "d":
				data=(SaveData) con.receiveObject();
				
				System.out.println("load data"+data.getFlag("loaddata"));
				break;
			case "e":
				con.sendObject(data);
				break;
				
			default: ;
			
				
			}
			//scan.close();
		}
		
	}
	
	
	

}
