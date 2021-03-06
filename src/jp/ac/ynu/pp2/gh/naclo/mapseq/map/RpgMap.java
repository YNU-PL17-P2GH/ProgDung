package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class RpgMap {
	MapBox boxs[][];
	private MapChip myMapChip;
	MapHandlerBase handler;

	public RpgMap(MapHandlerBase pHandler, String mapName , int player_x, int player_y, MAP_CONST.DIRECTION player_direct){
		handler = pHandler;

		loadMap(mapName);
		myMapChip = new MapChip(mapName);
		handler.onMapLoad();
	}


	@SuppressWarnings("unchecked")
	final void loadMap(String mapName){	//マップロード
		//myMapChip = new MapChip(mapName);
		BufferedReader ibr = null;
		String tSeparator = "[ |\t]*,[ |\t]*";
		Pattern tPattern = Pattern.compile(tSeparator);
		try {
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/" + mapName + "/map.txt").openStream()));
			String line = ibr.readLine();

			String datas[] = line.split(",", 3);
			boxs = new MapBox[Integer.parseInt(datas[0])][Integer.parseInt(datas[1])];
			//レイヤー数設定
			int layer = Integer.parseInt(datas[2]);
//			MapBox.setLayerNum(layer);

			//マップの初期STATE設定
			for(int i = 0; i < boxs.length; i++){
				line = ibr.readLine();
				datas = tPattern.split(line);
				MAP_CONST.STATE state;
				for(int j = 0; j < boxs[i].length; j++){
					state = MAP_CONST.STATE.getValue(Integer.parseInt(datas[j]));
					if(state == MAP_CONST.STATE.NEXT){		//マップ移動マスは特殊(移動先のマップ情報が必要だから)
						boxs[i][j] = new NextMapBox(state, layer);
					}else{
						boxs[i][j] = new MapBox(state, layer);
					}
				}
			}

			//マップチップ配置 レイヤーごと
			for(int k = 0; k < layer; k++){
				for(int i = 0; i < boxs.length; i++){
					line = ibr.readLine();
					if(line.indexOf(',') < 0){
						i--;
						continue;
					}
					datas = tPattern.split(line);
					for(int j = 0; j < boxs[i].length; j++){
						boxs[i][j].setChip(Integer.parseInt(datas[j]), k);
					}
				}
			}
			ibr.close();

			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/" + mapName + "/mapObj.txt").openStream()));
			line = ibr.readLine();

			while(line != null){
				if(line.startsWith("nextMap")){	//マップ移動マスのデータをセット
					datas = tPattern.split(line);
					int boxNum = Integer.parseInt(datas[4]);
					int x = Integer.parseInt(datas[2]);
					int y = Integer.parseInt(datas[3]);

					MAP_CONST.DIRECTION d = null;
					if(datas.length > (5 + boxNum * 2)){
						d = MAP_CONST.DIRECTION.getValue(Integer.parseInt(datas[5 + boxNum * 2]));
					}
					for(int i = 0; i < boxNum; i++){
						((NextMapBox)boxs[Integer.parseInt(datas[6 + i * 2])][Integer.parseInt(datas[5 + i * 2])]).setNextMap(datas[1], x, y, d);
					}
				}else if(line.startsWith("fixObj")){	//固定設置物
					datas = tPattern.split(line);
					handler.theObj.add(new MapFixedObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this));
				}else if(line.startsWith("fixNamedObj")){	//固定設置物 名前あり
					datas = tPattern.split(line);
					handler.theObj.add(new MapFixedNamedObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], datas[4], this));
				}else if(line.startsWith("animeObj")){	//固定設置物 アニメーションあり
					datas = tPattern.split(line);
					handler.theObj.add(new MapAnimeObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), Integer.parseInt(datas[4]), Integer.parseInt(datas[5]), datas[1], this));
				}else if(line.startsWith("animeNamedObj")){	//固定設置物 アニメーションあり 名前あり
					datas = tPattern.split(line);
					handler.theObj.add(new MapAnimeNamedObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), Integer.parseInt(datas[4]), Integer.parseInt(datas[5]),datas[1], datas[6], this));
				}else if(line.startsWith("clearObj")){	//クリア用pc
					datas = tPattern.split(line);
					handler.theObj.add(new MapClearObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this));
				}else if(line.startsWith("doorObj")){	//ドア設置
					datas = tPattern.split(line);
					handler.theObj.add(new MapDoorObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]),Integer.parseInt(datas[4]), Integer.parseInt(datas[5]), datas[1], datas[6], this));
				}else if (line.startsWith("pcObj")) {
					// PC
					datas = tPattern.split(line);
					handler.theObj.add(new MapPcObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this, datas[4]));

				}else if (line.startsWith("hintObj")) {
					// ヒント用
					datas = tPattern.split(line);
					handler.theObj.add(new MapHintObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this, mapName, datas[4]));
				}else if(line.startsWith("progObj")){
					datas = tPattern.split(line);
					// datas[1]にロードするプログラム
					// 該当のクラスはprogdung.map.progobjパッケージにある.
					String className = "jp.ac.ynu.pp2.gh.progdung.map.progobj.".concat(datas[4]);
					Constructor<MapProgObject> constructor;
					try {
						constructor = (Constructor<MapProgObject>) Class.forName(className).getConstructor(MapHandlerBase.class, int.class, int.class, String.class, RpgMap.class);
						handler.theObj.add(constructor.newInstance(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), datas[1], this));
					} catch (NoSuchMethodException | SecurityException | ClassNotFoundException
							| InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
//					handler.theObj.add(new MapSortObject(handler, Integer.parseInt(datas[2]), Integer.parseInt(datas[3]), "sort", this));
				}
				line = ibr.readLine();
			}

			ibr.close();

			connectPcToProg();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

	}


	private void connectPcToProg() {
		// PCに対応するProgObjectのリスト.
		Map<String, MapPcObject> tRel = new HashMap<>();
		for (MapObject tObject : handler.theObj) {
			if (tObject instanceof MapPcObject) {
				tRel.put(((MapPcObject) tObject).getAllocObjName(), (MapPcObject) tObject);
			}
		}

		for (MapObject tObject : handler.theObj) {
			if (tObject instanceof MapProgObject &&
					tRel.containsKey(tObject.getObjName())) {
				System.err.println("Connected " + tObject.getObjName() + " to " + tRel.get(tObject.getObjName()));
				tRel.get(tObject.getObjName()).allocObj = (MapProgObject) tObject;
			}
		}
	}


	public void update(ShareInfo sinfo){	//マップ更新
		handler.thePlayer.update(sinfo);
		for(int i = 0; i < handler.theObj.size(); i++){
			handler.theObj.get(i).update(sinfo);
			//System.out.println(handler.theObj.get(i).getCanPass());
		}
	}

	public void draw(ShareInfo sinfo){		//マップ描画
		int x, y;			//右上端座標
		int dx = 0, dy = 0;			//自キャラ位置によるズレ
		x = handler.thePlayer.box_x - 10;
		y = handler.thePlayer.box_y - 10;
		int playerX = 0, playerY = 0;
		//マップの描画座標の計算
		if(handler.thePlayer.point_x <= MAP_CONST.MAP_BOX_SIZE * 10){	//マップ左端が画面内
			x = 0;
			playerX = -1;
		}else if(handler.thePlayer.point_x >= MAP_CONST.MAP_BOX_SIZE *(boxs[0].length - 12)){	//マップ右端が画面内
			x = (boxs[0].length - 22);
			playerX = x * MAP_CONST.MAP_BOX_SIZE;
		}else{
			dx = handler.thePlayer.point_x - handler.thePlayer.box_x * MAP_CONST.MAP_BOX_SIZE;
			playerX = handler.thePlayer.point_x;
		}
		if(handler.thePlayer.point_y <= MAP_CONST.MAP_BOX_SIZE * 10){	//マップ上端が画面内
			y = 0;
			playerY = -1;
		}else if(handler.thePlayer.point_y >= MAP_CONST.MAP_BOX_SIZE *(boxs.length - 12)){	//マップ下端が画面内
			y = (boxs.length - 22);
			playerY = y * MAP_CONST.MAP_BOX_SIZE;
		}else{
			dy = handler.thePlayer.point_y - handler.thePlayer.box_y * MAP_CONST.MAP_BOX_SIZE;
			playerY = handler.thePlayer.point_y;
		}

		//背景描画(タイル)
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
				//System.out.println((i + y) +" "+ (j + x));
				boxs[i + y][j + x].drawObj(sinfo, MAP_CONST.MAP_BOX_SIZE * j - dx, MAP_CONST.MAP_BOX_SIZE * i - dy, j + x, i + y);
			}
		}

		//プレイヤー描画
		handler.thePlayer.draw(sinfo, playerX , playerY);
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

	public MapPlayer getMyPlayer() {
		return handler.thePlayer;
	}
}
