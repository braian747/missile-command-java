package gestor;

import java.util.ArrayList;
import estructuras.Base;
import estructuras.Estructura;
import jugadores.Enemigo;
import jugadores.Jugador;
import misiles.MAB;
import misiles.Misil;
import misiles.MisilKrytoliano;
import misiles.OndaExpansiva;

/**
 * la clase observador es el semidios del trabajo, se va a encargar de detectar
 * colisiones, explotar misiles,estructuras,bases todo.
 * 
 *
 */
public class Observador {
	private static Observador instancia = new Observador();
	private ArrayList<OndaExpansiva> explosiones = new ArrayList<OndaExpansiva>();
	private ArrayList<Misil> mabDestruidos = new ArrayList<Misil>();
	private ArrayList<Misil> mbiDestruidos = new ArrayList<Misil>();
	ArrayList<Estructura> estDestruidas = new ArrayList<Estructura>();
	private Observador() {
	}

	public static Observador getInstancia() {
		return Observador.instancia;
	}

	public void destruccionMisil() {
		
		ArrayList<Coordenada> elim = new ArrayList<Coordenada>();
		for (MAB mab : Jugador.getInstancia().getMisilesDisparados()) {
			if ((distanciaEntreDosPuntos(mab.getActual(), mab.getDestino()) <= 5)) {// si llego a su destino genera la
																					// explosion y elimina al misil
			
				mabDestruidos.add(mab);
				generarExplosion(mab);
				for (Coordenada c : mab.getEstela().getEstela()) {
					elim.add(c);

				}
				for (Coordenada e : elim) {
					mab.getEstela().getEstela().remove(e);
				}
			}
			
		}
		ArrayList<OndaExpansiva> exp = new ArrayList<OndaExpansiva>();
		for (MisilKrytoliano mk : Enemigo.getInstancia().getArregloMisiles()) {// para cada misil enemigo comprueba si
																				// choco con la explosion de algun misil
																				// de jugador
			for (OndaExpansiva o : explosiones) {
				if ((distanciaEntreDosPuntos(mk.getActual(), o.getPos())) <= (o.getRadio() - mk.getRadio())) {
					mbiDestruidos.add(mk);
					if (mk.getActual().getY() < 640) {// si la altura es mayor a 640(cerca del piso) no suma puntos
														// porque no se destruyo por una explosion del mab
						Puntaje.getInstancia().setMbiDestruidos(Puntaje.getInstancia().getMbiDestruidos() + 1);
					}
					OndaExpansiva oe = new OndaExpansiva(1, mk.getActual());
					exp.add(oe);

					for (Coordenada c : mk.getEstela().getEstela()) {
						elim.add(c);

					}

				}

			}
			for (OndaExpansiva o : exp) {
				explosiones.add(o);

			}
			if ((distanciaEntreDosPuntos(mk.getActual(), mk.getDestino())) <= 8) {
				generarExplosion(mk);
				mbiDestruidos.add(mk);

				for (Coordenada c : mk.getEstela().getEstela()) {
					elim.add(c);

				}
			}
			for (Coordenada e : elim) {
				mk.getEstela().getEstela().remove(e);
			}
		}
		for (Misil e : mabDestruidos) {
			Jugador.getInstancia().getMisilesDisparados().remove(e);
		}

		for (Misil e : mbiDestruidos) {
			Enemigo.getInstancia().getArregloMisiles().remove(e);
		}

	}

	public void destruccionBombardero() {
		OndaExpansiva explosion = new OndaExpansiva();
		for (OndaExpansiva o : explosiones) {

			if ((distanciaEntreDosPuntos(Enemigo.getInstancia().getB().getActual(), o.getPos())) <= (o.getRadio()
					- Enemigo.getInstancia().getB().getRadio())) {
				Enemigo.getInstancia().setB(null);
				OndaExpansiva oe = new OndaExpansiva(1, o.getPos());
				explosion = oe;
				Puntaje.getInstancia().setbDestruidos(Puntaje.getInstancia().getbDestruidos() + 1);
				break;

			}

		}
		explosiones.add(explosion);

	}

