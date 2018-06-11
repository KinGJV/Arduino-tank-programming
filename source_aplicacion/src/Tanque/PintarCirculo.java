package Tanque;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PintarCirculo extends JPanel{
	
	private int dimension = 20;
	private int x = 0;
	private int y = 0;
	private JLabel label = new JLabel();
	public void pintarCirculoPosicion(JLabel l, int x , int y) {
		l.removeAll();
		l.repaint();
		this.x = x;
		this.y = y;
		this.label = l;
	}
	
	public void pintar(Graphics g) {
		super.paint(g);
		Graphics2D circulo = (Graphics2D) g;
		circulo.setStroke(new BasicStroke(10.0f));
		circulo.setPaint(Color.CYAN);
		circulo.drawOval(x, y, dimension, dimension);
		
	}
}
