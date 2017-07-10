package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

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

	public RpgMap myMap;

	/**
	 * 引数に読み込むMapのパスを指定してHandler生成.
	 * TODO 初期位置はMapデータ依存が望ましい.
	 * @param pMapName
	 */
	public MapHandlerBase(String pMapName) {
		myMap = new RpgMap(this, pMapName, 19, 40, DIRECTION.UP);
	}

	public void draw(ShareInfo sinfo) {
		myMap.update(sinfo);
		myMap.draw(sinfo);
	}

	public void moveMap(NextMapBox pBox) {
		// TODO moving
		if (pBox == null) {
			throw new NullPointerException("Move Called but argument is null");
		}

		myMap.loadMap(pBox.getNextMapName());
		myMap.myPlayer.setPosition(pBox.getNext_x(), pBox.getNext_y());
		MAP_CONST.DIRECTION d = pBox.getNext_d();
		//もし移動先の自キャラの向きが指定されているなら
		if(d != null){
			myMap.myPlayer.setDirection(d);
		}
	}

	public boolean hitChecktoPlayer(MapObject obj){
		return obj.hitCheck(myMap.myPlayer);
	}
	public boolean hitChecktoObj(MapObject obj){
		return myMap.myPlayer.hitCheck(obj);
	}
}
