package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;
import jp.ac.ynu.pp2.gh.progdung.map.handlers.Array1;

public class Array1Object2  extends MapProgObject {

	private int[] array	= new int[6];
	private int[] initArray	= {0, 100, 200, 300, 400, 500};
	private final int[] answerArray	= {9, 5, 3, 4, 2, 6};

	private volatile boolean fragSuccess;

	public Array1Object2(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
		setOperator(array);
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
		if(!handler.getCallback().getSaveData().getBoolean("Array1002")){
			if(checkSuccess()){
				handler.getCallback().getSaveData().setTaken("Array1002");
				for (MapObject tObject : ((Array1)handler).getBoxs()) {
					if (tObject.getObjName().equals("box")) {
						tObject.setVisible(false);
					}
				}
				handler.getCallback().showHint("<html>道をふさぐ箱が消え去った!!!</html>", true);
			}
		}
	}

	private boolean checkSuccess() {
		boolean b = true;
		for(int i = 0; i < array.length; i++){
			if(array[i] != answerArray[i]){
				b = false;
				break;
			}
		}
		return b;
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

