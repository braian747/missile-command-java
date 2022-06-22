package estructuras;

import java.util.ArrayList;

import gestor.Coordenada;
import misiles.MAB;

public class Base extends Estructura {
	private int misiles;
	private String nombre;
	private ArrayList<MAB> arregloMAB = new ArrayList<MAB>();

	public Base(Coordenada pos, String nombre) {
		super(pos);
		this.nombre = nombre;
		for (int i = 0; i < 10; i++) {
			MAB m = new MAB();
			this.arregloMAB.add(m);// agrego 10 misiles a cada base(sin destino)

		}
	}

	public ArrayList<MAB> getArregloMAB() {
		return arregloMAB;
	}

	public void setArregloMAB(ArrayList<MAB> arregloMAB) {
		this.arregloMAB = arregloMAB;
	}

	public int getMisiles() {
		return misiles;
	}

	public void setMisiles(int misiles) {
		this.misiles = misiles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return nombre;
	}

}
