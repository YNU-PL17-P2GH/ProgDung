package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class PackjackObject extends MapProgObject {
	private String itemName[] = {"hana" ,"bin" ,"senrigan" ,"ukiwa" ,"tokei" ,"romazin" ,"tikuwa" ,"helmet"};
	private int[][] item = {{168, 496}, {145, 325}, {60, 347},  {124, 486}, {124, 446}, {105, 22}, {126, 110}, {184, 475}};
	private final int answer = 982;

	public PackjackObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		setOperator(new PackjackOparator());
	}

	@Override
	public String getMethodName() {
		return "knapsack";
	}

	@Override
	public String getArgumentString() {
		return "packjack";
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(ShareInfo sinfo) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public int [] getItemData(String name) {
		for(int i = 0 ; i < itemName.length; i++) {
			if(name.equals(itemName[i])) {
				return item[i];
			}
		}
		return null;
	}

	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		((PackjackOparator)getOperator()).init();
	}

	@Override
	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		if(!this.handler.getCallback().getSaveData().getBoolean("Packjack001")) {
			if(((PackjackOparator)getOperator()).checkResult()) {
				handler.getCallback().showHint("<html>扉の開く音がした!!!</html>", true);
				handler.getCallback().getSaveData().setTaken("Packjack001");
			}else {
				handler.getCallback().showHint("<html>なにも起こらなかった...</html>", true);
			}
		}
	}

/*


*/
	public class PackjackOparator {
		//1,4,5,6?
		private int capacity = 300;

		private boolean[] selectItem;
		private int myCapacity = 0;

		private boolean failFlag = false;		//失敗したとき

		public PackjackOparator() {
			selectItem = new boolean[item.length];
			for(int i = 0; i < selectItem.length; i++){
				selectItem[i] = false;
			}
		}

		public boolean checkResult() {
			int v = 0;
			for(int i = 0; i < selectItem.length; i++){
				if(selectItem[i]){
					v = v + item[i][1];
				}
			}

			return answer == v;
		}

		public void init() {
			failFlag = false;
			for(int i = 0; i < selectItem.length; i++){
				selectItem[i] = false;
			}
		}


		public int getCapacity(){
			return capacity;
		}

		public int getItemNum(){
			return item.length;
		}

		public void pickItem(String name){
			System.out.println(name);
			int i = 0;
			for(i = 0 ; i < itemName.length; i++) {
				if(name.equals(itemName[i])) {
					break;
				}
			}
			if(failFlag){
				return;
			}
			if(myCapacity > capacity){	//失敗
				failFlag = true;
				return;
			}
			if(selectItem[i]){	//失敗
				failFlag = true;
				return;
			}
			myCapacity +=  item[i][0];
			selectItem[i] = true;
		}

	}
}

/*
def knapsack(packjack)
  Integer i = 0
  Integer itemNum = packjack.getItemNum()
  Array itemName = ["hana" ,"bin" ,"senrigan" ,"ukiwa" ,"tokei" ,"romazin" ,"tikuwa" ,"helmet"]
  Array items = [[168, 496], [145, 325], [60, 347],  [124, 486], [124, 446], [105, 22], [126, 110], [184, 475]]
  Integer capacity = packjack.getCapacity()
  Array dp = Array.new(itemNum + 1){ Array.new(capacity + 1) }
  i = 0
  while i < capacity + 1
    dp[0][i] = 0
    i = i + 1
  end
  i = 0
  Integer j = 0
  while i < itemNum
    j = 0
    while j < capacity + 1
      if items[i][0] <= j then
        if items[i][1] + dp[i][j - items[i][0]] > dp[i][j] then
          dp[i + 1][j] = items[i][1] + dp[i][j - items[i][0]]
        else
          dp[i + 1][j] = dp[i][j]
        end
      else
        dp[i + 1][j] = dp[i][j]
      end
      j = j + 1
    end
    i = i + 1
  end

  i = itemNum
  j = capacity
  while i > 0
    if dp[i][j] != dp[i - 1][j] then
      packjack.pickItem(itemName[i - 1])
      while dp[i][j] == dp[i][j - 1]
        j = j - 1
      end
      j = j + 1 - items[i - 1][0]
    end
    i = i - 1
  end
end
 */
