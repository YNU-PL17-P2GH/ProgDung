package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public abstract class MapObject {
	protected int box_x, box_y;
	protected RpgMap myMap;
	protected boolean drawFlag = false;

	public abstract void draw(ShareInfo sinfo, int map_x, int map_y);

	public abstract void update();

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
}
