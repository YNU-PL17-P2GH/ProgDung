package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.Knight2Object;

public class Knight2 extends MapHandlerBase {

	private Knight2Object knight;

	public Knight2(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("knight2", player_x, player_y, player_d, play);
	}

	@Override
	public void draw(ShareInfo sinfo) {
		super.draw(sinfo);
		if(!callback.getSaveData().getBoolean("Knight2001")){
			if(knight.isFinish()) {
				knight.setFinishUpdate(false);
				if(knight.getSuccessFlag()) {
					showHint("<html>扉の開く音がした!!</html>", true);
					callback.getSaveData().setTaken("Knight2001");
				}else {
					showHint("<html>何も起こらなかった...</html>", true);
				}
			}
		}
	}

	@Override
	public void playerUpdate() {
		// TODO Auto-generated method stub
		if(knight.getUpdateFlag()) {
			getMap().getMyPlayer().setPosition(knight.getHorseX() + 1, knight.getHorseY());
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
		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof Knight2Object) {
				knight = (Knight2Object) tObject;
			}
		}
	}

}
