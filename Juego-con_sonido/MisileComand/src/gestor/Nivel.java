package gestor;

import interfaz.PanelConfiguracion;

public class Nivel {
	public static Nivel instancia = new Nivel();
	private int numNivel;
	private boolean mci;
	private boolean mct;
	private int multPuntaje;
	private double velMBI;
	private int alturaBomb;

	private Nivel() {

	}

	public int getAlturaBomb() {
		return alturaBomb;
	}

	public void setAlturaBomb(int alturaBomb) {
		this.alturaBomb = alturaBomb;
	}

	public int getMultPuntaje() {
		return multPuntaje;
	}

	public void setMultPuntaje(int multPuntaje) {
		this.multPuntaje = multPuntaje;
	}

	public void inicializarNivel() {
		if (PanelConfiguracion.getInstancia().obtenerNivel() == 1) {
			setNumNivel(1);
			setMultPuntaje(1);
			setMci(false);
			setMct(true);
			setAlturaBomb(100);
			setVelMBI(1.5);
		} else {
			// seteo el nivel seleccionado en configuracion
			setNumNivel(PanelConfiguracion.getInstancia().obtenerNivel());
			// seteo el multiplicador del puntaje
			if (getNumNivel() % 2 == 1) {
				setMultPuntaje((getNumNivel() + 1) / 2);

			} else
				setMultPuntaje(getNumNivel() / 2);
			// seteo el control de la clase controlador
			if (getNumNivel() <= 5) {
				Controlador.getInstancia().setControl(getNumNivel());
			} else if (getNumNivel() > 5 && getNumNivel() <= 11) {
				Controlador.getInstancia().setControl(getNumNivel() - 5);
			} else
				Controlador.getInstancia().setControl(getNumNivel() - 10);
			// seteo los misiles crucero tonto e inteligente
			if (Controlador.getInstancia().getControl() <= 2) {
				setMci(false);
				Nivel.getInstancia().setMct(true);
			} else {
				if (Controlador.getInstancia().getControl() <= 4) {
					setMci(true);
					Nivel.getInstancia().setMct(false);
				} else {
					Controlador.getInstancia().setControl(1);
					setMci(false);
					setMct(true);
				}
			}
			// seteo la altura del bombardero
			setAlturaBomb(getNumNivel() * 100 - 50);
			if (getAlturaBomb() > 400) {
				setAlturaBomb(400);
			}
			// seteo la velocidad de los mbi
			setVelMBI((Math.log(numNivel) / Math.log(10)) + 1.2);

		}
	}

	public int getNumNivel() {
		return numNivel;
	}

	public void setNumNivel(int numNivel) {
		this.numNivel = numNivel;
	}

	public boolean isMci() {
		return mci;
	}

	public void setMci(boolean mci) {
		this.mci = mci;
	}

	public boolean isMct() {
		return mct;
	}

	public void setMct(boolean mct) {
		this.mct = mct;
	}

	public double getVelMBI() {
		return velMBI;
	}

	public void setVelMBI(double velMBI) {
		this.velMBI = velMBI;
	}

	public static Nivel getInstancia() {

		return Nivel.instancia;
	}
}
