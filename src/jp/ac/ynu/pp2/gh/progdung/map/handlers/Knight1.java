package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.Knight1Object;

public class Knight1 extends MapHandlerBase {
	private Knight1Object knight;

	public Knight1(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("knight1", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
		if(knight.getUpdateFlag()) {
			getMap().getMyPlayer().setPosition(knight.getHorseX() + 1, knight.getHorseY() + 1);
		}
	}

	@Override
	public void onPlayerHitTo(MapObject object) {
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (pObject.getObjName().equals(knight.getObjName())) {
			if(knight.getRunRuby()) {
				knight.startUpdate();
				getMap().getMyPlayer().setCanMave(false);
			}
		}
		if(pObject instanceof MapHintObject) {
			showHintOnCoder((MapHintObject) pObject);
		}else if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof Knight1Object) {
				knight = (Knight1Object) tObject;
			}
		}
	}

}

