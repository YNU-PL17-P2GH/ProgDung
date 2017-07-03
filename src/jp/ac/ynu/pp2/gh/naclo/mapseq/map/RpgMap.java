package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class RpgMap {
	private MapBox boxs[][];
	private MapChip myMapChip;
	private MapPlayer myPlayer;
	public RpgMap(String mapName , int player_x, int player_y, MAP_CONST.DIRECTION player_direct){
		loadMap(mapName);
		myMapChip = new MapChip(mapName);
		myPlayer = new MapPlayer(player_x,  player_y, "player", player_direct, this);
		myObj.add(new MapSortObject(25, 13, "sort", this));
	}
	private ArrayList<MapObject> myObj;

	private void loadMap(String mapName){
		//myMapChip = new MapChip(mapName);
		BufferedReader ibr = null;
		try {
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/" + mapName + "/map.txt").openStream()));
			String line = ibr.readLine();

			String datas[] = line.split(",", 3);
			boxs = new MapBox[Integer.parseInt(datas[0])][Integer.parseInt(datas[1])];
			//レイヤー数設定
			int layer = Integer.parseInt(datas[2]);
			MapBox.setLayerNum(layer);
			//マップチップ配置
			for(int i = 0; i < boxs.length; i++){
				line = ibr.readLine();
				datas = line.split(",", boxs[i].length);
				MAP_CONST.STATE state;
				for(int j = 0; j < boxs[i].length; j++){
					state = MAP_CONST.STATE.getValue(Integer.parseInt(datas[j]));
					if(state == MAP_CONST.STATE.NEXT){
						boxs[i][j] = new NextMapBox(state);
					}else{
						boxs[i][j] = new MapBox(state);
					}
				}
			}


			for(int k = 0; k < layer; k++){
				for(int i = 0; i < boxs.length; i++){

					line = ibr.readLine();
					if(line.indexOf(',') < 0){
						i--;
						continue;
					}

					datas = line.split(",",  0);
					for(int j = 0; j < boxs[i].length; j++){
						boxs[i][j].setChip(Integer.parseInt(datas[j]), k);
					}
				}
			}
			ibr.close();
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/" + mapName + "/mapObj.txt").openStream()));
			line = ibr.readLine();
			myObj = new ArrayList<MapObject>();
			while(line != null){
				if(line.indexOf("nextMap") >= 0){
					datas = line.split(",", 0);
					int boxNum = Integer.parseInt(datas[4]);
					int x = Integer.parseInt(datas[2]);
					int y = Integer.parseInt(datas[3]);
					System.out.println();
					for(int i = 0; i < boxNum; i++){
						((NextMapBox)boxs[Integer.parseInt(datas[6 + i * 2])][Integer.parseInt(datas[5 + i * 2])]).setNextMap(datas[1], x, y);
					}
				}else if(line.indexOf("fixObj") >= 0){
					datas = line.split(",", 0);
					myObj.add(new MapFixedObject(Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this));
				}
				line = ibr.readLine();
			}

			ibr.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

	}


	public void update(ShareInfo sinfo){	//マップ更新
		myPlayer.move(sinfo);
		for(int i = 0; i < myObj.size(); i++){
			myObj.get(i).update();;
		}
	}

	public void draw(ShareInfo sinfo){		//マップ描画
		int x, y;			//右上端座標
		int dx = 0, dy = 0;			//自キャラ位置によるズレ
		x = myPlayer.box_x - 10;
		y = myPlayer.box_y - 10;
		int playerX = 0, playerY = 0;
		//マップの描画座標の計算
		if(myPlayer.point_x <= MAP_CONST.MAP_BOX_SIZE * 10){
			x = 0;
			playerX = -1;
		}else if(myPlayer.point_x >= MAP_CONST.MAP_BOX_SIZE *(boxs[0].length - 12)){
			x = (boxs[0].length - 22);
			playerX = x * MAP_CONST.MAP_BOX_SIZE;
		}else{
			dx = myPlayer.point_x - myPlayer.box_x * MAP_CONST.MAP_BOX_SIZE;
			playerX = myPlayer.point_x;
		}
		if(myPlayer.point_y <= MAP_CONST.MAP_BOX_SIZE * 10){
			y = 0;
			playerY = -1;
		}else if(myPlayer.point_y >= MAP_CONST.MAP_BOX_SIZE *(boxs.length - 12)){
			y = (boxs.length - 22);
			playerY = y * MAP_CONST.MAP_BOX_SIZE;
		}else{
			dy = myPlayer.point_y - myPlayer.box_y * MAP_CONST.MAP_BOX_SIZE;
			playerY = myPlayer.point_y;
		}

		//背景描画
		for(int i = -1; i < 23; i++){
			for(int j = -1; j < 23; j++){
				if(i + y < 0 || i + y >= boxs.length  || j + x < 0 || j + x >= boxs[0].length){
					continue;
				}
				//System.out.println((i + y) +" "+ (j + x));
				boxs[i + y][j + x].draw(sinfo, MAP_CONST.MAP_BOX_SIZE * j - dx, MAP_CONST.MAP_BOX_SIZE * i - dy, myMapChip);
			}
		}

		//オブジェクト描画
		for(int i = -1; i < 23; i++){
			for(int j = -1; j < 23; j++){
				if(i + y < 0 || i + y >= boxs.length  || j + x < 0 || j + x >= boxs[0].length){
					continue;
				}
				if(j + x == 5 && i + y==30){
					System.out.println((i + y) +" "+ (j + x));
				}
				//System.out.println((i + y) +" "+ (j + x));
				boxs[i + y][j + x].drawObj(sinfo, MAP_CONST.MAP_BOX_SIZE * j - dx, MAP_CONST.MAP_BOX_SIZE * i - dy, j + x, i + y);
			}
		}

		//プレイヤー描画
		myPlayer.draw(sinfo, playerX , playerY);
	}

	public MAP_CONST.STATE chackPlayerFoot(){
		if(myPlayer.isMoving()){
			return MAP_CONST.STATE.EMPTY;
		}
		//System.out.println(boxs[myPlayer.getBox_y()-1][myPlayer.getBox_x()].getState());
		if(boxs[myPlayer.getBox_y()][myPlayer.getBox_x()].getState() != MAP_CONST.STATE.EMPTY){
			return boxs[myPlayer.getBox_y()][myPlayer.getBox_x()].getState();
		}
		if(boxs[myPlayer.getBox_y()][myPlayer.getBox_x() + 1].getState() != MAP_CONST.STATE.EMPTY){
			return boxs[myPlayer.getBox_y()][myPlayer.getBox_x() + 1].getState();
		}
		if(boxs[myPlayer.getBox_y() + 1][myPlayer.getBox_x()].getState() != MAP_CONST.STATE.EMPTY){
			return boxs[myPlayer.getBox_y() + 1][myPlayer.getBox_x()].getState();
		}
		if(boxs[myPlayer.getBox_y() + 1][myPlayer.getBox_x() + 1].getState() != MAP_CONST.STATE.EMPTY){
			return boxs[myPlayer.getBox_y() + 1][myPlayer.getBox_x() + 1].getState();
		}
		return MAP_CONST.STATE.EMPTY;
	}

	public void mapToMap(){
		loadMap(((NextMapBox)boxs[myPlayer.getBox_y()][myPlayer.getBox_x()]).getNextMapName());
		myPlayer.setPosition(((NextMapBox)boxs[myPlayer.getBox_y()][myPlayer.getBox_x()]).getNext_x(),
				((NextMapBox)boxs[myPlayer.getBox_y()][myPlayer.getBox_x()]).getNext_y());
	}

	public MapBox getBox(int x, int y){
		return boxs[y][x];
	}

	public void setBoxState(int x, int y, MAP_CONST.STATE state){
		boxs[y][x].setState(state);
	}


	public void setObj(int x, int y, MapObject obj) {
		boxs[y][x].setObj(obj);
	}
}
