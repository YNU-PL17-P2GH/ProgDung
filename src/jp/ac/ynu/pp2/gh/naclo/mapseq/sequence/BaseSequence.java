package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public abstract class BaseSequence {
	protected BaseSequence mChild;
	protected BaseSequence mParent;

	public BaseSequence(BaseSequence parent){
		mParent = parent;

		if(parent != null){		//エラーチェック
			System.out.print(parent.getClass().getName().substring(parent.getClass().getName().lastIndexOf('.') + 1));
			System.out.print(" -> ");
			System.out.println(this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1));
		}
	}

	//このディスプレイを表示
	public abstract BaseSequence show(ShareInfo sinfo);

	//自分の階層番号を返す
	protected abstract int myLayerNumber();

	//親の層番号を指定して取得する
	protected BaseSequence getParent(int parentLayer){
		if(parentLayer == this.myLayerNumber()){		//自分の層が指定されていたら
			return this;
		}else if(parentLayer < this.myLayerNumber()){	//自分より上層が指定されていたら
			return this.mParent.getParent(parentLayer);
		}else{											//自分より下層が指定されていたらエラー
			return null;
		}
	}
}