	public void destruccionEstructura() {
		
		ArrayList<Coordenada> elim = new ArrayList<Coordenada>();
		for (MisilKrytoliano mk : Enemigo.getInstancia().getArregloMisiles()) {// para cada misil comprueba si choco con
																				// una estructura
			for (Estructura c : Juego.getInstancia().getCiudades()) {// choque con ciudades
				if ((distanciaEntreDosPuntos(mk.getActual(), c.getPos())) <= (c.getRadio() - mk.getRadio())) {
					generarExplosion(mk);
					estDestruidas.add(c);
					mbiDestruidos.add(mk);
					Coordenada c1 = new Coordenada(c.getPos().getX() - 10, c.getPos().getY() - 10);
					OndaExpansiva explosion = new OndaExpansiva(1, c1);
					explosiones.add(explosion);
					for (Coordenada coord : mk.getEstela().getEstela()) {
						elim.add(coord);
					}
					Juego.getInstancia().setBonusCity(c.getPos());
					break;
				}

			}
			for (Estructura b : Juego.getInstancia().getBases()) {// choque con bases
				if ((distanciaEntreDosPuntos(mk.getActual(), b.getPos())) <= (b.getRadio() - mk.getRadio())) {
					generarExplosion(mk);
					estDestruidas.add(b);
					mbiDestruidos.add(mk);
					Coordenada c = new Coordenada(b.getPos().getX() - 10, b.getPos().getY() - 10);
					OndaExpansiva explosion = new OndaExpansiva(1, c);
					explosiones.add(explosion);
					for (Coordenada c1 : mk.getEstela().getEstela()) {
						elim.add(c1);

					}
					break;
				}

			}

			for (Coordenada e : elim) {
				mk.getEstela().getEstela().remove(e);
			}

		}
		for (Misil e : mbiDestruidos) {
			Enemigo.getInstancia().getArregloMisiles().remove(e);

		}
		for (Estructura e : estDestruidas) {
			if (e instanceof Base)
				Juego.getInstancia().getBases().remove(e);
			else
				Juego.getInstancia().getCiudades().remove(e);

		}

	}

	/**
	 * recorre las listas de misiles,estelas,etc y las elimina de la pantalla
	 */
	public void limpiarPantalla() {
		ArrayList<Misil> eliminables = new ArrayList<Misil>();
		ArrayList<Coordenada> elim = new ArrayList<Coordenada>();
		for (Misil mab : Jugador.getInstancia().getMisilesDisparados()) {
			eliminables.add(mab);

			for (Coordenada c : mab.getEstela().getEstela()) {
				elim.add(c);

			}
			for (Coordenada e : elim) {
				mab.getEstela().getEstela().remove(e);
			}
		}

		Enemigo.getInstancia().setB(null);
		for (Misil e : eliminables) {
			Jugador.getInstancia().getMisilesDisparados().remove(e);

		}
	}

	public void generarExplosion(Misil m) {
		OndaExpansiva o = new OndaExpansiva(1, m.getActual());
		explosiones.add(o);
	}

	public void animacionExplosiones() {
		ArrayList<OndaExpansiva> eliminables = new ArrayList<OndaExpansiva>();
		for (OndaExpansiva o : explosiones) {
			o.generarOnda();
			if (o.getEstado() == 0 && o.getRadio() <= 1) {
				eliminables.add(o);
			}
		}
		for (OndaExpansiva o : eliminables) {
			explosiones.remove(o);
		}
	}
	public void liberarMemoria(){
		mabDestruidos.clear();
		mbiDestruidos.clear();
		estDestruidas.clear();
		System.gc();
	}

	public double distanciaEntreDosPuntos(Coordenada c1, Coordenada c2) {
		return Math.sqrt(Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2));
	}

	public void moverMisiles() {
		for (MisilKrytoliano i : Enemigo.getInstancia().getArregloMisiles()) {
			i.setActual(i.mover());
		}
		for (Misil m : Jugador.getInstancia().getMisilesDisparados()) {
			m.setActual(m.mover());
		}
	}

	public void comprobarColisiones() {
		destruccionEstructura();
		destruccionMisil();
		if (Nivel.getInstancia().getNumNivel() >= 2 && (Enemigo.getInstancia().getB() != null)) {
			destruccionBombardero();
		}
	}

	public void moverBombardero() {
		if (Nivel.getInstancia().getNumNivel() >= 2 && (Enemigo.getInstancia().getB() != null))
			Enemigo.getInstancia().getB().setActual(Enemigo.getInstancia().getB().mover());

	}

	/**
	 * mueve todos los misiles y realiza la animacion de explosion.
	 */
	public void actualizar() {
		moverMisiles();
		if (Nivel.getInstancia().getNumNivel() >= 2 && (Enemigo.getInstancia().getB() != null)) {
			moverBombardero();
		}
		comprobarColisiones();
		animacionExplosiones();
	}

	public ArrayList<OndaExpansiva> getExplosiones() {
		return explosiones;
	}

	public void setExplosiones(ArrayList<OndaExpansiva> explosiones) {
		this.explosiones = explosiones;
	}

}