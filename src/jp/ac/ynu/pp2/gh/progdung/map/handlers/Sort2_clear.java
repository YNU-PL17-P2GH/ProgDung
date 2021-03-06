package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapClearObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Sort2_clear extends MapHandlerBase{

	private MapClearObject pc;

	public Sort2_clear(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("sort2_clear", player_x, player_y, player_d, play);
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
			if(!callback.getSaveData().getBoolean("Sort2001")){
				showHint("<html>なんと鍵をみつけた!!!</html>", true);
				pc.setMahouzinOnFlag(true);
				callback.getSaveData().setTaken("Sort2001");
			}
		}
	}

	@Override
	public void onMapLoad(){
		for (MapObject tObject : theObj) {
			if (tObject instanceof MapClearObject) {
				((MapClearObject)tObject).setMahouzinOnFlag(callback.getSaveData().getBoolean("Sort2001"));
				pc = (MapClearObject) tObject;
			}
		}
	}
}
