package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapFixedObject extends MapObject{
	private BufferedImage objImg;
	public MapFixedObject(int bx, int by, String objName, RpgMap map){
		myMap = map;
		box_x = bx;
		box_y = by;
		//objNameに従ってロード
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/" + objName + ".png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}
		myMap.setBoxState(box_x, box_y, MAP_CONST.MAP_STATE_BLOCK);
		myMap.setObj(box_x, box_y, this);
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		/*int x = (box_x + 5) * MAP_CONST.MAP_BOX_SIZE - player_x;
		int y = (box_y + 5) * MAP_CONST.MAP_BOX_SIZE - player_y;*/
		sinfo.g.drawImage(objImg, map_x, map_y, null);
	}

	@Override
	public void update() {}
}
