package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import gestor.Juego;

public class PanelGameOver extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelGameOver instancia = new PanelGameOver();
	private int tamanio;
	private JButton b = new JButton();

	private PanelGameOver() {
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		b = new JButton("Menú");
		panel.add(b, BorderLayout.WEST);
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Juego.getInstancia().fin();
				VentanaPrincipal.getInstancia().setContentPane(PanelMenu.getInstancia());
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, tamanio));
		g.drawString("GAME OVER", 40, 350);

	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public static PanelGameOver getInstancia() {
		return PanelGameOver.instancia;
	}
}
