import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana principal del Buscaminas, en esta clase se inicializaran tanto los
 * componentes como los listener de los botones y se colocaran adecuadamente
 * para tener una interfaz sencilla de usar. tambien existen metodos para
 * mostrar el fin del juego. {@link #inicializar()}
 * 
 * 
 * pero la clase que controla el juego es ControlJuego que coloca las minas,
 * abre las casillas etc.
 * 
 * @see ControlJuego
 * 
 * @author Miguel Pérez Martin
 * @version 1.0
 * @since 1.0
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		juego = new ControlJuego();

	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {

				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		botonesJuego = new JButton[10][10];
		// Botones
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
				botonesJuego[i][j].setEnabled(true);
			}
		}

		// BotónEmpezar:
		botonEmpezar.setIcon(new ImageIcon(getClass().getResource("/imagenes/nueva.png")));
		panelEmpezar.add(botonEmpezar);

		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		// TODO

		// dar listener a los botones para que se abran casillas.

		botonEmpezar.addActionListener(new ActionListener() {

			// boton de volver a empezar el juego, quitamos todos lo componentes de los
			// paneles e
			// insertamos de nuevo los botones de juego y tambien los activamos.
			// por ultimo llamammos al metodo inicializar partida para empezar el juego.
			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego.length; j++) {
						panelesJuego[i][j].removeAll();
						panelesJuego[i][j].add(botonesJuego[i][j]);
						// ponemos el textop a los botones y les quitamos el icono de las minas.
						botonesJuego[i][j].setText("-");
						botonesJuego[i][j].setIcon(new ImageIcon(getClass().getResource("")));

						botonesJuego[i][j].setEnabled(true);
						// icono al boton de empezar
						botonEmpezar.setIcon(new ImageIcon(getClass().getResource("/imagenes/nueva.png")));
					}
				}

				juego.inicializarPartida();
				actualizarPuntuacion();
			}
		});

		// botones de juego
		for (int i = 0; i < juego.LADO_TABLERO; i++) {
			for (int j = 0; j < juego.LADO_TABLERO; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
			}
		}
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca el
	 * botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto según
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		// TODO
		// seleccionar el panel[i][j] correspondiente
		// Eliminar todos sus componentes: //Buscarlo en internet
		// Añadimos un JLabel centrado y no editable con el umero de minas alrededor
		// El numero minas alrededor se saca de ControlJuego.

		int minasAlrededor = juego.getMinasAlrededor(i, j);
		JLabel minasA = new JLabel("" + minasAlrededor);
		minasA.setHorizontalAlignment(SwingConstants.CENTER);
		int color = Integer.parseInt(minasA.getText());

		switch (color) {
			case 0:
				minasA.setForeground(correspondenciaColores[0]);
				break;
			case 1:
				minasA.setForeground(correspondenciaColores[1]);
				break;
			case 2:
				minasA.setForeground(correspondenciaColores[2]);
				break;
			case 3:
				minasA.setForeground(correspondenciaColores[3]);
				break;
			default:
				minasA.setForeground(correspondenciaColores[4]);
				break;
		}

		panelesJuego[i][j].removeAll();
		panelesJuego[i][j].add(minasA);

	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].setEnabled(false);
			}
		}

		String cadena = "";

		if (porExplosion) {
			verMinas();
			botonEmpezar.setIcon(new ImageIcon(getClass().getResource("/imagenes/perdio.png")));
			cadena += "BUUMM!! \n";
			cadena += "Has perdido por explotar una mina \n";
			cadena += "Tu puntuacion es: " + juego.getPuntuacion() + "\n";
			cadena += "Intentalo de nuevo \n";
		} else {
			verMinas();
			// para poner un icono al boton
			botonEmpezar.setIcon(new ImageIcon(getClass().getResource("/imagenes/gano.png")));
			cadena += "ENHORABUENAA!! \n";
			cadena += "Has conseguido abrir todas las casillas sin explotar las minas \n";
		}

		JOptionPane.showMessageDialog(ventana, cadena, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * metodo que consigue que al acabar el juego nos abra todas las casillas para
	 * ver donde estaban las minas.
	 */
	public void verMinas() {
		for (int i = 0; i < juego.LADO_TABLERO; i++) {
			for (int j = 0; j < juego.LADO_TABLERO; j++) {
				if (juego.minas(i, j)) {
					botonesJuego[i][j].setText("");
					botonesJuego[i][j].setIcon(new ImageIcon(getClass().getResource("/imagenes/minaJuego.png")));
				} else {
					mostrarNumMinasAlrededor(i, j);
				}
			}
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText("" + juego.getPuntuacion());
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa {@code 
	 * public void inicializar() {
	 * // IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
	 * // COMPONENTES.
	 * ventana.setVisible(true); inicializarComponentes(); inicializarListeners();
	 * }}
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
