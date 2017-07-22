package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapFixedObject extends MapObject{

	public MapFixedObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map){
		super(pHandler, bx, by, objName, map);

		//objNameに従ってロード
		objImg = getImage();
		width = objImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		height = objImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				myMap.setObj(box_x + j, box_y + i, this);
			}
		}
		canPass = false;
	}
	
	public final BufferedImage getImage() {
		try {
			return ImageIO.read(getImagePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public URL getImagePath() {
		return getClass().getClassLoader().getResource("FirstRpg/media/map/obj/" + getObjName() + ".png");
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		if(!visible) return;
		sinfo.g.drawImage(objImg, map_x, map_y , map_x + MAP_CONST.MAP_BOX_SIZE  * width, map_y + MAP_CONST.MAP_BOX_SIZE * height,
				0, 0, width * MAP_CONST.MAP_CHIP_SIZE, (height * MAP_CONST.MAP_CHIP_SIZE), null);
		drawFlag = true;
	}

	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
