package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHintObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.Array2Object;

public class Array2 extends MapHandlerBase{

	private Array2Object ido;

	public Array2(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("array2", player_x, player_y, player_d, play);
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
		if (pObject.getObjName().equals("ido")) {
			if(!callback.getSaveData().getBoolean("Array2001")){
				if(ido.getRunRuby()){
					if(ido.checkFragSuccess()){
						showHint("<html>魔法陣が起動し、鍵をてにいれた！！</html>", true);
						callback.getSaveData().setTaken("Array2001");
					}else{
						showHint("<html>魔法陣が起動しようとした...<br>\t\tしかし何も起こらなかった</html>", true);
					}
				}
			}
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
			if (tObject instanceof Array2Object) {
				((Array2Object)tObject).setFragSuccess(callback.getSaveData().getBoolean("Array2001"));
				ido = (Array2Object) tObject;
			}
		}
	}

}

