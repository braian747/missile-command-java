package estructuras;

import gestor.Coordenada;

public class Ciudad extends Estructura {

	private int dimX;
	private int dimY;

	public Ciudad(Coordenada pos) {
		super(pos);
	}

	public int getDimX() {
		return dimX;
	}

	public void setDimX(int dimX) {
		this.dimX = dimX;
	}

	public int getDimY() {
		return dimY;
	}

	public void setDimY(int dimY) {
		this.dimY = dimY;
	}

}
