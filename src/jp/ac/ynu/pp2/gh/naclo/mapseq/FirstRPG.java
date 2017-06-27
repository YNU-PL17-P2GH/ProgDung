package jp.ac.ynu.pp2.gh.naclo.mapseq;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import jp.ac.ynu.pp2.gh.naclo.mapseq.sequence.RootSequence;

public class FirstRPG {
	public static void main(String[] args) {
		FirstRPG fst = new FirstRPG();
		fst.start();
	}

	JFrame mainwindow;
	BufferStrategy strategy;
	RootSequence display;
	private boolean newKeystate[] = new boolean[8];

	//コンストラクタ
	FirstRPG(){
		//ウインドウの設定
		this.mainwindow = new JFrame("RPG(仮)");
		this.mainwindow.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainwindow.setSize(704, 704);
		this.mainwindow.setLocationRelativeTo(null);
		this.mainwindow.setVisible(true);
		this.mainwindow.setResizable(false);
		//バッファストラテジーの設定
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();
		//キーアダプター
		this.mainwindow.addKeyListener(new MyKeyAdapter());
		for(int i = 0; i < 8; i++){
			newKeystate[i] = false;
		}
		display = new RootSequence(null);
	}

	void start() {
		Timer t = new Timer();
		t.schedule(new RenderTask(), 0, 16);
			//16ミリ秒ごと(60fps)に run() を呼び出すようにする
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
		g.clearRect(0, 0,
				this.mainwindow.getWidth(), this.mainwindow.getHeight());
		sinfo.g = g;

		this.display.show(sinfo);

		g.dispose();
		this.strategy.show();
	}

	class RenderTask extends TimerTask{
		@Override
		public void run() {
			FirstRPG.this.render();
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
			}
		}

	}
}
