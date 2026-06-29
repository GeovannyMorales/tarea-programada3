/**
 * Lista enlazada simple que almacena palabras vecinas con sus frecuencias.
 * Representa el contexto de una palabra específica dentro del corpus.
 */
public class NeighborList {

    /** Cabeza de la lista de vecinos. */
    private NeighborListNode cabeza;

    /** Cantidad de elementos únicos en la lista. */
    private int tamanio;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public NeighborList() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    /**
     * Agrega una palabra vecina a la lista. Si ya existe, incrementa su frecuencia.
     *
     * @param palabraVecina La palabra vecina a agregar.
     */
    public void agregarVecino(String palabraVecina) {
        NeighborListNode actual = cabeza;
        while (actual != null) {
            if (actual.getPalabraVecina().equals(palabraVecina)) {
                actual.incrementarFrecuencia();
                return;
            }
            actual = actual.getSiguiente();
        }
        // No existe, insertar al frente
        NeighborListNode nuevoNodo = new NeighborListNode(palabraVecina);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
        tamanio++;
    }

    /**
     * Obtiene la cabeza de la lista de vecinos.
     *
     * @return El primer nodo de la lista.
     */
    public NeighborListNode getCabeza() {
        return cabeza;
    }

    /**
     * Obtiene la cantidad de palabras únicas en la lista.
     *
     * @return El tamaño de la lista.
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Obtiene la frecuencia de una palabra vecina específica.
     *
     * @param palabraVecina La palabra a buscar.
     * @return La frecuencia de la palabra, 0 si no existe.
     */
    public int getFrecuencia(String palabraVecina) {
        NeighborListNode actual = cabeza;
        while (actual != null) {
            if (actual.getPalabraVecina().equals(palabraVecina)) {
                return actual.getFrecuencia();
            }
            actual = actual.getSiguiente();
        }
        return 0;
    }
}
