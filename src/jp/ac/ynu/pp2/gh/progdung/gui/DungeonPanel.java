package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.StringWriter;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
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

	private Thread hideThread;

	private JTextArea stdoutArea;

	private JScrollPane stdoutPane;

	private StringWriter stdout;

	private StringWriter stderr;

	private JTextArea stderrArea;

	private JScrollPane stderrPane;

	private JPanel menuPanel;

	private JButton saveButton;

	private JPanel operatePanel;

	public DungeonPanel(TransitionCallback pCallback, int selectedStage) {
		super();

		callback = pCallback;
		setLayout(new BorderLayout());

		// Overlay
		hintPanel = new JPanel();
		hintPanel.setBackground(Color.blue);
		hintLabel = new JLabel();
		hintLabel.setForeground(Color.white);
		Font lFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		hintLabel.setFont(lFont);
		hintPanel.add(hintLabel);
		add(hintPanel);

		//標準出力
		stdout = new StringWriter();
		stdoutArea = new JTextArea();
		stdoutArea.setFocusable(false);
		stdoutArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		stdoutArea.setEditable(false);
		stdoutArea.setBackground(Color.BLACK);
		stdoutArea.setForeground(Color.WHITE);

		stdoutPane = new JScrollPane(stdoutArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(stdoutPane);
		setLayer(stdoutPane, 1);
		stdoutPane.setBounds(1000, 100, 260, 200);

		//エラー出力
		stderr = new StringWriter();
		stderrArea = new JTextArea();
		stderrArea.setFocusable(false);
		stderrArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		stderrArea.setEditable(false);
		stderrArea.setBackground(Color.BLACK);
		stderrArea.setForeground(Color.RED);

		stderrPane = new JScrollPane(stderrArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(stderrPane);
		setLayer(stderrPane, 1);
		stderrPane.setBounds(1000, 500, 260, 200);

		// TextArea
		sourceArea = new JTextArea();
		sourceArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		sourcePane = new JScrollPane(sourceArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sourcePane.setBounds(30, 30, callback.getMainFrame().getWidth()-75, callback.getMainFrame().getHeight()-90);
		sourcePane.setVisible(false);
		sourceArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					hideCoder();
					callback.getMainFrame().requestFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		add(sourcePane);
		setLayer(sourcePane, 2);

		//操作説明
		operatePanel = new JPanel(new FlowLayout());
		operatePanel.setAlignmentX(FlowLayout.CENTER);
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 24);
		setLayer(operatePanel, 1);
		operatePanel.setBounds(20, 250, 260, 200);
		JLabel operateLabel = new JLabel("<html><font color=\"red\">Z : 調べる</font><br>"
				+ "<font color=\"green\">X : メニューを開く</font><br>"
				+ "<font color=\"blue\"> C : 走る</font></html>");
		operateLabel.setFont(font);
		operatePanel.add(operateLabel);
		add(operatePanel);

		// menu
		menuPanel = new JPanel();
		menuPanel.setBounds(100, 240, 1080, 280);
		menuPanel.setBackground(Color.black);
		menuPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		FlowLayout lFlow2 = new FlowLayout();
		lFlow2.setHgap(25);
		lFlow2.setAlignment(FlowLayout.CENTER);
		menuPanel.setLayout(lFlow2);

		Font lFont2 = new Font(Font.MONOSPACED, Font.BOLD, 20);
		JLabel lMenuLabel = new JLabel("MENU");
		lMenuLabel.setFont(lFont2);
		lMenuLabel.setForeground(Color.white);

		JButton lResumeButton = new JButton("再開");
		lResumeButton.setBorderPainted(false);
		lResumeButton.setContentAreaFilled(false);
		lResumeButton.setForeground(Color.white);
		lResumeButton.setFont(lFont2);
		lResumeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callback.getMainFrame().requestFocus();
				toggleMenu();
			}
		});

		saveButton = new JButton("保存");
		saveButton.setBorderPainted(false);
		saveButton.setContentAreaFilled(false);
		saveButton.setForeground(Color.white);
		saveButton.setFont(lFont2);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (callback.saveUserData()) {
					((AbstractButton) arg0.getSource()).setText("保存しました");
				} else {
					JOptionPane.showMessageDialog(DungeonPanel.this, "保存できませんでした", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton lBackButton = new JButton("ステージ変更(難易度選択へ戻る)");
		lBackButton.setForeground(Color.white);
		lBackButton.setBorderPainted(false);
		lBackButton.setContentAreaFilled(false);
		lBackButton.setForeground(Color.white);
		lBackButton.setFont(lFont2);
		lBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callback.showSelect(callback);
			}
		});

		menuPanel.add(lMenuLabel);
		menuPanel.add(new Separator());
		menuPanel.add(lResumeButton);
		menuPanel.add(new Separator());
		menuPanel.add(saveButton);
		menuPanel.add(new Separator());
		menuPanel.add(lBackButton);
		add(menuPanel);
		menuPanel.setVisible(false);
		setLayer(menuPanel, 4);

		JPanel lPlayCoverPanel = new JPanel();
		lPlayCoverPanel.setOpaque(false);
		FlowLayout lFlow1 = new FlowLayout();
		lFlow1.setAlignment(FlowLayout.LEFT);
		lPlayCoverPanel.setLayout(lFlow1);
		lPlayCoverPanel.setBorder(BorderFactory.createEmptyBorder(10, 640-352, 10, 0));

		lDungeonPlay = new DungeonPlay(callback, selectedStage);

		lPlayCoverPanel.add(lDungeonPlay);
		add(lPlayCoverPanel/*, BorderLayout.CENTER*/);
		setLayer(lPlayCoverPanel, 0);

		hintPanel.setVisible(false);
