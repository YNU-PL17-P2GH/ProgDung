package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapBox {
	private static int layerNum = 0;
	private int box_state;
	private int box_mapchipID[];
	private MapObject boxObj = null;

	public MapBox(int state){
		box_mapchipID = new int[layerNum];
		box_state = state;
	}

	public void setState(int state){
		box_state = state;
	}

	public void setObj(MapObject obj){
		boxObj = obj;
	}

	public int getState() {
		return box_state;
	}

	public void setChip(int chipID, int layer){
		box_mapchipID[layer] = chipID;
	}

	public void draw(ShareInfo sinfo, int x, int y, MapChip myMapChip) {
		for(int i = 0; i < layerNum; i++){
			myMapChip.drawChip(sinfo, x, y, box_mapchipID[i]);
		}

	}

	public static void setLayerNum(int layer){
		layerNum = layer;
	}

	public void drawObj(ShareInfo sinfo, int x, int y, int bx, int by) {
		if(boxObj != null){
			if(boxObj.getdrawFlag()){
				return;
			}
			boxObj.draw(sinfo, x + (boxObj.getBox_x() - bx) * MAP_CONST.MAP_BOX_SIZE, y +
					(boxObj.getBox_y() - by) * MAP_CONST.MAP_BOX_SIZE);//オブジェクトの一番右上の座標

		}
	}
}
