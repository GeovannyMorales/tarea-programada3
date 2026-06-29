import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Árbol binario de búsqueda que almacena el contexto de cada palabra del corpus
 * Cada nodo contiene una palabra y su lista de palabras vecinas con frecuencias
 * Se construye con una ventana de tamaño n=2 (2 palabras antes y 2 después)
 */
public class ContextTree {
    private ContextNode raiz; // Raíz del árbol de contextos
    private static final int TAMANIO_VENTANA = 2;

    /**
     * Constructor que inicializa un árbol de contextos vacío
     */
    public ContextTree() {
        this.raiz = null;
    }

    /**
     * Inserta o actualiza el contexto de una palabra agregando un vecino
     *
     * @param palabra La palabra principal.
     * @param vecino  La palabra vecina a asociar.
     */
    public void agregarContexto(String palabra, String vecino) {
        raiz = agregarContextoRec(raiz, palabra, vecino);
    }

    /**
     * Inserta o actualiza recursivamente el contexto en el subárbol dado
     *
     * @param nodo    Raíz del subárbol actual.
     * @param palabra La palabra principal.
     * @param vecino  La palabra vecina a agregar.
     * @return El nodo raíz del subárbol tras la operación.
     */
    private ContextNode agregarContextoRec(ContextNode nodo, String palabra, String vecino) {
        if (nodo == null) {
            ContextNode nuevoNodo = new ContextNode(palabra);
            nuevoNodo.agregarVecino(vecino);
            return nuevoNodo;
        }
        int comparacion = palabra.compareTo(nodo.getPalabra());
        if (comparacion < 0) {
            nodo.setHijoIzquierdo(agregarContextoRec(nodo.getHijoIzquierdo(), palabra, vecino));
        } else if (comparacion > 0) {
            nodo.setHijoDerecho(agregarContextoRec(nodo.getHijoDerecho(), palabra, vecino));
        } else {
            nodo.agregarVecino(vecino);
        }
        return nodo;
    }

    /**
     * Busca y retorna el nodo de contexto de una palabra
     *
     * @param palabra La palabra a buscar.
     * @return El nodo de contexto o null si no existe
     */
    public ContextNode buscar(String palabra) {
        return buscarRec(raiz, palabra);
    }

    /**
     * Busca recursivamente un nodo de contexto en el subárbol dado
     *
     * @param nodo    Raíz del subárbol actual.
     * @param palabra La palabra a buscar.
     * @return El nodo encontrado o null si no existe.
     */
    private ContextNode buscarRec(ContextNode nodo, String palabra) {
        if (nodo == null) return null;
        int comparacion = palabra.compareTo(nodo.getPalabra());
        if (comparacion < 0) return buscarRec(nodo.getHijoIzquierdo(), palabra);
        if (comparacion > 0) return buscarRec(nodo.getHijoDerecho(), palabra);
        return nodo;
    }

    /**
     * Procesa un archivo de texto y construye el árbol de contextos
     * Cada palabra del texto tiene registradas sus n vecinas anteriores y posteriores
     * Se normaliza el texto a minúsculas y se eliminan caracteres especiales y números
     *
     * @param rutaArchivo Ruta al archivo de texto a procesar.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public void construirDesdeArchivo(String rutaArchivo) throws IOException {
        WordBuffer buffer = new WordBuffer(10000);
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] tokens = linea.toLowerCase().split("\\s+");
                for (String token : tokens) {
                    String limpia = limpiarPalabra(token);
                    if (!limpia.isEmpty()) {
                        buffer.agregar(limpia);
                    }
                }
            }
        }

        String[] palabras = buffer.convertirAArreglo();
        int total = palabras.length;
        for (int i = 0; i < total; i++) {
            String actual = palabras[i];
            // Vecinos anteriores
            for (int j = Math.max(0, i - TAMANIO_VENTANA); j < i; j++) {
                agregarContexto(actual, palabras[j]);
            }
            // Vecinos posteriores
            for (int j = i + 1; j <= Math.min(total - 1, i + TAMANIO_VENTANA); j++) {
                agregarContexto(actual, palabras[j]);
            }
        }
    }

    /**
     * Limpia una palabra eliminando caracteres especiales y números.
     * Solo conserva letras, incluyendo letras con acentos del español.
     *
     * @param palabra La palabra a limpiar
     * @return La palabra limpia, o cadena vacía si no contiene letras.
     */
    private String limpiarPalabra(String palabra) {
        StringBuilder sb = new StringBuilder();
        for (char caracter : palabra.toCharArray()) {
            if (Character.isLetter(caracter)) {
                sb.append(caracter);
            }
        }
        return sb.toString();
    }

    /**
     * Construye un modelo de frecuencias para un conjunto de palabras
     * Busca el contexto de cada palabra en el árbol y acumula las frecuencias vecinas
     * en un árbol de frecuencias unificado (modelo de la frase o sentimiento)
     *
     * @param palabras Arreglo de palabras para las cuales construir el modelo.
     * @return Un árbol de frecuencias con el contexto combinado de todas las palabras.
     */
    public FrequencyTree construirModelo(String[] palabras) {
        FrequencyTree modelo = new FrequencyTree();
        for (String palabra : palabras) {
            String limpia = limpiarPalabra(palabra.toLowerCase());
            if (limpia.isEmpty()) continue;
            ContextNode contexto = buscar(limpia);
            if (contexto != null) {
                NeighborList listaVecinos = contexto.getListaVecinos();
                NeighborListNode actual = listaVecinos.getCabeza();
                while (actual != null) {
                    modelo.insertarConFrecuencia(actual.getPalabraVecina(), actual.getFrecuencia());
                    actual = actual.getSiguiente();
                }
            }
        }
        return modelo;
    }

    /**
     * Construye un modelo de frecuencias para todas las palabras de un archivo
     * Lee el archivo, extrae sus palabras y construye el modelo de frecuencias
     *
     * @param rutaArchivo Ruta al archivo de texto del sentimiento
     * @return Un árbol de frecuencias que representa el modelo del sentimiento
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public FrequencyTree construirModeloDesdeArchivo(String rutaArchivo) throws IOException {
        WordBuffer buffer = new WordBuffer(10000);
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] tokens = linea.toLowerCase().split("\\s+");
                for (String token : tokens) {
                    String limpia = limpiarPalabra(token);
                    if (!limpia.isEmpty()) {
                        buffer.agregar(limpia);
                    }
                }
            }
        }
        return construirModelo(buffer.convertirAArreglo());
    }
}
