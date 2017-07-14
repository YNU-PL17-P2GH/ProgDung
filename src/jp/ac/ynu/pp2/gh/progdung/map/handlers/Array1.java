package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Array1  extends MapHandlerBase{

	public Array1(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("array1", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onPlayerHitTo(MapObject object) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
