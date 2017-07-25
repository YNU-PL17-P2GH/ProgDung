package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapClearObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Lastmap extends MapHandlerBase {

	private MapClearObject pc;

	public Lastmap(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("lastmap", player_x, player_y, player_d, play);
	}
	int wait= 0;
	@Override
	public void draw(ShareInfo sinfo) {
		super.draw(sinfo);
		if(callback.getSaveData().getBoolean("Lastmap001")){
			if(wait > 200) {
				showGameClear(0);
			}
			wait++;
		}
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
		if(pObject instanceof MapClearObject){
			if(!callback.getSaveData().getBoolean("Lastmap001")){
				showHint("<html>魔法陣が起動した...<html>", true);
				pc.setMahouzinOnFlag(true);
				callback.getSaveData().setTaken("Lastmap001");
				callback.saveUserData();
				thePlayer.setCanMave(false);
			}
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof MapClearObject) {
				((MapClearObject)tObject).setMahouzinOnFlag(callback.getSaveData().getBoolean("Lastmap001"));
				pc = (MapClearObject) tObject;
			}
		}
	}

}
