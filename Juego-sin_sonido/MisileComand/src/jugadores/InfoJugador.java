package jugadores;

import java.io.Serializable;

public class InfoJugador implements Serializable, Comparable<InfoJugador> {
	private static final long serialVersionUID = 1L;
	private int posicion;
	private String nombre;
	private int puntaje;
	private long tiempo;

	public InfoJugador(String nombre, int puntaje, long tiempo) {
		super();
		this.nombre = nombre;
		this.puntaje = puntaje;
		this.tiempo = tiempo;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	@Override
	public int compareTo(InfoJugador i) {
		int res = 0;
		if (puntaje > i.puntaje) {
			res = -1;
		} else if (puntaje < i.puntaje) {
			res = 1;
		} else { // si hay empate, se desempata por tiempo ascendente
			if (tiempo < i.tiempo) {
				res = -1;
			}
			if (tiempo > i.tiempo) {
				res = 1;
			}

		}
		return res;
	}
}
