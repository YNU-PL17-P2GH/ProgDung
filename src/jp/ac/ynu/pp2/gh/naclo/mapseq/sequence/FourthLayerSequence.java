package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;


public abstract class FourthLayerSequence extends BaseSequence {

	public FourthLayerSequence(BaseSequence parent) {
		super(parent);
	}

	@Override
	protected int myLayerNumber() {
		return 4;
	}

}
