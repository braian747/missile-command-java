package misiles;

import gestor.Coordenada;

public class Sensor {

	private double radio;

	public Sensor(double radio) {
		this.radio = radio;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public boolean detectarMAB(Coordenada x) {
		return true;
	}

}
