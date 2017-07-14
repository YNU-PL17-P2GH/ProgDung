package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.net.URL;

public class MapPcObject extends MapFixedObject {
	
	protected MapProgObject allocObj;
	private String allocObjName;

	public MapPcObject(MapHandlerBase pHandler, int bx, int by, String objName, RpgMap map, String pPOName) {
		super(pHandler, bx, by, objName, map);
		
		allocObjName = pPOName;
	}
	
	@Override
	public URL getImagePath() {
		return getClass().getClassLoader().getResource("media/map/obj/pc/" + getObjName() + ".png");
	}
	
	public String getAllocObjName() {
		return allocObjName;
	}

}
