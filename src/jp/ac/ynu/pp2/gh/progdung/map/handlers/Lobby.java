package jp.ac.ynu.pp2.gh.progdung.map.handlers;

import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

public class Lobby extends MapHandlerBase {
	
	public Lobby(int player_x, int player_y, DIRECTION player_d, DungeonPlay play) {
		super("roby", player_x, player_y, player_d, play);
	}

}