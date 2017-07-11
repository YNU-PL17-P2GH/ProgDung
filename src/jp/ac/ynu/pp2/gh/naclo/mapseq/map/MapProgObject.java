package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import org.jruby.Ruby;

public abstract class MapProgObject extends MapObject{	//プログラムで動作させる設置物クラス
	public MapProgObject(MapHandlerBase pHandler, String pObjName) {
		super(pHandler, pObjName);
	}

	//プログラム実行
	public abstract void runRuby(Ruby ruby);
}
