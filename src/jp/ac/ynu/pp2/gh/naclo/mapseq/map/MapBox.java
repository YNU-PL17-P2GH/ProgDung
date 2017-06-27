package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapBox {
	private static int layerNum = 0;
	private int box_state;
	private int box_mapchipID[][];
	private MapObject boxObj = null;

	public MapBox(int state){
		box_mapchipID = new int[layerNum][4];
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

	public void setChip(int chipID1, int chipID2, int chipID3, int chipID4, int layer){
		box_mapchipID[layer][0] = chipID1;
		box_mapchipID[layer][1] = chipID2;
		box_mapchipID[layer][2] = chipID3;
		box_mapchipID[layer][3] = chipID4;
	}

	public void draw(ShareInfo sinfo, int x, int y, MapChip myMapChip) {
		for(int i = 0; i < layerNum; i++){
			myMapChip.drawChip(sinfo, x, y, box_mapchipID[i]);
		}
		if(boxObj != null){
			boxObj.draw(sinfo, x, y);
		}
	}

	public static void setLayerNum(int layer){
		layerNum = layer;
	}
}
