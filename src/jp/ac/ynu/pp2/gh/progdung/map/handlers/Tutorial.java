package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Tutorial extends MapHandlerBase {
	
	public Tutorial(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("tutorial", player_x, player_y, player_d, play);
	}

}
