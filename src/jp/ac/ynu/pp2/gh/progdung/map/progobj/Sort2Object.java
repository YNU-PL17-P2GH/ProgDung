package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class Sort2Object extends MapProgObject{
	private int showArray[];
	private volatile LinkedList<Point> exhengList = new LinkedList<Point>();
	//ArrayOperator myArrayOperator = new ArrayOperator();

	public Sort2Object(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map){
		super(pHandler, bx, by, objName, map);

		//objNameに従ってロード
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("FirstRpg/media/map/obj/sort.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

		setOperator(new Sort2Operator());

		//設置されているマスにオブジェクトを登録
		for(int i = 0; i < showArray.length * 2 ; i++){
			for(int j = 0 ;j < 10; j++){
				myMap.setObj(box_x - j, box_y + i, this);
			}
		}

		//箱の初期位置にあたり判定
		for(int i = 0; i < showArray.length * 2 ; i++){
			for(int j = 0 ;j < showArray[i / 2]; j++){
				myMap.setBoxState(box_x - j, box_y + i, MAP_CONST.STATE.BLOCK);
			}
		}

		//仮で実行
//		runRuby(Ruby.newInstance());
	}
	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		for(int i = 0; i < showArray.length ; i++){
			for(int j = showArray[i]; j > 0 ; j--){
				//map_x は設置物の最も右上のマスの左上の座標だから
				sinfo.g.drawImage(objImg, map_x - (j - 1)  * MAP_CONST.MAP_BOX_SIZE, map_y + i * MAP_CONST.MAP_BOX_SIZE *  2 , null);
			}
		}
		drawFlag = true;
	}
	int animeCount = 0;
	boolean swaping = false;
	int indexA, indexB;
	int a, b;
	private boolean successFlag;

	@Override
	public void update(ShareInfo sinfo) {
		if(drawFlag){
			if(!(((Sort2Operator)getOperator()).getFailFlag() && exhengList.size() == 0)) {
				if(!swaping){
					if(exhengList.size() > 0){
						//交換する要素のそれぞれのインデックスと値を取得
						Point p = exhengList.poll();
						indexA = (int)p.getX();
						indexB = (int)p.getY();
						a = showArray[indexA];
						b = showArray[indexB];
						swaping = true;
					}
				}else{
					if(animeCount % 30 == 0){	//動作の間隔調整
						if(showArray[indexA] > b){
							//穴の状態は書き換えない
							if(myMap.getBox(box_x - showArray[indexA] + 1, box_y + indexA * 2).getState() != MAP_CONST.STATE.NEXT){
								myMap.setBoxState(box_x - showArray[indexA] + 1, box_y + indexA * 2, MAP_CONST.STATE.EMPTY);
								myMap.setBoxState(box_x - showArray[indexA] + 1, box_y + indexA * 2 + 1, MAP_CONST.STATE.EMPTY);
							}
							showArray[indexA]--;
						}else if(showArray[indexA] < b){
							//穴の状態は書き換えない
							if(myMap.getBox(box_x - showArray[indexA], box_y + indexA * 2).getState() != MAP_CONST.STATE.NEXT){
								myMap.setBoxState(box_x - showArray[indexA], box_y + indexA * 2, MAP_CONST.STATE.BLOCK);
								myMap.setBoxState(box_x - showArray[indexA], box_y + indexA * 2 + 1, MAP_CONST.STATE.BLOCK);
							}
							showArray[indexA]++;
						}
						if(showArray[indexB] > a){
							//穴の状態は書き換えない
							if(myMap.getBox(box_x - showArray[indexB] + 1, box_y + indexB * 2).getState() != MAP_CONST.STATE.NEXT){
								myMap.setBoxState(box_x - showArray[indexB] + 1, box_y + indexB * 2, MAP_CONST.STATE.EMPTY);
								myMap.setBoxState(box_x - showArray[indexB] + 1, box_y + indexB * 2 + 1, MAP_CONST.STATE.EMPTY);
							}
							showArray[indexB]--;
						}else if(showArray[indexB] < a){
							//穴の状態は書き換えない
							if(myMap.getBox(box_x - showArray[indexB], box_y + indexB * 2).getState() != MAP_CONST.STATE.NEXT){
								myMap.setBoxState(box_x - showArray[indexB], box_y + indexB * 2, MAP_CONST.STATE.BLOCK);
								myMap.setBoxState(box_x - showArray[indexB], box_y + indexB * 2 + 1, MAP_CONST.STATE.BLOCK);
							}
							showArray[indexB]++;
						}
						if(showArray[indexA] == b && showArray[indexB] == a){
							swaping = false;
						}
					}
				}
			}else {
				for (int i = 0; i < showArray.length; i++) {
					if(showArray[i] <= 12) {
						showArray[i]++;
					}
				}
			}
			//あたり判定
			while(handler.hitChecktoPlayer(this)){
				myMap.getMyPlayer().forcedMove(-1, 0);
			}
		/*
		for(int i = 0 ; i < showArray.length; i++){
			System.out.print(showArray[i] + " ");
		}
		System.out.println();
		*/
		}
		drawFlag = false;
		animeCount++;
	}
/* 正解コード
def sort(array)
	Integer i = 0
	Integer j = 0
	Integer min = 0
	while i < array.length()
		j = i
		min = i
		while j < array.length()
			if array.compare(min , j) then
				min = j
			end
			j = j + 1
		end
		array.exchange(i ,min)
		i = i + 1
	end
end
 */

	@Override
	public String getMethodName() {
		return "sort";
	}

	@Override
	public int getTimeout() {
		return 10;
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
		container.callMethod(ruby.getCurrentContext(), "sort", getOperator());
	}

	public class Sort2Operator {
		private int array[];
		private int count = 0;		//交換回数
		private boolean failFlag = false;

		public Sort2Operator(){
			showArray = new int[10];
			array = new int[10];
			for(int i = 0; i < array.length; i++){
				array[i] = i ;
			}

			//ランダムに
			Random r = new Random();
			for(int i = 0; i < 100; i++){
				int x = r.nextInt(array.length);
				int y = r.nextInt(array.length);
				int c = array[x];
				array[x] = array[y];
				array[y] = c;
			}

			for(int i = 0; i < array.length; i++){
				showArray [i] = array[i];
			}
			count = 0;
		}

		public boolean getFailFlag() {
			return failFlag;
		}

		public boolean compare(int i, int j){	//比較
			if(array[i] >= array[j]){
				return true;
			}else{
				return false;
			}
		}

		public void exchange(int i, int j){		//交換
			if(failFlag) {
				return;
			}
			exhengList.add(new Point(i, j));
			int c = array[i];
			array[i] = array[j];
			array[j] = c;
			count++;
			if(count > 12) {
				failFlag = true;
			}
		}

		public int length(){
			return array.length;
		}

		public void cleared() {
			//箱の初期位置のあたり判定除去
			for(int i = 0; i < showArray.length * 2 ; i++){
				for(int j = 0 ;j < showArray[i / 2]; j++){
					myMap.setBoxState(box_x - j, box_y + i, MAP_CONST.STATE.EMPTY);
				}
			}
			for(int i = 0; i < array.length; i++){
				array[i] = i ;
				showArray[i] = i;
			}
			//箱の初期位置のあたり判定除去
			for(int i = 0; i < showArray.length * 2 ; i++){
				for(int j = 0 ;j < showArray[i / 2]; j++){
					myMap.setBoxState(box_x - j, box_y + i, MAP_CONST.STATE.BLOCK);
				}
			}
		}
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		//足元で判定
		int pY = obj.getBox_y() + 1;
		if(pY < box_y || pY >= box_y + showArray.length * 2){
			return false;
		}
		int hitIndex = (pY - box_y) / 2;
		if(this.box_x - showArray[hitIndex]  < (obj.getBox_x() + 1) && (this.box_x) > obj.getBox_x()){
			//if((this.box_y + hitIndex * 2) < (obj.getBox_y() + 2) && (this.box_y + 2 + hitIndex * 2) > (obj.getBox_y() + 1)){
				return !obj.getCanPass();
			//}
		}

		return false;
	}
	public void setSuccessFlag(boolean b) {
		successFlag = b;
		if(successFlag){
			((Sort2Operator)getOperator()).cleared();
		}
	}
}

