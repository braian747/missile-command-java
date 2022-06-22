package estructuras;


import gestor.Coordenada;

public class Estructura {
	private static final int RADIO = 35;
	
	private Coordenada pos = new Coordenada();

	public int getRadio() {
		return RADIO;
	}

	public Estructura(Coordenada pos) {
		this.pos.setX(pos.getX());
		this.pos.setY(pos.getY());
	}

	public Coordenada getPos() {
		return pos;
	}

	public void setPos(Coordenada pos) {
		this.pos = pos;
	}



}
