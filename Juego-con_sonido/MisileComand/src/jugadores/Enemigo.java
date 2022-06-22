package jugadores;

import java.util.ArrayList;

import gestor.Coordenada;
import gestor.Juego;
import gestor.Nivel;
import misiles.Bombardero;
import misiles.MBI;
import misiles.MisilKrytoliano;

/**
 * 
 * Clase que va a crear los misiles enemigos, posee los metodos para mover los
 * mismos, etc.
 *
 */
public class Enemigo {
	private static Enemigo instancia = new Enemigo();
	private int cantM;
	private Bombardero b;
	private Boolean lanzarB;
	private ArrayList<MisilKrytoliano> arregloMisiles = new ArrayList<MisilKrytoliano>();

	private Enemigo() {

	}

	public void atacar(int ciclo) {
		if (Juego.getInstancia().getCiudades().isEmpty()) {
			setCantM(0);
		}
		if (ciclo % 200 == 0 && cantM > 0 && !Juego.getInstancia().getCiudades().isEmpty()) {
			int grupoMisiles = (int) Math.floor(Math.random() * (4 - 3 + 1) + 3);// elige al azar cuantos misiles va a
																					// lanzar a la vez(3 o 4);
			if (grupoMisiles <= cantM) {// si quedan suficientes, se lanza el grupo completo de misiles
				for (int i = 0; i < grupoMisiles; i++) {
					dispararMBI();

					setCantM(getCantM() - 1);
				}

			} else { // si hay menos misiles que los necesarios para formar un grupo(3 o 4)se lanzan
						// los misiles sobrantes
				for (int i = 0; i < cantM; i++) {
					dispararMBI();
					setCantM(getCantM() - 1);
				}
			}
		}
		if ((Nivel.getInstancia().getNumNivel() >= 2) && (lanzarB == true) && (cantM < 10)) {
			b = new Bombardero("avion", 1.9, Nivel.getInstancia().getAlturaBomb());
			b.getSonido().start();
			setLanzarB(false);
		}
	}

	public Bombardero getB() {
		return b;
	}

	public void setB(Bombardero b) {
		this.b = b;
	}

	public void dispararMBI() {
		int origenX = (int) (Math.random() * 525 + 1);// genera una coordenada aleatoria para el origen del misil(en y
														// es siempre la misma(0))
		Coordenada origen = new Coordenada(origenX, 0);
		Coordenada destino = atacarCiudad();
		int random = (int) Math.floor(Math.random() * (1 + 1));
		if (random == 0) {
			destino = atacarCiudad();
		} else if (!Juego.getInstancia().getBases().isEmpty()) {
			destino = atacarBase();// recorre la lista de ciudades y obtiene la coordenada destino
		}

		MisilKrytoliano mbi = new MBI(destino, origen, Nivel.getInstancia().getVelMBI());
		arregloMisiles.add(mbi);
	}

	public Coordenada atacarBase() {
		int baseObjetivo = (int) (Math.random() * (Juego.getInstancia().getBases().size() - 1)); // elige una base al
																									// azar idetificados
																									// de 0 a 2
		if (!Juego.getInstancia().getBases().contains(Juego.getInstancia().getBases().get(baseObjetivo))) {
			baseObjetivo = 0;
			while (!Juego.getInstancia().getBases().contains(Juego.getInstancia().getBases().get(baseObjetivo))) {// mientras
																													// la
																													// ciudad
																													// objetivo
																													// este
																													// destruida
																													// elige
																													// otra
																													// comenzando
																													// desde
																													// 0
				++baseObjetivo;
			}
		}
		Coordenada destino = Juego.getInstancia().getBases().get(baseObjetivo).getPos();// obtiene la coordenada del
																						// objetivo elegido
		return destino;
	}

	public Coordenada atacarCiudad() {
		int ciudadObjetivo = (int) (Math.random() * (Juego.getInstancia().getCiudades().size() - 1));// elige una ciudad
																										// al azar
																										// idetificados
																										// de 0 a 5
		if (!Juego.getInstancia().getCiudades().isEmpty() && !Juego.getInstancia().getCiudades()
				.contains(Juego.getInstancia().getCiudades().get(ciudadObjetivo))) {
			ciudadObjetivo = 0;
			while (!Juego.getInstancia().getCiudades()
					.contains(Juego.getInstancia().getCiudades().get(ciudadObjetivo))) {// mientras la estructura
																						// objetivo este destruida elige
																						// otra comenzando desde 0
				++ciudadObjetivo;
			}
		}
		Coordenada destino = Juego.getInstancia().getCiudades().get(ciudadObjetivo).getPos();// obtiene la coordenada
																								// del objetivo elegido
		return destino;
	}

	public ArrayList<MisilKrytoliano> getArregloMisiles() {
		return arregloMisiles;
	}

	public void setArregloMisiles(ArrayList<MisilKrytoliano> arregloMisiles) {
		this.arregloMisiles = arregloMisiles;
	}

	public void dispararMC() {

	}

	public void bombadero() {

	}

	public int getCantM() {
		return cantM;
	}

	public void setCantM(int cantM) {
		this.cantM = cantM;
	}

	public Boolean getLanzarB() {
		return lanzarB;
	}

	public void setLanzarB(Boolean lanzarB) {
		this.lanzarB = lanzarB;
	}

	public void inicializarCantMisiles() {
		this.cantM = (int) Math.floor(Math.random() * (17 - 12 + 1) + 12);// elige al azar cuantos misiles va a lanzar,
																			// entre 12 y 17
	}

	public static Enemigo getInstancia() {
		return Enemigo.instancia;
	}

}
