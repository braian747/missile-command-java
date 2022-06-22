package interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import estructuras.Base;
import estructuras.Ciudad;
import gestor.CampoDeJuego;
import gestor.Coordenada;
import gestor.Juego;
import gestor.Nivel;
import gestor.Observador;
import gestor.Puntaje;
import jugadores.Enemigo;
import jugadores.Jugador;
import misiles.Misil;
import misiles.MisilKrytoliano;
import misiles.OndaExpansiva;

/**
 * 
 * Es el panel del juego en ejecucion este detecta el clic del mouse del jugador
 * para añadirlo a lista de destinos
 *
 */
public class PanelJuego extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelJuego instancia = new PanelJuego();
	private URL url = getClass().getResource("/imagenes/fondo.jpg/");
	private Image img = new ImageIcon(url).getImage();
	private URL urlMisil = getClass().getResource("/imagenes/misil.png/");
	private Image misil = new ImageIcon(urlMisil).getImage();
	private URL urlMisil2 = getClass().getResource("/imagenes/misil2.png/");
	private Image misil2 = new ImageIcon(urlMisil2).getImage();
	private URL urlCiudad = getClass().getResource("/imagenes/ciudad.png/");
	private Image ciudad = new ImageIcon(urlCiudad).getImage();
	private URL urlBase = getClass().getResource("/imagenes/base.png/");
	private Image base = new ImageIcon(urlBase).getImage();
	private URL urlExp = getClass().getResource("/imagenes/explosion.png/");
	private Image explosion = new ImageIcon(urlExp).getImage();
	private URL urlBomb = getClass().getResource("/imagenes/bomb.png/");
	private Image bomb = new ImageIcon(urlBomb).getImage();

	private PanelJuego() {

		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getY() < CampoDeJuego.getInstancia().getUmbral() && (Jugador.getInstancia().cantMisiles() >= 1)) {
					Coordenada c = new Coordenada(e.getX() + 1, e.getY() + 1);
					Jugador.getInstancia().getDestinos().add(c);
				}
			}
		});

		this.setPreferredSize(new Dimension(650, 700));

	}

	@SuppressWarnings("unchecked")
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 650, 700, null);
		g.setColor(Color.ORANGE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 18));
		g.drawString("NIVEL: " + Nivel.getInstancia().getNumNivel(), 10, 25);
		g.drawString("PUNTAJE: " + (Puntaje.getInstancia().puntajeParcial()), 490, 25);
		if ((Nivel.getInstancia().getNumNivel() >= 2) && (Enemigo.getInstancia().getB() != null)) {
			g.drawImage(bomb, Enemigo.getInstancia().getB().getActual().getX()-15,
					Enemigo.getInstancia().getB().getActual().getY()-15, 30, 30, null);
		}
		ArrayList<OndaExpansiva> copia = (ArrayList<OndaExpansiva>) Observador.getInstancia().getExplosiones().clone();
		for (OndaExpansiva o : copia) {
			g.drawImage(explosion, o.getPos().getX() - 8, o.getPos().getY() - 8, (int) o.getRadio(), (int) o.getRadio(),
					null);
			g.drawImage(explosion, (int) (o.getPos().getX() - o.getRadio() + 8),
					(int) (o.getPos().getY() - o.getRadio() + 8), (int) o.getRadio(), (int) o.getRadio(), null);
			g.drawImage(explosion, (int) (o.getPos().getX() - o.getRadio() + 8), (int) (o.getPos().getY() - 8),
					(int) o.getRadio(), (int) o.getRadio(), null);
			g.drawImage(explosion, (int) (o.getPos().getX() - 8), (int) (o.getPos().getY() - o.getRadio() + 8),
					(int) o.getRadio(), (int) o.getRadio(), null);
		}
		ArrayList<MisilKrytoliano> copiaMisil = (ArrayList<MisilKrytoliano>) Enemigo.getInstancia().getArregloMisiles()
				.clone();
		for (MisilKrytoliano m : copiaMisil) {
			g.drawImage(misil, m.getActual().getX(), m.getActual().getY(), 5, 5, null);
			LinkedList<Coordenada> copiaEst=(LinkedList<Coordenada>) m.getEstela().getEstela();
			for(Coordenada c: copiaEst) {
				g.drawImage(misil, c.getX()+2, c.getY(), 1, 1, null);
			}
		}
		ArrayList<Misil> copiaJugador = (ArrayList<Misil>) Jugador.getInstancia().getMisilesDisparados().clone();
		for (Misil m : copiaJugador) {
			g.drawImage(misil2, m.getActual().getX(), m.getActual().getY(), 5, 5, null);
			LinkedList<Coordenada> copiaEst=(LinkedList<Coordenada>) m.getEstela().getEstela();
			for(Coordenada c: copiaEst) {
				g.drawImage(misil2, c.getX()+2, c.getY(), 1, 6, null);
			}
		}
		ArrayList<Base> copiaBase = (ArrayList<Base>) Juego.getInstancia().getBases().clone();
		for (Base b : copiaBase) {
			g.drawImage(base, b.getPos().getX() - 45, b.getPos().getY() - 80, 80, 80, null);
		}
		ArrayList<Ciudad> copiaCiudad = (ArrayList<Ciudad>) Juego.getInstancia().getCiudades().clone();
		for (Ciudad c : copiaCiudad) {
			g.drawImage(ciudad, c.getPos().getX() - 45, c.getPos().getY() - 50, 60, 60, null);
		}
	}

	public static PanelJuego getInstancia() {
		return PanelJuego.instancia;
	}
}