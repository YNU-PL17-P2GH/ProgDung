package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;
import jp.ac.ynu.pp2.gh.progdung.map.handlers.Array1;

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
	public String getMethodName() {
		return "operate";
	}

	@Override
	public String getArgumentString() {
		return "array";
	}

	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		for (int i = 0; i < array.length; i++) {
			array[i] = initArray[i];
		}
	}
	@Override
	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		if(!handler.getCallback().getSaveData().getBoolean("Array1001")){
			if(array[0] == initArray[initArray.length - 1]){
				handler.getCallback().getSaveData().setTaken("Array1001");
				handler.getCallback().showHint("<html>何かが光っている！！</html>", true);
				((Array1)handler).getOrb().setVisible(true);
			}
		}
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

}
