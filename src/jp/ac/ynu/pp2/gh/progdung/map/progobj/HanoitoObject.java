package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class HanoitoObject extends MapProgObject {
	private BufferedImage towerImgs[];
	private int showTower[][];
	private int[] showIndex;
	private int showTowerOffsetH[] = {18, 22, 23, 24, 26, 30};
	private int showTowerOffsetW[] = {-18, -9, 0};
	public HanoitoObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		sourceRuby = "def operator(hanoi)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";
		width = 0;
		height = 0;
		setOperator(new HanoitoOperator());
		towerImgs = new BufferedImage[((HanoitoOperator)getOperator()).getTowerNum()];
		try {
			for (int i = 0; i < towerImgs.length; i++) {
				String s = "media/map/hanoi/hanoi" + i +".png";
				towerImgs[i] = ImageIO.read(getClass().getClassLoader().getResource("media/map/hanoi/hanoi" + (towerImgs.length - i - 1) + ".png"));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
			for(int i = 0; i < 26; i++) {
				getMap().setObj(bx - 18 + i, by, this);
			}
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		// TODO Auto-generated method stub

		for(int i = 0; i < showTower.length; i++){
			int offsetH = 0;
			for(int j = 0; j < showTower[i].length; j++){
				if(showTower[i][j] == 0) {
					break;
				}
				sinfo.g.drawImage(towerImgs[showTower[i][j] - 1],
						map_x + towerImgs[5].getWidth() / 2  - towerImgs[showTower[i][j] - 1].getWidth() / 2 + showTowerOffsetW[i] * MAP_CONST.MAP_BOX_SIZE,
						map_y - offsetH, null);
				offsetH += showTowerOffsetH[showTower[i][j] - 1];
			}
		}

		drawFlag = true;
	}
	int animeCount = 0;
	int index = 0;
	public void update(ShareInfo sinfo) {
		/*for(int i=0;i<3;i++){
			if(Hanoito.tou[i][3]==3)Hanoito.up1--;
			if(Hanoito.tou[i][2]==2)Hanoito.up1--;
			if(Hanoito.tou[i][1]==1)Hanoito.up1--;
			if(Hanoito.tou[i][0]==0){getMap().setObj(box_x + 3-i*9, box_y + Hanoito.up1, this);}
			Hanoito.up1=0;
		}*/
		if(animeCount % 60 == 0) {
			if(((HanoitoOperator)getOperator()).fromToList.size() > index) {
				Point from_to = ((HanoitoOperator)getOperator()).fromToList.get(index);
				showTower[(int)from_to.getY()][showIndex[(int)from_to.getY()] + 1] = showTower[(int)from_to.getX()][showIndex[(int)from_to.getX()]];
				showTower[(int)from_to.getX()][showIndex[(int)from_to.getX()]] = 0;
				showIndex[(int)from_to.getY()]++;
				showIndex[(int)from_to.getX()]--;
				index++;
			}
			for(int i = 0;i < showIndex.length;i++){
				if(showIndex[i] < 0) {
					for(int j = 0; j < 8; j++) {
						getMap().getBox(box_x + showTowerOffsetW[i] + j, box_y + 2).setState(MAP_CONST.STATE.EMPTY);
					}
				}else{
					for(int j = 0; j < 8; j++) {
						getMap().getBox(box_x + showTowerOffsetW[i] + j, box_y + 2).setState(MAP_CONST.STATE.BLOCK);
					}
				}
			}
		}
		animeCount++;
		drawFlag = false;
	}
/* 正解コード
def operator(hanoi)
	hanoiSub(hanoi, 1, 0, 2, 6)
end

def hanoiSub(tower, from, to, relay, n)
	if n > 0 then
		hanoiSub(tower, from, relay, to, n - 1)
		tower.move(from, to)
		hanoiSub(tower, relay, to, from, n - 1)
	end
end
*/
	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		((HanoitoOperator)getOperator()).init();
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		return false;
	}

	@Override
	public String getMethodName() {
		// TODO 自動生成されたメソッド・スタブ
		return "operator";
	}
	@Override
	public String getArgumentString() {
		// TODO 自動生成されたメソッド・スタブ
		return "hanoi";
	}

	public void cleared() {
		for(int i = 0; i < showTower[0].length ;i++){
			showTower[0][i] = showTower[0].length - i;
			showTower[1][i] = 0;
			showTower[2][i] = 0;
		}
	}


	/*private void rrwrapper(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);

		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		container.callMethod(ruby.getCurrentContext(), "operate", theOperator);
	}*/

	public class HanoitoOperator {
		private int[][] tower;
		private int[] index;
		private boolean failFlag = false;
		Vector<Point> fromToList = new Vector<Point>();
		public HanoitoOperator(){
			tower = new int[3][6];
			showTower = new int[3][6];
			index = new int[3];
			showIndex = new int[3];
			init();
		}

		public void init() {
			for(int i = 0; i < tower[0].length ;i++){
				tower[0][i] = 0;
				tower[1][i] = tower[0].length - i;
				tower[2][i] = 0;
				showTower[0][i] = 0;
				showTower[1][i] = tower[0].length - i;
				showTower[2][i] = 0;
			}
			index[0] = -1;
			index[1] = tower[0].length - 1;
			index[2] = -1;
			showIndex[0] = -1;
			showIndex[1] = tower[0].length - 1;
			showIndex[2] = -1;
			failFlag = false;
		}

		public void move(int from, int to){
			if(failFlag){
				return;
			}
			//System.out.println(from + "->" + to);
			if(index[from] < 0){
				failFlag = true;
				return;
			}
			if(index[to] >= 0) {
				if(tower[to][index[to]] < tower[from][index[from]]){
					failFlag = true;
					return;
				}
			}
			fromToList.add(new Point(from, to));
			tower[to][index[to] + 1] = tower[from][index[from]];
			tower[from][index[from]] = 0;
			index[to]++;
			index[from]--;
		}

		public int getTowerNum() {
			return tower[0].length;
		}
	}
}

