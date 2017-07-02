package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapSortObject extends MapObject{	//クラスローダーにする気だけど仮で
	int array[] = {0,1,2,3,4,5,6,7,8,9};
	private BufferedImage objImg;
	public MapSortObject(int bx, int by, String objName, RpgMap map){
		myMap = map;
		box_x = bx;
		box_y = by;
		//objNameに従ってロード
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/sort.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}
		myMap.setBoxState(box_x, box_y, MAP_CONST.MAP_STATE_BLOCK);
		for(int i = 0; i < array.length ; i++){
			for(int j = 0 ;j < 10; j++){
				myMap.setObj(box_x - j, box_y + i, this);
			}
		}

	}
	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		for(int i = 0; i < array.length ; i++){
			for(int j = array[i] ;j > 0; j--){
				sinfo.g.drawImage(objImg, map_x - (j - 1) * MAP_CONST.MAP_BOX_SIZE, map_y + i * MAP_CONST.MAP_BOX_SIZE*  2 , null);
			}
		}
		drawFlag = true;
	}

	@Override
	public void update() {
		drawFlag = false;
		int c = array[9];
		array[9] = array[8];
		array[8] = c;
	}

}
