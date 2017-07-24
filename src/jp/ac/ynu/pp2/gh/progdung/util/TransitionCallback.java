package jp.ac.ynu.pp2.gh.progdung.util;

import javax.swing.JFrame;

public interface TransitionCallback {

	boolean login(String pUsername, char[] pWord);

	boolean checkUser(String pUsername);

	boolean userRegister(String pUsername, char[] pWord);

	boolean saveUserData();

	boolean isLoggedin();

	// TODO 実装
//	UserData getUserData();

	void showTitle(TransitionCallback pCallback);

	void showStory(TransitionCallback pCallback);

	void showSelect(TransitionCallback pCallback);

	void showDungeon(TransitionCallback pCallback, int selectedStage);

	void showGameClear(TransitionCallback pCallback, int clearStage);

	JFrame getMainFrame();

	void showHint(String string, boolean force);

	void showCoder();

	void hideCoder();

	boolean isCoderShown();

	boolean isHintShown();

	SaveData getSaveData();

	void setSource(String pSource);

	void stdoutUpdate();

	void stderrUpdate();

	void showHintOnCoder();

	void showRegister(TransitionCallback pCallback);

	void toggleMenu();
}
