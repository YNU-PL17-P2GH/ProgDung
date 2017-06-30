package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapMoveObject extends MapObject{
	protected static final int delay = 10;
	protected int point_x, point_y;
	protected int next_x, next_y;		//移動先マス座標
	protected BufferedImage objImg;
	protected int animeCount;
	protected int directNum;			//向きの数
	protected int imgNum;				//画像枚数
	protected int direction;			//現在の向き
	

	public MapMoveObject(int bx, int by, String objName, int direct, RpgMap map){
		myMap = map;
		box_x = bx;
		box_y = by;
		animeCount = 0;
		//objNameに従ってロード
		BufferedReader ibr = null;
		try {
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/obj/" + objName + "/obj.txt").openStream()));
			String line = ibr.readLine();

			if(line.indexOf("moveObjData") >= 0){
				directNum = Integer.parseInt(line.split(" ", 0)[1]);
				imgNum =  Integer.parseInt(line.split(" ", 0)[2]);
			}else{	//ファイルエラー
				JOptionPane.showMessageDialog(null, "エラー");
				System.exit(0);
			}

			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/" + objName +"/obj.png"));

			ibr.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

		point_x = MAP_CONST.MAP_BOX_SIZE * bx;
		point_y = MAP_CONST.MAP_BOX_SIZE * by;
		next_x = box_x;
		next_y = box_y;
		myMap.setBoxState(box_x, box_y, MAP_CONST.MAP_STATE_BLOCK);
		direction = direct;
	}

	@Override
	public void draw(ShareInfo sinfo, int player_x, int player_y) {
		int x = point_x - (player_x - 5 * MAP_CONST.MAP_BOX_SIZE);
		int y = point_y - (player_y - 5 * MAP_CONST.MAP_BOX_SIZE);
		sinfo.g.drawImage(objImg, x, y, x + MAP_CONST.MAP_BOX_SIZE , y + MAP_CONST.MAP_BOX_SIZE ,
			(animeCount % imgNum) * MAP_CONST.MAP_CHIP_SIZE, (direction % directNum) * MAP_CONST.MAP_CHIP_SIZE, (animeCount % imgNum + 1) * MAP_CONST.MAP_CHIP_SIZE, (direction % directNum+ 1) * MAP_CONST.MAP_CHIP_SIZE, null);

		animeCount++;
	}
}

