package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javafx.scene.layout.Border;
import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class DungeonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5911049728428748438L;
	
	private TransitionCallback callback;

	private DungeonPlay lDungeonPlay;
	
	public DungeonPanel(TransitionCallback pCallback) {
		super();
		
		callback = pCallback;
		setLayout(new BorderLayout());
		
		JPanel lPlayCoverPanel = new JPanel();
		FlowLayout lFlow1 = new FlowLayout();
		lFlow1.setAlignment(FlowLayout.LEFT);
		lPlayCoverPanel.setLayout(lFlow1);
		lPlayCoverPanel.setBorder(BorderFactory.createEmptyBorder(10, 640-352, 10, 0));
		lDungeonPlay = new DungeonPlay(callback);
		lPlayCoverPanel.add(lDungeonPlay);
		add(lPlayCoverPanel, BorderLayout.CENTER);
	}
	
	public void init() {
		lDungeonPlay.init();
	}
	
	public void start() {
		lDungeonPlay.start();
	}

}
