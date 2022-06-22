package misiles;

import javax.sound.sampled.Clip;
import gestor.Controlador;
import gestor.Coordenada;

public class MAB extends Misil {
	private Clip sonido = Controlador.cargarSonido("/sonidos/misil.wav");

	public MAB() {

	}

	public MAB(Coordenada origen, Coordenada destino, double velocidad) {
		super(origen, destino, velocidad);
		sonido.start();

	}

	public Clip getSonido() {
		return sonido;
	}

	public void setSonido(Clip sonido) {
		this.sonido = sonido;
	}

}
