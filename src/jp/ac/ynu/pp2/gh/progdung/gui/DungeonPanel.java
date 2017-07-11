package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

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
		setLayout(new OverlayLayout(this));
		
		// TODO ヒントとか出したい
		JPanel lpanel = new JPanel();
//		lpanel.setPreferredSize(new Dimension(300, 100));
//		lpanel.setBackground(Color.blue);
//		lpanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		add(lpanel);
		
		JPanel lPlayCoverPanel = new JPanel();
		FlowLayout lFlow1 = new FlowLayout();
		lFlow1.setAlignment(FlowLayout.LEFT);
		lPlayCoverPanel.setLayout(lFlow1);
		lPlayCoverPanel.setOpaque(false);
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
