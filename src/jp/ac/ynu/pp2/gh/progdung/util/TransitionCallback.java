package jp.ac.ynu.pp2.gh.progdung.util;

import javax.swing.JFrame;

public interface TransitionCallback {
	
	boolean login(String pUsername, char[] pWord);
	
	boolean userRegister(String pUsername, char[] pWord);
	
	boolean saveUserData();
	
	// TODO 実装
//	UserData getUserData();
	
	void showTitle();
	
	void showSelect();
	
	void showDungeon(int selectedStage);
	
	JFrame getMainFrame();
	
	void showHint(String string, boolean force);

	void showCoder();

	void hideCoder();

	boolean isCoderShown();

	boolean isHintShown();

	SaveData getSaveData();

}
