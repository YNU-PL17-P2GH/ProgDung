package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.net.URL;

public class MapAnimeNamedObject extends MapAnimeObject {
	private String originName;
	public MapAnimeNamedObject(MapHandlerBase pHandler, int bx, int by, int w, int h, String objName, String oriName, RpgMap map) {
		super(pHandler, bx, by, w, h, objName, map);
		originName = oriName;
	}

	@Override
	public String getObjName() {
		return originName;
	}

	public URL getImagePath() {
		return getClass().getClassLoader().getResource("FirstRpg/media/map/obj/" + super.getObjName() + ".png");
	}
}
