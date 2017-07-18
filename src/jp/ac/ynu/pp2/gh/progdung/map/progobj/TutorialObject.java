package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringWriter;

import javax.imageio.ImageIO;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

import org.jruby.Ruby;

public class TutorialObject extends MapProgObject {

	private int currentState = -1;
	private BufferedImage objImg;
	private int updateTick = -1;

	public TutorialObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/" + pObjName + ".png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		theOperator = new TutorialOperator();
		width = 2;
		height = 4;

		sourceRuby = "def run(" + pObjName + ")\n"
				+ "\t# この下にソースを入力\n"
				+ "end";

	}

	@Override
	public void runRuby(final Ruby ruby, final StringWriter stdin, final StringWriter stderr) {
		new Thread() {
			@Override
			public void run() {
				rrwrapper(ruby, stdin, stderr);
			}
		}.start();
	}

	private void rrwrapper(Ruby ruby, StringWriter stdin, StringWriter stderr) {
		super.runRuby(ruby, stdin, stderr);
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.drawImage(objImg, map_x, map_y, 64, 128, null);
		drawFlag = true;
	}

	@Override
	public synchronized void update(ShareInfo sinfo) {
		if (updateTick == -1) {
			getMap().setObj(box_x, box_y, this);
		}
		drawFlag = false;

		// Move
		if (currentState < 0) {
			updateTick = 0;
			return;
		}

		if (updateTick++ >= 25) {
			updateTick = 0;
		} else {
			return;
		}

		int bx = box_x;
		int by = box_y;

		switch (currentState) {
		case 0:
			for (int i = 0; i < 4; i++) {
				if (getMap().getBox(box_x + 2, box_y + i).getState() == STATE.BLOCK) {
					currentState = -1;
					return;
				}
			}
			box_x++;
			break;
		case 1:
			for (int i = 0; i < 2; i++) {
				if (getMap().getBox(box_x + i, box_y - 1).getState() == STATE.BLOCK) {
					currentState = -1;
					return;
				}
			}
			box_y--;
			break;
		case 2:
			for (int i = 0; i < 4; i++) {
				if (getMap().getBox(box_x - 1, box_y + i).getState() == STATE.BLOCK) {
					currentState = -1;
					return;
				}
			}
			box_x--;
			break;
		case 3:
			for (int i = 0; i < 2; i++) {
				if (getMap().getBox(box_x + i, box_y + 4).getState() == STATE.BLOCK) {
					currentState = -1;
					return;
				}
			}
			box_y++;
			break;
		default:
			break;
		}
		getMap().setObj(bx, by, null);
		getMap().setObj(box_x, box_y, this);
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
			try {
				while (currentState >= 0) {
					Thread.sleep(1000);
				}
				currentState = 0;
			} catch (InterruptedException e) {
			}
		}

		public void moveUp() {
			try {
				while (currentState >= 0) {
					Thread.sleep(1000);
				}
				currentState = 1;
			} catch (InterruptedException e) {
			}
		}

		public void moveLeft() {
			try {
				while (currentState >= 0) {
					Thread.sleep(1000);
				}
				currentState = 2;
			} catch (InterruptedException e) {
			}
		}

		public void moveDown() {
			try {
				while (currentState >= 0) {
					Thread.sleep(1000);
				}
				currentState = 3;
			} catch (InterruptedException e) {
			}
		}

	}

}
