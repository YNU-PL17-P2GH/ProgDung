package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class Game_Map extends SecondLayerSequence{
	protected RpgMap myMap;
	public Game_Map(BaseSequence parent) {
		super(parent);
		mChild = new Game_Map_Main(this);
		myMap = new RpgMap("sort", 6, 33 ,MAP_CONST.DIRECTION.UP);		//セーブデータからロードするべき
	}

	@Override
	public BaseSequence show(ShareInfo sinfo) {
		BaseSequence next;
		next = mChild.show(sinfo);
		//階層間移動
		if((this.myLayerNumber()+1) != next.myLayerNumber()){
			return next;
		}

		//階層内移動
		if(next != mChild){
			mChild = next;
		}

		return this;
	}

}
