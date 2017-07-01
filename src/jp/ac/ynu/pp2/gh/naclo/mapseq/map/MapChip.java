package jp.ac.ynu.pp2.gh.naclo.mapseq.map;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import jp.ac.ynu.pp2.gh.naclo.mapseq.ShareInfo;

public class MapChip {
	private BufferedImage mapChipImags[];
	private int chipX[];
	private int chipY[];
	public MapChip(String mapName){
		//ファイルからロードに
		BufferedReader ibr = null;
		try {
			ibr = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource("media/map/chips/chip.txt").openStream()));
			String line = ibr.readLine();

			if(line.indexOf("chipData") >= 0){
				int imgNum = Integer.parseInt(line.split(" ", 0)[1]);
				mapChipImags = new BufferedImage[imgNum];
				chipX = new int[imgNum];
				chipY = new int[imgNum];
			}else{	//ファイルエラー
				JOptionPane.showMessageDialog(null, "エラー");
				System.exit(0);
			}
			String data[];
			for (int i = 0; i < mapChipImags.length; i++) {
				data = ibr.readLine().split(" ", 0);
				mapChipImags[i] = ImageIO.read(getClass().getClassLoader().getResource("media/map/chips/"+ data[0] + ".png"));
				chipX[i] = Integer.parseInt(data[1]);
				chipY[i] = Integer.parseInt(data[2]);
			}
			ibr.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}
	}

	public void drawChip(ShareInfo sinfo, int x, int y, int[] chipID){

		for(int j = 0; j < 4; j++){
			int i = 0;
			int id = chipID[j];
			if(id <= 0){
				continue;
			}
			while(id >= (chipX[i] * chipY[i])){
				id = id - (chipX[i] * chipY[i]);
				i++;
			}
			id--;
			/*System.out.println((x + MAP_CONST.MAP_BOX_SIZE / 2 * (j % 2))+" "+ (y + MAP_CONST.MAP_BOX_SIZE / 2 * (j / 2))+ " " +
					(x + MAP_CONST.MAP_BOX_SIZE / 2 * (j % 2 + 1)) + " " + (y + MAP_CONST.MAP_BOX_SIZE / 2 * (j / 2 + 1)));*/
			sinfo.g.drawImage(mapChipImags[i], x + MAP_CONST.MAP_BOX_SIZE / 2 * (j % 2), y + MAP_CONST.MAP_BOX_SIZE / 2 * (j / 2),
					x + MAP_CONST.MAP_BOX_SIZE / 2 * (j % 2 + 1), y + MAP_CONST.MAP_BOX_SIZE / 2 * (j / 2 + 1),
				(id % chipX[i]) * MAP_CONST.MAP_CHIP_SIZE, (id / chipX[i]) * MAP_CONST.MAP_CHIP_SIZE, (id % chipX[i] + 1) * MAP_CONST.MAP_CHIP_SIZE, (id / chipX[i] + 1) * MAP_CONST.MAP_CHIP_SIZE, null);

		}
	}
}
