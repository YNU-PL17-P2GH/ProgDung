package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class NQueenObject extends MapProgObject {
	private int[][] direct = {{0, -1}, {1, -1},{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
	private int[][] showBoard;
	private int[][] queenPlace;
	private int queenCount;
	private Font font = new Font("Arial", Font.BOLD, 48);

	private char route[][] = {{'L','U','D','D','U','R','L','U'},
			{'R','L','U','L','D','D','U','R'},
			{'U','D','D','R','D','L','R','U'},
			{'U','L','U','U','R','D','U','L'},
			{'D','L','R','D','D','L','L','R'},
			{'R','D','D','L','L','R','L','D'},
			{'D','R','D','D','U','U','R','L'},
			{'L','U','D','L','D','R','R','U'}};

	public NQueenObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/queen.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setOperator(new NQueenOperator());
		width = 0;
		height = 0;
		for(int i = 0 ;i < showBoard.length * 2; i++) {
			for(int j = 0; j < showBoard[i / 2].length * 2; j++) {
				getMap().setObj(bx + j, by + j, this);
			}
		}
		for(int i = 0; i < showBoard.length; i++){
			for(int j = 0; j < showBoard[i].length; j++){
				showBoard[i][j] = 0;
			}
		}
	}
	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		((NQueenOperator)getOperator()).init();
	}

	@Override
	public String getMethodName() {
		return "nqueen";
	}

	@Override
	public String getArgumentString() {
		return "queen";
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.setFont(font);
		for(int i = 0; i < showBoard.length; i++){
			for(int j = 0; j < showBoard[i].length; j++){

				sinfo.g.setColor(Color.YELLOW);
				if(i == 3 && j == 3) {
					sinfo.g.setColor(Color.RED);
				}
				sinfo.g.drawString("" + route[i][j],
						map_x + j * MAP_CONST.MAP_BOX_SIZE * 2 + 18,
						map_y + i * MAP_CONST.MAP_BOX_SIZE * 2 + 50);
				if(showBoard[i][j] == 0) {
					sinfo.g.setColor(Color.BLACK);
					sinfo.g.fillRect(map_x + j * MAP_CONST.MAP_BOX_SIZE * 2,
							map_y + i * MAP_CONST.MAP_BOX_SIZE * 2,
							MAP_CONST.MAP_BOX_SIZE * 2, MAP_CONST.MAP_BOX_SIZE * 2);
				}
			}
		}
		for(int j = 0; j < queenCount; j++) {
			sinfo.g.fillRect(map_x + queenPlace[j][0] * MAP_CONST.MAP_BOX_SIZE * 2,
					map_y + queenPlace[j][1] * MAP_CONST.MAP_BOX_SIZE * 2,
					MAP_CONST.MAP_BOX_SIZE * 2, MAP_CONST.MAP_BOX_SIZE * 2);
			sinfo.g.drawImage(objImg, map_x + queenPlace[j][0] * MAP_CONST.MAP_BOX_SIZE*2,
					map_y + queenPlace[j][1] * MAP_CONST.MAP_BOX_SIZE*2, null);
		}


		drawFlag = true;
	}

	private int animeCount = 0, rate = 10;

	@Override
	public void update(ShareInfo sinfo) {
		if(animeCount % rate == 0) {
			updateBorad();
		}
		animeCount++;
		drawFlag = false;
	}
/*
def nqueen(queen)
	Array direct = [[-1, -1], [0, -1], [1, -1]]
	Array board = Array.new(8){Array.new(8, 0)}
	Array place = Array.new(8){Array.new(2)}
  Array answer = []
	nqueenSub(direct, board, place, 0, answer)
	Integer i = 0
	Integer j = 0	#ここで変更
	while i < answer[j].length()
		queen.putQueen(answer[j][i][0], answer[j][i][1])
		i = i + 1
	end
end

def nqueenSub(direct, board, place, n, answer)
	if place.length() == n then
	  Array a = Array.new(8){Array(2)}
	  Integer k = 0
	  while k < 8
	    a[k][0] = place[k][0]
	    a[k][1] = place[k][1]
	    k = k + 1
	  end
    answer << a
		return
	end
	Integer i = 0
	Integer j = 0
	Integer x = 0
	Integer y = n
	while j < 8
		i = 0
		while i < 3
			y = n
			x = j + direct[i][0]
			y = n + direct[i][1]
			while x >= 0 && x < board[0].length() && y >= 0
				if board[y][x] == 1 then
					break
				end
				x = x + direct[i][0]
				y = y + direct[i][1]
			end
			if y >= 0 && x >= 0 && x < board[0].length() then
				break
			end
			i = i + 1
		end
		if x < 0 || x >= board[0].length() || y < 0 then
			#print(j ," ", n, "\n")
			board[n][j] = 1
			place[n][0] = j
			place[n][1] = n
			#p board
			#sleep 2
			nqueenSub(direct, board, place, n + 1, answer)
			board[n][j] = 0
		end
		j = j + 1
	end
	return
end
 */

	private void updateBorad() {
		for(int i = 0; i < showBoard.length; i++){
			for(int j = 0; j < showBoard[i].length; j++){
				showBoard[i][j] = 0;
			}
		}
		for(int j = 0; j < queenCount; j++) {
			int x = queenPlace[j][0];
			int y = queenPlace[j][0];
			int nx, ny;
			for(int i = 0; i < 8; i++){
				nx = x + direct[i][0];
				ny = y + direct[i][1];
				while(nx >= 0 && nx < showBoard[0].length && ny >= 0 && ny < showBoard.length){
					showBoard[ny][nx] = 1;
					nx = nx + direct[i][0];
					ny = ny + direct[i][1];
					//System.out.println(nx+" "+ ny);
				}
			}
		}
	}
/*	private boolean checkSucsess() {
		for(int i = 0; i < showBoard.length; i++){
			for(int j = 0; j < showBoard[i].length; j++){
				if(showBoard[i][j] != 1) {
					return false;
				}
			}
		}

		return true;
	}*/
	@Override
	public boolean getCanPass() {
		return false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}


	public class NQueenOperator{
		private int[][] board;
		private final int WIDTH = 8;

		private boolean failFlag = false;


		public NQueenOperator(){
			showBoard = new int[WIDTH][WIDTH];
			init();
		}

		public void init() {
			board = new int[WIDTH][WIDTH];
			showBoard = new int[WIDTH][WIDTH];
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					board[i][j] = 0;
					showBoard[i][j] = 0;
				}
			}
			queenPlace = new int[WIDTH][2];
			queenCount = 0;
			failFlag = false;
		}

		public void boardClear(){
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[i].length; j++){
					board[i][j] = 0;
				}
			}
		}

		public void putQueen(int x, int y){
			int nx, ny;
			if(failFlag){
				return;
			}
			if(board[y][x] == 1){	//失敗
				failFlag = true;
				return;
			}
			for(int i = 0; i < 8; i++){
				nx = x + direct[i][0];
				ny = y + direct[i][1];
				while(nx >= 0 && nx < board[0].length && ny >= 0 && ny < board.length){
					if(board[ny][nx] == 1){	//失敗
						failFlag = true;
						return;
					}
					nx = nx + direct[i][0];
					ny = ny + direct[i][1];
					//System.out.println(nx+" "+ ny);
				}
			}
			board[y][x] = 1;
			queenPlace[queenCount][0] = x;
			queenPlace[queenCount][1] = y;
			queenCount++;
		}
	}
}
