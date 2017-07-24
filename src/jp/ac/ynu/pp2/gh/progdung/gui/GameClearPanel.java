package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class GameClearPanel extends BackgroundedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391381659704172647L;
	
	public GameClearPanel(final TransitionCallback pCallback) {
		super();
		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/clear.png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		setBorder(BorderFactory.createEmptyBorder(500, 0, 0, 0));
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(50);
		layout1.setHgap(120);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);
		
		JButton lButton = new JButton("難易度選択へ戻る");
		lButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showSelect(pCallback);
			}
		});
		
		add(lButton);
		
	}

}
