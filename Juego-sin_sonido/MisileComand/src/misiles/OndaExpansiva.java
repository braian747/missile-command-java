package misiles;

import gestor.Coordenada;

public class OndaExpansiva {
	private double radio;
	private double radiomax;
	private Coordenada pos = new Coordenada();
	private int estado;// indica si la onda se esta expandiendo(1) o reduciendo(0)

	public OndaExpansiva(double radio, Coordenada pos) {
		this.radio = radio;
		this.pos = pos;
		this.estado = 1;
		this.radiomax = 40;
	}

	public OndaExpansiva() {
	}

	public double getRadiomax() {
		return radiomax;
	}

	public void setRadiomax(double radiomax) {
		this.radiomax = radiomax;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Coordenada getPos() {
		return pos;
	}

	public void setPos(Coordenada pos) {
		this.pos = pos;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public void generarOnda() {
		if (getRadio() <= getRadiomax() && estado == 1) {
			setRadio((getRadio() + 1));
		} else {
			setEstado(0);
			if (getRadio() >= 1) {
				setRadio((getRadio() - 1));
			}
		}

	}

}
