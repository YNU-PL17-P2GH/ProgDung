package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Color;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.KEY_STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class Knight2Object extends MapProgObject {

	private int showX, showY;
	private int[][] showBoard;
	private boolean runRuby;
	private boolean finishRuby;

	public Knight2Object(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/" + pObjName + ".png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		sourceRuby = "def tour(horse)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";
		setOperator(new Knight2Operator());
		width = 24;
		height = 24;
		for(int i = 0 ;i < showBoard.length * 3; i++) {
			for(int j = 0; j < showBoard[i / 3].length * 3; j++) {
				getMap().setObj(bx + j, by + j, this);
			}
		}
	}
	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		runRuby = true;
		((Knight2Operator)getOperator()).init();
	}

	@Override
	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		finishRuby = true;
	}

	@Override
	public String getMethodName() {
		return "tour";
	}

	@Override
	public String getArgumentString() {
		return "horse";
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		if(updateFlag) {
			for(int i = 0; i < showBoard.length; i++){
				for(int j = 0; j < showBoard[i].length; j++){
					if(showBoard[i][j] == 1) {
						sinfo.g.setColor(Color.BLUE);
						sinfo.g.fillRect(map_x + j * 3 * MAP_CONST.MAP_BOX_SIZE,
								map_y + i * 3 * MAP_CONST.MAP_BOX_SIZE,
								3 * MAP_CONST.MAP_BOX_SIZE, 3 * MAP_CONST.MAP_BOX_SIZE);
					}
				}
			}
		}
		if(checkFlag) {
			Knight2Operator ko = (Knight2Operator)getOperator();
			int cX = showX + ko.direct[chechDirect][0];
			int cY = showY + ko.direct[chechDirect][1];
			if( cX < 0 || cX >= showBoard[0].length){
				sinfo.g.setColor(Color.RED);
			}else if( cY < 0 || cY >= showBoard.length){
				sinfo.g.setColor(Color.RED);
			}else if(showBoard[cY][cX] != 0){
				sinfo.g.setColor(Color.RED);
			}else {
				sinfo.g.setColor(Color.YELLOW);
			}
			sinfo.g.fillRoundRect(map_x + cX * 3* MAP_CONST.MAP_BOX_SIZE,
					map_y + cY * 3 * MAP_CONST.MAP_BOX_SIZE,
					3 * MAP_CONST.MAP_BOX_SIZE, 3 * MAP_CONST.MAP_BOX_SIZE,
					3 * MAP_CONST.MAP_BOX_SIZE, 3 * MAP_CONST.MAP_BOX_SIZE);
		}
		sinfo.g.drawImage(objImg, map_x + showX * MAP_CONST.MAP_BOX_SIZE * 3 + 16,
				map_y + showY * MAP_CONST.MAP_BOX_SIZE * 3 + 16,
				64, 64, null);
		drawFlag = true;
	}

	private int animeCount = 0, rate = 10;
	private int index = 0;
	private boolean updateFlag = false;
	private boolean checkFlag = false;
	private int chechDirect;

	@Override
	public void update(ShareInfo sinfo) {
		if(updateFlag && !sinfo.getKeyRepeat(KEY_STATE.C)) {
			if(sinfo.getKeyRepeat(KEY_STATE.Z)) {
				rate = 30;
			}else {
				rate = 5;
			}
			if(animeCount % rate == 0) {
				if(((Knight2Operator)getOperator()).answerProcess.size() > index) {
					Knight2Operator ko = (Knight2Operator)getOperator();
					checkFlag = false;
					int d = ko.answerProcess.get(index);
					if(d < 8) {
						showX = showX + ko.direct[d][0];
						showY = showY + ko.direct[d][1];
						showBoard[showY][showX] = 1;
					}else if(d < 16){
						showBoard[showY][showX] = 0;
						showX = showX - ko.direct[d - 8][0];
						showY = showY - ko.direct[d - 8][1];
					}else{
						checkFlag = true;
						chechDirect = d - 16;
					}
					index++;
				}
				if(finishRuby && ((Knight2Operator)getOperator()).answerProcess.size() == index) {
					updateFlag = false;
					runRuby = false;
					getMap().getMyPlayer().setCanMave(true);
					getMap().getMyPlayer().setPosition(25, 12);
					if(!handler.getCallback().getSaveData().getBoolean("Knight2001")){
						if(checkSucsess()) {
							handler.getCallback().showHint("<html>扉の開く音がした!!</html>", true);
							handler.getCallback().getSaveData().setTaken("Knight2001");
						}else {
							handler.getCallback().showHint("<html>何も起こらなかった...</html>", true);
						}
					}

				}
			}
			animeCount++;
		}
		drawFlag = false;
	}

