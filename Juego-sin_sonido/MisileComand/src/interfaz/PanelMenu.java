package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import gestor.Controlador;

public class PanelMenu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelMenu instancia = new PanelMenu();
	private URL url = getClass().getResource("/imagenes/fondomenu.jpg/");
	Image img = new ImageIcon(url).getImage();
	private URL url2 = getClass().getResource("/imagenes/nombre.png/");
	Image nombre = new ImageIcon(url2).getImage();
	private JButton b = new JButton();

	private PanelMenu() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		JPanel panel2 = new JPanel();
		BorderLayout superior = new BorderLayout();
		BorderLayout central = new BorderLayout();
		panel2.setOpaque(false);
		panel.setLayout(superior); //
		add(panel, BorderLayout.NORTH);
		panel2.setLayout(central);
		panel2.setPreferredSize(new Dimension(10, 50));
		add(panel2, BorderLayout.SOUTH);
		b = new JButton("Reglas de Juego");
		panel2.add(b, BorderLayout.WEST);
		this.setPreferredSize(new Dimension(650, 700));
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelReglas.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		});
		b = new JButton("A jugar!");
		panel2.add(b, BorderLayout.CENTER);
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelJuego.getInstancia());
				VentanaPrincipal.getInstancia().pack();
				new Thread(Controlador.getInstancia()).start();

			}
		});
		b = new JButton("Ranking");
		panel2.add(b, BorderLayout.EAST);
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Controlador.getInstancia().cargarTablaRanking();
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelRanking.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		});
		b = new JButton("Configuracion");
		panel.add(b, BorderLayout.EAST);
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelConfiguracion.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		});

	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 0, 0, 650, 622, this);
		g.drawImage(nombre, 220, 50, 400, 150, null);
		setOpaque(false);

	}

	public static PanelMenu getInstancia() {
		return PanelMenu.instancia;
	}
}