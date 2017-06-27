package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;

public abstract class SecondLayerSequence  extends BaseSequence{
	public SecondLayerSequence(BaseSequence parent) {
		super(parent);
	}

	@Override
	protected int myLayerNumber() {
		return 2;
	}
}
