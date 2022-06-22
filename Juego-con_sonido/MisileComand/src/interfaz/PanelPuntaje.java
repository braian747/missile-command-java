package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelPuntaje extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelPuntaje instancia = new PanelPuntaje();
	private URL url = getClass().getResource("/imagenes/fondo.jpg/");
	private Image img = new ImageIcon(url).getImage();
	private URL urlMisil2 = getClass().getResource("/imagenes/misil2.png/");
	private Image misil2 = new ImageIcon(urlMisil2).getImage();
	private URL urlCiudad = getClass().getResource("/imagenes/ciudad.png/");
	private Image ciudad = new ImageIcon(urlCiudad).getImage();
	private int puntosCiudad;// sirve para ir mostrando el puntaje de las ciudades a medida que se van viendo
	private int puntosMisil;
	private ArrayList<Integer> posiciones = new ArrayList<Integer>();// sirve para mostrar todas las ciudades(a las vez)
																		// anteriores a la ultima que se muestra
	private ArrayList<Integer> posMisil = new ArrayList<Integer>();
	private int parte; // sirve para saber si el paint tiene que mostrar solo la parte de las
						// ciudades(parte 1) o la de las ciudades y misiles(parte 2)
	private boolean bonusCity;

	private PanelPuntaje() {
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		this.setPreferredSize(new Dimension(650, 700));
		this.puntosCiudad = 0;
		this.parte = 1;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 650, 700, null);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 30));
		g.drawString("BONUS POINTS", 200, 200);
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < posiciones.size(); i++) {
			g.drawImage(ciudad, posiciones.get(i), 270, 50, 50, null);

		}
		g.drawString(puntosCiudad + "", 110, 307);
		if (parte == 2) {
			for (int i = 0; i < posMisil.size(); i++) {

				g.drawImage(misil2, posMisil.get(i), 410, 5, 5, null);

			}
			g.drawString(puntosMisil + "", 110, 420);
		}
		if (isBonusCity()) {
			g.drawString("BONUS CITY HAS BEEN ADDED!", 70, 500);
			setBonusCity(false);
		}
	}

	public boolean isBonusCity() {
		return bonusCity;
	}

	public void setBonusCity(boolean bonusCity) {
		this.bonusCity = bonusCity;
	}

	public int getPuntosMisil() {
		return puntosMisil;
	}

	public void setPuntosMisil(int puntosMisil) {
		this.puntosMisil = puntosMisil;
	}

	public ArrayList<Integer> getPosMisil() {
		return posMisil;
	}

	public void setPosMisil(ArrayList<Integer> posMisil) {
		this.posMisil = posMisil;
	}

	public int getParte() {
		return parte;
	}

	public void setParte(int parte) {
		this.parte = parte;
	}

	public int getPuntosCiudad() {
		return puntosCiudad;
	}

	public void setPuntosCiudad(int puntosCiudad) {
		this.puntosCiudad = puntosCiudad;
	}

	public ArrayList<Integer> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(ArrayList<Integer> posiciones) {
		this.posiciones = posiciones;
	}

	public static PanelPuntaje getInstancia() {
		return PanelPuntaje.instancia;
	}
}
