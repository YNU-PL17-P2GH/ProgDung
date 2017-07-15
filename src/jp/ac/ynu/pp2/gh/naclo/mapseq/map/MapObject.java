package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public abstract class MapObject {
	protected int box_x, box_y;
	protected int width, height;				//オブジェクトの大きさ(タイルの枚数)
	protected RpgMap myMap;
	protected MapHandlerBase handler;
	protected boolean drawFlag = false;
	protected boolean canPass = false;
	protected String objName;

	protected BufferedImage objImg;
	protected boolean visible = true;

	public MapObject(MapHandlerBase pHandler, int pX, int pY, String pObjName, RpgMap pMap) {
		objName = pObjName;
		handler = pHandler;

		box_x = pX;
		box_y = pY;
		myMap = pMap;
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

	public String getObjName() {
		return objName;
	}

	public void setVisible(boolean b) {
		this.visible = b;
	}

	public abstract boolean hitCheck(MapObject obj);
}
