package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public abstract class MapProgObject extends MapObject{	//プログラムで動作させる設置物クラス
	protected Object theOperator;

	public String sourceRuby;

	public MapProgObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
	}

	//プログラム実行
	public void runRuby(Ruby ruby, StringWriter stdin, StringWriter stderr) {
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

		String rwrapper = setTimeout("run", objName, 10);
		container.runScriptlet(rwrapper);
		container.callMethod(ruby.getCurrentContext(), "rwrapper", theOperator);
		handler.stdoutUpdate();
		handler.stderrUpdate();
	}

	public String setTimeout(String methodName, String argument, int time){
		String rwrapper = "require 'timeout'\n"
				+ "def rwrapper(" + argument + ")\n"
				+ 	"begin\n"
				+ 		"Timeout.timeout(" + time + "){\n"
				+ 			methodName +"(" + argument + ")\n"
				+ 		"}\n"
				+ 	"rescue => e\n"
				+ 		"STDERR.puts e.class\n"
				+ 		"STDERR.puts e.message\n"
				+ 		"STDERR.puts e.backtrace\n"
				+ 	"end\n"
				+ "end";
		return rwrapper;
	}
}
