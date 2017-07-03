package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import org.jruby.Ruby;

public abstract class MapProgObject extends MapObject{
	public abstract void runRuby(Ruby ruby);
}
