/**
 * Buffer dinámico de palabras implementado con arreglos.
 * Reemplaza el uso de ArrayList para cumplir con la restricción de no usar
 * las colecciones de Java (java.util.List, java.util.ArrayList, etc.).
 */
public class WordBuffer {

    /** Arreglo interno de palabras. */
    private String[] datos;

    /** Cantidad actual de palabras en el buffer. */
    private int tamanio;

    /**
     * Constructor que inicializa el buffer con una capacidad inicial.
     *
     * @param capacidadInicial Capacidad inicial del buffer.
     */
    public WordBuffer(int capacidadInicial) {
        this.datos = new String[capacidadInicial];
        this.tamanio = 0;
    }

    /**
     * Agrega una palabra al final del buffer, redimensionando si es necesario.
     *
     * @param palabra La palabra a agregar.
     */
    public void agregar(String palabra) {
        if (tamanio == datos.length) {
            redimensionar();
        }
        datos[tamanio++] = palabra;
    }

    /**
     * Obtiene la palabra en la posición indicada.
     *
     * @param indice Índice de la posición.
     * @return La palabra en esa posición.
     */
    public String obtener(int indice) {
        return datos[indice];
    }

    /**
     * Retorna la cantidad de palabras almacenadas en el buffer.
     *
     * @return El tamaño actual del buffer.
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Retorna las palabras del buffer como un arreglo de tamaño exacto.
     *
     * @return Arreglo con todas las palabras del buffer.
     */
    public String[] convertirAArreglo() {
        String[] resultado = new String[tamanio];
        for (int i = 0; i < tamanio; i++) {
            resultado[i] = datos[i];
        }
        return resultado;
    }

    /**
     * Duplica la capacidad del arreglo interno cuando se llena.
     */
    private void redimensionar() {
        String[] nuevosDatos = new String[datos.length * 2];
        for (int i = 0; i < tamanio; i++) {
            nuevosDatos[i] = datos[i];
        }
        datos = nuevosDatos;
    }

    /**
     * Limpia el buffer, eliminando todos los elementos.
     */
    public void limpiar() {
        tamanio = 0;
    }
}
