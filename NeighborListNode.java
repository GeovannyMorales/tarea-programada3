/**
 * Nodo de lista enlazada simple para almacenar palabras vecinas y su frecuencia.
 * Se usa como estructura auxiliar para construir el contexto de cada palabra.
 */
public class NeighborListNode {

    /** Palabra vecina almacenada en este nodo de lista. */
    private String palabraVecina;

    /** Frecuencia con la que esta palabra aparece como vecina. */
    private int frecuencia;

    /** Siguiente nodo en la lista. */
    private NeighborListNode siguiente;

    /**
     * Constructor que inicializa el nodo con la palabra vecina y frecuencia 1.
     *
     * @param palabraVecina La palabra vecina a almacenar.
     */
    public NeighborListNode(String palabraVecina) {
        this.palabraVecina = palabraVecina;
        this.frecuencia = 1;
        this.siguiente = null;
    }

    /**
     * Obtiene la palabra vecina almacenada en este nodo.
     *
     * @return La palabra vecina.
     */
    public String getPalabraVecina() {
        return palabraVecina;
    }

    /**
     * Obtiene la frecuencia de esta palabra vecina.
     *
     * @return La frecuencia de aparición.
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * Incrementa en uno la frecuencia de la palabra vecina.
     */
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    /**
     * Obtiene el siguiente nodo en la lista.
     *
     * @return El siguiente nodo.
     */
    public NeighborListNode getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el siguiente nodo en la lista.
     *
     * @param siguiente El siguiente nodo a enlazar.
     */
    public void setSiguiente(NeighborListNode siguiente) {
        this.siguiente = siguiente;
    }
}
