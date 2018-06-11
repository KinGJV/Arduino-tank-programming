package Tanque;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Zoom extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image FOTO_ORIGINAL;
	private Image FOTO_tmp;
	private BufferedImage Imagen_en_memoria;
	private Graphics2D g2D;
	private boolean con_foto = false;
	private int x = 0;
	private int y = 0;
	private int valEscalaX = 0;
	private int valEscalaY = 0;

	public Zoom(BufferedImage f) {
		this.FOTO_ORIGINAL = f;
		this.FOTO_tmp = f;
		this.setSize(f.getWidth(), f.getHeight());
		this.setVisible(true);
		this.con_foto = true;
	}

	public void ActualiciarImg(BufferedImage f) {
		this.FOTO_ORIGINAL = f;
		this.FOTO_tmp = f;

		
			Aumentar(300);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (this.con_foto) {
			Imagen_en_memoria = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2D = Imagen_en_memoria.createGraphics();
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.drawImage(FOTO_tmp, x, y, FOTO_tmp.getWidth(this), FOTO_tmp.getHeight(this), this);
			g2.drawImage(Imagen_en_memoria, 0, 0, this);
		}
	}

	public void Aumentar(int Valor_Zoom) {
		valEscalaX = (int) (FOTO_tmp.getWidth(this) * escala(Valor_Zoom));
		valEscalaY = (int) (FOTO_tmp.getHeight(this) * escala(Valor_Zoom));
		this.FOTO_tmp = FOTO_tmp.getScaledInstance((int) (FOTO_tmp.getWidth(this) + valEscalaX),
				(int) (FOTO_tmp.getHeight(this) + valEscalaY), Image.SCALE_AREA_AVERAGING);
		resize();
	}

	public void Disminuir(int Valor_Zoom) {
		valEscalaX = (int) (FOTO_tmp.getWidth(this) * escala(Valor_Zoom));
		valEscalaY = (int) (FOTO_tmp.getHeight(this) * escala(Valor_Zoom));
		this.FOTO_tmp = FOTO_tmp.getScaledInstance((int) (FOTO_tmp.getWidth(this) - valEscalaX),
				(int) (FOTO_tmp.getHeight(this) - valEscalaY), Image.SCALE_AREA_AVERAGING);
		resize();
	}

	private float escala(int v) {
		return v / 100f;
	}

	public BufferedImage Enviar() {
		return (BufferedImage) FOTO_ORIGINAL;

	}

	public void Restaurar() {
		this.FOTO_tmp = this.FOTO_ORIGINAL;
		resize();
	}

	private void resize() {
		this.setSize(FOTO_tmp.getWidth(this), FOTO_tmp.getHeight(this));
	}
	public void SetX (int x) {
		this.x=x;
	}
	public void SetY (int y) {
		this.y=y;
	}

}
