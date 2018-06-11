package Tanque;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PintarMapa {
	private BufferedImage img;
	private Zoom zoom;
	private int auxX;
	private int auxY;
	private JLabel label;
	boolean auxContadorZoom = false;
	
	public PintarMapa(BufferedImage img, int auxX, int auxY,  Zoom z, JLabel lab , boolean auxContadorZoom) {
		this.img = img;
		this.auxX = auxX;
		this.auxY = auxY;
		this.zoom = z;
		this.label = lab;
		this.auxContadorZoom = auxContadorZoom;
		
	}
	public void limpiar() {
		for(int i = 0; i < 400; i++) {
			for(int j = 0; j < 400; j++) {
				img.setRGB(i, j, 0);
			}
		}
		label.removeAll();
		label.repaint();
	}
	public void pintar() {
		
		BufferedImage image3 = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		int r= 255;
		int g= 0;
		int b= 0;
		
		int r1= 0;
		int g1= 255;
		int b1= 255;
		
		int r2= 255;
		int g2= 255;
		int b2= 0;
		
		int rgb=new Color(r,g,b).getRGB();
		int rgb1=new Color(r1,g1,b1).getRGB();
		int rgb2=new Color(r2,g2,b2).getRGB();
		for(int i = 0; i < 400; i++) {
			for(int j = 0; j < 400; j++) {
				if(img.getRGB(i, j) == rgb) {
					image3.setRGB(i, j, rgb1);
					
				}
				if(img.getRGB(i, j) == rgb1) {
					image3.setRGB(i, j, rgb1);
					
				}
				if(img.getRGB(i, j) == rgb2) {
					image3.setRGB(i, j, rgb2);
					
				}
			}
		}
		for(int i = 0; i < 400; i++) {
			for(int j = 0; j < 400; j++) {
				img.setRGB(i, j, image3.getRGB(i, j));
			}
		}
		
		img.setRGB(auxX,auxY ,rgb);
		if(auxContadorZoom == false) {
		zoom.ActualiciarImg(img);
		}
		label.removeAll();
		if(auxContadorZoom == false) {
		label.add(zoom);
		}
		label.repaint();
		label.setIcon(new ImageIcon(img));
						
	}
	
}


