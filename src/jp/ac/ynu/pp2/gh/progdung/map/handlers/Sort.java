package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Sort extends MapHandlerBase {
	
	public Sort(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("sort1", player_x, player_y, player_d, play);
	}

}