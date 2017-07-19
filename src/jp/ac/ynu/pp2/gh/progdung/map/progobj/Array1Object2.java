package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public class Array1Object2  extends MapProgObject {

	private int[] array	= new int[6];
	private int[] initArray	= {0, 100, 200, 300, 400, 500};
	private final int[] answerArray	= {9, 5, 3, 4, 2, 6};

	private volatile boolean fragSuccess;

	public Array1Object2(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
	}
/*	正解コード
def operate(array)
	array[0] = 9
	array[1] = 5
	array[2] = 3
	array[3] = 4
	array[4] = 2
	array[5] = 6
end
 */
	@Override
	public void launchRubyWithThread(final Ruby ruby, StringWriter stdin, StringWriter stderr, Object... pArguments) {
		for (int i = 0; i < array.length; i++) {
			array[i] = initArray[i];
		}
		new Thread() {
			@Override
			public void run() {
				runRuby(ruby, stdin, stderr, pArguments);
				if(!fragSuccess){
					boolean b = true;
					for(int i = 0; i < array.length; i++){
						if(array[i] != answerArray[i]){
							b = false;
							break;
						}
					}
					fragSuccess = b;
				}
			}
		}.start();
	}
	
	@Override
	public String getArgumentString() {
		return "array";
	}

	private void rrwrapper(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);

		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		container.callMethod(ruby.getCurrentContext(), "operate", array);
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
	}

	@Override
	public void update(ShareInfo sinfo) {
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

	public void setFragSuccess(boolean b) {
		this.fragSuccess = b;
	}
	public boolean getFragSuccess() {
		return fragSuccess;
	}
}

