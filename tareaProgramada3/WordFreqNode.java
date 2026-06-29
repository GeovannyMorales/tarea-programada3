/**
 * Nodo de árbol binario de búsqueda que almacena una palabra y su frecuencia.
 * Se utiliza en los modelos de frecuencia para calcular similitud de cosenos.
 */
public class WordFreqNode {

    /** Palabra almacenada en este nodo. */
    private String palabra;

    /** Frecuencia acumulada de la palabra. */
    private int frecuencia;

    /** Hijo izquierdo (palabras menores lexicográficamente). */
    private WordFreqNode hijoIzquierdo;

    /** Hijo derecho (palabras mayores lexicográficamente). */
    private WordFreqNode hijoDerecho;

    /**
     * Constructor que inicializa el nodo con una palabra y frecuencia 1.
     *
     * @param palabra La palabra a almacenar.
     */
    public WordFreqNode(String palabra) {
        this.palabra = palabra;
        this.frecuencia = 1;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    /**
     * Obtiene la palabra almacenada en este nodo.
     *
     * @return La palabra del nodo.
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * Obtiene la frecuencia acumulada de la palabra.
     *
     * @return La frecuencia de la palabra.
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * Incrementa en uno la frecuencia de la palabra.
     */
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    /**
     * Agrega una cantidad específica a la frecuencia actual.
     *
     * @param cantidad Cantidad a agregar a la frecuencia.
     */
    public void agregarFrecuencia(int cantidad) {
        this.frecuencia += cantidad;
    }

    /**
     * Obtiene el hijo izquierdo del nodo.
     *
     * @return El hijo izquierdo.
     */
    public WordFreqNode getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    /**
     * Establece el hijo izquierdo del nodo.
     *
     * @param hijoIzquierdo El nuevo hijo izquierdo.
     */
    public void setHijoIzquierdo(WordFreqNode hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    /**
     * Obtiene el hijo derecho del nodo.
     *
     * @return El hijo derecho.
     */
    public WordFreqNode getHijoDerecho() {
        return hijoDerecho;
    }

    /**
     * Establece el hijo derecho del nodo.
     *
     * @param hijoDerecho El nuevo hijo derecho.
     */
    public void setHijoDerecho(WordFreqNode hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
}
