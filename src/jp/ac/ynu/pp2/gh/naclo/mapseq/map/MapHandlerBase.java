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
	 * @param pMapName
	 */
	public MapHandlerBase(String pMapName) {
		myMap = new RpgMap(this, pMapName, 19, 40, DIRECTION.UP);
	}

	public void draw(ShareInfo sinfo) {
		myMap.update(sinfo);
		myMap.draw(sinfo);
	}

	public void moveMap() {
		// TODO moving
	}

}
