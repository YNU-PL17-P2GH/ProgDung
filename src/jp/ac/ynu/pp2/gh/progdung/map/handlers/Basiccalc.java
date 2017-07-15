package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapPcObject;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.BasiccalcObject;

public class Basiccalc extends MapHandlerBase {

	public Basiccalc(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("basiccalc", player_x, player_y, player_d, play);
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
		if (pObject.getObjName().equals("tubo")) {
			if(callback.getSaveData().getBoolean("Basiccalc004")){
				showHint("<html>\"tubo\"に鍵のかけらAが入っています</html>", true);
			}else if (callback.getSaveData().getBoolean("Basiccalc001")) {
				showHint("<html>\"tubo\"に鍵のかけらAを入れました</html>", true);
				((BasiccalcObject)pObject).setKey("tubo");
				callback.getSaveData().setFlag("Basiccalc001", "false");
				callback.getSaveData().setTaken("Basiccalc004");
			}else{
				showHint("<html>\"tubo\"はからっぽ！</html>", true);
			}
		}

		if (pObject.getObjName().equals("oke")) {
			if(callback.getSaveData().getBoolean("Basiccalc005")){
				showHint("<html>\"oke\"に鍵のかけらBが入っています</html>", true);
			}else if (callback.getSaveData().getBoolean("Basiccalc002")) {
				showHint("<html>\"oke\"に鍵のかけらBを入れました</html>", true);
				callback.getSaveData().setFlag("Basiccalc002", "false");
				((BasiccalcObject)pObject).setKey("oke");
				callback.getSaveData().setTaken("Basiccalc005");
			}else{
				showHint("<html>\"oke\"はからっぽ！</html>", true);
			}
		}

		if (pObject.getObjName().equals("hako")) {
			if(callback.getSaveData().getBoolean("Basiccalc006")){
				showHint("<html>\"hako\"に鍵のかけらCが入っています</html>", true);
			}else if (callback.getSaveData().getBoolean("Basiccalc003")) {
				showHint("<html>\"hako\"に鍵のかけらCを入れました</html>", true);
				((BasiccalcObject)pObject).setKey("hako");
				callback.getSaveData().setFlag("Basiccalc003", "false");
				callback.getSaveData().setTaken("Basiccalc006");
			}else{
				showHint("<html>\"hako\"はからっぽ！</html>", true);
			}
		}

		if (pObject.getObjName().equals("basiccalc")) {
			if(!callback.getSaveData().getBoolean("Basiccalc007")){
				if(((BasiccalcObject)pObject).checkResult()){
					showHint("<html>なんと鍵を見つけた!!!</html>", true);
					callback.getSaveData().setFlag("Basiccalc004", "false");
					callback.getSaveData().setFlag("Basiccalc005", "false");
					callback.getSaveData().setFlag("Basiccalc006", "false");
					callback.getSaveData().setTaken("Basiccalc007");			//マップクリア!!!
				}else{
					showHint("<html>何も入っていないようだ...</html>", true);
				}
			}else{
				showHint("<html>何も入っていないようだ...</html>", true);
			}
		}

		if (pObject.getObjName().equals("keyA")) {
			if(!callback.getSaveData().getBoolean("Basiccalc001")){
				showHint("<html>なんと鍵のかけらAを見つけた!!!</html>", true);
				callback.getSaveData().setTaken("Basiccalc001");
				pObject.setVisible(false);
			}
		}

		if (pObject.getObjName().equals("keyB")) {
			if(!callback.getSaveData().getBoolean("Basiccalc002")){
				showHint("<html>なんと鍵のかけらBを見つけた!!!</html>", true);
				callback.getSaveData().setTaken("Basiccalc002");
				pObject.setVisible(false);
			}
		}

		if (pObject.getObjName().equals("keyC")) {
			if(!callback.getSaveData().getBoolean("Basiccalc003")){
				showHint("<html>なんと鍵のかけらCを見つけた!!!</html>", true);
				callback.getSaveData().setTaken("Basiccalc003");
				pObject.setVisible(false);
			}
		}

		if (pObject instanceof MapPcObject) {
			showCoder((MapPcObject) pObject);
		}
	}

	@Override
	public void onMapLoad() {
	}
}

