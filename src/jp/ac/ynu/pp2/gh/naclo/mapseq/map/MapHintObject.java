package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.net.URL;

public class MapHintObject extends MapPcObject {

	public MapHintObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map, String mapName, String hintName) {
		super(pHandler, bx, by, objName, map, "dummy");

		allocObj = new MapDummyProgObject(mapName, hintName);
	}

	@Override
	public URL getImagePath() {
		return getClass().getClassLoader().getResource("FirstRpg/media/map/obj/" + getObjName() + ".png");
	}
}
