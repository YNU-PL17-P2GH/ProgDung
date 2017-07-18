package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TitlePanel extends JPanel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3675595981556096954L;
	
	public TitlePanel() {
		setLayout(new FlowLayout());
		
		JLabel lTitleLabel = new JLabel("ALGEON");
		lTitleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 144));
		
		JLabel lUserLabel = new JLabel("ユーザーネーム");
		JTextField lUserField = new JTextField(32);
		JLabel lPassLabel = new JLabel("パスワード");
		JPasswordField lPassField = new JPasswordField(32);
		
		JButton lLoginButton = new JButton("ログイン");
		JButton lRegisterButton = new JButton("新規登録");
		
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
	
	public class Separator extends JPanel {

		/**
		 * 
		 */
		private static final long	serialVersionUID	= -7709551027972943549L;
		
		public Separator() {
			super();
			setPreferredSize(new Dimension(1280, 0));
		}
	}

}
