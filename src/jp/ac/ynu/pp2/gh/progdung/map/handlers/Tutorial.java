package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
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
		if (callback.getSaveData().getBoolean("Tutorial003")) {
			return;
		}
		if (object.getObjName().startsWith("pc")) {
			showHint("<html>これがPCです.<br>Zキーでソースを入力できるようです.</html>", true);
			callback.getSaveData().setTaken("Tutorial003");
		}
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (callback.isHintShown()) {
			return;
		}
		if (pObject.getObjName().startsWith("pc")) {
			showCoder();
			if (!callback.getSaveData().getBoolean("Tutorial004")) {
				showHint("<html>これがソース入力画面です.<br>Escキーで閉じます.</html>", true);
				callback.getSaveData().setTaken("Tutorial004");
			}
		}
	}

	@Override
	public void onMapLoad() {
		if (!callback.getSaveData().getBoolean("Tutorial002")) {	
			showHint("<html>どこかにコードを入力するための<br>PCがあるはずです.探してみましょう...</html>", true);
			callback.getSaveData().setTaken("Tutorial002");
		}
	}

}
