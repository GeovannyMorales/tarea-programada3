import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

// ventana principal de la app, aqui es donde el usuario interactua con todo
public class EmotionAnalyzerGUI extends JFrame {

    private JTextField campoPalabra;    // donde el usuario escribe la frase
    private JButton botonAnalizar;        // boton para analizar la frase
    private JButton botonCargar;         // boton para cargar los archivos
    private JLabel etiquetaEstado;        // muestra mensajes de lo que esta pasando
    private EmotionPlane planoEmocional;  // el plano donde se dibuja la emocion
    private JTextField[] camposArchivo;   // muestra la ruta de cada archivo seleccionado
    private String[] rutasArchivos;        // guarda las rutas de los 4 archivos
    private ContextTree arbolContextos;  // arbol construido con todos los archivos
    private FrequencyTree[] modelosSentimientos; // un modelo por cada sentimiento
    private static final String[] NOMBRES_SENTIMIENTOS = {"Feliz", "Triste", "Calmado", "Enojado"};
    private boolean modelosCargados;       // para saber si ya se cargaron los modelos

    // constructor, arranca la ventana y configura todo
    public EmotionAnalyzerGUI() {
        super("Análisis de Emociones - Similitud de Cosenos");
        rutasArchivos       = new String[4];
        camposArchivo       = new JTextField[4];
        modelosSentimientos = new FrequencyTree[4];
        modelosCargados     = false;

        inicializarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(800, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // arma todos los componentes visuales y los pone en su lugar
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));
        getContentPane().setBackground(new Color(240, 240, 248));

        JPanel panelIzquierdo = construirPanelIzquierdo();
        add(panelIzquierdo, BorderLayout.WEST);

