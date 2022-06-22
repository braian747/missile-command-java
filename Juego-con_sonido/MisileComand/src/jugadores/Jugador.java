package jugadores;

import java.util.ArrayList;
import estructuras.Base;
import gestor.Coordenada;
import gestor.Juego;
import gestor.Observador;
import misiles.MAB;

/**
 * 
 * El jugador posee una lista con los destinos, esta lista se carga cuando el
 * jugador haga clic en la pantalla esa coodernada se almacena, luego el metodo
 * disparar envia un misil a ese destino.
 *
 */

public class Jugador {
	private static Jugador instancia = new Jugador();
	private ArrayList<Coordenada> destinos = new ArrayList<Coordenada>();
	private ArrayList<MAB> misilesDisparados = new ArrayList<MAB>(); // arreglo de misiles activos de jugador

	public static Jugador getInstancia() {
		return Jugador.instancia;
	}

	private Jugador() {

	}

	public Base baseMasCercana(Coordenada c) {// retorna la base mas cercana y que tenga misiles
		double distMin = 99999;
		double dist = 0;
		Base base = null;
		for (Base b : Juego.getInstancia().getBases()) {
			if (b.getArregloMAB().isEmpty()) {// si la base no tiene misiles pasa a la siguiente
				continue;
			}
			dist = Observador.getInstancia().distanciaEntreDosPuntos(c, b.getPos());
			if (dist < distMin) {
				distMin = dist;
				base = b;
			}
		}

		return base;
	}

	public void disparar() {
		ArrayList<Coordenada> destinosElegidos = new ArrayList<Coordenada>();// guarda los destinos a donde ya se
																				// dispararon para eliminarlos de la
																				// lista de destinos
		for (Coordenada c : destinos) {// para cada destinos elige la base mas cercana para disparar
			Base base = baseMasCercana(c);
			if (base != null) {// Si la base tiene misiles
				int posBase = Juego.getInstancia().getBases().indexOf(base);
				Coordenada origen = new Coordenada(base.getPos().getX(), base.getPos().getY() - 75);
				MAB mab = new MAB(origen, c, 12);
				misilesDisparados.add(mab);
				Juego.getInstancia().getBases().get(posBase).getArregloMAB()
						.remove(Juego.getInstancia().getBases().get(posBase).getArregloMAB().get(0));// elimina el
																										// primer misil
																										// de la lista
				destinosElegidos.add(c);
			}
		}
		for (Coordenada d : destinosElegidos) {
			this.destinos.remove(d);
		}
	}

	public int cantMisiles() {
		int total = 0;
		for (Base b : Juego.getInstancia().getBases()) {
			total += b.getArregloMAB().size();
		}

		return total;
	}

	public ArrayList<MAB> getMisilesDisparados() {
		return misilesDisparados;
	}

	public void setMisilesDisparados(ArrayList<MAB> misilesDisparados) {
		this.misilesDisparados = misilesDisparados;
	}

	public ArrayList<Coordenada> getDestinos() {
		return destinos;
	}

	public void setDestinos(ArrayList<Coordenada> destinos) {
		this.destinos = destinos;
	}

}
