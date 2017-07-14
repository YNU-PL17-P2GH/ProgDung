package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Tutorial extends MapHandlerBase {

	public Tutorial(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("tutorial", player_x, player_y, player_d, play);


		sourceRuby = "def TutorialObject(tori)\n"
				+ "\t# ここにソースを入力\n"
				+ "end";
		callback.setSource(sourceRuby);
	}

	@Override
	public void playerUpdate() {
	}

	@Override
	public void onPlayerHitTo(MapObject object) {
		if (object.getObjName().equals("TutorialObject")) {
			if (!callback.getSaveData().getBoolean("Tutorial003")) {
				showHint("<html>この\"tori\"という名前の石像をどかす<br>必要があるようです!<br>"
						+ "石像を動かすために,左上のPCを調べて<br>みましょう...</html>", true);
				callback.getSaveData().setTaken("Tutorial003");
			}
		}
		if (object.getObjName().startsWith("pc/")) {
			if (callback.getSaveData().getBoolean("Tutorial003") &&
					!callback.getSaveData().getBoolean("Tutorial004")) {
				showHint("<html>これがPCです.<br>ZキーでRubyコードを入力できます.</html>", true);
				callback.getSaveData().setTaken("Tutorial004");
			}
		}
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (pObject.getObjName().startsWith("pc")) {
			if (callback.getSaveData().getBoolean("Tutorial004")) {
				showCoder();
				if (!callback.getSaveData().getBoolean("Tutorial005")) {
					showHint("<html>これがソース入力画面です.<br>Escキーで閉じます.<br><br>"
							+ "手始めに,指定された位置に<br>"
							+ "tori.moveRight();<br>"
							+ "と入力してみましょう.</html>", true);
					callback.getSaveData().setTaken("Tutorial005");
				}
			}
		}

	}

	@Override
	public void onMapLoad() {
		if (!callback.getSaveData().getBoolean("Tutorial002")) {
			showHint("<html>まずはマップを探索してみましょう.<br>下に道が続いているようですね.</html>", true);
			callback.getSaveData().setTaken("Tutorial002");
		}
	}

}
