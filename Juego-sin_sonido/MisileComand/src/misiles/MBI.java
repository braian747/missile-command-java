package misiles;

import gestor.Coordenada;

public class MBI extends MisilKrytoliano {
	private double altMinima;

	public MBI(Coordenada destino, Coordenada origen, double v) {
		super(destino, origen, v);
		this.altMinima = 200;
	}

	public double getAltMinima() {
		return altMinima;
	}

	public void setAltMinima(double altMinima) {
		this.altMinima = altMinima;
	}

}
