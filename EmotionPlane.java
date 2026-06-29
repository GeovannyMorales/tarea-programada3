import java.awt.*;
import javax.swing.*;

/**
 * Componente gráfico que dibuja el plano bidimensional de emociones.
 * Muestra los cuatro sentimientos en los extremos (Feliz, Triste, Calmado, Enojado)
 * y ubica un punto representando la frase ingresada en proporción a su similitud
 * con cada sentimiento.
 */
public class EmotionPlane extends JPanel {

    /** Similitud de la frase con la emoción "Feliz". */
    private double similitudFeliz;

    /** Similitud de la frase con la emoción "Triste". */
    private double similitudTriste;

    /** Similitud de la frase con la emoción "Calmado". */
    private double similitudCalmado;

    /** Similitud de la frase con la emoción "Enojado". */
    private double similitudEnojado;

    /** Indica si se ha calculado una posición para graficar. */
    private boolean tienePunto;

    /** Color de fondo del plano. */
    private static final Color COLOR_FONDO = new Color(245, 245, 250);

    /** Color del eje central. */
    private static final Color COLOR_EJE = new Color(100, 100, 120);

    /** Color del punto que representa la frase. */
    private static final Color COLOR_PUNTO = new Color(220, 50, 50);

    /** Color del texto de felicidad. */
    private static final Color COLOR_ETIQUETA_FELIZ = new Color(30, 150, 60);

    /** Color del texto de tristeza. */
    private static final Color COLOR_ETIQUETA_TRISTE = new Color(60, 80, 180);

    /** Color del texto de calma. */
    private static final Color COLOR_ETIQUETA_CALMADO = new Color(20, 160, 200);

    /** Color del texto de enojo. */
    private static final Color COLOR_ETIQUETA_ENOJADO = new Color(200, 60, 20);

    /**
     * Constructor que inicializa el plano sin punto graficado.
     */
    public EmotionPlane() {
        this.tienePunto = false;
        setPreferredSize(new Dimension(500, 500));
        setBackground(COLOR_FONDO);
    }

    /**
     * Actualiza las similitudes y redibuja el plano con la nueva posición de la frase.
     *
     * @param similitudFeliz   Similitud de cosenos con "Feliz".
     * @param similitudTriste  Similitud de cosenos con "Triste".
     * @param similitudCalmado Similitud de cosenos con "Calmado".
     * @param similitudEnojado Similitud de cosenos con "Enojado".
     */
    public void actualizarPunto(double similitudFeliz, double similitudTriste,
                                double similitudCalmado, double similitudEnojado) {
        this.similitudFeliz   = similitudFeliz;
        this.similitudTriste  = similitudTriste;
        this.similitudCalmado = similitudCalmado;
        this.similitudEnojado = similitudEnojado;
        this.tienePunto = true;
        repaint();
    }

    /**
     * Resetea el plano eliminando el punto graficado.
     */
    public void limpiarPunto() {
        this.tienePunto = false;
        repaint();
    }

    /**
     * Dibuja el plano de emociones con ejes, etiquetas y el punto de la frase.
     *
     * @param g Contexto gráfico de Java AWT.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graficos = (Graphics2D) g;
        graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graficos.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto  = getHeight();
        int margen = 70;
        int anchoPlano = ancho - 2 * margen;
        int altoPlano  = alto  - 2 * margen;
        int centroX = ancho / 2;
        int centroY = alto  / 2;

        // Fondo del plano
        graficos.setColor(new Color(235, 235, 245));
        graficos.fillRect(margen, margen, anchoPlano, altoPlano);

        // Borde del plano
        graficos.setColor(new Color(180, 180, 200));
        graficos.setStroke(new BasicStroke(1.5f));
        graficos.drawRect(margen, margen, anchoPlano, altoPlano);

        // Ejes centrales
        graficos.setColor(COLOR_EJE);
        graficos.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{6, 4}, 0.0f));
        graficos.drawLine(centroX, margen, centroX, alto - margen);   // eje vertical
        graficos.drawLine(margen, centroY, ancho - margen, centroY);  // eje horizontal

        // Etiquetas de emociones
        graficos.setStroke(new BasicStroke(1f));
        Font fuenteEtiqueta = new Font("SansSerif", Font.BOLD, 16);
        graficos.setFont(fuenteEtiqueta);

        graficos.setColor(COLOR_ETIQUETA_FELIZ);
        dibujarTextocentrado(graficos, "Feliz", ancho - margen / 2 - 10, centroY);

        graficos.setColor(COLOR_ETIQUETA_TRISTE);
        dibujarTextocentrado(graficos, "Triste", margen / 2 + 5, centroY);

        graficos.setColor(COLOR_ETIQUETA_CALMADO);
        dibujarTextocentrado(graficos, "Calmado", centroX, margen / 2 + 5);

        graficos.setColor(COLOR_ETIQUETA_ENOJADO);
        dibujarTextocentrado(graficos, "Enojado", centroX, alto - margen / 2 + 5);

        // Punto de la frase ingresada
        if (tienePunto) {
            double totalHorizontal = similitudFeliz + similitudTriste;
            double ratioX = (totalHorizontal > 0) ? (similitudFeliz - similitudTriste) / totalHorizontal : 0;

            double totalVertical = similitudCalmado + similitudEnojado;
            double ratioY = (totalVertical > 0) ? (similitudEnojado - similitudCalmado) / totalVertical : 0;

            int px = centroX + (int) (ratioX * (anchoPlano / 2));
            int py = centroY + (int) (ratioY * (altoPlano  / 2));

            px = Math.max(margen + 5, Math.min(ancho - margen - 5, px));
            py = Math.max(margen + 5, Math.min(alto  - margen - 5, py));

            // Sombra del punto
            graficos.setColor(new Color(0, 0, 0, 60));
            graficos.fillOval(px - 7, py - 5, 16, 16);

            // Punto principal
            graficos.setColor(COLOR_PUNTO);
            graficos.fillOval(px - 8, py - 8, 16, 16);

            // Borde blanco del punto
            graficos.setColor(Color.WHITE);
            graficos.setStroke(new BasicStroke(2f));
            graficos.drawOval(px - 8, py - 8, 16, 16);

            // Valores de similitud
            Font fuentePequena = new Font("SansSerif", Font.PLAIN, 11);
            graficos.setFont(fuentePequena);
            graficos.setColor(new Color(60, 60, 80));
            int infoX = margen + 8;
            int infoY = alto - margen - 10;
            graficos.drawString(String.format("Feliz: %.3f  Triste: %.3f", similitudFeliz, similitudTriste), infoX, infoY - 15);
            graficos.drawString(String.format("Calmado: %.3f  Enojado: %.3f", similitudCalmado, similitudEnojado), infoX, infoY);
        } else {
            Font fuenteHint = new Font("SansSerif", Font.ITALIC, 13);
            graficos.setFont(fuenteHint);
            graficos.setColor(new Color(160, 160, 180));
            dibujarTextocentrado(graficos, "Ingrese una frase para ver el resultado", centroX, centroY);
        }
    }

    /**
     * Dibuja un texto centrado horizontalmente en la posición indicada.
     *
     * @param graficos Contexto gráfico.
     * @param texto    Texto a dibujar.
     * @param x        Coordenada X del centro del texto.
     * @param y        Coordenada Y de la línea base del texto.
     */
    private void dibujarTextocentrado(Graphics2D graficos, String texto, int x, int y) {
        FontMetrics medidas = graficos.getFontMetrics();
        int anchoTexto = medidas.stringWidth(texto);
        graficos.drawString(texto, x - anchoTexto / 2, y + medidas.getAscent() / 2);
    }
}
