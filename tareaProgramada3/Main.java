import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase main del programa
 */
public class Main {

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
