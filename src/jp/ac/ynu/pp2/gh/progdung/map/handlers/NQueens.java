package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class NQueens extends MapHandlerBase {

	public NQueens(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("nqueens", player_x, player_y, player_d, play);
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
	}

}
