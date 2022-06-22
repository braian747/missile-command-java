package gestor;

import taller2.grafico.Dibujable;
import taller2.grafico.InformacionDibujable;

public class Coordenada implements Dibujable {
	private int x;
	private int y;

	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordenada() {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public InformacionDibujable getInformacionDibujable() {
		InformacionDibujable d = new InformacionDibujable(this.getX(), this.getY(), '.');
		return d;
	}

	@Override
	public String toString() {
		return "" + x + "," + y;
	}

}
