package gestor;

import java.util.ArrayList;
import estructuras.Base;
import estructuras.Ciudad;
import jugadores.Enemigo;
import jugadores.Jugador;

/**
 * 
 * La clase juego es la encargada de la creacion y posicionamiento de todas las
 * bases,ciudades.
 *
 */
public class Juego {

	private Juego() {

	}

	private static Juego instancia = new Juego();
	private ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
	private ArrayList<Base> bases = new ArrayList<Base>();
	private Coordenada bonusCity;
	private boolean bonusCityCreada;

	public void inicializar() {
		Coordenada c1 = new Coordenada(45, 665);
		Base alpha = new Base(c1, "Alpha");
		bases.add(alpha);
		Coordenada c2 = new Coordenada(335, 665);
		Base delta = new Base(c2, "Delta");
		bases.add(delta);
		Coordenada c3 = new Coordenada(615, 665);
		Base omega = new Base(c3, "Omega");
		bases.add(omega);
		Coordenada c4 = new Coordenada(130, 665);
		Ciudad ciu1 = new Ciudad(c4);
		ciudades.add(ciu1);
		Coordenada c5 = new Coordenada(200, 665);
		Ciudad ciu2 = new Ciudad(c5);
		ciudades.add(ciu2);
		Coordenada c6 = new Coordenada(270, 665);
		Ciudad ciu3 = new Ciudad(c6);
		ciudades.add(ciu3);
		Coordenada c7 = new Coordenada(415, 665);
		Ciudad ciu4 = new Ciudad(c7);
		ciudades.add(ciu4);
		Coordenada c8 = new Coordenada(485, 665);
		Ciudad ciu5 = new Ciudad(c8);
		ciudades.add(ciu5);
		Coordenada c9 = new Coordenada(555, 665);
		Ciudad ciu6 = new Ciudad(c9);
		ciudades.add(ciu6);
		Enemigo.getInstancia().setLanzarB(true);
		Nivel.getInstancia().inicializarNivel();
	

	}

	public boolean isBonusCityCreada() {
		return bonusCityCreada;
	}

	public void setBonusCityCreada(boolean bonusCityCreada) {
		this.bonusCityCreada = bonusCityCreada;
	}

	public Coordenada getBonusCity() {
		return bonusCity;
	}

	public void setBonusCity(Coordenada bonusCity) {
		this.bonusCity = bonusCity;
	}

	public ArrayList<Base> getBases() {
		return bases;
	}

	public void setBases(ArrayList<Base> bases) {
		this.bases = bases;
	}

	public ArrayList<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(ArrayList<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	/**
	 * el metodo gameLoop se encagar de realizar la creacion de los misiles, y
	 * actualizar su movimiento llamando a los respectivos metodos necesarios
	 */
	public void gameLoop() {
		Enemigo.getInstancia().atacar(Controlador.getInstancia().getCiclo());
		Jugador.getInstancia().disparar();
		Observador.getInstancia().actualizar();
		Controlador.getInstancia().setCiclo(Controlador.getInstancia().getCiclo() + 1);

	}

	/**
	 * 
	 * @param numero  siguiente nivel
	 * @param control es utilizado para saber cuando aumentar el multiplicador de
	 *                puntaje
	 */
	public void nuevoNivel(int n, int control) {
		bonusCity();
		Nivel.getInstancia().setNumNivel(Nivel.getInstancia().getNumNivel() + 1);
		Observador.getInstancia().limpiarPantalla();
		Nivel.getInstancia().setVelMBI(Nivel.getInstancia().getVelMBI() + 0.2);
		Enemigo.getInstancia().setLanzarB(true);
		Nivel.getInstancia().setAlturaBomb(Nivel.getInstancia().getAlturaBomb() + 50);
		if (Nivel.getInstancia().getAlturaBomb() > 400) {
			Nivel.getInstancia().setAlturaBomb(400);
		}
		this.bases.clear();
		Coordenada c1 = new Coordenada(45, 665);
		Base alpha = new Base(c1, "Alpha");
		bases.add(alpha);
		Coordenada c2 = new Coordenada(335, 665);
		Base delta = new Base(c2, "Delta");
		bases.add(delta);
		Coordenada c3 = new Coordenada(615, 665);
		Base omega = new Base(c3, "Omega");
		bases.add(omega);
		Nivel.getInstancia().setNumNivel(n);
		if (control <= 2) {
			Nivel.getInstancia().setMci(false);
			Nivel.getInstancia().setMct(true);
		} else {
			if (control <= 4) {
				Nivel.getInstancia().setMci(true);
				Nivel.getInstancia().setMct(false);
			} else {
				control = 1;
				Nivel.getInstancia().setMci(false);
				Nivel.getInstancia().setMct(true);
			}
		}
	}

	public void bonusCity() {
		if (Puntaje.getInstancia().getPuntajeTotal() >= 10000 && (ciudades.size() < 6) && (!bonusCityCreada)) {
			Ciudad c = new Ciudad(bonusCity);// la coordenada de bonusCity se setea en Observador, en la posicion de la
												// ultima ciudad destruida(metodo destruccionEstructura)
			ciudades.add(c);
			setBonusCityCreada(true);
		}
	}

	/**
	 * limpia todas las listas
	 */
	public void fin() {
		ciudades.clear();
		bases.clear();
		Jugador.getInstancia().getMisilesDisparados().clear();
		Enemigo.getInstancia().getArregloMisiles().clear();
		Enemigo.getInstancia().setB(null);
		Observador.getInstancia().limpiarPantalla();
		Observador.getInstancia().getExplosiones().clear();

	}

	public static Juego getInstancia() {
		return Juego.instancia;
	}
}