        planoEmocional = new EmotionPlane();
        planoEmocional.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(160, 160, 200), 1),
                    "Plano de Emociones",
                    TitledBorder.CENTER, TitledBorder.TOP,
                    new Font("SansSerif", Font.BOLD, 13),
                    new Color(60, 60, 100)),
                new EmptyBorder(6, 6, 6, 6)
            ));
        add(planoEmocional, BorderLayout.CENTER);

        etiquetaEstado = new JLabel("  Seleccione los archivos de sentimientos y presione 'Cargar Modelos'.");
        etiquetaEstado.setFont(new Font("SansSerif", Font.ITALIC, 12));
        etiquetaEstado.setForeground(new Color(80, 80, 120));
        etiquetaEstado.setBorder(new EmptyBorder(6, 4, 4, 4));
        add(etiquetaEstado, BorderLayout.SOUTH);
    }

    // construye el panel de la izquierda con los selectores de archivos y la entrada de texto
    private JPanel construirPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 248));
        panel.setPreferredSize(new Dimension(290, 500));

        JPanel panelArchivos = new JPanel();
        panelArchivos.setLayout(new GridBagLayout());
        panelArchivos.setBackground(new Color(240, 240, 248));
        panelArchivos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(160, 160, 200), 1),
                    "Archivos de Sentimientos",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("SansSerif", Font.BOLD, 12),
                    new Color(60, 60, 100)),
                new EmptyBorder(4, 6, 6, 6)
            ));

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(4, 2, 4, 2);
        restricciones.fill   = GridBagConstraints.HORIZONTAL;

        // cada emocion tiene su color para distinguirlas visualmente
        Color[] coloresEtiqueta = {
                new Color(30, 150, 60),
                new Color(60, 80, 180),
                new Color(20, 160, 200),
                new Color(200, 60, 20)
            };

        for (int i = 0; i < 4; i++) {
            final int indice = i;
            JLabel etiqueta = new JLabel(NOMBRES_SENTIMIENTOS[i] + ":");
            etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
            etiqueta.setForeground(coloresEtiqueta[i]);
            restricciones.gridx = 0; restricciones.gridy = i * 2;
            restricciones.gridwidth = 2; restricciones.weightx = 1;
            panelArchivos.add(etiqueta, restricciones);

            camposArchivo[i] = new JTextField("(sin archivo)", 18);
            camposArchivo[i].setEditable(false);
            camposArchivo[i].setFont(new Font("SansSerif", Font.PLAIN, 11));
            camposArchivo[i].setForeground(new Color(120, 120, 140));
            restricciones.gridx = 0; restricciones.gridy = i * 2 + 1;
            restricciones.gridwidth = 1; restricciones.weightx = 1;
            panelArchivos.add(camposArchivo[i], restricciones);

            JButton botonExaminar = new JButton("...");
            botonExaminar.setFont(new Font("SansSerif", Font.BOLD, 11));
            botonExaminar.setPreferredSize(new Dimension(36, 24));
            botonExaminar.setToolTipText("Seleccionar archivo para " + NOMBRES_SENTIMIENTOS[i]);
            botonExaminar.addActionListener(e -> seleccionarArchivo(indice));
            restricciones.gridx = 1; restricciones.gridy = i * 2 + 1;
            restricciones.gridwidth = 1; restricciones.weightx = 0;
            panelArchivos.add(botonExaminar, restricciones);
        }

        panel.add(panelArchivos);
        panel.add(Box.createVerticalStrut(10));

        botonCargar = new JButton("Cargar Modelos");
        botonCargar.setFont(new Font("SansSerif", Font.BOLD, 13));
        botonCargar.setBackground(new Color(70, 100, 200));
        botonCargar.setForeground(Color.WHITE);
        botonCargar.setFocusPainted(false);
        botonCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCargar.addActionListener(e -> cargarModelos());
        panel.add(botonCargar);
        panel.add(Box.createVerticalStrut(14));

        JPanel panelFrase = new JPanel();
        panelFrase.setLayout(new BoxLayout(panelFrase, BoxLayout.Y_AXIS));
        panelFrase.setBackground(new Color(240, 240, 248));
        panelFrase.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(160, 160, 200), 1),
                    "Frase a Analizar",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("SansSerif", Font.BOLD, 12),
                    new Color(60, 60, 100)),
                new EmptyBorder(6, 6, 6, 6)
            ));

        campoPalabra = new JTextField();
        campoPalabra.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoPalabra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        campoPalabra.addActionListener(e -> analizarFrase());
        panelFrase.add(campoPalabra);
        panelFrase.add(Box.createVerticalStrut(8));

        botonAnalizar = new JButton("Analizar Frase");
        botonAnalizar.setFont(new Font("SansSerif", Font.BOLD, 13));
        botonAnalizar.setBackground(new Color(40, 160, 80));
        botonAnalizar.setForeground(Color.WHITE);
        botonAnalizar.setFocusPainted(false);
        botonAnalizar.setEnabled(false);
        botonAnalizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonAnalizar.addActionListener(e -> analizarFrase());
        panelFrase.add(botonAnalizar);

        panel.add(panelFrase);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // abre el explorador de archivos para seleccionar el txt de cada emocion
    private void seleccionarArchivo(int indice) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de " + NOMBRES_SENTIMIENTOS[indice]);
        selector.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        selector.setCurrentDirectory(new File("FrasesSentimientos"));
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rutasArchivos[indice] = selector.getSelectedFile().getAbsolutePath();
            String nombreArchivo   = selector.getSelectedFile().getName();
            camposArchivo[indice].setText(nombreArchivo);
            camposArchivo[indice].setForeground(new Color(40, 100, 40));
            actualizarEstado("Archivo seleccionado para " + NOMBRES_SENTIMIENTOS[indice] + ": " + nombreArchivo);
        }
    }

    // carga los modelos en un hilo aparte para que la interfaz no se congele
    private void cargarModelos() {
        for (int i = 0; i < 4; i++) {
            if (rutasArchivos[i] == null) {
                mostrarError("Falta seleccionar el archivo para: " + NOMBRES_SENTIMIENTOS[i]);
                return;
            }
        }

        botonCargar.setEnabled(false);
        botonAnalizar.setEnabled(false);
        modelosCargados = false;
        planoEmocional.limpiarPunto();
        actualizarEstado("Construyendo árbol de contextos... (puede tardar unos segundos)");

        SwingWorker<Void, String> trabajador = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    publish("Inicializando árbol de contextos...");
                    arbolContextos = new ContextTree();

                    for (int i = 0; i < 4; i++) {
                        publish("Procesando archivo: " + NOMBRES_SENTIMIENTOS[i] + "...");
                        arbolContextos.construirDesdeArchivo(rutasArchivos[i]);
                    }

                    for (int i = 0; i < 4; i++) {
                        publish("Construyendo modelo de " + NOMBRES_SENTIMIENTOS[i] + "...");
                        modelosSentimientos[i] = arbolContextos.construirModeloDesdeArchivo(rutasArchivos[i]);
                    }

                    modelosCargados = true;
                    return null;
                }

                @Override
                protected void process(java.util.List<String> mensajes) {
                    if (!mensajes.isEmpty()) {
                        actualizarEstado(mensajes.get(mensajes.size() - 1));
                    }
                }

                @Override
                protected void done() {
                    botonCargar.setEnabled(true);
                    try {
                        get();
                        botonAnalizar.setEnabled(true);
                        actualizarEstado("✓ Modelos cargados correctamente. Ingrese una frase para analizar.");
                    } catch (Exception excepcion) {
                        String mensaje = excepcion.getCause() != null
                            ? excepcion.getCause().getMessage()
                            : excepcion.getMessage();
                        mostrarError("Error al cargar modelos: " + mensaje);
                        actualizarEstado("Error al cargar los modelos.");
                    }
                }
            };
        trabajador.execute();
    }

    // toma la frase, arma su modelo y calcula que tan cercana es a cada emocion
    private void analizarFrase() {
        if (!modelosCargados) {
            mostrarError("Primero debe cargar los modelos de sentimientos.");
            return;
        }

        String frase = campoPalabra.getText().trim();
        if (frase.isEmpty()) {
            mostrarError("Por favor ingrese una frase para analizar.");
            return;
        }

        String[] palabras    = frase.toLowerCase().split("\\s+");
        FrequencyTree modeloFrase = arbolContextos.construirModelo(palabras);

        if (modeloFrase.getRaiz() == null) {
            actualizarEstado("⚠ Ninguna palabra de la frase encontrada en el corpus. Intente con otras palabras.");
            planoEmocional.limpiarPunto();
            return;
        }

        double[] similitudes = new double[4];
        for (int i = 0; i < 4; i++) {
            similitudes[i] = modeloFrase.similitudCosenos(modelosSentimientos[i]);
        }

        // Feliz=0, Triste=1, Calmado=2, Enojado=3
        planoEmocional.actualizarPunto(similitudes[0], similitudes[1], similitudes[2], similitudes[3]);

        actualizarEstado(String.format(
                "Análisis: Feliz=%.4f  Triste=%.4f  Calmado=%.4f  Enojado=%.4f",
                similitudes[0], similitudes[1], similitudes[2], similitudes[3]));
    }

    // actualiza el mensaje de abajo en la ventana
    private void actualizarEstado(String mensaje) {
        etiquetaEstado.setText("  " + mensaje);
    }

    // muestra un popup de error
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}