package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.HanoitoObject;

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
		if(pObject instanceof MapHintObject){
			showHintOnCoder((MapHintObject) pObject);
		}else if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		if(callback.getSaveData().getBoolean("Hanoito001")) {
			for(MapObject tObject: theObj) {
				((HanoitoObject)tObject).cleared();
			}
		}
	}
}
