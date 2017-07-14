package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapDoorObject extends MapObject{
	private BufferedImage objImg;
	private int imgNum; 					//アニメーションの枚数
	private int imgNumNow;					//現在の表示画像
	private String doorKey;

	public MapDoorObject(MapHandlerBase pHandler, int bx, int by, int w, int h, String objName, String key, RpgMap map) {
		super(pHandler, objName);

		myMap = map;
		box_x = bx;
		box_y = by;
		width = w;
		height = h;

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/door/" + objName + ".png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				myMap.setObj(box_x + j, box_y + i, this);
			}
		}

		canPass = false;
		//画像は縦方向にしか連結していないのが前提
		//また一番下の画像が開いている扉
		imgNum = objImg.getHeight() / (height * MAP_CONST.MAP_CHIP_SIZE);
		
		if(key.equals("null")){
			doorKey = null;
		}else{
			doorKey = key;
		}
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.drawImage(objImg, map_x, map_y , map_x + MAP_CONST.MAP_BOX_SIZE  * width, map_y + MAP_CONST.MAP_BOX_SIZE * height,
				0, imgNumNow * (height * MAP_CONST.MAP_CHIP_SIZE),
				width * MAP_CONST.MAP_CHIP_SIZE, (imgNumNow + 1) * (height * MAP_CONST.MAP_CHIP_SIZE), null);
		drawFlag = true;
	}
	private int animeCount = 0;
	private int delay = 20;
	private boolean flagOpen = false;

	@Override
	public void update(ShareInfo sinfo) {
		if(drawFlag == true){	//描画領域内にあるなら
			flagOpen = handler.hitChecktoPlayer(this);
			if(flagOpen){	//ドアを開くとき
				if(animeCount % delay ==0 && imgNumNow != (imgNum - 1)){
					imgNumNow++;
				}
			}else{			//ドアを閉じるとき
				if(animeCount % delay ==0 && imgNumNow != 0){
					imgNumNow--;
				}
			}
			
			if(imgNumNow == (imgNum - 1)){
				canPass = true;
			}else{
				canPass = false;
			}
			

			animeCount++;
		}
		drawFlag = false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		if(doorKey != null){
			if(!handler.callback.getSaveData().getBoolean(doorKey)){	//扉が開けるかチェック
				return false;
			}
		}
		//近づいたときなので判定エリアを拡大
		if(this.box_x - 1 < (obj.box_x + obj.width) && (this.box_x + 1 + this.width) > obj.box_x){
			if(this.box_y - 1 < (obj.box_y + obj.height) && (this.box_y + 1 + this.height) > obj.box_y){
				return true;
			}
		}
		return false;
	}

}