//		hintPanel.setBounds(800, 450, 0, 0);
		setLayer(hintPanel, 3);
		revalidate();


		revalidate();
	}

	public void init() {
		lDungeonPlay.init();
	}

	public void start() {
		lDungeonPlay.start();
	}

	void toggleMenu() {
		if (menuPanel.isVisible()) {
			menuPanel.setVisible(false);
		} else {
			saveButton.setText("保存");
			menuPanel.setVisible(true);
		}
	}

	void showHint(String string, boolean force) {
		if (!force && isHintShown()) {
			return;
		}
		hintLabel.setText(string);
		hintPanel.setBounds(640-200, 450, 400, 200);
		hintPanel.setVisible(true);
//		setLayer(hintPanel, 2);
		revalidate();
		if (hideThread != null && hideThread.isAlive()) {
			hideThread.interrupt();
		}
		hideThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
					hintPanel.setVisible(false);
//					hintPanel.setBounds(0, 0, 0, 0);
//					setLayer(hintPanel, 0);
					revalidate();
				} catch (InterruptedException e) {
				}
			};
		};
		hideThread.start();
	}

	void showCoder() {
		sourceArea.setText(lDungeonPlay.handler.getCurrentFocusedPc().getAllocObj().sourceRuby);
		sourcePane.setVisible(true);
		revalidate();
		sourceArea.requestFocus();
	}

	void hideCoder() {
		if (!sourcePane.isVisible()) {
			return;
		}

		sourcePane.setVisible(false);
		//ヒント表示なら
		if(lDungeonPlay.handler.getCurrentFocusedPc() instanceof MapHintObject) {
			sourceArea.setEditable(true);
			return;
		}

		lDungeonPlay.handler.getCurrentFocusedPc().getAllocObj().sourceRuby = sourceArea.getText();
		revalidate();

		// 実行?
		if (JOptionPane.showConfirmDialog(
				callback.getMainFrame(), "プログラムを今すぐ実行しますか?", "", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION) {

			lDungeonPlay.handler.getCurrentFocusedPc().getAllocObj().launchRubyWithThread(Ruby.newInstance(), stdout, stderr, lDungeonPlay.handler.getCurrentFocusedPc().getAllocObj().getOperator());
		}
	}

	void showHintOnCoder() {
		sourceArea.setEditable(false);
		showCoder();
	}

	boolean isCoderShown() {
		return sourcePane.isVisible();
	}

	boolean isHintShown() {
		return hintPanel.isVisible();
	}

	void setSource(String pSource) {
		sourceArea.setText(pSource);
	}

	void stdoutUpdate(){
		stdout.flush();
		StringBuffer sBuffer = stdout.getBuffer();
		stdoutArea.setText(sBuffer.toString());
		stdout = new StringWriter();	//初期化
	}

	void stderrUpdate() {
		stderr.flush();
		StringBuffer sBuffer = stderr.getBuffer();
		//String string = sBuffer.toString();
		stderrArea.setText(sBuffer.toString());
		stderr = new StringWriter();	//初期化
	}
}
