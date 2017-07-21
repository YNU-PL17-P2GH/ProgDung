package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class RegisterPanel extends BackgroundedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2515425115370666083L;
	
	public RegisterPanel(TransitionCallback pCallback) {
		try {
			setBackground(ImageIO.read(getClass().getClassLoader().getResourceAsStream("media/gui/title_back.png")));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		FlowLayout layout1 = new FlowLayout();
		layout1.setVgap(10);
		layout1.setAlignment(FlowLayout.CENTER);
		setLayout(layout1);
		
		JLabel lTitleLabel = new JLabel("新規登録");
		lTitleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 81));
		
		Font lFont = new Font(Font.MONOSPACED, Font.PLAIN, 28);
		JLabel lUserLabel = new JLabel("ユーザーネーム");
		lUserLabel.setFont(lFont);
		JTextField lUserField = new JTextField(32);
		lUserField.setFont(lFont);
		JLabel lPassLabel = new JLabel("パスワード");
		lPassLabel.setFont(lFont);
		JPasswordField lPassField = new JPasswordField(32);
		lPassField.setFont(lFont);
		JLabel lPassRLabel = new JLabel("パスワード再入力");
		lPassRLabel.setFont(lFont);
		JPasswordField lPassRField = new JPasswordField(32);
		lPassRField.setFont(lFont);
		
		JButton lDummyCheckButton = new JButton("チェック") {
			@Override
			public void paint(Graphics g) {
			}
		};
		lDummyCheckButton.setEnabled(false);
		JButton lCheckButton = new JButton("チェック");
		lCheckButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO User Check
				
			}
		});
		
		JButton lRegisterButton = new JButton("登録");
		lRegisterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!new String(lPassField.getPassword()).equals(new String(lPassRField.getPassword()))) {
					JOptionPane.showMessageDialog(RegisterPanel.this, "確認用のパスワードが一致しません", "", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (pCallback.userRegister(lUserField.getText(), lPassField.getPassword())) {
					pCallback.showStory(pCallback);
					return;
				}
				JOptionPane.showMessageDialog(RegisterPanel.this, "登録できませんでした", "", JOptionPane.ERROR_MESSAGE);
			}
		});
		lRegisterButton.setFont(lFont);
		
		JButton lBackButton = new JButton("キャンセル");
		lBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pCallback.showTitle(pCallback);
			}
		});
		
		add(lTitleLabel);
		add(new Separator());
		add(lUserLabel);
		add(new Separator());
		add(lDummyCheckButton);
		add(lUserField);
		add(lCheckButton);
		add(new Separator());
		add(lPassLabel);
		add(new Separator());
		add(lPassField);
		add(new Separator());
		add(lPassRLabel);
		add(new Separator());
		add(lPassRField);
		add(new Separator());
		
		add(lRegisterButton);
		add(new Separator());
		add(lBackButton);
	}
	

}
