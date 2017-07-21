package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Output extends MapHandlerBase{

	private MapObject key;

	public Output(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("output", player_x, player_y, player_d, play);
	}

	/* (非 Javadoc)
	 * @see jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase#draw(jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo)
	 */

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
		if (pObject.getObjName().equals("kirakira/yellow")) {
			if(!callback.getSaveData().getBoolean("Output002")) {
				showHint("<html>なんと鍵をみつけた!!!</html>", true);
				pObject.setVisible(false);
				callback.getSaveData().setTaken("Output002");
			}
		}
		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject)pObject);
		}
		if(pObject instanceof MapHintObject) {
			showHintOnCoder((MapHintObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject.getObjName().equals("kirakira/yellow")) {
				tObject.setVisible(false);
				key = tObject;
			}
		}
	}

	public MapObject getKey() {
		return key;
	}
}
