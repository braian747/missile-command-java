package misiles;

import javax.sound.sampled.Clip;

import gestor.Controlador;
import gestor.Coordenada;

public abstract class Misil {
	private static final int RADIO = 1;
	private double velocidad;
	private Coordenada origen = new Coordenada();
	private Coordenada destino = new Coordenada();
	private Coordenada actual = new Coordenada();
	private Estela estela = new Estela();
	private Clip sonidoExplosion = Controlador.cargarSonido("/sonidos/explosion.wav");

	public Misil() {
	}

	public Misil(Coordenada origen, Coordenada destino, double velocidad) {
		this.destino.setX(destino.getX());
		this.destino.setY(destino.getY());
		this.origen.setX(origen.getX());
		this.origen.setY(origen.getY());
		this.actual.setX(origen.getX());
		this.actual.setY(origen.getY());
		this.velocidad = velocidad;

	}

	public Coordenada obtenerMovimiento(Coordenada destino) {

		int movimientoX;
		int movimientoY;
		int distanciaY = destino.getY() - getActual().getY();
		int distanciaX = destino.getX() - getActual().getX();
		double a = Math.atan2(distanciaY, distanciaX);
		movimientoY = (int) (Math.sin(a) * this.velocidad);
		movimientoX = (int) (Math.cos(a) * this.velocidad);
		Coordenada c = new Coordenada(movimientoX, movimientoY);
		return c;
	}

	public Coordenada mover() {
		Coordenada c = new Coordenada();
		c = obtenerMovimiento(destino);
		this.actual.setX(this.actual.getX() + c.getX());
		this.actual.setY(this.actual.getY() + c.getY());
		//actualizarEstela();
		return actual;
	}

	public void actualizarEstela() {
		Coordenada c = new Coordenada(actual.getX(), actual.getY());
		this.estela.getEstela().addLast(c);
	}

	public Clip getSonidoExplosion() {
		return sonidoExplosion;
	}

	public void setSonidoExplosion(Clip sonidoExplosion) {
		this.sonidoExplosion = sonidoExplosion;
	}

	public int getRadio() {
		return RADIO;
	}

	public Coordenada getOrigen() {
		return origen;
	}

	public void setOrigen(Coordenada origen) {
		this.origen = origen;
	}

	public Coordenada getDestino() {
		return destino;
	}

	public void setDestino(Coordenada destino) {
		this.destino = destino;
	}

	public Coordenada getActual() {
		return actual;
	}

	public void setActual(Coordenada actual) {
		this.actual = actual;
	}

	public Estela getEstela() {
		return estela;
	}

	public void setEstela(Estela estela) {
		this.estela = estela;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

}
