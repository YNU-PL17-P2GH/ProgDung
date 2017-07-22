package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class TitlePanel extends BackgroundedPanel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 3675595981556096954L;

	public TitlePanel(final TransitionCallback pCallback) {
		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/title_back.png")));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}

		setPreferredSize(new Dimension(1260, 700));

		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(10);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);

		JLabel lTitleLabel;
		try {
			lTitleLabel = new JLabel(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/titlelogo.png"))));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
//		lTitleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 144));

		Font lFont = new Font(Font.MONOSPACED, Font.PLAIN, 28);
		JLabel lUserLabel = new JLabel("ユーザーネーム");
		lUserLabel.setFont(lFont);
		JTextField lUserField = new JTextField(32);
		lUserField.setFont(lFont);
		JLabel lPassLabel = new JLabel("パスワード");
		lPassLabel.setFont(lFont);
		JPasswordField lPassField = new JPasswordField(32);
		lPassField.setFont(lFont);

		JButton lLoginButton = new JButton("ログイン");
		lLoginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pCallback.login(lUserField.getText(), lPassField.getPassword())) {
					pCallback.showSelect(pCallback);
				}
			}

		});
		lLoginButton.setFont(lFont);
		JButton lRegisterButton = new JButton("新規登録");
		lRegisterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pCallback.showRegister(pCallback);
			}
		});
		lRegisterButton.setFont(lFont);

		add(lTitleLabel);
		add(new Separator());
		add(lUserLabel);
		add(new Separator());
		add(lUserField);
		add(new Separator());
		add(lPassLabel);
		add(new Separator());
		add(lPassField);
		add(new Separator());

		add(lLoginButton);
		add(new Separator());
		add(lRegisterButton);
	}

}
