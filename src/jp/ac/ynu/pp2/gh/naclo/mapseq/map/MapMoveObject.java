package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;

public class MapMoveObject extends MapObject{
	protected static final int delay = 10;
	protected int point_x, point_y;
	protected int next_x, next_y;		//移動先マス座標

	protected int animeCount;
	protected int directNum;			//向きの数
	protected int imgNum;				//画像枚数
	protected DIRECTION direction;			//現在の向き


	public MapMoveObject(MapHandlerBase pHandler,int bx, int by, String objName, MAP_CONST.DIRECTION direct, RpgMap map){
		super(pHandler, bx, by, objName, map);

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
		direction = direct;
	}

	@Override
	public void draw(ShareInfo sinfo, int player_x, int player_y) {
		int x = point_x - (player_x - 5 * MAP_CONST.MAP_BOX_SIZE);
		int y = point_y - (player_y - 5 * MAP_CONST.MAP_BOX_SIZE);
		sinfo.g.drawImage(objImg, x, y, x + MAP_CONST.MAP_BOX_SIZE , y + MAP_CONST.MAP_BOX_SIZE ,
			(animeCount % imgNum) * MAP_CONST.MAP_CHIP_SIZE, (direction.getVal() % directNum) * MAP_CONST.MAP_CHIP_SIZE, (animeCount % imgNum + 1) * MAP_CONST.MAP_CHIP_SIZE, (direction.getVal() % directNum+ 1) * MAP_CONST.MAP_CHIP_SIZE, null);

		animeCount++;
	}

	@Override
	public void update(ShareInfo sinfo) {}

	@Override
	public boolean hitCheck(MapObject obj) {
		return false;
	}
}

