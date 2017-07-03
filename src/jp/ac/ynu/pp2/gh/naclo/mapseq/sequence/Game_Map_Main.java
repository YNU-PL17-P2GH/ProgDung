package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;

public class Game_Map_Main extends TherdLayerSequence{

	public Game_Map_Main(BaseSequence parent) {
		super(parent);
		mChild = null;
	}

	@Override
	public BaseSequence show(ShareInfo sinfo) {
		Game_Map gm = (Game_Map)getParent(CLASS_LAYER_NUM.GAME_MAP);
		gm.myMap.update(sinfo);
		gm.myMap.draw(sinfo);
//		System.out.println(gm.myMap.chackPlayerFoot());
		if(gm.myMap.chackPlayerFoot() == MAP_CONST.STATE.NEXT){
			System.exit(0);
			gm.myMap.mapToMap();
		}
		return this;
	}

}
