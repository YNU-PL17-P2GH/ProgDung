package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Zentai  extends MapHandlerBase{

	public Zentai(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("zentai", player_x, player_y, player_d, play);
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
