package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapClearObject extends MapFixedObject{
	private BufferedImage mahouzinImg;

	private boolean mahouzinOnFlag;

	public MapClearObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map) {
		super(pHandler, bx, by, objName, map);

		try {
			mahouzinImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/mahouzin.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void setMahouzinOnFlag(boolean b) {
		this.mahouzinOnFlag = b;
	}

	@Override
	public URL getImagePath() {
		return getClass().getClassLoader().getResource("media/map/obj/pc/mini_ashi.png");
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		if(mahouzinOnFlag){
			sinfo.g.drawImage(mahouzinImg, map_x - MAP_CONST.MAP_BOX_SIZE  * 2 , map_y - MAP_CONST.MAP_BOX_SIZE ,
					MAP_CONST.MAP_BOX_SIZE  * 6, MAP_CONST.MAP_BOX_SIZE  * 6, null);
		}
		super.draw(sinfo, map_x, map_y);
	}
}
