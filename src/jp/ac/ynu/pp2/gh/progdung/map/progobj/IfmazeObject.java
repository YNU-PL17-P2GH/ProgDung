package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.imageio.ImageIO;

import org.jruby.Ruby;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

public class IfmazeObject extends MapProgObject{
	private boolean successFlag = false;
	private BufferedImage kirakira;

	public IfmazeObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("FirstRpg/media/map/obj/" + objName + ".png"));
			kirakira = ImageIO.read(getClass().getClassLoader().getResource("FirstRpg/media/map/obj/kirakira/yellow.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		setOperator(new IfmazeOperator());


		width = 4;
		height = 4;

		sourceRuby = "def care(horse)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";

		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				getMap().setObj(bx + x, by + y, this);
			}
		}
	}
	private static final int delay = 10;
	private int imgNum = 4;				//画像枚数
	private int columns = 4;
	private int animeCount = 0;

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.drawImage(objImg, map_x , map_y,
				MAP_CONST.MAP_BOX_SIZE  * width, MAP_CONST.MAP_BOX_SIZE  * height, null);

		if(successFlag){
			sinfo.g.drawImage(kirakira, map_x+ MAP_CONST.MAP_BOX_SIZE , map_y + 8,
					map_x + MAP_CONST.MAP_BOX_SIZE  * 3, map_y + MAP_CONST.MAP_BOX_SIZE  * 2 + 8,
					((animeCount / delay % imgNum) % columns) * MAP_CONST.MAP_CHIP_SIZE * 2,
					((animeCount / delay % imgNum) / columns) * MAP_CONST.MAP_CHIP_SIZE * 2,
					((animeCount / delay % imgNum) % columns + 1) * MAP_CONST.MAP_CHIP_SIZE * 2,
					((animeCount / delay % imgNum) / columns + 1) * MAP_CONST.MAP_CHIP_SIZE * 2, null);
			animeCount++;
		}
		drawFlag = true;
	}

	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void preRunRuby(Ruby ruby, Object[] pArguments) {
		((IfmazeOperator)getOperator()).init();
	}

	@Override
	public void postRunRuby(Ruby ruby, Object[] pArguments) {
		successFlag = ((IfmazeOperator)getOperator()).checkResult();
	}

	@Override
	public String getMethodName() {
		return "care";
	}

	@Override
	public String getArgumentString() {
		return "horse";
	}

	private void rrwrapper(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);


		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		container.callMethod(ruby.getCurrentContext(), "care", getOperator());
	}

/*	正解コード
def care(horse)
	Integer state = -1
	while true
		state = horse.getHorseState()
		if state == 0 then
			horse.careHorse("feed")
		elsif state == 1 then
			horse.careHorse("water")
		elsif state == 2 then
			horse.careHorse("wash")
		elsif state == 3 then
			horse.careHorse("end")
			break
		end
 	end
end
*/
	public void setSuccessFlag(boolean b) {
		this.successFlag = b;
	}

	public boolean getSuccessFlag(){
		return successFlag;
	}

	public void setInitflag(boolean b) {
		((IfmazeOperator)getOperator()).setInitflag(b);
	}

	public class IfmazeOperator {
		private int index;
		private int[] stateList = {0, 1, 2, 2, 1, 0, 2, 1, 3};	//0 空腹, 1 渇き, 2 汚れ, 3 死亡
		private boolean failFlag;
		private boolean initflag;
		public void setInitflag(boolean b) {
			this.initflag = b;
		}

		public void init(){
			index = -1;
			failFlag = initflag;
		}

		public int getHorseState(){
			index++;
			if(index >= stateList.length){
				failFlag = true;
				return -1;
			}
			return stateList[index];
		}

		public void careHorse(String take){
			if(failFlag) return;

			if(take.equals("feed")){
				if(stateList[index] != 0){
					failFlag = true;
				}
			}else if(take.equals("water")){
				if(stateList[index] != 1){
					failFlag = true;
				}
			}else if(take.equals("wash")){
				if(stateList[index] != 2){
					failFlag = true;
				}
			}else if(take.equals("end")){
				if(stateList[index] != 3){
					failFlag = true;
				}
			}
		}

		public boolean checkResult(){
			if(failFlag){
				return false;
			}
			if(index == stateList.length - 1){
				return true;
			}
			return false;
		}
	}
}
