package gestor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import excepciones.TooLongNameException;
import excepciones.TooShortNameException;
import interfaz.PanelConfiguracion;
import interfaz.PanelGameOver;
import interfaz.PanelJuego;
import interfaz.PanelPuntaje;
import interfaz.PanelRanking;
import interfaz.VentanaPrincipal;
import jugadores.Enemigo;
import jugadores.InfoJugador;
import jugadores.Jugador;

public class Controlador implements Runnable {
	private int ciclo = 0;
	private int control = 0;
	private ArrayList<InfoJugador> listaJugadores = new ArrayList<InfoJugador>();
	private static Controlador instancia = new Controlador();
	ObjectOutputStream salida = null;
	ObjectInputStream entrada = null;
	long tiempo;

	private Controlador() {

	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public void run() {
		Juego.getInstancia().inicializar();
		Puntaje.getInstancia().resetearPuntaje();
		Juego.getInstancia().setBonusCityCreada(false);
		PanelPuntaje.getInstancia().setBonusCity(false);
		PanelJuego.getInstancia().repaint();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long tInicial = System.currentTimeMillis();
		while (!Juego.getInstancia().getCiudades().isEmpty()) {// loop general del juego
			Enemigo.getInstancia().inicializarCantMisiles();

			do {

				Juego.getInstancia().gameLoop();
				PanelJuego.getInstancia().repaint();
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!Enemigo.getInstancia().getArregloMisiles().isEmpty() || Enemigo.getInstancia().getCantM() > 0);// loop de cada nivel																		
			while(Observador.getInstancia().getExplosiones().size()>0) {
				Juego.getInstancia().gameLoop();
				PanelJuego.getInstancia().repaint();
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				Observador.getInstancia().getExplosiones().clear();
			ciclo = 0;
			++control;
			if (!Juego.getInstancia().getCiudades().isEmpty()) {
				cambiarPanel();
				Juego.getInstancia().nuevoNivel(Nivel.getInstancia().getNumNivel(), control);
				PanelJuego.getInstancia().repaint();
			}
			
			Jugador.getInstancia().getMisilesDisparados().clear();
			Jugador.getInstancia().getDestinos().clear();
			Observador.getInstancia().liberarMemoria();
			PanelJuego.getInstancia().repaint();
			Nivel.getInstancia().setNumNivel(Nivel.getInstancia().getNumNivel() + 1);
			Puntaje.getInstancia().setBonus(Puntaje.getInstancia().getBonus()
					+ PanelPuntaje.getInstancia().getPuntosCiudad() + PanelPuntaje.getInstancia().getPuntosMisil());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (Nivel.getInstancia().getNumNivel() >= 11) {
				Nivel.getInstancia().setMultPuntaje(6);
			} else if (Nivel.getInstancia().getNumNivel() % 2 == 1) {
				Nivel.getInstancia().setMultPuntaje((Nivel.getInstancia().getNumNivel() + 1) / 2);

			}

		}
		long tFinal = System.currentTimeMillis();
		tiempo = (tFinal - tInicial) / 1000; // /1000 es para pasar de milisegundos a segundos
		Juego.getInstancia().fin();
		panelGameOver();
		if ((PanelConfiguracion.getInstancia().obtenerNumRanking() > listaJugadores.size())) {
			String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre:", "Ranking", 1);
			try {
				validarNombre(nombre);
			} catch (TooShortNameException e) {
				while (nombre.length() < 4) {
					nombre = JOptionPane.showInputDialog(null, "Su nombre debe contener al menos 4 caracteres:",
							"Ranking", 0);
				}
			} catch (TooLongNameException e) {
				nombre = nombre.replaceAll(" ", "");
				if (nombre.length() > 20)
					nombre = nombre.substring(0, 20);
			} finally {
				InfoJugador jugador = new InfoJugador(nombre, Puntaje.getInstancia().puntajeParcial(), tiempo);

				listaJugadores.add(jugador);
				Collections.sort(listaJugadores);
				for (InfoJugador i : listaJugadores) {
					i.setPosicion(listaJugadores.indexOf(i) + 1);
				}

				guardarInfo();
				Controlador.getInstancia().cargarTablaRanking();
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelRanking.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		}
	}

	public void validarNombre(String nombre) throws TooLongNameException, TooShortNameException {

		if (nombre.length() < 4) {
			throw new TooShortNameException();
		}
		if (nombre.length() > 20) {
			throw new TooLongNameException();
		}

	}

	public void guardarInfo() {

		try {
			salida = new ObjectOutputStream(new FileOutputStream("ranking.txt"));
			salida.writeObject(listaJugadores);
			salida.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				salida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public ArrayList<InfoJugador> leerInfo() {
		ArrayList<InfoJugador> lista = null;
		try {
			entrada = new ObjectInputStream(new FileInputStream("ranking.txt"));
			lista = (ArrayList<InfoJugador>) entrada.readObject();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (lista);
	}

	public void cargarTablaRanking() {
		ArrayList<InfoJugador> lista = leerInfo();
		Object[][] datos = new Object[lista.size()][4];
		for (int f = 0; f < lista.size(); f++) {
			datos[f][0] = "                        " + lista.get(f).getPosicion();
			datos[f][1] = " " + lista.get(f).getNombre();
			datos[f][2] = " " + lista.get(f).getPuntaje();
			datos[f][3] = " " + lista.get(f).getTiempo() + "  segundos";
		}
		DefaultTableModel modelo = new DefaultTableModel(datos, PanelRanking.getInstancia().getTitulos()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		PanelRanking.getInstancia().getGrilla().setModel(modelo);
		PanelRanking.getInstancia().setCantFilas(PanelConfiguracion.getInstancia().obtenerNumRanking(), modelo);
	}

	public JTextArea cargarTextoReglas(JTextArea area) {
		File archivo = new File("reglas.txt");
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader(archivo));
			String linea = lector.readLine();
			while (linea != null) {
				area.append(linea + "\n");
				linea = lector.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				lector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return area;
	}


	public void cambiarPanel() {
		
		int posCiudad = 200;
		int posMisil = 200;
		PanelPuntaje.getInstancia().setPuntosCiudad(0);
		PanelPuntaje.getInstancia().setPuntosMisil(0);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		VentanaPrincipal.getInstancia().setContentPane(new JPanel());
		VentanaPrincipal.getInstancia().setContentPane(PanelPuntaje.getInstancia());
		VentanaPrincipal.getInstancia().pack();
		PanelPuntaje.getInstancia().setParte(1);
		for (int i = 0; i < Juego.getInstancia().getCiudades().size(); i++) {
			PanelPuntaje.getInstancia().getPosiciones().add(posCiudad);
			PanelPuntaje.getInstancia().setPuntosCiudad(PanelPuntaje.getInstancia().getPuntosCiudad() + 100);
			PanelPuntaje.getInstancia().repaint();

			posCiudad += 50;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PanelPuntaje.getInstancia().setParte(2);
		int misiles = Jugador.getInstancia().cantMisiles();

		for (int i = 0; i < misiles; i++) {
			PanelPuntaje.getInstancia().getPosMisil().add(posMisil);
			PanelPuntaje.getInstancia().setPuntosMisil(PanelPuntaje.getInstancia().getPuntosMisil() + 5);
			PanelPuntaje.getInstancia().repaint();
			posMisil += 15;

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (Puntaje.getInstancia().getPuntajeTotal() >= 10000 && (Juego.getInstancia().getCiudades().size() < 6)
				&& (!Juego.getInstancia().isBonusCityCreada())) {
			System.out.println("ciudad bonus");
			PanelPuntaje.getInstancia().setBonusCity(true);
			PanelPuntaje.getInstancia().repaint();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		PanelPuntaje.getInstancia().getPosiciones().clear();
		PanelPuntaje.getInstancia().getPosMisil().clear();
		VentanaPrincipal.getInstancia().setContentPane(new JPanel());
		VentanaPrincipal.getInstancia().setContentPane(PanelJuego.getInstancia());
		VentanaPrincipal.getInstancia().pack();
	}

	public void panelGameOver() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		VentanaPrincipal.getInstancia().setContentPane(new JPanel());
		VentanaPrincipal.getInstancia().setContentPane(PanelGameOver.getInstancia());
		VentanaPrincipal.getInstancia().pack();
		PanelGameOver.getInstancia().setTamanio(10);
		for (int i = 0; i < 80; i++) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			PanelGameOver.getInstancia().repaint();
			PanelGameOver.getInstancia().setTamanio(PanelGameOver.getInstancia().getTamanio() + 1);

		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<InfoJugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(ArrayList<InfoJugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	public int getControl() {
		return control;
	}

	public void setControl(int control) {
		this.control = control;
	}

	public static Controlador getInstancia() {
		return Controlador.instancia;
	}
}
