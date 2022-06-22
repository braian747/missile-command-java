package gestor;

public class CampoDeJuego {
	public static CampoDeJuego instancia = new CampoDeJuego();
	private int ancho;
	private int alto;
	private int umbral;

	private CampoDeJuego() {
		this.ancho = 650;
		this.alto = 700;
		this.umbral = 550;
	}

	public static CampoDeJuego getInstancia() {
		return CampoDeJuego.instancia;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getUmbral() {
		return umbral;
	}

	public void setUmbral(int umbral) {
		this.umbral = umbral;
	}

	public static void setInstancia(CampoDeJuego instancia) {
		CampoDeJuego.instancia = instancia;
	}

}
