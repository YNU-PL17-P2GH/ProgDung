package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.Array1Object1;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.Array1Object2;

public class Array1  extends MapHandlerBase{
	private Array1Object1 array1;
	private Array1Object2 array2;
	private MapObject orb;

	public Array1(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("array1", player_x, player_y, player_d, play);
	}

	@Override
	public void playerUpdate() {
		if(!callback.getSaveData().getBoolean("Array1001")){
			if(array1.getFragSuccess()){
				callback.getSaveData().setTaken("Array1001");
				showHint("<html>何かが光っている！！</html>", true);
				orb.setVisible(true);
			}
		}
		if(!callback.getSaveData().getBoolean("Array1002")){
			if(array2.getFragSuccess()){
				callback.getSaveData().setTaken("Array1002");
				for (MapObject tObject : theObj) {
					if (tObject.getObjName().equals("box")) {
						tObject.setVisible(false);
					}
				}
				showHint("<html>道をふさぐ箱が消え去った!!!</html>", true);
			}
		}
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
	}

	@Override
	public void onMapLoad() {
		for (MapObject tObject : theObj) {
			if (tObject instanceof Array1Object1) {
				((Array1Object1)tObject).setFragSuccess(callback.getSaveData().getBoolean("Array1001"));
				array1 = (Array1Object1) tObject;
			}else if (tObject instanceof Array1Object2) {
				((Array1Object2)tObject).setFragSuccess(callback.getSaveData().getBoolean("Array1002"));
				array2 = (Array1Object2) tObject;
			}else if (tObject.getObjName().equals("kirakira/yellow")) {
				tObject.setVisible(false);
				orb = tObject;
			}
		}
	}

}
