package Tanque;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

public class mapaApp {
	public static final ReentrantReadWriteLock mouseClickedLock = new ReentrantReadWriteLock();
	private JFrame frmMapaTanque;
	PanamaHitek_Arduino ardi = new PanamaHitek_Arduino();
	boolean manual = true;
	boolean automatico = false;
	boolean auxArriba = false;
	boolean auxAbajo = false;
	boolean auxDerecha = true;
	boolean auxIzquierda = false;
	int auxX = 0;
	int auxY = 0;
	int auxContdor = 0;
	int escalaZoomMas = 0;
	int escalaZoomMas1 = 0;
	boolean auxContadorZoom = true;
	boolean auxContadorZoom1 = true;
	private JTextField textField;
	int auxMouseX = 0;
    int auxMouseY = 0;
    int auxPunto = 0;
    String auxRecibir;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					mapaApp window = new mapaApp();
					window.frmMapaTanque.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public mapaApp() {
	
		initialize();
		
				try {
					ardi.arduinoTX("COM11", 9600);
				} catch (ArduinoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	// Buscar y returnar en que X y Y esta el tanque
	private void buscarTanque(BufferedImage img) {
		for (int i = 0; i < 400; i++) {
			for (int j = 0; j < 400; j++) {
				int r = 128;
				int g = 255;
				int b = 0;

				int rgb = new Color(r, g, b).getRGB();
				if (img.getRGB(i, j) == rgb) {
					auxY = j;
					auxX = i;
				}

			}
		}
	}

	// Sacar dirección: DERECHA suma IZQUIERDA resta
	private void clcularDirecion() {

		for (int i = auxContdor; i >= 0; i = i - 4) {
			if (i >= 4) {
				switch (i - 4) {
				case 0:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = true;
					auxIzquierda = false;
					break;
				case 1:
					auxArriba = false;
					auxAbajo = true;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case 2:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = true;
					break;
				case 3:
					auxArriba = true;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				}
			} else {
				switch (i) {
				case 0:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = true;
					auxIzquierda = false;
					break;
				case 1:
					auxArriba = false;
					auxAbajo = true;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case 2:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = true;
					break;
				case 3:
					auxArriba = true;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				}
			}
		}

		for (int i = auxContdor; i < 0; i = i + 4) {

			if (i <= -5) {
				switch (i + 4) {
				case -1:
					auxArriba = true;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case -2:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = true;
					break;
				case -3:
					auxArriba = false;
					auxAbajo = true;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case -4:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = true;
					auxIzquierda = false;
					break;
				}
			} else {
				switch (i) {
				case -1:
					auxArriba = true;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case -2:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = false;
					auxIzquierda = true;
					break;
				case -3:
					auxArriba = false;
					auxAbajo = true;
					auxDerecha = false;
					auxIzquierda = false;
					break;
				case -4:
					auxArriba = false;
					auxAbajo = false;
					auxDerecha = true;
					auxIzquierda = false;
					break;
				}

			}
		}
	}

	// Mostrar solo la flechas hacia la direccion que esta mirando el tanque
	private void actualizarFlechas(JLabel labelDirArriba, JLabel labelDirAbajo, JLabel labelDirIzquierda,
			JLabel labelDirDerecha) {
		if (auxArriba == true) {
			labelDirArriba.setVisible(true);
			labelDirAbajo.setVisible(false);
			labelDirIzquierda.setVisible(false);
			labelDirDerecha.setVisible(false);
		}
		if (auxAbajo == true) {
			labelDirAbajo.setVisible(true);
			labelDirArriba.setVisible(false);
			labelDirIzquierda.setVisible(false);
			labelDirDerecha.setVisible(false);
		}
		if (auxIzquierda == true) {
			labelDirIzquierda.setVisible(true);
			labelDirDerecha.setVisible(false);
			labelDirAbajo.setVisible(false);
			labelDirArriba.setVisible(false);
		}
		if (auxDerecha == true) {
			labelDirDerecha.setVisible(true);
			labelDirArriba.setVisible(false);
			labelDirAbajo.setVisible(false);
			labelDirIzquierda.setVisible(false);
		}
	}

	private void initialize() {
		
		frmMapaTanque = new JFrame();
		frmMapaTanque.setResizable(false);
		frmMapaTanque.setTitle("Mapa y controlador del tanque");
		frmMapaTanque.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("src\\logo.png"));
		frmMapaTanque.setBounds(100, 100, 501, 659);
		frmMapaTanque.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmMapaTanque.getContentPane().setLayout(null);
		frmMapaTanque.addWindowListener( new WindowAdapter() { 
	        public void windowClosing( WindowEvent evt ) { 
	            int opcion = 0;

	            opcion = JOptionPane.showConfirmDialog(frmMapaTanque, "¿Seguro desea salir?", "Salir",
	                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

	            switch (opcion) {
	            case JOptionPane.YES_OPTION:
	                System.exit(0);
	                break;
	            case JOptionPane.NO_OPTION:
	                break;
	            case JOptionPane.CLOSED_OPTION:
	                break;
	            default:
	                break;
	            }
	        }
	    } ); 


		JButton btnManual = new JButton("Manual");
		btnManual.setBounds(45, 558, 80, 37);
		frmMapaTanque.getContentPane().add(btnManual);

		JButton btnAutomatico = new JButton("Autom\u00E1tico");
		btnAutomatico.setBounds(130, 558, 107, 37);
		frmMapaTanque.getContentPane().add(btnAutomatico);

		JSlider slider = new JSlider();
		slider.setEnabled(false);
		slider.setValue(0);
		slider.setBounds(45, 508, 167, 26);
		frmMapaTanque.getContentPane().add(slider);

		JLabel lblAc = new JLabel("Velocidad de 0% a 100%");
		lblAc.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAc.setBounds(65, 480, 217, 14);
		frmMapaTanque.getContentPane().add(lblAc);
		
//		PintarCirculo pnCirculo = new PintarCirculo();

		try {
			// Flechas
			BufferedImage imageAdelante;
			imageAdelante = ImageIO.read(
					new File("src\\newIMG\\adelante.png"));
			JLabel labelAdelante = new JLabel(new ImageIcon(imageAdelante));
			labelAdelante.setEnabled(false);
			labelAdelante.setBounds(370, 480, 32, 32);
			frmMapaTanque.getContentPane().add(labelAdelante);

			BufferedImage imageAtras;
			imageAtras = ImageIO.read(
					new File("src\\newIMG\\atras.png"));
			JLabel labelAtras = new JLabel(new ImageIcon(imageAtras));
			labelAtras.setEnabled(false);
			labelAtras.setBounds(370, 560, 32, 32);
			frmMapaTanque.getContentPane().add(labelAtras);

			BufferedImage imageLeft;
			imageLeft = ImageIO.read(
					new File("src\\newIMG\\izquierda.png"));
			JLabel labelLeft = new JLabel(new ImageIcon(imageLeft));
			labelLeft.setEnabled(false);
			labelLeft.setBounds(330, 520, 32, 32);
			frmMapaTanque.getContentPane().add(labelLeft);

			BufferedImage imageRight;
			imageRight = ImageIO.read(
					new File("src\\newIMG\\derecha.png"));
			JLabel labelRight = new JLabel(new ImageIcon(imageRight));
			labelRight.setEnabled(false);
			labelRight.setBounds(410, 520, 32, 32);
			frmMapaTanque.getContentPane().add(labelRight);

			// Direccion tanque
			BufferedImage imageDirDerecha;
			imageDirDerecha = ImageIO.read(new File(
					"src\\newIMG\\direccionDerecha.png"));
			JLabel labelDirDerecha = new JLabel(new ImageIcon(imageDirDerecha));
			labelDirDerecha.setVisible(false);
			labelDirDerecha.setBounds(219, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelDirDerecha);
			if (auxDerecha == true) {
				labelDirDerecha.setVisible(true);
			}

			BufferedImage imageDirIzquierda;
			imageDirIzquierda = ImageIO.read(new File(
					"src\\newIMG\\direccionIzquierda.png"));
			JLabel labelDirIzquierda = new JLabel(new ImageIcon(imageDirIzquierda));
			labelDirIzquierda.setVisible(false);
			labelDirIzquierda.setBounds(219, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelDirIzquierda);
			if (auxIzquierda == true) {
				labelDirIzquierda.setVisible(true);
			}

			BufferedImage imageDirArriba;
			imageDirArriba = ImageIO.read(new File(
					"src\\newIMG\\direccionArriba.png"));
			JLabel labelDirArriba = new JLabel(new ImageIcon(imageDirArriba));
			labelDirArriba.setVisible(false);
			labelDirArriba.setBounds(219, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelDirArriba);
			if (auxArriba == true) {
				labelDirArriba.setVisible(true);
			}

			BufferedImage imageDirBajo;
			imageDirBajo = ImageIO.read(new File(
					"src\\newIMG\\direccionBajo.png"));
			JLabel labelDirAbajo = new JLabel(new ImageIcon(imageDirBajo));
			labelDirAbajo.setVisible(false);
			labelDirAbajo.setBounds(219, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelDirAbajo);
			if (auxAbajo == true) {
				labelDirAbajo.setVisible(true);
			}

			// Zoom
			BufferedImage imageZoomMas;
			imageZoomMas = ImageIO
					.read(new File("src\\zoomMas.png"));
			JLabel labelZoomMas = new JLabel(new ImageIcon(imageZoomMas));
			labelZoomMas.setBounds(370, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelZoomMas);

			BufferedImage imageZoomMenos;
			imageZoomMenos = ImageIO
					.read(new File("src\\zoomMenos.png"));
			JLabel labelZoomMenos = new JLabel(new ImageIcon(imageZoomMenos));
			labelZoomMenos.setBounds(413, 15, 32, 32);
			frmMapaTanque.getContentPane().add(labelZoomMenos);

			// Capa transparente donde se elije los puntos
			BufferedImage image3 = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			JLabel label3 = new JLabel(new ImageIcon(image3));
			label3.setBounds(45, 50, 400, 400);
			frmMapaTanque.getContentPane().add(label3);
			
			// Capa transparente
			BufferedImage image2 = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			JLabel label2 = new JLabel(new ImageIcon(image2));
			label2.setBounds(45, 50, 400, 400);
			frmMapaTanque.getContentPane().add(label2);

			// Imagen de fondo
			BufferedImage image;
			image = ImageIO.read(new File("src\\1.jpg"));
			JLabel label = new JLabel(new ImageIcon(image));
			label.setBounds(45, 50, 400, 400);
			frmMapaTanque.getContentPane().add(label);

			textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			textField.setEnabled(false);
			textField.setBounds(219, 508, 72, 22);
			frmMapaTanque.getContentPane().add(textField);
			textField.setColumns(10);
			textField.setText("" + slider.getValue());

			JLabel lblDireccinDelTanque = new JLabel("Direcci\u00F3n del tanque:");
			lblDireccinDelTanque.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblDireccinDelTanque.setBounds(48, 21, 251, 16);
			frmMapaTanque.getContentPane().add(lblDireccinDelTanque);
			
			JButton button = new JButton("?");
			button.setBounds(242, 558, 49, 37);
			frmMapaTanque.getContentPane().add(button);
			
			//poner manual por defecto
			manual = true;
			automatico = false;
			labelRight.setEnabled(true);
			labelAtras.setEnabled(true);
			labelLeft.setEnabled(true);
			labelAdelante.setEnabled(true);
			slider.setEnabled(true);
			btnManual.setEnabled(false);
			btnAutomatico.setEnabled(true);
			
			// Conseguir X y Y de de la imagen
			
				label3.addMouseMotionListener(new MouseAdapter() {
						@Override
		                public void mouseMoved(MouseEvent e) {
							if(automatico == true) {
								auxMouseX = e.getX();
								auxMouseY = e.getY();
			                	System.out.println(e.getX());
			                	System.err.println(e.getY());
							}
		                }
				 });
				label3.addMouseListener(new MouseAdapter() {
	                @Override
					public void mouseClicked(MouseEvent arg0) {
	                	if(automatico == true && auxPunto <5) {
		                	int r = 0;
							int g = 0;
							int b = 0;
							int r1 = 255;
							int g1 = 0;
							int b1 = 255;
							
							JLabel label4 = new JLabel();
							label4.setBounds(45, 50, 400, 400);
							frmMapaTanque.getContentPane().add(label4);
							
		//					pnCirculo.pintarCirculoPosicion(label4, auxMouseX, auxMouseY);
							int rgb1 = new Color(r1, g1, b1).getRGB();
							int rgb = new Color(r, g, b).getRGB();
							image3.setRGB(auxMouseX, auxMouseY, rgb1);
							auxPunto++;
							if(auxMouseX-4 >= 0) {
			                	image3.setRGB(auxMouseX-4, auxMouseY, rgb);
							}
							if(auxMouseX+4 <= 400) {
			                	image3.setRGB(auxMouseX+4, auxMouseY, rgb);
							}
							if(auxMouseY+4 <= 400) {
			                	image3.setRGB(auxMouseX, auxMouseY+4, rgb);
							}
							if(auxMouseY-4 >= 0) {
			                	image3.setRGB(auxMouseX, auxMouseY-4, rgb);
							}
			                	label3.setIcon(new ImageIcon(image3));
							
	                	}
	                }
	            });
			
			Zoom zoom = new Zoom(image2);
			Zoom zoom1 = new Zoom(image);
			int rgb = new Color(128, 255, 0).getRGB();
			image2.setRGB(200, 200, rgb);
			
			//evento ayuda
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(java.awt.Desktop.isDesktopSupported()){
						 try{
						      Desktop dk = Desktop.getDesktop();
						      dk.browse(new URI("https://sites.google.com/view/manual-de-usuario-tanque/p%C3%A1gina-principal"));
						 }catch(Exception e){
						     
						 }
						}  
				}
			});

			// evento Zoom menos
			labelZoomMenos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(manual == true) {
						// capa transparente
						zoom.SetX(0);
						zoom.SetY(0);
						zoom1.SetX(0);
						zoom1.SetY(0);
						zoom.Restaurar();
						for (int i = 0; i < 400; i++) {
							for (int j = 0; j < 400; j++) {
								image2.setRGB(i, j, zoom.Enviar().getRGB(i, j));
							}
						}
						label2.setIcon(new ImageIcon(zoom.Enviar()));
						label2.removeAll();
						label2.add(zoom);
						label2.repaint();
						auxContadorZoom = true;
						
						// capa fondo
						zoom1.Restaurar();
						for (int i = 0; i < 400; i++) {
							for (int j = 0; j < 400; j++) {
								image.setRGB(i, j, zoom1.Enviar().getRGB(i, j));
							}
						}
						label.setIcon(new ImageIcon(zoom1.Enviar()));
						label.removeAll();
						label.add(zoom1);
						label.repaint();
						auxContadorZoom1 = true;
					}
				}
			});

			// evento Zoom mas
			labelZoomMas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						if(manual == true){
							
							buscarTanque(image2);
							
							//comprobaciones para calcular en que tipo de zoom esta el punto z1, x2 o x3
							int x = auxX*3;
							int y = auxY*3;
							
								
							
							
							// Aqui se setean los puntos donde se tienen que hacer zoom
							// fondo
							zoom.SetX(-(x+3));
							zoom.SetY(-(y+3));
							//transparente
							zoom1.SetX(-(x+3));
							zoom1.SetY(-(y+3));
							
							// capa transparente
							if (auxContadorZoom ==true) {
								zoom.Aumentar(300);
								for (int i = 0; i < 400; i++) {
									for (int j = 0; j < 400; j++) {
										image2.setRGB(i, j, zoom.Enviar().getRGB(i, j));
									}
								}
								label2.setIcon(new ImageIcon(zoom.Enviar()));
								label2.removeAll();
								label2.add(zoom);
								label2.repaint();
								auxContadorZoom = false;
							}
	
							// capa fondo
							if (auxContadorZoom1 == true) {
								zoom1.Aumentar(300);
								for (int i = 0; i < 400; i++) {
									for (int j = 0; j < 400; j++) {
										image.setRGB(i, j, zoom1.Enviar().getRGB(i, j));
									}
								}
								label.setIcon(new ImageIcon(zoom1.Enviar()));
								label.removeAll();
								label.add(zoom1);
								label.repaint();
								auxContadorZoom1 = false;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});

			// evento slide
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					textField.setText("" + slider.getValue());
				}
			});
			// Evento boton automatico
			btnAutomatico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					manual = false;
					automatico = true;
					labelRight.setEnabled(false);
					labelAtras.setEnabled(false);
					labelLeft.setEnabled(false);
					labelAdelante.setEnabled(false);
					slider.setEnabled(false);
					btnAutomatico.setEnabled(false);
					btnManual.setEnabled(true);
					labelZoomMas.setEnabled(false);
					labelZoomMenos.setEnabled(false);
					
					zoom.SetX(0);
					zoom.SetY(0);
					zoom1.SetX(0);
					zoom1.SetY(0);
					zoom.Restaurar();
					for (int i = 0; i < 400; i++) {
						for (int j = 0; j < 400; j++) {
							image2.setRGB(i, j, zoom.Enviar().getRGB(i, j));
						}
					}
					label2.setIcon(new ImageIcon(zoom.Enviar()));
					label2.removeAll();
					label2.add(zoom);
					label2.repaint();
					auxContadorZoom = true;
					
					// capa fondo
					zoom1.Restaurar();
					for (int i = 0; i < 400; i++) {
						for (int j = 0; j < 400; j++) {
							image.setRGB(i, j, zoom1.Enviar().getRGB(i, j));
						}
					}
					label.setIcon(new ImageIcon(zoom1.Enviar()));
					label.removeAll();
					label.add(zoom1);
					label.repaint();
					auxContadorZoom1 = true;
				}
			});
			// Evento boton manual
			btnManual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					manual = true;
					automatico = false;
					labelRight.setEnabled(true);
					labelAtras.setEnabled(true);
					labelLeft.setEnabled(true);
					labelAdelante.setEnabled(true);
					slider.setEnabled(true);
					btnManual.setEnabled(false);
					btnAutomatico.setEnabled(true);
					labelZoomMas.setEnabled(true);
					labelZoomMenos.setEnabled(true);
					PintarMapa pn = new PintarMapa(image3, 0, 0, null, label3,auxContadorZoom);
					pn.limpiar();
					label3.setIcon(new ImageIcon(image3));
					auxPunto = 0;
				}
			});
			// Accelerar evento
			labelAdelante.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (manual == true) {
						try {
							buscarTanque(image2);
							// pintar la acceleracion
								if(auxY == 0 && auxArriba == true) {
								}
								else if(auxX == 0 && auxIzquierda == true) {
								}
								else if(auxY == 400 && auxAbajo == true) {
								}
								else if(auxX == 400 && auxDerecha == true) {
								}
								else {
									if (auxArriba == true) {
										auxY -= 1;
									}
									if (auxAbajo == true) {
										auxY += 1;
									}
									if (auxDerecha == true) {
										auxX += 1;
									}
									if (auxIzquierda == true) {
										auxX -= 1;
									}
								PintarMapa pm = new PintarMapa(image2, auxX, auxY, zoom, label2, auxContadorZoom);
								pm.pintar();
								
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						try {
							
							ardi.sendData("A");
							
						} catch (ArduinoException e) {
							// TODO Auto-generated catch block
							System.out.println(e);
						} catch (SerialPortException e) {
							// TODO Auto-generated catch block
							System.out.println(e);
						}
					}
				}
			});
			// Marcha atras evento
			labelAtras.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (manual == true) {

						try {
							buscarTanque(image2);
							if (auxArriba == true) {
								auxY += 1;
							}
							if (auxAbajo == true) {
								auxY -= 1;
							}
							if (auxDerecha == true) {
								auxX -= 1;
							}
							if (auxIzquierda == true) {
								auxX += 1;
							}
							PintarMapa pm = new PintarMapa(image2, auxX, auxY, zoom, label2, auxContadorZoom);
							pm.pintar();

						} catch (Exception e2) {
							// TODO: handle exception
						}

						 try {
						 ardi.sendData("B");
						 
						 } catch (ArduinoException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 } catch (SerialPortException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 }
					}
				}
			});
			// Giro izquierda evento
			labelLeft.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (manual == true) {

						auxContdor--;
						clcularDirecion();
						actualizarFlechas(labelDirArriba, labelDirAbajo, labelDirIzquierda, labelDirDerecha);

						 try {
						 ardi.sendData("I");
						 } catch (ArduinoException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 } catch (SerialPortException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 }
					}
				}
			});
			// Giro derecha evento
			labelRight.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (manual == true) {

						auxContdor++;
						clcularDirecion();
						actualizarFlechas(labelDirArriba, labelDirAbajo, labelDirIzquierda, labelDirDerecha);

						 try {
						 ardi.sendData("D");
						 } catch (ArduinoException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 } catch (SerialPortException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
						 }
					}
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
