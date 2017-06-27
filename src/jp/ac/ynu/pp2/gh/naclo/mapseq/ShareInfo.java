package jp.ac.ynu.pp2.gh.naclo.mapseq;


import java.awt.Graphics2D;

public class ShareInfo {
	//グラフィックス
		public Graphics2D g;
		//現在のミリ秒数
		private long currenttime;
		//キーステート
		private boolean[][] keystate;
		//コンストラクタ
		public ShareInfo(){
			//キーステートの初期化
			this.keystate = new boolean[2][8];
			for(int j = 0; j < 2; j++){
				for(int i=0; i<8; i++){
					this.keystate[j][i] = false;
				}
			}
		}
		public boolean getKeyPress(int key){
			return ((this.keystate[0][key] == true) && (this.keystate[1][key] == false));
		}
		public boolean getKeyRepeat(int key){
			return ((this.keystate[0][key] == true) && (this.keystate[1][key] == true));
		}
		public boolean getKeyRelease(int key){
			return ((this.keystate[0][key] == false) && (this.keystate[1][key] == true));
		}

		public void updateKeyState(boolean[] newKeystate) {	//キー入力更新
			for(int i=0; i<8; i++){
				this.keystate[1][i] = this.keystate[0][i];
				this.keystate[0][i] = newKeystate[i];
			}
		}

		public long getCurrenttime() {
			return currenttime;
		}

		public void setCurrenttime(long currenttime) {
			this.currenttime = currenttime;
		}
}
