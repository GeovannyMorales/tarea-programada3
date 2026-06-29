/**
 * Nodo del árbol de contextos. Cada nodo almacena una palabra del corpus
 * junto con su lista de palabras vecinas (contexto con ventana n=2).
 */
public class ContextNode {

    /** Palabra principal de este nodo de contexto. */
    private String palabra;

    /** Lista de palabras vecinas y sus frecuencias. */
    private NeighborList listaVecinos;

    /** Hijo izquierdo (palabras menores lexicográficamente). */
    private ContextNode hijoIzquierdo;

    /** Hijo derecho (palabras mayores lexicográficamente). */
    private ContextNode hijoDerecho;

    /**
     * Constructor que inicializa el nodo con una palabra y lista de vecinos vacía.
     *
     * @param palabra La palabra a almacenar en este nodo.
     */
    public ContextNode(String palabra) {
        this.palabra = palabra;
        this.listaVecinos = new NeighborList();
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    /**
     * Obtiene la palabra almacenada en este nodo.
     *
     * @return La palabra principal del nodo.
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * Obtiene la lista de palabras vecinas del contexto.
     *
     * @return La lista de vecinos con frecuencias.
     */
    public NeighborList getListaVecinos() {
        return listaVecinos;
    }

    /**
     * Agrega una palabra vecina al contexto de esta palabra.
     *
     * @param vecino La palabra vecina a agregar.
     */
    public void agregarVecino(String vecino) {
        listaVecinos.agregarVecino(vecino);
    }

    /**
     * Obtiene el hijo izquierdo del nodo.
     *
     * @return El hijo izquierdo.
     */
    public ContextNode getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    /**
     * Establece el hijo izquierdo del nodo.
     *
     * @param hijoIzquierdo El nuevo hijo izquierdo.
     */
    public void setHijoIzquierdo(ContextNode hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    /**
     * Obtiene el hijo derecho del nodo.
     *
     * @return El hijo derecho.
     */
    public ContextNode getHijoDerecho() {
        return hijoDerecho;
    }

    /**
     * Establece el hijo derecho del nodo.
     *
     * @param hijoDerecho El nuevo hijo derecho.
     */
    public void setHijoDerecho(ContextNode hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
}
