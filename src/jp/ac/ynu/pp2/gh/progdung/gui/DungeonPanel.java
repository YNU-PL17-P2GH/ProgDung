package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javafx.scene.layout.Border;
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

	private JTextArea sourceArea;

	private JScrollPane sourcePane;
	
	public DungeonPanel(TransitionCallback pCallback) {
		super();
		
		callback = pCallback;
		setLayout(new BorderLayout());
		
		// Overlay
		hintPanel = new JPanel();
		hintPanel.setBackground(Color.blue);
		hintLabel = new JLabel();
		hintLabel.setForeground(Color.white);
		hintLabel.setFont(new Font("sans", Font.BOLD, 16));
		hintPanel.add(hintLabel);
		add(hintPanel);
		
		// TextArea
		sourceArea = new JTextArea();
		sourcePane = new JScrollPane(sourceArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sourcePane.setBounds(30, 30, callback.getMainFrame().getWidth()-75, callback.getMainFrame().getHeight()-90);
		sourcePane.setVisible(false);
		add(sourcePane);
		setLayer(sourcePane, 1);
		

		JPanel lPlayCoverPanel = new JPanel();
		lPlayCoverPanel.setOpaque(false);
		FlowLayout lFlow1 = new FlowLayout();
		lFlow1.setAlignment(FlowLayout.LEFT);
		lPlayCoverPanel.setLayout(lFlow1);
		lPlayCoverPanel.setBorder(BorderFactory.createEmptyBorder(10, 640-352, 10, 0));
		lDungeonPlay = new DungeonPlay(callback);
		lPlayCoverPanel.add(lDungeonPlay);
		add(lPlayCoverPanel/*, BorderLayout.CENTER*/);
		setLayer(lPlayCoverPanel, 0);
		
		hintPanel.setVisible(false);
//		hintPanel.setBounds(800, 450, 0, 0);
		setLayer(hintPanel, 2);
		revalidate();
	}
	
	public void init() {
		lDungeonPlay.init();
	}
	
	public void start() {
		lDungeonPlay.start();
	}

	void showHint(String string) {
		if (hintPanel.isVisible()) {
			return;
		}
		hintLabel.setText(string);
		hintPanel.setBounds(640-150, 400, 300, 200);
		hintPanel.setVisible(true);
//		setLayer(hintPanel, 2);
		revalidate();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				hintPanel.setVisible(false);
//				hintPanel.setBounds(0, 0, 0, 0);
//				setLayer(hintPanel, 0);
				revalidate();
			};
		}.start();
	}

	void showCoder() {
		sourcePane.setVisible(true);
		revalidate();
	}

	void hideCoder() {
		sourcePane.setVisible(false);
		revalidate();
	}

	boolean isCoderShown() {
		return sourcePane.isVisible();
	}

}
