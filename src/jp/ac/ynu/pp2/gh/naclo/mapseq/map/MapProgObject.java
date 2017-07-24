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
	private Object theOperator;

	public String sourceRuby;

	public MapProgObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		sourceRuby = "def " + getMethodName() + "(" + getArgumentString() + ")\n"
				+ "\t#この下の行にソースを入力\n"
				+ "\t\n"
				+ "end";
	}

	//プログラム実行
	public void launchRubyWithThread(final Ruby ruby, final StringWriter stdin, final StringWriter stderr, final Object... pArguments) {
		new Thread() {
			@Override
			public void run() {
				preRunRuby(ruby, pArguments);
				try{
					runRuby(ruby, stdin, stderr, pArguments);
				}catch (Exception e){
					stderr.append(e.getMessage());
				}finally{
						handler.stdoutUpdate();
						handler.stderrUpdate();
				}
				postRunRuby(ruby, pArguments);
			}
		}.start();
	}
	
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		
	}

	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		
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
		container.callMethod(ruby.getCurrentContext(), "rwrapper", pArguments);
		
	}
	
	public Object getOperator() {
		return theOperator;
	}

	protected void setOperator(Object theOperator) {
		this.theOperator = theOperator;
	}

	public int getTimeout() {
		return 10;
	}
	
	public Object[] getArgumentObjects() {
		return new Object[] {theOperator};
	}

	public abstract String getArgumentString();

	public String setTimeout(){
		String rwrapper = "require 'timeout'\n"
				+ "def rwrapper(" + getArgumentString() + ")\n"
				+ 	"begin\n"
				+ 		"Timeout.timeout(" + getTimeout() + "){\n"
				+ 			getMethodName() +"(" + getArgumentString() + ")\n"
				+ 		"}\n"
				+ 	"rescue => e\n"
				+ 		"STDERR.puts e.class\n"
				+ 		"STDERR.puts e.message\n"
				+ 		"STDERR.puts e.backtrace\n"
				+ 	"end\n"
				+ "end";
		return rwrapper;
	}

	public String getMethodName() {
		return "run";
	}
}
