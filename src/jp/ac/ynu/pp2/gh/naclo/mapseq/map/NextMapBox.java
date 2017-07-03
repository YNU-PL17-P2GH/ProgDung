package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

public class NextMapBox extends MapBox{
	private int next_x, next_y;
	private String nextMapName;
	public NextMapBox(MAP_CONST.STATE state) {
		super(state);
	}

	public void setNextMap(String name, int x,int y){
		nextMapName = name;
		next_x = x;
		next_y = y;
	}

	public String getNextMapName() {
		return nextMapName;
	}

	public int getNext_x() {
		return next_x;
	}

	public int getNext_y() {
		return next_y;
	}
}
