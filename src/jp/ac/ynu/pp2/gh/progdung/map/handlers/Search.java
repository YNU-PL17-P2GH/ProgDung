package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.SearchObject;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.SearchObject.SearchSubObject;

public class Search extends MapHandlerBase {

	private SearchObject boxs;

	public Search(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("search", player_x, player_y, player_d, play);
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
		if(pObject instanceof SearchSubObject) {
			if(!callback.getSaveData().getBoolean("Search001")) {
				if(boxs.selectBox((SearchSubObject) pObject)) {
					showHint("扉の開く音がした!!!", true);
					callback.getSaveData().setTaken("Search001");
				}
			}
		}
		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
		for(MapObject tObject: theObj) {
			if(tObject instanceof SearchObject) {
				boxs = (SearchObject)tObject;
			}
		}

		for(MapObject box: boxs.getBoxs()){
			theObj.add(box);
		}
	}

}

