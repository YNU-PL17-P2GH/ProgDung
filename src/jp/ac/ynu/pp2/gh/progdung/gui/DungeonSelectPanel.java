package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class DungeonSelectPanel extends BackgroundedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -765287721854265734L;
	
	public DungeonSelectPanel(TransitionCallback pCallback) {
		super();

		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/select_back.bmp")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(30);
		layout1.setHgap(120);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);
		
		Font lFont1 = new Font(Font.SERIF, Font.BOLD, 96);
		JLabel lSelectLabel = new JLabel("ステージ選択");
		lSelectLabel.setForeground(Color.WHITE);
		lSelectLabel.setFont(lFont1);

		Font lFont2 = new Font(Font.SERIF, Font.BOLD, 36);
		JButton lBeginner = new JButton("初心者向け");
		lBeginner.setFont(lFont2);
		lBeginner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showDungeon(pCallback, 0);
			}
		});
		lBeginner.setPreferredSize(new Dimension(360, 360));
		
		JButton lAlgo = new JButton("アルゴリズム");
		lAlgo.setFont(lFont2);
		lAlgo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showDungeon(pCallback, 1);
			}
		});
		lAlgo.setPreferredSize(new Dimension(360, 360));
		
		JButton lBack = new JButton("タイトルへ戻る");
		lBack.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		lBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				pCallback.showTitle(pCallback);
			}
		});
		
		add(lSelectLabel);
		add(new Separator());
		add(lBeginner);
		add(lAlgo);
		add(new Separator());
		add(lBack);
	}

}
