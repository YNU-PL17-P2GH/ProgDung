package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.util.ArrayList;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST.DIRECTION;
import jp.ac.ynu.pp2.gh.progdung.gui.DungeonPlay;

/**
 * マップ移動に関するすべての処理はここを通すようにする.
 * Rubyソースなんかもここに保持して,オブジェクトもここを介する.
 * 基本的にマップごとにHandlerを作ることになる,
 * その場合コンストラクタとしてint, int, DIRECTION, DungeonPlayを持たせること.
 * @author b1564304
 *
 */
public abstract class MapHandlerBase {

	private RpgMap theMap;

	protected MapPlayer thePlayer;

	protected ArrayList<MapObject> theObj;

	protected DungeonPlay callback;

	private MapPcObject currentFocusedPc;


	/**
	 * 引数に読み込むMapのパスを指定してHandler生成.
	 * TODO 初期位置はMapデータ依存が望ましい.
	 * @param pMapName
	 */
	protected MapHandlerBase(String pMapName, int player_x, int player_y, DIRECTION player_d, DungeonPlay playCallback) {
		callback = playCallback;
		theObj = new ArrayList<MapObject>();

		theMap = new RpgMap(this, pMapName, player_x, player_y, player_d);
		thePlayer = new MapPlayer(this, player_x, player_y, "player", player_d, getMap());
		for (MapObject tObject : theObj) {
			if (tObject instanceof MapDoorObject) {
				if(hitChecktoPlayer(tObject)) {
					((MapDoorObject)tObject).setOpend();
				}
			}
		}
	}

	public void draw(ShareInfo sinfo) {
		getMap().update(sinfo);
		getMap().draw(sinfo);
	}

	public final void moveMap(NextMapBox pBox) {
		// TODO moving
		if (pBox == null) {
			throw new NullPointerException("Move Called but argument is null");
		}

		String lClassName = "jp.ac.ynu.pp2.gh.progdung.map.handlers.".concat(pBox.getNextMapName());
		int lX = pBox.getNext_x();
		int lY = pBox.getNext_y();
		DIRECTION lD = pBox.getNext_d();
		if(lD == null){
			lD = DIRECTION.UP;
		}

		callback.moveMap(lClassName, lX, lY, lD);

	}

	public boolean hitChecktoPlayer(MapObject obj){
		return obj.hitCheck(thePlayer);
	}
	public boolean hitChecktoObj(MapObject obj){
		return thePlayer.hitCheck(obj);
	}

	public DungeonPlay getCallback() {
		return callback;
	}

	protected void showHint(String pString, boolean force) {
		callback.showHint(pString, force);
	}


	protected void showCoder(MapPcObject pObject) {
		currentFocusedPc = pObject;
		callback.showCoder();
	}

	protected void showHintOnCoder(MapHintObject pObject) {
		currentFocusedPc = pObject;
		callback.showHintOnCoder();
	}

	protected void stdoutUpdate() {
		callback.stdoutUpdate();
	}

	protected void stderrUpdate() {
		callback.stderrUpdate();
	}

	public MapPcObject getCurrentFocusedPc() {
		return currentFocusedPc;
	}

	public abstract void onMapLoad();

	public abstract void playerUpdate();

	public abstract void onPlayerHitTo(MapObject object);

	public abstract void onPlayerInteract(MapObject pObject);

	public RpgMap getMap() {
		return theMap;
	}

	public void toggleMenu() {
		callback.toggleMenu();
	}

	public void showSelect() {
		callback.showSelect();
	}

	public void showGameClear(int stage) {
		callback.showGameClear(stage);
	}
}
