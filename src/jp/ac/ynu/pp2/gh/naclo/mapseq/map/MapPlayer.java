package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import jp.ac.ynu.pp2.gh.naclo.mapseq.KEY_STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.STATE;

public class MapPlayer extends MapMoveObject{

	public MapPlayer(MapHandlerBase pHandler,int bx, int by, String objName, MAP_CONST.DIRECTION direct, RpgMap map){
		super(pHandler, bx, by, objName, direct, map);
		width = 2;
		height = 2;
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
		if(Math.abs((double)point_x / (double)MAP_CONST.MAP_BOX_SIZE  - (double)box_x) > 1.0
				|| Math.abs((double)point_y/ (double)MAP_CONST.MAP_BOX_SIZE  - (double)box_y) > 1.0){	//原因不明位置ずれ補正
			point_x = box_x * MAP_CONST.MAP_BOX_SIZE;
			point_y = box_y * MAP_CONST.MAP_BOX_SIZE;
		}

		if (getPlayerFoot() == STATE.BLOCK) return;
		if(!this.isStartMoving()){
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

		System.out.printf("[POS]%d / %d %d / %d\n", box_x, box_y, point_x,point_y);

		// 壁
		if(!this.isStartMoving()){
			if(direction == MAP_CONST.DIRECTION.RIGHT){
				if(myMap.getBox(box_x + 2, box_y + 1).getState() == MAP_CONST.STATE.BLOCK){
					System.err.println("RIGHT BLOCK");
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.UP){
				if(myMap.getBox(box_x, box_y).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x + 1, box_y).getState() == MAP_CONST.STATE.BLOCK){
					System.err.println("UP BLOCK");
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.DOWN){
				if(myMap.getBox(box_x, box_y + 2).getState() == MAP_CONST.STATE.BLOCK
						|| myMap.getBox(box_x + 1, box_y + 2).getState() == MAP_CONST.STATE.BLOCK){
					System.err.println("DOWN BLOCK");
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION.LEFT){
				if(myMap.getBox(box_x - 1, box_y + 1).getState() == MAP_CONST.STATE.BLOCK){
					System.err.println("LEFT BLOCK");
					return;
				}
			}
		}
		//theObjとの当たり判定
		for(MapObject tObj : handler.theObj){
			if(handler.hitChecktoObj(tObj)){
				handler.onPlayerHitTo(tObj);
				moveCancel();
			}
		}
		
	
		//マスの境に達したときにマスの位置更新
		if(!this.isStartMoving()){
			if(direction == MAP_CONST.DIRECTION.RIGHT){
				if(sinfo.getKeyRepeat(KEY_STATE.RIGHT)){
					next_x = box_x + 1;
					next_y = box_y;
					point_x = point_x + 2;
				}
			}else if(direction == MAP_CONST.DIRECTION.UP){
				if(sinfo.getKeyRepeat(KEY_STATE.UP)){
					next_x = box_x;
					next_y = box_y - 1;
					point_y = point_y - 2;
				}
			}else if(direction == MAP_CONST.DIRECTION.DOWN){
				if(sinfo.getKeyRepeat(KEY_STATE.DOWN)){
					next_x = box_x;
					next_y = box_y + 1;
					point_y = point_y + 2;
				}
			}else if(direction == MAP_CONST.DIRECTION.LEFT){
				if(sinfo.getKeyRepeat(KEY_STATE.LEFT)){
					next_x = box_x - 1;
					next_y = box_y;
					point_x = point_x - 2;
				}
			}
		}

		if(point_x % MAP_CONST.MAP_BOX_SIZE == 0 && point_y % MAP_CONST.MAP_BOX_SIZE == 0){
			if(isStartMoving()){
				/*
				for(int i = 0; i < holdBox.length; i++){
					 if(!checkHold(box_x + holdBox[i][0], box_y + holdBox[i][1])){
						myMap.setBoxState(box_x + holdBox[i][0], box_y + holdBox[i][1], MAP_CONST.STATE.EMPTY);
					 }
				}
				*/
				box_x = next_x;
				box_y = next_y;
				next_x = box_x;
				next_y = box_y;
			}
		}
	}
	private int holdBox[][] = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};

	private boolean checkHold(int x,int y){
		for(int i = 0; i < holdBox.length; i++){
			if(x == next_x + holdBox[i][0] && y ==next_y + holdBox[i][1]){
				return true;
			}
		}
		return false;
	}

	public boolean isStartMoving() {
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

	public void forcedMove(int dx, int dy){	//押し出しなど
		System.err.println("FORCE");
		/*
		//元居た部分をENPTYに
		myMap.setBoxState(box_x, box_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x, box_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x + 1, box_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(box_x + 1, box_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.EMPTY);
		myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.EMPTY);
		*/

		box_x = box_x + dx;
		box_y = box_y + dy;

		/*
		//足元の判定も移動
		if(myMap.getBox(box_x, box_y).getState() != MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x, box_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x, box_y + 1).getState() != MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x, box_y + 1, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x + 1, box_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x + 1, box_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(box_x + 1, box_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(box_x + 1, box_y + 1, MAP_CONST.STATE.BLOCK);
		*/

		if(dx != 0){	//強制移動があったらマスに座標を合わせる
			point_x = MAP_CONST.MAP_BOX_SIZE * box_x;
		}else{
			point_x = point_x + MAP_CONST.MAP_BOX_SIZE * dx;
		}
		if(dy != 0){	//強制移動があったらマスに座標を合わせる
			point_y = MAP_CONST.MAP_BOX_SIZE * box_y;
		}else{
			point_y = point_y + MAP_CONST.MAP_BOX_SIZE * dy;
		}


		next_x = box_x;
		next_y = box_y;

		/*
		if(myMap.getBox(next_x, next_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x, next_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(next_x, next_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x, next_y + 1, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(next_x + 1, next_y).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x + 1, next_y, MAP_CONST.STATE.BLOCK);

		if(myMap.getBox(next_x + 1, next_y + 1).getState() !=MAP_CONST.STATE.NEXT)	//押し出しによる移動をさせるため
			myMap.setBoxState(next_x + 1, next_y + 1, MAP_CONST.STATE.BLOCK);
		*/
	}
	@Override
	public void update(ShareInfo sinfo) {
		handler.playerUpdate();
		move(sinfo);
		if (getPlayerFoot() == STATE.NEXT) {
			handler.moveMap(getNextBoxOnFoot());
		}
	}

	public MAP_CONST.STATE getPlayerFoot(){
		int pX = getBox_x();
		int pY = getBox_y();

		if(myMap.getBox(pX, pY + 1).getState() != MAP_CONST.STATE.EMPTY){
			return myMap.getBox(pX, pY + 1).getState();
		}
		if(myMap.getBox(pX + 1, pY + 1).getState() != MAP_CONST.STATE.EMPTY){
			return myMap.getBox(pX + 1, pY + 1).getState();
		}
		return MAP_CONST.STATE.EMPTY;
	}

	public NextMapBox getNextBoxOnFoot() {
		int pX = getBox_x();
		int pY = getBox_y();

		MapBox pBox;
		for (int i = 0; i < 2; i++) {
			pBox = myMap.getBox(pX + i, pY + 1);
			if (pBox.getState() == STATE.NEXT) {
				return (NextMapBox) pBox;
			}
		}

		return null;
	}

	public void moveCancel() {
		point_x = MAP_CONST.MAP_BOX_SIZE * box_x;
		point_y = MAP_CONST.MAP_BOX_SIZE * box_y;
		next_x = box_x;
		next_y = box_y;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		//足元の移動先で判定
		if(this.next_x  < (obj.box_x + obj.width) && (this.next_x + this.width ) > obj.box_x){
				if(this.next_y + 1 < (obj.box_y + obj.height) && (this.next_y + 2) > obj.box_y){
				return !obj.getCanPass();
			}
		}
		return false;
	}
	public void setDirection(MAP_CONST.DIRECTION d) {
		direction = d;
	}

}

