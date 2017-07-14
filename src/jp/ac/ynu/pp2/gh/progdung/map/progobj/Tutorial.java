package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import org.jruby.Ruby;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class Tutorial extends MapProgObject {

	public Tutorial(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);
	}

	@Override
	public void runRuby(Ruby ruby) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ShareInfo sinfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
