package jp.ac.ynu.pp2.gh.progdung.map.progobj;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MAP_CONST;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapHandlerBase;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.MapProgObject;
import jp.ac.ynu.pp2.gh.naclo.mapseq.map.RpgMap;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyFixnum;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.io.ReaderInputStream;
import org.jruby.util.KCode;

public class Array2Object  extends MapProgObject {
	private int[] showArray	= {0, 0, 0, 0, 0, 0, 0, 0};
	private int[] playerArray	= {0, 0, 0, 0, 0, 0, 0, 0};
	private int[] answerArray = {0, 1, 1, 0, 1, 0, 0, 1};
	private volatile boolean fragSuccess;
	private volatile boolean ranRuby = false;

	BufferedImage tate_onImg;
	BufferedImage tate_offImg;

	BufferedImage mahouzinImg;

	public Array2Object(MapHandlerBase pHandler, int bx, int by, String pObjName, RpgMap pMap) {
		super(pHandler, bx, by, pObjName, pMap);

		try {
			objImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/" + pObjName + ".png"));
			tate_onImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/tate_on.png"));
			tate_offImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/tate_off.png"));
			mahouzinImg = ImageIO.read(getClass().getClassLoader().getResource("media/map/obj/mahouzin.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		sourceRuby = "def operate(ido)\n"
				+ "\t# この下にソースを入力\n"
				+ "end";

		width = 2;
		height = 2;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				getMap().setObj(bx + i, by + j, this);
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < showArray.length * 2; j++) {
				getMap().setObj(bx + j - 7, by + i, this);
			}
		}

		theOperator = new Array2Operator();
	}
/*	正解コード
def operate(ido)
	Array a = ido.getArray()
	Array answer = Array.new(a.length)
	Integer i = 0
	while i < a.length
		if a[i] == 1 then
			answer[i] = "on"
		else
			answer[i] = "off"
		end
		i = i + 1
	end
	ido.setResult(answer)
end
 */

	@Override
	public void runRuby(Ruby ruby) {
		ranRuby = true;
		new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < showArray.length; i++) {
					showArray[i] = 0;
					playerArray[i] = 0;
				}
				rrwrapper(ruby);
				if(!fragSuccess){
					/*if(array[0] == initArray[initArray.length - 1]){
						fragSuccess = true;
					}*/
				}
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
		container.callMethod(ruby.getCurrentContext(), "operate", theOperator);
	}

	@Override
	public void draw(ShareInfo sinfo, int map_x, int map_y) {
		if(fragSuccess){
			sinfo.g.drawImage(mahouzinImg, map_x - MAP_CONST.MAP_BOX_SIZE  * 2 , map_y - MAP_CONST.MAP_BOX_SIZE  * 2,
					MAP_CONST.MAP_BOX_SIZE  * 6, MAP_CONST.MAP_BOX_SIZE  * 6, null);
		}

		sinfo.g.drawImage(objImg, map_x, map_y,
				MAP_CONST.MAP_BOX_SIZE  * width, MAP_CONST.MAP_BOX_SIZE  * height, null);

		for(int i = 0; i < showArray.length; i++){
			if(showArray[i] == 0){
				sinfo.g.drawImage(tate_offImg, map_x + MAP_CONST.MAP_BOX_SIZE  * (i * 2 - 7), map_y - MAP_CONST.MAP_BOX_SIZE  * 8,
					MAP_CONST.MAP_BOX_SIZE  * 2, MAP_CONST.MAP_BOX_SIZE  * 2, null);
			}else if(showArray[i] == 1){
				sinfo.g.drawImage(tate_onImg, map_x + MAP_CONST.MAP_BOX_SIZE  * (i * 2 - 7), map_y - MAP_CONST.MAP_BOX_SIZE  * 8,
						MAP_CONST.MAP_BOX_SIZE  * 2, MAP_CONST.MAP_BOX_SIZE  * 2, null);
			}
		}

		drawFlag = true;
	}

	@Override
	public void update(ShareInfo sinfo) {
		drawFlag = false;
	}

	@Override
	public boolean hitCheck(MapObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getRunRuby(){
		return ranRuby;
	}

	public void setFragSuccess(boolean b) {
		if(b){
			for (int i = 0; i < showArray.length; i++) {
				showArray[i] = answerArray[i];
			}
		}
		this.fragSuccess = b;
	}

	public boolean checkFragSuccess() {
		if(fragSuccess){
			return  fragSuccess;
		}
		boolean b = true;
		for (int i = 0; i < showArray.length; i++) {
			showArray[i] = playerArray[i];
			if(showArray[i] != answerArray[i]){
				b = false;
			}
		}
		fragSuccess = b;
		ranRuby = false;
		return fragSuccess;
	}

	public class Array2Operator {
		public int[] getArray(){
			int a[] = new int[answerArray.length];
			for (int i = 0; i < a.length; i++) {
				a[i] = answerArray[i];
			}

			return a;
		}
		public void setResult(Object array) {
			if(array instanceof RubyArray){
				int length = RubyFixnum.num2int(((RubyArray) array).length());
				if(length > answerArray.length){
					return;
				}
				for (int i = 0; i < length; i++) {
					JOptionPane.showMessageDialog(null, ((RubyArray) array).get(i).getClass().getName());

					if(((RubyArray) array).get(i).equals("on")){
						playerArray[i] = 1;
					}else if(((RubyArray) array).get(i).equals("off")){
						playerArray[i] = 0;
					}
				}
			}
		}
	}
}

