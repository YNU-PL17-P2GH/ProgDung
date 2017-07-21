package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import java.util.ArrayList;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Array1  extends MapHandlerBase{
	private MapObject orb;
	private ArrayList<MapObject> boxs;

	public Array1(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("array1", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {

	}

	@Override
	public void onPlayerHitTo(MapObject object) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void onPlayerInteract(MapObject pObject) {
		if (pObject.getObjName().equals("tori")) {
			if(!callback.getSaveData().getBoolean("Array1003")){
				showHint("<html>何かをはめられそうな穴が開いている...</html>", true);
			}else {
				showHint("<html>石像は音もなく崩れ去った...</html>", true);
				pObject.setVisible(false);
			}
		}
		if (pObject.getObjName().equals(orb.getObjName())) {
			showHint("<html>光るオーブをてにいれた！</html>", true);
			orb.setVisible(false);
			callback.getSaveData().setTaken("Array1003");
		}
		if (pObject.getObjName().equals("kirakira/blue")) {
			showHint("<html>鍵をみつけた!!！</html>", true);
			pObject.setVisible(false);
			callback.getSaveData().setTaken("Array1004");			//クリアーフラグ
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
		boxs = new ArrayList<MapObject>();
		for (MapObject tObject : theObj) {
			if (tObject.getObjName().equals("kirakira/yellow")) {
				tObject.setVisible(false);
				orb = tObject;
			}else if(tObject.getObjName().equals("box")) {
				boxs.add(tObject);
			}
		}
	}

	public MapObject getOrb() {
		return orb;
	}

	public ArrayList<MapObject> getBoxs() {
		return boxs;
	}

}
