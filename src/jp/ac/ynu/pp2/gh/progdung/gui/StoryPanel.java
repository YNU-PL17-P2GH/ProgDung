package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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
		setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(20);
		layout1.setHgap(120);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);

		// TODO 適当にストーリーを書き込む
		//Font font = new Font(Font.MONOSPACED, Font.PLAIN, 32);
		/*JLabel storyText = new JLabel("<html><Div Align=\"center\">ここは過去に崩壊した文明の遺跡...<br>"
				+ "今よりもはるかに進んだその文明が作り上げたその『迷宮』は<br>"
				+ "その文明が滅び、すべて終わりを迎えてもなお<br>"
				+ "その役割を忘れず、侵入者を拒む...<br>"
				+ "あなたはその『迷宮』の謎を解き明かし<br>"
				+ "その文明の英知を我がものとしようとする冒険者の一人<br>"
				+ "あなたに果たしてこの『迷宮』を解き明かし<br>"
				+ "英知をその手にすることができるのでしょうか<br>"
				+ "あなたの冒険が今幕を上げる...</Div></html>");*/

		JLabel storyText = null;
		try {
			storyText = new JLabel(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/story_text.png"))));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/*storyText.setFont(font);
		storyText.setForeground(Color.LIGHT_GRAY);
		Font lfont = new Font(Font.MONOSPACED, Font.PLAIN, 42);
		JLabel storySubText = new JLabel("<html>あなたを阻むその『迷宮』の名は「<font color=\"red\">ALGEON</font>」</html>") ;
		storySubText.setFont(lfont);
		storySubText.setForeground(Color.WHITE);*/

		JButton lButton = new JButton("進む");
		lButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showSelect(pCallback);
			}
		});
		lButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

		add(storyText);
		add(new Separator());
		//add(storySubText);
		//add(new Separator());
		add(lButton);

	}

}
