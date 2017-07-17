package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.STATE;
import jp.ac.ynu.pp2.gh.progdung.map.progobj.IfmazeObject.IfmazeOperator;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public class Sort1Object extends MapProgObject{
	private int showArray[];
	private volatile LinkedList<Point> exhengList = new LinkedList<Point>();
	//ArrayOperator myArrayOperator = new ArrayOperator();

	public Sort1Object(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map){
		super(pHandler, bx, by, objName, map);

		//objNameに従ってロード
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/sort1/sort.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}
		
		theOperator = new Array1Operator();

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
		
		sourceRuby = "def sort(array)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";
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
	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
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
						hitIndex = indexA;
						while(handler.hitChecktoPlayer(this)){
							myMap.getMyPlayer().forcedMove(-1, 0);
						}
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
						//プレイヤーが存在するときそのマスはMAP_CONST.STATE.BLOCK
						hitIndex = indexB;
						while(handler.hitChecktoPlayer(this)){
							myMap.getMyPlayer().forcedMove(-1, 0);
						}
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
		/*
		for(int i = 0 ; i < showArray.length; i++){
			System.out.print(showArray[i] + " ");
		}
		System.out.println();
		*/
		
		animeCount++;
	}
	@Override
	public void runRuby(final Ruby ruby) {
		new Thread() {
			@Override
			public void run() {
				rrwrapper(ruby);
			}
		}.start();	
	}
	
	private void rrwrapper(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);

		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		container.callMethod(ruby.getCurrentContext(), "sort", theOperator);
	}

	public class Array1Operator {
		private int array[];
		private int initArray[];	//init用
		private int count = 0;		//交換回数

		public Array1Operator(){
			showArray = new int[10];
			array = new int[10];
			initArray = new int[10];
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
				initArray[i] = array[i];
				showArray [i] = array[i];
			}
			count = 0;
		}

		public void initArray(){
			for(int i = 0; i < array.length; i++){
				array[i] = initArray[i];
			}
			count = 0;
		}

		public boolean compare(int i, int j){	//比較
			if(array[i] >= array[j]){
				return true;
			}else{
				return false;
			}
		}

		public void exchange(int i, int j){		//交換
			exhengList.add(new Point(i, j));
			int c = array[i];
			array[i] = array[j];
			array[j] = c;
			count++;
		}

		public int length(){
			return array.length;
		}
	}
	int hitIndex = -1;
	@Override
	public boolean hitCheck(MapObject obj) {
		//足元で判定
		//一つ増えたときの判定が必要である
		if(this.box_x - showArray[hitIndex] - 1  < (obj.getBox_x() + 1) && (this.box_x) > obj.getBox_x()){
			if((this.box_y + hitIndex * 2) < (obj.getBox_y() + 2) && (this.box_y + 2 + hitIndex * 2) > (obj.getBox_y() + 1)){
				return !obj.getCanPass();
			}
		}

		return false;
	}
}
