package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gestor.Controlador;

public class PanelRanking extends JPanel {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private static PanelRanking instancia = new PanelRanking();
	private int cantFilas = 1;
	private JTable grilla = new JTable();
	private String[] titulos = { "Posicion", "Nombre", "Puntaje", "Tiempo" };
	private JButton b = new JButton();

	private PanelRanking() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		b = new JButton("Atras");
		panel.add(b, BorderLayout.WEST);
		b.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				VentanaPrincipal.getInstancia().setContentPane(new JPanel());
				VentanaPrincipal.getInstancia().setContentPane(PanelMenu.getInstancia());
				VentanaPrincipal.getInstancia().pack();
				if (!VentanaPrincipal.getInstancia().getMusica().isRunning()) {
					VentanaPrincipal.getInstancia().setMusica(Controlador.cargarSonido("/sonidos/menu.wav"));
					VentanaPrincipal.getInstancia().getMusica().start();
				}
			}
		});
		add(new JScrollPane(grilla), BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(650, 700));
	}

	public int getCantFilas() {
		return cantFilas;
	}

	public void setCantFilas(int cantFilas, DefaultTableModel modelo) {
		this.cantFilas = cantFilas;
		modelo.setRowCount(cantFilas);

	}

	public String[] getTitulos() {
		return titulos;
	}

	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}

	public static PanelRanking getInstancia() {
		return PanelRanking.instancia;
	}

	public JTable getGrilla() {
		return grilla;
	}

	public void setGrilla(JTable grilla) {
		this.grilla = grilla;
	}
}
