/**
 * Árbol binario de búsqueda que almacena palabras con sus frecuencias.
 * Se usa como modelo de frecuencia para una frase o sentimiento.
 * Solo contiene el string de la palabra y su frecuencia acumulada.
 */
public class FrequencyTree {

    /** Raíz del árbol de frecuencias. */
    private WordFreqNode raiz;

    /**
     * Constructor que inicializa un árbol de frecuencias vacío.
     */
    public FrequencyTree() {
        this.raiz = null;
    }

    /**
     * Inserta una palabra en el árbol. Si ya existe, incrementa su frecuencia.
     *
     * @param palabra La palabra a insertar o actualizar.
     */
    public void insertar(String palabra) {
        raiz = insertarRec(raiz, palabra);
    }

    /**
     * Inserta recursivamente una palabra en el subárbol dado.
     *
     * @param nodo    Raíz del subárbol actual.
     * @param palabra La palabra a insertar.
     * @return El nodo raíz del subárbol tras la inserción.
     */
    private WordFreqNode insertarRec(WordFreqNode nodo, String palabra) {
        if (nodo == null) {
            return new WordFreqNode(palabra);
        }
        int comparacion = palabra.compareTo(nodo.getPalabra());
        if (comparacion < 0) {
            nodo.setHijoIzquierdo(insertarRec(nodo.getHijoIzquierdo(), palabra));
        } else if (comparacion > 0) {
            nodo.setHijoDerecho(insertarRec(nodo.getHijoDerecho(), palabra));
        } else {
            nodo.incrementarFrecuencia();
        }
        return nodo;
    }

    /**
     * Agrega una palabra con una frecuencia específica. Si existe, suma la frecuencia.
     *
     * @param palabra    La palabra a insertar o actualizar.
     * @param frecuencia La frecuencia a agregar.
     */
    public void insertarConFrecuencia(String palabra, int frecuencia) {
        raiz = insertarConFrecuenciaRec(raiz, palabra, frecuencia);
    }

    /**
     * Inserta recursivamente una palabra con frecuencia en el subárbol dado.
     *
     * @param nodo       Raíz del subárbol actual.
     * @param palabra    La palabra a insertar.
     * @param frecuencia La frecuencia a agregar.
     * @return El nodo raíz del subárbol tras la inserción.
     */
    private WordFreqNode insertarConFrecuenciaRec(WordFreqNode nodo, String palabra, int frecuencia) {
        if (nodo == null) {
            WordFreqNode nuevoNodo = new WordFreqNode(palabra);
            nuevoNodo.agregarFrecuencia(frecuencia - 1); // -1 porque el constructor ya pone 1
            return nuevoNodo;
        }
        int comparacion = palabra.compareTo(nodo.getPalabra());
        if (comparacion < 0) {
            nodo.setHijoIzquierdo(insertarConFrecuenciaRec(nodo.getHijoIzquierdo(), palabra, frecuencia));
        } else if (comparacion > 0) {
            nodo.setHijoDerecho(insertarConFrecuenciaRec(nodo.getHijoDerecho(), palabra, frecuencia));
        } else {
            nodo.agregarFrecuencia(frecuencia);
        }
        return nodo;
    }

    /**
     * Busca la frecuencia de una palabra en el árbol.
     *
     * @param palabra La palabra a buscar.
     * @return La frecuencia de la palabra, 0 si no existe.
     */
    public int getFrecuencia(String palabra) {
        WordFreqNode nodo = buscarRec(raiz, palabra);
        return (nodo == null) ? 0 : nodo.getFrecuencia();
    }

    /**
     * Busca un nodo en el árbol por palabra.
     *
     * @param nodo    Raíz del subárbol actual.
     * @param palabra La palabra a buscar.
     * @return El nodo encontrado o null si no existe.
     */
    private WordFreqNode buscarRec(WordFreqNode nodo, String palabra) {
        if (nodo == null) return null;
        int comparacion = palabra.compareTo(nodo.getPalabra());
        if (comparacion < 0) return buscarRec(nodo.getHijoIzquierdo(), palabra);
        if (comparacion > 0) return buscarRec(nodo.getHijoDerecho(), palabra);
        return nodo;
    }

    /**
     * Obtiene la raíz del árbol.
     *
     * @return La raíz del árbol de frecuencias.
     */
    public WordFreqNode getRaiz() {
        return raiz;
    }

    /**
     * Calcula la norma (magnitud) del vector de frecuencias del árbol.
     * Se usa para calcular similitud de cosenos.
     *
     * @return La norma del vector de frecuencias.
     */
    public double getNorma() {
        return Math.sqrt(sumarCuadradosRec(raiz));
    }

    /**
     * Calcula recursivamente la suma de cuadrados de las frecuencias.
     *
     * @param nodo Raíz del subárbol actual.
     * @return La suma de cuadrados de frecuencias del subárbol.
     */
    private double sumarCuadradosRec(WordFreqNode nodo) {
        if (nodo == null) return 0;
        double frec = nodo.getFrecuencia();
        return frec * frec + sumarCuadradosRec(nodo.getHijoIzquierdo()) + sumarCuadradosRec(nodo.getHijoDerecho());
    }

    /**
     * Calcula el producto punto entre este árbol y otro árbol de frecuencias.
     *
     * @param otro El otro árbol de frecuencias.
     * @return El producto punto de los dos vectores de frecuencias.
     */
    public double productoPunto(FrequencyTree otro) {
        return productoPuntoRec(this.raiz, otro);
    }

    /**
     * Calcula recursivamente el producto punto de un subárbol contra otro árbol.
     *
     * @param nodo Raíz del subárbol actual de este árbol.
     * @param otro El otro árbol de frecuencias.
     * @return El producto punto parcial.
     */
    private double productoPuntoRec(WordFreqNode nodo, FrequencyTree otro) {
        if (nodo == null) return 0;
        double frecOtro = otro.getFrecuencia(nodo.getPalabra());
        double contribucion = nodo.getFrecuencia() * frecOtro;
        return contribucion
                + productoPuntoRec(nodo.getHijoIzquierdo(), otro)
                + productoPuntoRec(nodo.getHijoDerecho(), otro);
    }

    /**
     * Calcula la similitud de cosenos entre este árbol y otro árbol de frecuencias.
     *
     * @param otro El otro árbol de frecuencias.
     * @return Un valor entre 0 y 1 que representa la similitud (1 = idéntico, 0 = sin similitud).
     */
    public double similitudCosenos(FrequencyTree otro) {
        double producto = productoPunto(otro);
        double normaA = this.getNorma();
        double normaB = otro.getNorma();
        if (normaA == 0 || normaB == 0) return 0;
        return producto / (normaA * normaB);
    }
}
