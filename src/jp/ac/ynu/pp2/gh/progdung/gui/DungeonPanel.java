package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class DungeonPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5911049728428748438L;
	
	private TransitionCallback callback;

	private DungeonPlay lDungeonPlay;

	private JPanel hintPanel;

	private JLabel hintLabel;
	
	public DungeonPanel(TransitionCallback pCallback) {
		super();
		
		callback = pCallback;
		setLayout(new BorderLayout());
		
		// Overlay
		hintPanel = new JPanel();
		hintPanel.setBackground(Color.blue);
		hintLabel = new JLabel();
		hintLabel.setForeground(Color.white);
		hintPanel.add(hintLabel);
		add(hintPanel);

		JPanel lPlayCoverPanel = new JPanel();
		lPlayCoverPanel.setOpaque(false);
		FlowLayout lFlow1 = new FlowLayout();
		lFlow1.setAlignment(FlowLayout.LEFT);
		lPlayCoverPanel.setLayout(lFlow1);
		lPlayCoverPanel.setBorder(BorderFactory.createEmptyBorder(10, 640-352, 10, 0));
		lDungeonPlay = new DungeonPlay(callback);
		lPlayCoverPanel.add(lDungeonPlay);
		add(lPlayCoverPanel/*, BorderLayout.CENTER*/);
		setLayer(lPlayCoverPanel, 1);
		
		hintPanel.setBounds(800, 450, 0, 0);
		setLayer(hintPanel, 0);
		validate();
	}
	
	public void init() {
		lDungeonPlay.init();
	}
	
	public void start() {
		lDungeonPlay.start();
	}

	void showHint(String string) {
		if (getLayer(hintLabel) > 0) {
			return;
		}
		hintLabel.setText(string);
		hintPanel.setBounds(640, 450, 300, 200);
		setLayer(hintPanel, 2);
		validate();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				hintPanel.setBounds(0, 0, 0, 0);
				setLayer(hintPanel, 0);
				validate();
			};
		}.start();
	}

}
