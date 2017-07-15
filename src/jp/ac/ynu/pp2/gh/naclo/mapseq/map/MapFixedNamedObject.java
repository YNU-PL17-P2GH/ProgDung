package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.net.URL;

public class MapFixedNamedObject extends MapFixedObject {
	private String originName;
	public MapFixedNamedObject(MapHandlerBase pHandler, int bx, int by, String objName, String oriName,RpgMap map) {
		super(pHandler, bx, by, objName, map);
		originName = oriName;
	}

	@Override
	public String getObjName() {
		return originName;
	}

	public URL getImagePath() {
		return getClass().getClassLoader().getResource("media/map/obj/" + super.getObjName() + ".png");
	}
}
