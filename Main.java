import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal del programa de análisis de emociones mediante modelos de lenguaje.
 * Lanza la interfaz gráfica de usuario en el hilo de despacho de eventos de Swing.
 *
 * <p>El programa construye un árbol de contextos a partir de cuatro archivos de texto
 * (uno por emoción: Feliz, Triste, Calmado, Enojado), calcula modelos de frecuencias
 * usando similitud de cosenos y ubica una frase ingresada por el usuario en un plano
 * bidimensional de emociones.</p>
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     * Configura el Look &amp; Feel del sistema y lanza la ventana principal.
     *
     * @param argumentos Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] argumentos) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Si falla, continúa con el Look & Feel por defecto
        }

        SwingUtilities.invokeLater(() -> {
            new EmotionAnalyzerGUI();
        });
    }
}
