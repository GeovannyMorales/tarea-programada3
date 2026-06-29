/**
 * Nodo de lista enlazada simple para almacenar palabras vecinas y su frecuencia.
 * Se usa como auxiliar para construir el contexto de cada palabra.
 */
public class NeighborListNode {
    private String palabraVecina;
    private int frecuencia;
    private NeighborListNode siguiente;

    /**
     * COnstructor
     */
    public NeighborListNode(String palabraVecina) {
        this.palabraVecina = palabraVecina;
        this.frecuencia = 1;
        this.siguiente = null;
    }
    
    /**
     * Incrementa en uno la frecuencia de la palabra vecina.
     */
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    //getters
    public String getPalabraVecina() {
        return palabraVecina;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public NeighborListNode getSiguiente() {
        return siguiente;
    }

    //stter
    public void setSiguiente(NeighborListNode siguiente) {
        this.siguiente = siguiente;
    }
}