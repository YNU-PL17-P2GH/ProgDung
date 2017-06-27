package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import javax.swing.JOptionPane;

import javafx.scene.control.DialogPane;
import jp.ac.ynu.pp2.gh.naclo.mapseq.KEY_STATE;
import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapPlayer extends MapMoveObject{

	public MapPlayer(int bx, int by, String objName, int direct, RpgMap map){
		super(bx, by, objName, direct, map);
	}
	@Override
	public void draw(ShareInfo sinfo, int player_x, int player_y) {
		int x = 5 * MAP_CONST.MAP_BOX_SIZE;
		int y = 5 * MAP_CONST.MAP_BOX_SIZE;
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
		sinfo.g.drawImage(objImg[(animeCount / 10) % imgNum + direction * imgNum], x, y, null);
		animeCount++;
	}

	public void move(ShareInfo sinfo){
		//System.out.println(box_x + " " + box_y + " " + next_x + " " + next_y);
		if(next_x == box_x && next_y == box_y){
			if(sinfo.getKeyPress(KEY_STATE.RIGHT) || sinfo.getKeyRepeat(KEY_STATE.RIGHT)){
				direction = MAP_CONST.DIRECTION_RIGHT;
			}else if(sinfo.getKeyPress(KEY_STATE.UP) || sinfo.getKeyRepeat(KEY_STATE.UP)){
				direction = MAP_CONST.DIRECTION_UP;
			}else if(sinfo.getKeyPress(KEY_STATE.DOWN) || sinfo.getKeyRepeat(KEY_STATE.DOWN)){
				direction = MAP_CONST.DIRECTION_DOUN;
			}else if(sinfo.getKeyPress(KEY_STATE.LEFT) || sinfo.getKeyRepeat(KEY_STATE.LEFT)){
				direction = MAP_CONST.DIRECTION_LEFY;
			}
		}

		if(next_x - box_x == -1){
			point_x = point_x - 2;
		}else if(next_x - box_x == 1){
			point_x = point_x + 2;
		}else if(next_y - box_y == -1){
			point_y = point_y - 2;
		}else if(next_y - box_y == 1){
			point_y = point_y + 2;
		}

		if(!this.isMoving()){
			if(direction == MAP_CONST.DIRECTION_RIGHT){
				if(myMap.getBox(box_x + 1, box_y).getState() == MAP_CONST.MAP_STATE_BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION_UP){
				if(myMap.getBox(box_x, box_y - 1).getState() == MAP_CONST.MAP_STATE_BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION_DOUN){
				if(myMap.getBox(box_x, box_y + 1).getState() == MAP_CONST.MAP_STATE_BLOCK){
					return;
				}
			}else if(direction == MAP_CONST.DIRECTION_LEFY){
				if(myMap.getBox(box_x - 1, box_y).getState() == MAP_CONST.MAP_STATE_BLOCK){
					return;
				}
			}
		}

		if(next_x == box_x && next_y == box_y){
			if(direction == MAP_CONST.DIRECTION_RIGHT){
				if(sinfo.getKeyRepeat(KEY_STATE.RIGHT)){
					next_x = box_x + 1;
					next_y = box_y;
					point_x = point_x + 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.MAP_STATE_ENPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.MAP_STATE_BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION_UP){
				if(sinfo.getKeyRepeat(KEY_STATE.UP)){
					next_x = box_x;
					next_y = box_y - 1;
					point_y = point_y - 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.MAP_STATE_ENPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.MAP_STATE_BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION_DOUN){
				if(sinfo.getKeyRepeat(KEY_STATE.DOWN)){
					next_x = box_x;
					next_y = box_y + 1;
					point_y = point_y + 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.MAP_STATE_ENPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.MAP_STATE_BLOCK);
					}
				}
			}else if(direction == MAP_CONST.DIRECTION_LEFY){
				if(sinfo.getKeyRepeat(KEY_STATE.LEFT)){
					next_x = box_x - 1;
					next_y = box_y;
					point_x = point_x - 2;
					if(myMap.getBox(next_x, next_y).getState() == MAP_CONST.MAP_STATE_ENPTY){
						myMap.setBoxState(next_x, next_y, MAP_CONST.MAP_STATE_BLOCK);
					}
				}
			}
		}

		if(point_x % MAP_CONST.MAP_BOX_SIZE == 0 && point_y % MAP_CONST.MAP_BOX_SIZE == 0){
			if(isMoving()){
				myMap.setBoxState(box_x,box_y, MAP_CONST.MAP_STATE_ENPTY);
				box_x = next_x;
				box_y = next_y;
				next_x = box_x;
				next_y = box_y;
			}
		}
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
}
