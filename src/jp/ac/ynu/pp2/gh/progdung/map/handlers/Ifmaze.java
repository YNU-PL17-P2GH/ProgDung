package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.IfmazeObject;

public class Ifmaze  extends MapHandlerBase {

	private IfmazeObject mizugame;

	public Ifmaze(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("ifmaze", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
	}

	@Override
	public void onPlayerHitTo(MapObject object) {
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if(!callback.getSaveData().getBoolean("Ifmaze001")){
			if(pObject.getObjName().equals("mizugame")){
				if(mizugame.getSuccessFlag()){
					showHint("<html>なんと水がめの中から鍵をみつけた!!！</html>", true);
					callback.getSaveData().setTaken("Ifmaze001");
					mizugame.setSuccessFlag(false);
					mizugame.setInitflag(true);
				}else{
					showHint("<html>水がめの中には何も入っていない...</html>", true);
				}
			}
		}else {
			showHint("<html>水がめの中には何も入っていない...</html>", true);
		}
		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
		if(pObject instanceof MapHintObject) {
			showHintOnCoder((MapHintObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof IfmazeObject) {
				mizugame = (IfmazeObject) tObject;
				mizugame.setInitflag(callback.getSaveData().getBoolean("Ifmaze001"));
			}
		}
	}
}
