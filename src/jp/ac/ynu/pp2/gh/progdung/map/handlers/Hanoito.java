package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Hanoito extends MapHandlerBase{
	public Hanoito(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("hanoi", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPlayerHitTo(MapObject pObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
	}

}
