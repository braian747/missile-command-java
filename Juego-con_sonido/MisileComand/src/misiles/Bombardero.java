package misiles;

import javax.sound.sampled.Clip;

import gestor.Controlador;
import gestor.Coordenada;
import gestor.Juego;
import gestor.Nivel;
import jugadores.Enemigo;

public class Bombardero {
	private static final int RADIO = 1;
	private int altura; // altura inicial
	private int posX; // posX donde va a disparar el misil
	private double velocidad;
	private Coordenada origen = new Coordenada();
	private Coordenada destino = new Coordenada();
	private Coordenada actual = new Coordenada();
	private boolean estado = true; // cuando dispare lo cambio a false, asi evito que dispare 3920471238904623
									// misiles
	private Clip sonido = Controlador.cargarSonido("/sonidos/bombardero.wav");
	private Clip sonidoExplosion = Controlador.cargarSonido("/sonidos/explosion.wav");

	public Bombardero(String nombre, double velocidad, int altura) {
		this.nombre = nombre;
		this.altura = altura;

		this.posX = (int) (Math.random() * 525 + 15);
		this.origen.setX(0);
		this.origen.setY(altura);
		this.destino.setX(800);
		this.destino.setY(altura);
		this.actual.setX(origen.getX());
		this.actual.setY(origen.getY());
		this.velocidad = velocidad;

	}

	public Clip getSonidoExplosion() {
		return sonidoExplosion;
	}

	public void setSonidoExplosion(Clip sonidoExplosion) {
		this.sonidoExplosion = sonidoExplosion;
	}

	public Clip getSonido() {
		return sonido;
	}

	public void setSonido(Clip sonido) {
		this.sonido = sonido;
	}

	public int getRadio() {
		return RADIO;
	}

	public Coordenada getActual() {
		return actual;
	}

	public void setActual(Coordenada actual) {
		this.actual = actual;
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

	private String nombre;

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Coordenada mover() {
		Coordenada c = new Coordenada();
		c = obtenerMovimiento(destino);
		this.actual.setX(this.actual.getX() + c.getX());
		if (actual.getX() >= posX && estado == true && !Juego.getInstancia().getCiudades().isEmpty()
				&& !Juego.getInstancia().getBases().isEmpty()) {
			disparar();
			estado = false;
		}
		return actual;
	}

	public Coordenada obtenerMovimiento(Coordenada destino) {
		int movimientoX;
		int distanciaX = destino.getX() - getActual().getX();
		double a = Math.atan2(0, distanciaX);
		movimientoX = (int) (Math.cos(a) + 0.5);
		Coordenada c = new Coordenada(movimientoX, 0);
		return c;
	}

	public void disparar() {
		int cantMbi = (int) Math.random() * 4 + 2;
		for (int i = 1; i <= cantMbi; ++i) {
			dispararMBI();
		}
	}

	public void dispararMBI() {
		Coordenada origen = new Coordenada(this.actual.getX(), altura);
		Coordenada destino = null;
		int random = (int) Math.floor(Math.random() * (1 + 1));
		if (random == 0) {
			destino = atacarCiudad();
		} else if (!Juego.getInstancia().getBases().isEmpty()) {
			destino = atacarBase();// recorre la lista de ciudades y obtiene la coordenada destino
		}
		MisilKrytoliano mbi = new MBI(destino, origen, Nivel.getInstancia().getVelMBI());
		Enemigo.getInstancia().getArregloMisiles().add(mbi);
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

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

}