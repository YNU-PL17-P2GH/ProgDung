package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapDummyProgObject extends MapProgObject {

	public MapDummyProgObject(String mapName, String hintName) {
		super(null, 0, 0, "dummy", null);
		BufferedReader ibr;
		StringBuffer sb = new StringBuffer();
		try {
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/" + mapName + "/hint/"+ hintName +".txt").openStream()));
			String line = ibr.readLine();
			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = ibr.readLine();
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		sourceRuby = sb.toString();
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {}

	@Override
	public void update(ShareInfo sinfo) {}

	@Override
	public boolean hitCheck(MapObject obj) {
		return false;
	}

}
