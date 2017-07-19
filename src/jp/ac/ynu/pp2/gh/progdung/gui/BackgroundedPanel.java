package jp.ac.ynu.pp2.gh.progdung.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class BackgroundedPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2675363210351286547L;

	private	Image	image;
	
	public BackgroundedPanel() {
		this(null);
	}
	
	public BackgroundedPanel(Image pImage) {
		super();
		setBackground(pImage);
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (g != null && image != null) {
			g.drawImage(image, 0, 0, 1280, 760, this);
			super.paintComponent(g);
		}
	}
	
	public void setBackground(Image pImage) {
		image = pImage;
	}

}
