package jp.ac.ynu.pp2.gh.progdung.test;

import jp.ac.ynu.pp2.gh.progdung.util.SaveData;

public class SaveDataDriver {
	
	public static void main(String[] args) {
		SaveData saveData = new SaveData();
		
		System.out.println("Set userName <- 6suke");
		saveData.setFlag("userName", "6suke");
		System.out.println("Set passWord <- mian");
		saveData.setFlag("passWord", "mian");
		
		System.out.println();
		
		System.out.println("userName = " + saveData.getFlag("userName"));
		System.out.println("passWord = " + saveData.getFlag("passWord"));
	}

}
