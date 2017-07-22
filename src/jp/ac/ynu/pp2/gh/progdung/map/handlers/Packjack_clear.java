package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapClearObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Packjack_clear  extends MapHandlerBase {

	private MapClearObject pc;

	public Packjack_clear(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("packjack_clear", player_x, player_y, player_d, play);
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
		if(pObject instanceof MapClearObject){
			if(!callback.getSaveData().getBoolean("Packjack002")){
				showHint("<html>なんと鍵をみつけた!!!</html>", true);
				pc.setMahouzinOnFlag(true);
				callback.getSaveData().setTaken("Packjack002");
			}
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof MapClearObject) {
				((MapClearObject)tObject).setMahouzinOnFlag(callback.getSaveData().getBoolean("Knight1002"));
				pc = (MapClearObject) tObject;
			}
		}
	}
}
