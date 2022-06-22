package gestor;

import estructuras.Base;

public class Puntaje {
	private static Puntaje instancia = new Puntaje();
	private int mbiDestruidos;
	private int mcDestruidos;
	private int bDestruidos;
	private int puntajeTotal;
	private int bonus;

	private Puntaje() {

	}

	public int puntajeMbi() {
		return mbiDestruidos * 25;
	}

	public int puntajeMisilesCrucero() {
		return mcDestruidos * 125;
	}

	public int puntajeMisilesSinUsar() {
		int n = 0;
		for (Base b : Juego.getInstancia().getBases()) {
			n += b.getArregloMAB().size();
		}
		return n * 5;
	}

	public int puntajeCiudadesSalvadas() {

		return Juego.getInstancia().getCiudades().size() * 100;
	}

	public int puntajeBombarderos() {
		return bDestruidos * 25;
	}

	public int calcularPuntaje() {// puntaje por nivel
		puntajeTotal += puntajeCiudadesSalvadas() + puntajeMbi() + puntajeMisilesCrucero() + puntajeMisilesSinUsar()
				+ puntajeBombarderos();
		return puntajeCiudadesSalvadas() + puntajeMbi() + puntajeMisilesCrucero() + puntajeMisilesSinUsar()
				+ puntajeBombarderos();
	}

	public int puntajeParcial() {
		return puntajeMbi() + puntajeMisilesCrucero() + puntajeBombarderos() + getPuntajeTotal() + getBonus();
	}

	public int getMbiDestruidos() {
		return mbiDestruidos;
	}

	public void setMbiDestruidos(int mbiDestruidos) {
		this.mbiDestruidos = mbiDestruidos;
	}

	public int getMcDestruidos() {
		return mcDestruidos;
	}

	public void setMcDestruidos(int mcDestruidos) {
		this.mcDestruidos = mcDestruidos;
	}

	public int getbDestruidos() {
		return bDestruidos;
	}

	public void setbDestruidos(int bDestruidos) {
		this.bDestruidos = bDestruidos;
	}

	public int getPuntajeTotal() {
		return puntajeTotal;
	}

	public void setPuntajeTotal(int puntajeTotal) {
		this.puntajeTotal = puntajeTotal;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void resetearPuntaje() {
		setbDestruidos(0);
		setMcDestruidos(0);
		setMbiDestruidos(0);
		setPuntajeTotal(0);
		setBonus(0);
	}

	public static Puntaje getInstancia() {
		return Puntaje.instancia;
	}
}
