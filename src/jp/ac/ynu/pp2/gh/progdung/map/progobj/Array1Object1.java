package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class Array1Object1 extends MapProgObject {

	private int[] array	= new int[6];
	private int[] initArray	= {0, 100, 200, 300, 400, 500};

	private volatile boolean fragSuccess;


	public Array1Object1(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		setOperator(array);
	}
/*	正解コード
def operate(array)
	Integer length = array.length()
	array[0] = array[length-1]
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
					if(array[0] == initArray[initArray.length - 1]){
						fragSuccess = true;
					}
				}
			}
		}.start();
	}

	@Override
	public String getMethodName() {
		return "operate";
	}

	@Override
	public String getArgumentString() {
		return "array";
	}

	private void rrwrapper(final Ruby ruby, StringWriter stdin, StringWriter stderr) {
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
