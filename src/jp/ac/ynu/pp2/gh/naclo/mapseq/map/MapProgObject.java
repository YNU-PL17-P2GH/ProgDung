package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.InputStream;
import java.io.StringReader;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public abstract class MapProgObject extends MapObject{	//プログラムで動作させる設置物クラス
	protected Object theOperator;

	public MapProgObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
	}

	//プログラム実行
	public void runRuby(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);
		
		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(handler.sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		container.callMethod(ruby.getCurrentContext(), getObjName(), theOperator);
	}
}
