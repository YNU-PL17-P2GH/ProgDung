package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Basiccalc extends MapHandlerBase {
	
	public Basiccalc(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("basiccalc", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHitTo(MapObject object) {
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
	}

	@Override
	public void onMapLoad() {
		if (!callback.getSaveData().getBoolean("Tutorial001")) {
			showHint("<html>ようこそAlgeonへ.<br>まずはすぐ左上の扉に入り,<br>プログラミングを体験してみましょう", true);
			callback.getSaveData().setTaken("Tutorial001");
		}
	}
}

