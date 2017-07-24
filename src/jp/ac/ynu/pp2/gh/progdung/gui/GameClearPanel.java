package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class GameClearPanel extends BackgroundedPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4391381659704172647L;

	public GameClearPanel(final TransitionCallback pCallback, int clearStage) {
		super();
		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/clear_back.jpg")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(30);
		layout1.setHgap(120);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);

		Font lFont = new Font(Font.MONOSPACED, Font.PLAIN, 72);
		JLabel clearLabel = new JLabel("Congratulation!!!");
		clearLabel.setFont(lFont);
		clearLabel.setForeground(Color.YELLOW);

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 32);
		JLabel clearText =  null;
		if(clearStage == 0) {
			clearText = new JLabel("<html><Div Align=\"center\">あなたは英知のかけらを手に入れた<br>"
					+ "それは目に見えなず、形もない<br>"
					+ "しかし、それは確かにあなたの手の中に...</Div></html>");
		}else if(clearStage == 1) {
			clearText = new JLabel("<html><Div Align=\"center\">あなたは英知を手に入れた<br>"
					+ "それには、色も匂いもない<br>"
					+ "しかし、あなたはその存在を強く認識しているだろう...</Div></html>");
		}
		clearText.setFont(font);
		clearText.setForeground(Color.LIGHT_GRAY);
		clearLabel.setHorizontalAlignment(JLabel.CENTER);


		JLabel nextText = null;
		if(clearStage == 0) {
			nextText = new JLabel("<html>あなたがもし更なる英知を求めるなら 次なるステージへと向かうといい...</html>");
		}else if(clearStage == 1) {
			nextText = new JLabel("<html><Div Align=\"center\">あなたがもし更なる高みを目指すのなら それを阻むものは何もない<br>"
					+ "ここで得られたものは、ほんのヒトカケラ あなたの更なる研鑽を願う...<br>"
					+ "THANK YOU FOR PLAYING</Div></html>");
		}
		nextText.setFont(font);
		nextText.setForeground(Color.WHITE);

		JButton lButton = new JButton("難易度選択へ戻る");
		lButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showSelect(pCallback);
			}
		});

		add(clearLabel);
		add(new Separator());
		add(clearText);
		add(new Separator());
		add(nextText);
		add(lButton);

	}

}
