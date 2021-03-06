package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import jp.ac.ynu.pp2.gh.naclo.mapseq.KEY_STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.progdung.map.handlers.Lobby;
import jp.ac.ynu.pp2.gh.progdung.map.handlers.Zentai;
import jp.ac.ynu.pp2.gh.progdung.util.SaveData;
import jp.ac.ynu.pp2.gh.progdung.util.TransitionCallback;

public class DungeonPlay extends Canvas {
	/**
	 *
	 */
	private static final long serialVersionUID = 6888570703399298605L;

	// Render per sec
	public static final int RENDER_TIMER_RATE = 100;

	Canvas mainwindow;
	BufferStrategy strategy;
	MapHandlerBase handler;
	private boolean newKeystate[] = new boolean[8];

	TransitionCallback callback;

	private int selectedStage;

	//コンストラクタ
	DungeonPlay(TransitionCallback pCallback, int selectedStage){
		super();
		setFocusable(false);

		this.selectedStage = selectedStage;
		callback = pCallback;
	}

	public void init() {
		mainwindow = this;
		//ウインドウの設定
		this.mainwindow.setSize(704, 704);
		this.mainwindow.setPreferredSize(new Dimension(704, 704));
//		this.mainwindow.setLocationRelativeTo(null);
		this.mainwindow.setVisible(true);
//		this.mainwindow.setResizable(false);
		//バッファストラテジーの設定
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();

		// KeyAdapter
		for(int i = 0; i < 8; i++){
			newKeystate[i] = false;
		}
		//handler = new Lobby(19, 40, DIRECTION.UP, this);
		if (selectedStage == 0) {
			handler = new Lobby(26, 41, DIRECTION.UP, this);
		} else {
			handler = new Zentai(19, 40, DIRECTION.UP, this);
		}
	}

	public void showHint(String pString, boolean force) {
		callback.showHint(pString, force);
	}

	public boolean isHintShown() {
		return callback.isHintShown();
	}

	public void showCoder() {
		callback.showCoder();
	}

	public void hideCoder() {
		callback.hideCoder();
	}

	public void showHintOnCoder() {
		callback.showHintOnCoder();
	}

	public boolean isCorderShown() {
		return callback.isCoderShown();
	}

	@SuppressWarnings("unchecked")
	public final void moveMap(String name, int pX, int pY, DIRECTION pD) {
		try {
			Constructor<MapHandlerBase> cnst = (Constructor<MapHandlerBase>) Class.forName(name)
					.getConstructor(int.class, int.class, DIRECTION.class, DungeonPlay.class);
			handler = cnst.newInstance(pX, pY, pD, this);
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	void start() {
		Timer t = new Timer();
		t.schedule(new RenderTask(), 0, 1000 / RENDER_TIMER_RATE);

		callback.getMainFrame().requestFocus();
		callback.getMainFrame().addKeyListener(new MyKeyAdapter());
	}

	long lasttime = System.currentTimeMillis();
	ShareInfo sinfo = new ShareInfo();

	void render(){	//1フレームごとに呼び出される関数
		//時間計測
		sinfo.setCurrenttime(System.currentTimeMillis());
		//キー入力更新
		sinfo.updateKeyState(newKeystate);

		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		g.setBackground(Color.black);
		g.clearRect(0, 0, this.mainwindow.getWidth(), this.mainwindow.getHeight());
		sinfo.g = g;

		this.handler.draw(sinfo);

		//stdinUpdate();

		g.dispose();
		this.strategy.show();
	}

	class RenderTask extends TimerTask{
		@Override
		public void run() {
			DungeonPlay.this.render();
		}
	}

	class MyKeyAdapter extends KeyAdapter{	//キーボードの入力を取得するクラス

		@Override
		public void keyPressed(KeyEvent e) {
			this.setValue(e.getKeyCode(), true);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			this.setValue(e.getKeyCode(), false);
		}


		private void setValue(int keycode, boolean b){
			switch(keycode){
			case KeyEvent.VK_LEFT:
				newKeystate[KEY_STATE.LEFT] = b;
				break;
			case KeyEvent.VK_RIGHT:
				newKeystate[KEY_STATE.RIGHT] = b;
				break;
			case KeyEvent.VK_UP:
				newKeystate[KEY_STATE.UP] = b;
				break;
			case KeyEvent.VK_DOWN:
				newKeystate[KEY_STATE.DOWN] = b;
				break;
			case KeyEvent.VK_Z:
				newKeystate[KEY_STATE.Z] = b;
				break;
			case KeyEvent.VK_X:
				newKeystate[KEY_STATE.X] = b;
				break;
			case KeyEvent.VK_C:
				newKeystate[KEY_STATE.C] = b;
				break;
			case KeyEvent.VK_SPACE:
				newKeystate[KEY_STATE.SPACE] = b;
				break;
			case KeyEvent.VK_ESCAPE:
				hideCoder();
				break;
			}
		}

	}

	public SaveData getSaveData() {
		return callback.getSaveData();
	}

	public void toggleMenu() {
		callback.toggleMenu();
	}

	public void setSource(String pSource) {
		callback.setSource(pSource);
	}

	public void stdoutUpdate(){
		callback.stdoutUpdate();
	}

	public void stderrUpdate() {
		callback.stderrUpdate();
	}

	public void showSelect() {
		callback.showSelect(callback);
	}

	public void showGameClear(int stage) {
		callback.showGameClear(callback, stage);
	}

	public void saveUserData() {
		callback.saveUserData();
	}
}
