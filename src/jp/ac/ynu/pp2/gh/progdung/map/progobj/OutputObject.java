package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.io.StringWriter;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;
import jp.ac.ynu.pp2.gh.progdung.map.handlers.Output;

public class OutputObject  extends MapProgObject {
	private volatile boolean fragSuccess;

	public OutputObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		setOperator(15);
	}
/*	正解コード 混乱しそうだし print のみでもいいかなっと(デバックに使えるみたいなアドバイスもほしいかも？)
def output(argument)
	print("Hello, Algeon!!\n")
	print("このダンジョンには、", argument , "個の問題があります!!")
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
		if(stdin.getBuffer().toString().equals("Hello, Algeon!!\nこのダンジョンには、"+ 15 + "個の問題があります!!")) {
			fragSuccess = true;
		}
	}
	@Override
	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		if(!handler.getCallback().getSaveData().getBoolean("Output001")) {
			if(fragSuccess){
				handler.getCallback().showHint("<html>扉の前に光る何かが現れた!!!</html>", true);
				((Output)handler).getKey().setVisible(true);
				handler.getCallback().getSaveData().setTaken("Output001");
			}else{
				handler.getCallback().showHint("<html>何も起こらない...</html>", true);
			}

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
}

