package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Lastmap extends MapHandlerBase {

	public Lastmap(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("lastmap", player_x, player_y, player_d, play);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapLoad() {
		// TODO Auto-generated method stub
		
	}

}
