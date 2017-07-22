package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapFixedObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class SearchObject extends MapProgObject {


	public SearchObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		sourceRuby = "def search(boxs)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";
		width = 0;
		height = 0;
		setOperator(new SearchOparator());
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("FirstRpg/media/map/obj/sort.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for(int i = 0; i < 38; i++) {
			for(int j = 0; j < 38; j++) {
				getMap().setObj(bx + i, by + j, this);
			}
		}
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {

		drawFlag = true;
	}

	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}
/* 正解コード

*/

	@Override
	public boolean hitCheck(MapObject obj) {
		return false;
	}

	@Override
	public String getMethodName() {
		return "search";
	}

	@Override
	public String getArgumentString() {
		return "boxs";
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

	public class SearchOparator {
		private int[] array;
		private int targetID;		//探す数値の添え字
		private int count;
		private int result;		//プレイヤーの答え
		public SearchOparator(){
			array = new int[100];
			init();
		}

		public void init(){
			Random r = new Random();
			array[0] = r.nextInt(100);
			for(int i = 1; i < array.length; i++){
				array[i] = array[i - 1] + r.nextInt(100);
			}

			targetID = r.nextInt(array.length);
			initResult();
		}

		public void initResult(){
			result = -1;
			count = 0;
		}

		public int checkElement(int i, int target){	//ターゲットを探す
			count++;
			if(array[i] == target){		//範囲外だと例外吐いてくれるはず
				return 0;
			}else if(array[i] > target){
				return -1;
			}else{
				return 1;
			}
		}

		public int length(){
			return array.length;
		}

		public void setResult(int r){
			result = r;
		}

		public int getTarget() {
			return array[targetID];
		}
	}

	public class SearchSubObjct extends MapFixedObject{
		private int number;
		private boolean selected;

		public SearchSubObjct(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map, int num) {
			super(pHandler, bx, by, objName, map);
			number = num;
			selected = false;
		}

		@Override
		public void draw(ShareInfo sinfo, int map_x, int map_y) {
			super.draw(sinfo, map_x, map_y);
			sinfo.g.setColor(Color.YELLOW);
			if(selected) {
				sinfo.g.fillRect(map_x, map_y, width * MAP_CONST.MAP_BOX_SIZE, height * MAP_CONST.MAP_BOX_SIZE);
			}
		}

		public int getNumber() {
			return number;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
}


