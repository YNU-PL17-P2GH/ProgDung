package jp.ac.ynu.pp2.gh.progdung;

import javax.swing.JFrame;

public class ProgDungMain extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3568697032148655175L;

	public ProgDungMain() {
		super("Programme Dungeona");
		setSize(1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new ProgDungMain();
	}

}
