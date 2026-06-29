/**
 * Nodo del árbol de contextos. Cada nodo almacena una palabra del corpus
 * junto con lista de palabras vecinas (contexto con ventana n=2)
 */
public class ContextNode {
    private String palabra;
    private NeighborList listaVecinos;
    private ContextNode hijoIzquierdo;
    private ContextNode hijoDerecho;

    /**
     * Constructor que inicializa el nodo con una palabra y lista de vecinos vacía
     *
     * @param palabra La palabra a almacenar en este nodo
     */
    public ContextNode(String palabra) {
        this.palabra = palabra;
        this.listaVecinos = new NeighborList();
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    /**
     * Obtiene la palabra almacenada en este nodo
     *
     * @return La palabra principal del nodo
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * Obtiene la lista de palabras vecinas del contexto
     * @return La lista de vecinos con frecuencias
     */
    public NeighborList getListaVecinos() {
        return listaVecinos;
    }

    /**
     * Agregar una palabra vecina al contexto de esta palabra
     * @param el vecino La palabra vecina a agregar
     */
    public void agregarVecino(String vecino) {
        listaVecinos.agregarVecino(vecino);
    }

    //ggeter
    public ContextNode getHijoDerecho() {
        return hijoDerecho;
    }

    public ContextNode getHijoIzquierdo() {
        return hijoIzquierdo;
    }
    //setter
    public void setHijoIzquierdo(ContextNode hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(ContextNode hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
}
