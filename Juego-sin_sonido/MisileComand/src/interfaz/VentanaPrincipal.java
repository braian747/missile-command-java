package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.Clip;
import gestor.Controlador;

public class VentanaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static VentanaPrincipal instancia = new VentanaPrincipal();
	

	private VentanaPrincipal() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(PanelMenu.getInstancia());
		setTitle("Missile Command");
		setSize(650, 700);
		setVisible(true);
		setResizable(false);
		setPreferredSize(new Dimension(650, 700));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	public void refrescar() {
		PanelJuego.getInstancia().repaint();
	}


	public static VentanaPrincipal getInstancia() {
		return VentanaPrincipal.instancia;
	}
}