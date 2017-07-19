package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.StringWriter;

import javax.swing.BorderFactory;
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

		//標準出力
		stdout = new StringWriter();
		stdoutArea = new JTextArea();
		stdoutArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		stdoutArea.setEditable(false);
		stdoutArea.setBackground(Color.BLACK);
		stdoutArea.setForeground(Color.WHITE);

		stdoutPane = new JScrollPane(stdoutArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(stdoutPane);
		setLayer(stdoutPane, 2);
		stdoutPane.setBounds(1000, 100, 260, 200);

		//エラー出力
		stderr = new StringWriter();
		stderrArea = new JTextArea();
		stderrArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		stderrArea.setEditable(false);
		stderrArea.setBackground(Color.BLACK);
		stderrArea.setForeground(Color.WHITE);

		stderrPane = new JScrollPane(stderrArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(stderrPane);
		setLayer(stderrPane, 2);
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
		String string = sBuffer.toString();
		stderrArea.setText(sBuffer.toString());
		stderr = new StringWriter();	//初期化
	}
}
