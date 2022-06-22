package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import gestor.Controlador;

public class PanelReglas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelReglas instancia = new PanelReglas();
	private JTextArea area = new JTextArea();
	private JButton b = new JButton();

	private PanelReglas() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		b = new JButton("Atras");
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelMenu.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		});

		Box cuadro = Box.createHorizontalBox();
		area = Controlador.getInstancia().cargarTextoReglas(area);
		area.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		area.setBackground(Color.LIGHT_GRAY);
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);
		scroll.getVerticalScrollBar().setValue(1);
		cuadro.add(scroll);

		panel.setBackground(Color.LIGHT_GRAY);

		panel.add(b, BorderLayout.WEST);
		add(cuadro, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(650, 600));
	}

	public static PanelReglas getInstancia() {
		return PanelReglas.instancia;
	}
}
