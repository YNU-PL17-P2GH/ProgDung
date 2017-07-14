package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class TutorialObject extends MapProgObject {
	
	private int currentState = -1;
	private BufferedImage objImg;
	private int updateTick = -1;
	
	public TutorialObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/tori.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		theOperator = new TutorialOperator();
		width = 2;
		height = 4;
	}

	@Override
	public void runRuby(Ruby ruby) {
		super.runRuby(ruby);
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.drawImage(objImg, map_x, map_y, 64, 128, null);
		drawFlag = true;
	}

	@Override
	public void update(ShareInfo sinfo) {
		handler.getMap().setObj(box_x, box_y, this);
		drawFlag = false;
		
		// Move
		if (updateTick == -1) {
			runRuby(Ruby.newInstance());
		}
		if (updateTick++ >= 100) {
			updateTick = 0;
		} else {
			return;
		}
		
		switch (currentState) {
		case 0:
			getMap().setObj(box_x, box_y, null);
			box_x++;
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean getCanPass() {
		return false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public class TutorialOperator {
		public void moveRight() {
			currentState = 0;
		}
	}

}
