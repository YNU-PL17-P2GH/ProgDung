package jp.ac.ynu.pp2.gh.progdung.test;

import jp.ac.ynu.pp2.gh.progdung.util.Connection;
import jp.ac.ynu.pp2.gh.progdung.util.SaveData;

public class ConnectionTester {
	
	public static void main(String[] args) {
		Connection.init();
		Connection.sendObject("SEND");
		System.out.println(Connection.receiveObject());
		
		SaveData saveData = new SaveData();
		saveData.setFlag("userName", "6suke");
		saveData.setFlag("passWord", "mian");
		
		Connection.sendObject(saveData);
		Object dObject = Connection.receiveObject();
		System.out.println(((SaveData) dObject).getFlag("userName"));
		System.out.println(((SaveData) dObject).getFlag("passWord"));
	}

}
