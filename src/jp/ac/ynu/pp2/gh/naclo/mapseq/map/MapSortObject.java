package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;

public class MapSortObject extends MapProgObject{
	private int showArray[];
	private volatile LinkedList<Point> exhengList = new LinkedList<Point>();
	ArrayOperator myArrayOperator = new ArrayOperator();
	private BufferedImage objImg;
	public MapSortObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map){
		super(pHandler);

		myMap = map;
		box_x = bx;
		box_y = by;
		//objNameに従ってロード
		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/sort/sort.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

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
		runRuby(Ruby.newInstance());
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
						if(myMap.getBox(box_x - showArray[indexA], box_y + indexA * 2).getState() == MAP_CONST.STATE.BLOCK
								|| myMap.getBox(box_x - showArray[indexA], box_y + indexA * 2 + 1).getState() == MAP_CONST.STATE.BLOCK){
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
						if(myMap.getBox(box_x - showArray[indexB], box_y + indexB * 2).getState() == MAP_CONST.STATE.BLOCK
								|| myMap.getBox(box_x - showArray[indexB], box_y + indexB * 2 + 1).getState() == MAP_CONST.STATE.BLOCK){
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
	public void runRuby(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.runScriptlet(org.jruby.embed.PathType.RELATIVE, "FirstRpg/media/map/sort/sort.rb");
		System.out.println("バブルソート");
		container.callMethod(ruby.getCurrentContext(), "sort", myArrayOperator);
	}

	public class ArrayOperator {
		private int array[];
		private int initArray[];	//init用
		private int count = 0;		//交換回数

		public ArrayOperator(){
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
}
