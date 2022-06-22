package misiles;

import java.util.LinkedList;

import gestor.Coordenada;

public class Estela {
	private LinkedList<Coordenada> estela = new LinkedList<Coordenada>();

	public LinkedList<Coordenada> getEstela() {
		return estela;
	}

	public void setEstela(LinkedList<Coordenada> estela) {
		this.estela = estela;
	}

}
