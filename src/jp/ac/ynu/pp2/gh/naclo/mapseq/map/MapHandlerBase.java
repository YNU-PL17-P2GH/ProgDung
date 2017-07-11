package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.util.ArrayList;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;

/**
 * マップ移動に関するすべての処理はここを通すようにする.
 * Rubyソースなんかもここに保持して,オブジェクトもここを介する.
 * @author b1564304
 *
 */
public class MapHandlerBase {

	public Object rubyOperator;

	public RpgMap theMap;

	MapPlayer thePlayer;

	ArrayList<MapObject> theObj;

	/**
	 * 引数に読み込むMapのパスを指定してHandler生成.
	 * TODO 初期位置はMapデータ依存が望ましい.
	 * @param pMapName
	 */
	public MapHandlerBase(String pMapName) {
		theObj = new ArrayList<MapObject>();

		theMap = new RpgMap(this, pMapName, 19, 40, DIRECTION.UP);
		thePlayer = new MapPlayer(this, 19, 40, "player", DIRECTION.UP, theMap);
	}

	public void draw(ShareInfo sinfo) {
		theMap.update(sinfo);
		theMap.draw(sinfo);
	}

	public void moveMap(NextMapBox pBox) {
		// TODO moving
		if (pBox == null) {
			throw new NullPointerException("Move Called but argument is null");
		}

		theMap.loadMap(pBox.getNextMapName());
		thePlayer.setPosition(pBox.getNext_x(), pBox.getNext_y());
		MAP_CONST.DIRECTION d = pBox.getNext_d();
		//もし移動先の自キャラの向きが指定されているなら
		if(d != null){
			thePlayer.setDirection(d);
		}
	}

	public boolean hitChecktoPlayer(MapObject obj){
		return obj.hitCheck(thePlayer);
	}
	public boolean hitChecktoObj(MapObject obj){
		return thePlayer.hitCheck(obj);
	}
}
