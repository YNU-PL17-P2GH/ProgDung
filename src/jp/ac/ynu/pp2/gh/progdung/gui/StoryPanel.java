package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class StoryPanel extends BackgroundedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391381659704172647L;
	
	public StoryPanel(final TransitionCallback pCallback) {
		super();
		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/story_back.png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(50);
		layout1.setHgap(120);
		layout1.setAlignment(FlowLayout.TRAILING);
		setLayout(layout1);
		
		// TODO 適当にストーリーを書き込む
		
		JButton lButton = new JButton("進む");
		lButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showSelect(pCallback);
			}
		});
		
		add(lButton);
		
	}

}
