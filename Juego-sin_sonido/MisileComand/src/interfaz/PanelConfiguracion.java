package interfaz;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelConfiguracion extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelConfiguracion instancia = new PanelConfiguracion();
	Choice nivelInicial = new Choice();
	Choice ranking = new Choice();
	private JButton b = new JButton();
	private Label label;
	private Label label2;

	private PanelConfiguracion() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(600, 5));
		panel2.setLayout(new GridLayout(20, 5000));
		add(panel, BorderLayout.NORTH);
		add(panel2, BorderLayout.WEST);
		b = new JButton("Atras");

		panel.add(b, BorderLayout.WEST);
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelMenu.getInstancia());
				VentanaPrincipal.getInstancia().pack();
			}
		});
		label = new Label("Seleccione el numero de nivel desde el cual se quiere iniciar el juego:");
		panel2.add(label);
		for (int i = 1; i <= 20; i++) {
			nivelInicial.add("" + i);
		}
		panel2.add(nivelInicial);
		label2 = new Label("Seleccione el numero de valores que seran almacenados en la tabla de ranking:");
		panel2.add(label2);

		for (int i = 1; i <= 10; i++) {
			ranking.add("" + i);
		}
		ranking.select(9);
		panel2.add(ranking);
		this.setPreferredSize(new Dimension(650, 700));
	}

	public int obtenerNivel() {
		return nivelInicial.getSelectedIndex() + 1;
	}

	public int obtenerNumRanking() {
		return ranking.getSelectedIndex() + 1;
	}

	public static PanelConfiguracion getInstancia() {
		return PanelConfiguracion.instancia;
	}

}
