package jp.ac.ynu.pp2.gh.naclo.mapseq.sequence;

public abstract class TherdLayerSequence   extends BaseSequence{
	public TherdLayerSequence(BaseSequence parent) {
		super(parent);
	}

	@Override
	protected int myLayerNumber() {
		return 3;
	}
}