/*	greedy
def tour(horse)
  tourSub(horse, 0)
end

def tourSub(knight, n)
  if n ==63 then
    return true
  end
  Array nextJumps = getNextJumps(knight)
  Integer i = 0
  while i < nextJumps.length
    if knight.canMove(nextJumps[i]) then
      knight.move(nextJumps[i])
      if tourSub(knight, n + 1) then
        return true
      else
        knight.backtrack()
      end
    end
    i = i + 1
  end
  return false
end

def getNextJumps(knight)
  Array canMoves = Array.new(8)
  Array index = [0, 1, 2, 3, 4, 5, 6, 7]
  Integer i = 0
  Integer j = 0
  Integer count = 0
  while i < canMoves.length
    if knight.canMove(i) then
      knight.move(i)
      canMoves[i] = 0;
      j = 0
      count = count + 1
      while j < canMoves.length
        if knight.canMove(j) then
          canMoves[i] = canMoves[i] + 1
        end
        j = j + 1
      end
      knight.backtrack()
    else
      canMoves[i] = 9;
    end
    i = i + 1
  end
  i = 0
  j = 0
  Integer c = 0
  Integer min = 0
  while i < canMoves.length
    j = i
    min = i
    while j < canMoves.length
      if canMoves[min] > canMoves[j] then
        min = j
      end
      j = j + 1
    end
    c = canMoves[min]
    canMoves[min] = canMoves[i]
    canMoves[i] = c
    c = index[min]
    index[min] = index[i]
    index[i] = c
    i = i + 1
  end
  Array nextJump = Array.new(count)
  i = 0
  while i < nextJump.length
    nextJump[i] = index[i]
    i = i + 1
  end
  return nextJump
end
 */

	private boolean checkSucsess() {
		for(int i = 0; i < showBoard.length; i++){
			for(int j = 0; j < showBoard[i].length; j++){
				if(showBoard[i][j] != 1) {
					return false;
				}
			}
		}

		return true;
	}
	@Override
	public boolean getCanPass() {
		return false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getRunRuby() {
		return runRuby;
	}

	public void startUpdate() {
		runRuby = false;
		updateFlag = true;
	}
	public boolean getUpdateFlag() {
		return updateFlag;
	}
	public int getHorseX() {
		return box_x + showX * 3;
	}
	public int getHorseY() {
		return box_y + showY * 3;
	}

	public class Knight2Operator{
		private int[][] direct = {{1, 2}, {2, 1},{2, -1}, {1, -2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
		private int[][] board;
		private int x, y;

		private boolean failFlag = false;

		Vector<Integer> answerProcess;

		private Stack<Integer> moveProcess;
		public Knight2Operator() {
			init();
		}

		void init() {
			x = 0;
			y = 0;
			board = new int[8][8];
			showX = x;
			showY = y;
			showBoard = new int[8][8];
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					board[i][j] = 0;
					showBoard[i][j] = 0;
				}
			}
			board[y][x] = 1;
			showBoard[y][x] = 1;
			answerProcess = new Vector<Integer>();
			moveProcess = new Stack<Integer>();
			failFlag = false;
		}

		public void move(int d){
			//System.out.println(x + " " + y + " "+ d);
			if(failFlag){
				return;
			}
			if( x + direct[d][0] < 0 || x + direct[d][0] >= board[0].length){
				failFlag = true;
				return;
			}
			if( y + direct[d][1] < 0 || y + direct[d][1] >= board.length){
				failFlag = true;
				return;
			}
			if(board[y + direct[d][1]][x + direct[d][0]] != 0){
				failFlag = true;
				return;
			}

			board[y + direct[d][1]][x + direct[d][0]] = 1;
			y = y + direct[d][1];
			x = x + direct[d][0];
			//System.out.println(x + " " + y + " "+ d);
			//printBoard();
			answerProcess.add(d);
			moveProcess.push(d);
			return;
		}

		public boolean canMove(int d){
			int ny = y + direct[d][1];
			int nx = x + direct[d][0];
			answerProcess.add(d + 16);
			if( x + direct[d][0] < 0 || x + direct[d][0] >= board[0].length){
				return false;
			}
			if( y + direct[d][1] < 0 || y + direct[d][1] >= board.length){
				return false;
			}
			if(board[ny][nx] == 1){
				return false;
			}
			return true;
		}

		public void backtrack(){
			//System.out.println(x +" "+ y);
			board[y][x] = 0;
			int lastd = moveProcess.pop();
			y = y - direct[lastd][1];
			x = x - direct[lastd][0];
			//printBoard();
			answerProcess.add(lastd + 8);
		}
	}
}

