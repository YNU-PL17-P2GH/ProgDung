package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.imageio.ImageIO;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

import org.jruby.Ruby;
import org.jruby.RubyInteger;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public class BasiccalcObject extends MapProgObject{
	private BufferedImage tuboImg;
	private int tubo_w, tubo_h;
	private boolean tuboInFlag = false;

	private BufferedImage okeImg;
	private int oke_w, oke_h;
	private boolean okeInFlag = false;

	private BufferedImage hakoImg;
	private int hako_w, hako_h;
	private boolean hakoInFlag = false;

	private int tuboC_w, tuboC_h;

	private int result = Integer.MAX_VALUE;

	public BasiccalcObject(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/tubo_covered.png"));
			tuboImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/tubo.png"));;
			okeImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/oke.png"));;
			hakoImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/hako.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		theOperator = new BaciccalcOperator();


		tubo_w = tuboImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		tubo_h = tuboImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;

		oke_w = okeImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		oke_h = okeImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;

		hako_w = tuboImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		hako_h = tuboImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;

		tuboC_w = objImg.getWidth() / MAP_CONST.MAP_CHIP_SIZE;
		tuboC_h = objImg.getHeight() / MAP_CONST.MAP_CHIP_SIZE;

		width = tuboC_w + tubo_w + oke_w + hako_w + 3;
		height = tuboC_h;

		sourceRuby = "def calc(tubo, oke, hako, tubo_covered)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";

		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				getMap().setObj(bx + x, by + y, this);
			}
		}
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		sinfo.g.drawImage(tuboImg, map_x, map_y,
				MAP_CONST.MAP_BOX_SIZE  * tubo_w, MAP_CONST.MAP_BOX_SIZE  * tubo_h, null);

		sinfo.g.drawImage(okeImg, map_x + tubo_w * MAP_CONST.MAP_BOX_SIZE, map_y,
				MAP_CONST.MAP_BOX_SIZE  * oke_w, MAP_CONST.MAP_BOX_SIZE  * oke_h, null);

		sinfo.g.drawImage(hakoImg, map_x + (tubo_w + oke_w) * MAP_CONST.MAP_BOX_SIZE, map_y,
				MAP_CONST.MAP_BOX_SIZE  * hako_w, MAP_CONST.MAP_BOX_SIZE  * hako_h, null);

		sinfo.g.drawImage(objImg, map_x + (tubo_w + oke_w + hako_w + 3) * MAP_CONST.MAP_BOX_SIZE, map_y,
				MAP_CONST.MAP_BOX_SIZE  * tuboC_w, MAP_CONST.MAP_BOX_SIZE  * tuboC_h, null);
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
	public void runRuby(final Ruby ruby, StringWriter stdin, StringWriter stderr) {
		new Thread() {
			@Override
			public void run() {
				rrwrapper(ruby);
			}
		}.start();
	}
	private void rrwrapper(Ruby ruby) {
		ScriptingContainer container = new ScriptingContainer();
		container.setKCode(KCode.UTF8);

		// Issue #2
		InputStream lStream = new ReaderInputStream(new StringReader(sourceRuby), "UTF-8");
//		EmbedEvalUnit lUnit = container.parse(lStream, "temp.rb");
		container.runScriptlet(lStream, "template.rb");
		int tubo = Integer.MAX_VALUE;
		int oke = Integer.MAX_VALUE;
		int hako = Integer.MAX_VALUE;
		if(tuboInFlag){
			tubo = 45;
		}
		if(okeInFlag){
			oke = 93;
		}
		if(hakoInFlag){
			hako = -13;
		}
		container.callMethod(ruby.getCurrentContext(), "calc", tubo, oke, hako, theOperator);
	}
	
	public void setKey(String oName){
		if(oName == "tubo"){
			tuboInFlag = true;
		}else if(oName == "oke"){
			okeInFlag = true;
		}else if(oName == "hako"){
			hakoInFlag = true;
		}
	}

	

/*	正解コード
	def calc(tubo, oke, hako, tubo_covered)
		# この下にソースを入力
		Integer a = tubo + oke
		Integer b = tubo - hako
		Integer c = tubo / hako
		a = a + b + a * b
		a = a / c  * hako
		a = a % oke
		tubo_covered.setResult(a)
	end
*/
	private final int answer = 52;
	public boolean checkResult() {
		return (result == answer);
	}

	@Override
	public String getObjName() {
		if(getMap().getMyPlayer() == null){
			return super.getObjName();
		}
		int pX = getMap().getMyPlayer().getBox_x();
		if(pX < box_x + tubo_w){
			return "tubo";
		}else if(pX < box_x + tubo_w + oke_w ){
			return "oke";
		}else if(pX < box_x + tubo_w + oke_w + hako_w){
			return "hako";
		}
		return super.getObjName();
	}

	public class BaciccalcOperator {
		public void setResult(RubyInteger rint) {
			result = RubyInteger.num2int(rint);
			//JOptionPane.showMessageDialog(null, "" + result);
		}
	}
}
