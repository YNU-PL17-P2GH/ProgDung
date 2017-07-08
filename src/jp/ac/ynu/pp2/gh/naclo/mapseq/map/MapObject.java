package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public abstract class MapObject {
	protected int box_x, box_y;
	protected int width, height;				//オブジェクトの大きさ(タイルの枚数)
	protected RpgMap myMap;
	protected MapHandlerBase handler;
	protected boolean drawFlag = false;
	protected boolean canPass = false;

	public MapObject(MapHandlerBase pHandler) {
		handler = pHandler;
	}

	public abstract void draw(ShareInfo sinfo, int map_x, int map_y);

	public abstract void update(ShareInfo sinfo);

	public int getBox_x() {
		return box_x;
	}

	public int getBox_y() {
		return box_y;
	}

	public boolean getdrawFlag(){
		return drawFlag;
	}

	public RpgMap getMap() {
		return myMap;
	}

	public boolean getCanPass() {
		return canPass;
	}

	public abstract boolean hitCheck(MapObject obj);
}
