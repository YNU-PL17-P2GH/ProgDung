package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.io.StringWriter;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class OutputObject  extends MapProgObject {

	private int[] array	= new int[6];
	private int[] initArray	= {0, 100, 200, 300, 400, 500};

	private volatile boolean fragSuccess;
	private boolean runRuby = false;



	public OutputObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		setOperator(15);
	}
/*	正解コード 混乱しそうだし print のみでもいいかなっと(デバックに使えるみたいなアドバイスもほしいかも？)
def output(argument)
	print("Hello, Algeon!!\n")
	print("このステージには、", argument , "個の鍵が隠されています\n")
end
 */

	@Override
	public String getMethodName() {
		return "output";
	}

	@Override
	public String getArgumentString() {
		return "argument";
	}

	@Override
	public void runRuby(Ruby ruby, StringWriter stdin, StringWriter stderr, Object... pArguments) {
		super.runRuby(ruby, stdin, stderr, pArguments);
		runRuby = true;
		if(stdin.getBuffer().toString().equals("Hello, Algeon!!\nこのステージには、"+ 15 + "個の鍵が隠されています\n")) {
			fragSuccess = true;
		}
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
	}

	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}

	@Override
	public boolean getCanPass() {
		return true;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkFragSuccess() {
		runRuby = false;
		return fragSuccess;
	}

	public boolean getRunRuby() {
		return runRuby ;
	}
}

