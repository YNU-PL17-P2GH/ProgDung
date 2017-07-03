package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.KEY_STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapPlayer extends MapMoveObject{

	public MapPlayer(int bx, int by, String objName, MAP_CONST.DIRECTION direct, RpgMap map){
		super(bx, by, objName, direct, map);
	}
	@Override
	public void draw(ShareInfo sinfo, int player_x, int player_y) {
		//System.out.println(box_x + " "+box_y);
		int x = 10 * MAP_CONST.MAP_BOX_SIZE;
		int y = 10 * MAP_CONST.MAP_BOX_SIZE;
		if(player_x != point_x){
			if(player_x < 0){
				x = point_x;
			}else {
				x = point_x - player_x;
			}
		}
		if(player_y != point_y){
			if(player_y < 0){
				y = point_y;
			}else {
				y = point_y - player_y;
			}
		}
		sinfo.g.drawImage(objImg, x + 12,y + 12 , x + MAP_CONST.MAP_BOX_SIZE * 2 - 12, y + MAP_CONST.MAP_BOX_SIZE * 2 - 12,
				(animeCount / delay % imgNum) * 32, (direction.getVal() % directNum) * MAP_CONST.MAP_BOX_SIZE, (animeCount / delay % imgNum + 1) * MAP_CONST.MAP_BOX_SIZE, (direction.getVal() % directNum+ 1) * MAP_CONST.MAP_BOX_SIZE, null);

		animeCount++;
	}

	public void move(ShareInfo sinfo){
		//System.out.println(box_x + " " + box_y + " " + next_x + " " + next_y);
		//向き変更
		if(!this.isMoving()){
			int dx = 0, dy = 0;
			if(sinfo.getKeyRepeat(KEY_STATE.RIGHT)){
				dx++;
			}
			if(sinfo.getKeyRepeat(KEY_STATE.UP)){
				dy--;
			}
			if(sinfo.getKeyRepeat(KEY_STATE.DOWN)){
				dy++;
			}
			if(sinfo.getKeyRepeat(KEY_STATE.LEFT)){
				dx--;
			}

			if(dx == 0 && dy == 0){
				if(sinfo.getKeyPress(KEY_STATE.RIGHT)){
					direction = MAP_CONST.DIRECTION.RIGHT;
				}else if(sinfo.getKeyPress(KEY_STATE.UP)){
					direction = MAP_CONST.DIRECTION.UP;
				}else if(sinfo.getKeyPress(KEY_STATE.DOWN)){
					direction = MAP_CONST.DIRECTION.DOWN;
				}else if(sinfo.getKeyPress(KEY_STATE.LEFT)){
					direction = MAP_CONST.DIRECTION.LEFT;
				}
			}else if(dx == 1 && dy == 0){
				direction = MAP_CONST.DIRECTION.RIGHT;
			}else if(dx == 0 && dy == 1){
				direction = MAP_CONST.DIRECTION.DOWN;
			}else if(dx == 0 && dy == -1){
				direction = MAP_CONST.DIRECTION.UP;
			}else if(dx == -1 && dy == 0){
				direction = MAP_CONST.DIRECTION.LEFT;
			}else if(dx == 1 && dy == 1){
				if(direction == MAP_CONST.DIRECTION.RIGHT){
					direction = MAP_CONST.DIRECTION.DOWN;
				}else{
					direction = MAP_CONST.DIRECTION.RIGHT;
				}
			}else if(dx == 1 && dy == -1){
				if(direction == MAP_CONST.DIRECTION.UP){
					direction = MAP_CONST.DIRECTION.RIGHT;
				}else{
					direction = MAP_CONST.DIRECTION.UP;
				}
			}else if(dx == -1 && dy == 1){
				if(direction == MAP_CONST.DIRECTION.DOWN){
					direction = MAP_CONST.DIRECTION.LEFT;
				}else{
					direction = MAP_CONST.DIRECTION.DOWN;
				}
			}else if(dx == -1 && dy == -1){
				if(direction == MAP_CONST.DIRECTION.LEFT){
					direction = MAP_CONST.DIRECTION.UP;
				}else{
					direction = MAP_CONST.DIRECTION.LEFT;
				}
			}
		}

		//進もうとしている方向に進み続ける
		if(next_x - box_x == -1){
			point_x = point_x - 2;
		}else if(next_x - box_x == 1){
			point_x = point_x + 2;
		}else if(next_y - box_y == -1){
			point_y = point_y - 2;
		}else if(next_y - box_y == 1){
			point_y = point_y + 2;
		}

		//進み始める
		if(!this.isMoving()){
			if(direction == MAP_CONST.DIRECTION.RIGHT){
				if(myMap.getBox(box_x + 2, box_y).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x + 2, box_y + 1).getState() == MAP_CONST.STATE.BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.UP){
				if(myMap.getBox(box_x, box_y - 1).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x + 1, box_y - 1).getState() == MAP_CONST.STATE.BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.DOWN){
				if(myMap.getBox(box_x, box_y + 2).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x + 1, box_y + 2).getState() == MAP_CONST.STATE.BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.LEFT){
				if(myMap.getBox(box_x - 1, box_y).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x - 1, box_y + 1).getState() == MAP_CONST.STATE.BLOCK){
					return;
				}
			}
		}

		//マスの境に達したときにマスの位置更新
		if(!this.isMoving()){
			if(direction == MAP_CONST.DIRECTION.RIGHT){
				if(sinfo.getKeyRepeat(KEY_STATE.RIGHT)){
					next_x = box_x + 1;
					next_y = box_y;
					point_x = point_x + 2;
					if(myMap.getBox(next_x + 1, next_y).getState() == MAP_CONST.STATE.EMPTY
							&& myMap.getBox(next_x + 1, next_y + 1).getState() == MAP_CONST.STATE.EMPTY){
						myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.BLOCK);
						myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION.UP){
				if(sinfo.getKeyRepeat(KEY_STATE.UP)){
					next_x = box_x;
					next_y = box_y - 1;
					point_y = point_y - 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.STATE.EMPTY
							&& myMap.getBox(next_x + 1, next_y).getState() == MAP_CONST.STATE.EMPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.BLOCK);
						myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION.DOWN){
				if(sinfo.getKeyRepeat(KEY_STATE.DOWN)){
					next_x = box_x;
					next_y = box_y + 1;
					point_y = point_y + 2;
					if(myMap.getBox(next_x, next_y + 1).getState() == MAP_CONST.STATE.EMPTY
							&& myMap.getBox(next_x + 1, next_y + 1).getState() == MAP_CONST.STATE.EMPTY){
						myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.BLOCK);
						myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION.LEFT){
				if(sinfo.getKeyRepeat(KEY_STATE.LEFT)){
					next_x = box_x - 1;
					next_y = box_y;
					point_x = point_x - 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.STATE.EMPTY
							&& myMap.getBox(next_x, next_y + 1).getState() == MAP_CONST.STATE.EMPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.BLOCK);
						myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.BLOCK);
					}
				}
			}
		}

		if(point_x % MAP_CONST.MAP_BOX_SIZE == 0 && point_y % MAP_CONST.MAP_BOX_SIZE == 0){
			if(isMoving()){
				for(int i = 0; i < holdBox.length; i++){
					 if(!chackHold(box_x + holdBox[i][0], box_y + holdBox[i][1])){
						myMap.setBoxState(box_x + holdBox[i][0], box_y + holdBox[i][1], MAP_CONST.STATE.EMPTY);
					 }
				}

				box_x = next_x;
				box_y = next_y;
				next_x = box_x;
				next_y = box_y;
			}
		}
	}
	private int holdBox[][] = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
	private boolean chackHold(int x,int y){
		for(int i = 0; i < holdBox.length; i++){
			if(x == next_x + holdBox[i][0] && y ==next_y + holdBox[i][1]){
				return true;
			}
		}
		return false;
	}

	public boolean isMoving() {
		return (box_x != next_x || box_y != next_y);
	}
	public void setPosition(int x, int y) {
		box_x = x;
		box_y = y;
		point_x = MAP_CONST.MAP_BOX_SIZE * x;
		point_y = MAP_CONST.MAP_BOX_SIZE * y;
		next_x = box_x;
		next_y = box_y;
	}

	public void forcedMove(int dx, int dy){	//押し出しなど(スマートな書き方じゃないのでなんかいい方法あるならよろしく)
		//元居た部分をENPTYに
		myMap.setBoxState(box_x, box_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x, box_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x + 1, box_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x + 1, box_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.EMPTY);
		
		box_x = box_x + dx;
		box_y = box_y + dy;
		//足元の判定も移動
		if(myMap.getBox(box_x, box_y).getState() != MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x, box_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x, box_y + 1).getState() != MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x, box_y + 1, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x + 1, box_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x + 1, box_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x + 1, box_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x + 1, box_y + 1, MAP_CONST.STATE.BLOCK);


		point_x = point_x + MAP_CONST.MAP_BOX_SIZE * dx;
		point_y = point_y + MAP_CONST.MAP_BOX_SIZE * dy;

		next_x = next_x + dx;
		next_y = next_y + dy;
		if(myMap.getBox(next_x, next_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(next_x, next_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.BLOCK);
		
		if(myMap.getBox(next_x + 1, next_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.BLOCK);
		
		if(myMap.getBox(next_x + 1, next_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.BLOCK);
	}
	@Override
	public void update() {}
}

