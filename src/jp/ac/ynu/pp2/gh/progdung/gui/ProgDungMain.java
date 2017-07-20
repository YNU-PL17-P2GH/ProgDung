package jp.ac.ynu.pp2.gh.progdung.gui;

import javax.swing.JFrame;

import jp.ac.ynu.pp2.gh.progdung.util.SaveData;
import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class ProgDungMain extends JFrame implements TransitionCallback {

	/**
	 *
	 */
	private static final long serialVersionUID = 3568697032148655175L;
	private DungeonPanel lDungeonPanel;
	private SaveData myData;

	public ProgDungMain() {
		super("Programme Dungeona");
		myData = new SaveData();
		setSize(1280, 760);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		setVisible(true);

		showTitle(this);
	}

	public static void main(String[] args) {
		new ProgDungMain();
	}

	@Override
	public boolean login(String pUsername, char[] pWord) {
		return true;
	}

	@Override
	public boolean checkUser(String pUsername) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean userRegister(String pUsername, char[] pWord) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean saveUserData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLoggedin() {
		return myData != null;
	}

	@Override
	public void showTitle(TransitionCallback pCallback) {
		setContentPane(new TitlePanel(pCallback));
		validate();
	}

	@Override
	public void showStory(TransitionCallback pCallback) {
		setContentPane(new StoryPanel(pCallback));
		validate();
	}
	
	@Override
	public void showGameClear(TransitionCallback pCallback) {
		setContentPane(new GameClearPanel(pCallback));
		validate();
	}
	
	@Override
	public void showRegister(TransitionCallback pCallback) {
		setContentPane(new RegisterPanel(pCallback));
		validate();
	}

	@Override
	public void showSelect(TransitionCallback pCallback) {
		setContentPane(new DungeonSelectPanel(pCallback));
		validate();
	}

	@Override
	public void showDungeon(TransitionCallback pCallback, int selectedStage) {
		lDungeonPanel = new DungeonPanel(pCallback, selectedStage);
		setContentPane(lDungeonPanel);
		validate();
		lDungeonPanel.init();
		lDungeonPanel.start();
	}

	@Override
	public JFrame getMainFrame() {
		return this;
	}

	@Override
	public void showHint(String string, boolean force) {
		lDungeonPanel.showHint(string, force);
	}

	@Override
	public boolean isHintShown() {
		return lDungeonPanel.isHintShown();
	}

	@Override
	public void showCoder() {
		lDungeonPanel.showCoder();
	}

	@Override
	public void hideCoder() {
		lDungeonPanel.hideCoder();
	}

	@Override
	public void showHintOnCoder() {
		lDungeonPanel.showHintOnCoder();
	}

	@Override
	public void setSource(String pSource) {
		lDungeonPanel.setSource(pSource);
	}

	@Override
	public boolean isCoderShown() {
		return lDungeonPanel.isCoderShown();
	}

	@Override
	public SaveData getSaveData() {
		return myData;
	}

	@Override
	public void stdoutUpdate() {
		lDungeonPanel.stdoutUpdate();
	}

	@Override
	public void stderrUpdate() {
		lDungeonPanel.stderrUpdate();
	}
}
