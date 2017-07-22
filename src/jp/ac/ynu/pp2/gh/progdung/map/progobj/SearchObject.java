package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Color;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapFixedObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class SearchObject extends MapProgObject {
	private SearchSubObject boxs[] = new SearchSubObject[100];

	private int clearCount = 3;

	public SearchObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		width = 0;
		height = 0;
		setOperator(new SearchOparator());
		for(int i = 0; i < 38; i++) {
			for(int j = 0; j < 38; j++) {
				getMap().setObj(bx + i, by + j, this);
			}
		}

		for(int i = 0; i < boxs.length; i++) {
			boxs[i] = new SearchSubObject(pHandler, bx + i % 10 * 4, by +i / 10 * 4, "box", pMap, i);
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
		return "boxs,target";
	}
	@Override
	public int getTimeout() {
		return 20;
	}
/*
def search(boxs, target)
  Integer left = 0
  Integer mid = -1
  Integer right = boxs.length()
  while left <= right
    mid  = (left + right) / 2
    if boxs.checkElement(mid, target) == 0 then
      boxs.setResult(mid)
      break
    elsif boxs.checkElement(mid, target) == -1 then
      right = mid - 1
    elsif boxs.checkElement(mid, target) == 1 then
      left = mid + 1
    end
  end
end
 */
	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		initFlag();
	}

	public void runRuby(final Ruby ruby, StringWriter stdin, StringWriter stderr, Object... pArguments) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);
		PrintWriter pstdout= new PrintWriter(stdin);
		container.setWriter(pstdout);
		PrintWriter pstderr = new PrintWriter(stderr);
		container.setErrorWriter(pstderr);

		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");

		String rwrapper = setTimeout();
		container.runScriptlet(rwrapper);
		container.callMethod(ruby.getCurrentContext(), "rwrapper", getOperator(), ((SearchOparator)getOperator()).getTarget());
		handler.getCallback().stdoutUpdate();
		handler.getCallback().stderrUpdate();
	}

	public SearchSubObject[] getBoxs(){
		return boxs;
	}


	public boolean selectBox(SearchSubObject box) {
		initFlag();
		if(((SearchOparator)getOperator()).checkAnswer(box.getNumber())) {
			((SearchOparator)getOperator()).init();
			clearCount--;
			if(clearCount > 0) {
				handler.getCallback().showHint("あと" + clearCount+"回!!", true);
				return false;
			}else{
				return true;
			}
		}else {
			((SearchOparator)getOperator()).init();
			clearCount = 3;
			handler.getCallback().showHint("不正解....", true);
			return false;
		}
	}


	public void initFlag() {
		for(int i = 1; i < boxs.length; i++){
			boxs[i].setCheck(false);
			boxs[i].setSelected(false);
		}
	}

	public class SearchOparator {
		private int[] array;
		private int targetID;		//探す数値の添え字
		public SearchOparator(){
			array = new int[100];
			init();
		}

		public boolean checkAnswer(int number) {
			return number == targetID;
		}

		public void init(){
			Random r = new Random();
			array[0] = r.nextInt(100);
			for(int i = 1; i < array.length; i++){
				array[i] = array[i - 1] + r.nextInt(100);
			}

			targetID = r.nextInt(array.length);
		}

		public int checkElement(int i, int target){	//ターゲットを探す
			boxs[i].setCheck(true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boxs[i].setCheck(false);
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
			boxs[r].setSelected(true);
		}

		public int getTarget() {
			return array[targetID];
		}
	}

	public class SearchSubObject extends MapFixedObject{
		private int number;
		private boolean selected;
		private boolean check;

		public SearchSubObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map, int num) {
			super(pHandler, bx, by, objName, map);
			number = num;
			selected = false;
		}

		public void setCheck(boolean b) {
			check = b;
		}

		@Override
		public void draw(ShareInfo sinfo, int map_x, int map_y) {
			super.draw(sinfo, map_x, map_y);

			if(check) {
				sinfo.g.setColor(Color.YELLOW);
				sinfo.g.fillRect(map_x, map_y, width * MAP_CONST.MAP_BOX_SIZE, height * MAP_CONST.MAP_BOX_SIZE);
			}
			if(selected) {
				sinfo.g.setColor(Color.RED);
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


