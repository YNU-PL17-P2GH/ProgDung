package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Tutorial extends MapHandlerBase {

	public Tutorial(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("tutorial", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
	}

	@Override
	public void onPlayerHitTo(MapObject object) {
		if (object.getObjName().equals("tori")) {
			if (!callback.getSaveData().getBoolean("Tutorial003")) {
				showHint("<html>この\"tori\"という名前の石像をどかす<br>必要があるようです!<br>"
						+ "石像を動かすために,左上のPCを調べて<br>みましょう...</html>", true);
				callback.getSaveData().setTaken("Tutorial003");
			}
		}
		if (object.getObjName().equals("robe") &&
				!callback.getSaveData().getBoolean("Tutorial010")) {
			showHint("<html>今度は\"robe\"という石像が<br>"
					+ "道をふさいでいます!<br>"
					+ "なので,\"robe\"を動かす必要がありそうです...<br><br>"
					+ "右にあるPCで,\"robe\"を動かすコードを<br>"
					+ "入力してみましょう!最後はノーヒントです.</html>", true);
			callback.getSaveData().setTaken("Tutorial010");
		}
		if (object instanceof MapPcObject) {
			if (callback.getSaveData().getBoolean("Tutorial003") &&
					!callback.getSaveData().getBoolean("Tutorial004")) {
				showHint("<html>これがPCです.<br>ZキーでRubyコードを入力できます.<br>コードはdef と end に挟まれた行に書きます</html>", true);
				callback.getSaveData().setTaken("Tutorial004");
			}
		}
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (pObject instanceof MapPcObject) {
			if (!callback.getSaveData().getBoolean("Tutorial009") &&
					callback.getSaveData().getBoolean("Tutorial007")) {
				showHint("<html>得られた情報をもとに,さらに石像を動かす<br>"
						+ "コードを入力してみましょう!<br><br>"
						+ "先ほどtori.moveRight()を入力した行の<br>"
						+ "すぐ下に,新しい行を作って入力してみてください.</html>", true);
				callback.getSaveData().setTaken("Tutorial009");
			}
			if (callback.getSaveData().getBoolean("Tutorial004")) {
				showCoder((MapPcObject) pObject);
				if (!callback.getSaveData().getBoolean("Tutorial005")) {
					showHint("<html>これがソース入力画面です.<br>Escキーで閉じます.<br><br>"
							+ "手始めに,指定された位置に<br>"
							+ "tori.moveRight()<br>"
							+ "と入力してみましょう.</html>", true);
					callback.getSaveData().setTaken("Tutorial005");
				}
			}
		}
		if (pObject.getObjName().equals("mover") &&
				callback.getSaveData().getBoolean("Tutorial006")) {
			showHint("<html>石像の名前.moveRight()<br>"
					+ "　-　対象となる石像を右へ動かす.<br>"
					+ "石像の名前.moveUp()<br>"
					+ "　-　対象となる石像を上へ動かす.<br>"
					+ "石像の名前.moveLeft()<br>"
					+ "　-　対象となる石像を左へ動かす.</html>", true);
			callback.getSaveData().setTaken("Tutorial007");
		}
		if (pObject.getObjName().equals("mover2") &&
				callback.getSaveData().getBoolean("Tutorial006")) {
			showHint("<html>石像の名前.moveDown()<br>"
					+ "　-　対象となる石像を下へ動かす.</html>", true);
		}
		if (pObject.getObjName().equals("kirakira/yellow")) {
			if(!callback.getSaveData().getBoolean("Tutorial011")) {
				showHint("<html>なんと鍵をみつけた!!!</html>", true);
				pObject.setVisible(false);
				callback.getSaveData().setTaken("Tutorial011");					//クリアフラグ
			}
		}
	}

	@Override
	public void onMapLoad() {
		if (!callback.getSaveData().getBoolean("Tutorial002")) {
			showHint("<html>まずはマップを探索してみましょう.<br>下に道が続いているようですね.</html>", true);
			callback.getSaveData().setTaken("Tutorial002");
		}
		if(callback.getSaveData().getBoolean("Tutorial011")) {
			for(MapObject tObject: theObj) {
				if(tObject.getObjName().equals("kirakira/yellow")) {
					tObject.setVisible(false);
				}
			}
		}
	}

}
