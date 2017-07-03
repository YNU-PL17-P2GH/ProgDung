package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapBox {
	private static int layerNum = 0;
	private MAP_CONST.STATE box_state;
	private int box_mapchipID[];
	private MapObject boxObj = null;

	public MapBox(MAP_CONST.STATE state){
		box_mapchipID = new int[layerNum];
		box_state = state;
	}

	public void setState(MAP_CONST.STATE state){
		box_state = state;
	}

	public void setObj(MapObject obj){
		boxObj = obj;
	}

	public MAP_CONST.STATE getState() {
		return box_state;
	}

	public void setChip(int chipID, int layer){
		box_mapchipID[layer] = chipID;
	}

	public void draw(ShareInfo sinfo, int x, int y, MapChip myMapChip) {
		for(int i = 0; i < layerNum; i++){		//タイルチップには階層がある
			myMapChip.drawChip(sinfo, x, y, box_mapchipID[i]);
		}

	}

	public static void setLayerNum(int layer){
		layerNum = layer;
	}

	public void drawObj(ShareInfo sinfo, int x, int y, int bx, int by) {
		if(boxObj != null){		//設置物がないマス用
			if(boxObj.getdrawFlag()){		//複数のマスにまたがる設置物が複数回書かれないための処理
				return;
			}
			boxObj.draw(sinfo, x + (boxObj.getBox_x() - bx) * MAP_CONST.MAP_BOX_SIZE, y +
					(boxObj.getBox_y() - by) * MAP_CONST.MAP_BOX_SIZE);//オブジェクトの一番右上の座標

		}
	}
}
