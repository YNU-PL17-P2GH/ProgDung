package jp.ac.ynu.pp2.gh.progdung.gui;

import javax.swing.JFrame;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class ProgDungMain extends JFrame implements TransitionCallback {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3568697032148655175L;
	private DungeonPanel lDungeonPanel;

	public ProgDungMain() {
		super("Programme Dungeona");
		setSize(1280, 760);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setVisible(true);

		lDungeonPanel = new DungeonPanel(this);
		add(lDungeonPanel);
		validate();
		lDungeonPanel.init();
		lDungeonPanel.start();
	}

	public static void main(String[] args) {
		new ProgDungMain();
	}

	@Override
	public boolean login(String pUsername, char[] pWord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userRegister(String pUsername, char[] pWord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveUserData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showTitle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDungeon(int selectedStage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JFrame getMainFrame() {
		return this;
	}

	@Override
	public void showHint(String string) {
		lDungeonPanel.showHint(string);
	}
	
	

}
