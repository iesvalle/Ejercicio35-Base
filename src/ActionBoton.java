import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener{

	private VentanaPrincipal ventana;
	private int fila;
	private int columna;

	public ActionBoton(VentanaPrincipal ventanaJuego, int filaPulsada, int columnaPulsada) {
		this.ventana=ventanaJuego;
		this.fila=filaPulsada;
		this.columna=columnaPulsada;
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (this.ventana.getJuego().abrirCasilla(fila, columna)) {
			this.ventana.mostrarNumMinasAlrededor(fila, columna);
			this.ventana.actualizarPuntuacion();
		}else{
			
			this.ventana.mostrarFinJuego(this.ventana.getJuego().esFinJuego()); //comprobamos si se acaba el juego por explosion de una mina o por ganar

		}
		
	}

}