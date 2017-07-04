package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapFixedObject extends MapObject{
	private BufferedImage objImg;
	public MapFixedObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map){
		super(pHandler);

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
		int x = objImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		int y = objImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				myMap.setBoxState(box_x + j, box_y + x, MAP_CONST.STATE.BLOCK);
				myMap.setObj(box_x + j, box_y + x, this);
			}
		}
		
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		/*int x = (box_x + 5) * MAP_CONST.MAP_BOX_SIZE - player_x;
		int y = (box_y + 5) * MAP_CONST.MAP_BOX_SIZE - player_y;*/
		sinfo.g.drawImage(objImg, map_x, map_y, null);
	}

	@Override
	public void update(ShareInfo sinfo) {}
}
