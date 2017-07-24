package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapFixedObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.PackjackObject;

public class Packjack extends MapHandlerBase {

	private PackjackObject pack;

	public Packjack(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("packjack", player_x, player_y, player_d, play);
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
		if(pObject instanceof MapHintObject) {
			showHintOnCoder((MapHintObject) pObject);
		}else if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}else if(pObject instanceof MapFixedObject) {
			int data[] = pack.getItemData(pObject.getObjName());
			showHint("<html>"+ pObject.getObjName() +"<br>"
					+ "重さ" + data[0] +"<br>"
					+ "価値" + data[1] +"<br>"
					+ "</html>", true);
		}


	}

	@Override
	public void onMapLoad() {
		for(MapObject tObject: theObj) {
			if(tObject instanceof PackjackObject) {
				pack = (PackjackObject)tObject;
			}
		}
	}

}
