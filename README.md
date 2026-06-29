# tarea-programada3
# Analizador de Emociones mediante Similitud de Cosenos

## Descripción del Proyecto

Este proyecto consiste en una aplicación desarrollada en **Java** que permite analizar emociones presentes en una frase o texto ingresado por el usuario.

El sistema construye modelos de lenguaje a partir de archivos de entrenamiento correspondientes a diferentes emociones y, utilizando la técnica de **similitud de cosenos**, determina qué tan cercana es la frase analizada a cada emoción.

Las emociones consideradas son:

-  Feliz
-  Triste
-  Calmado
-  Enojado

Los resultados se representan gráficamente en un plano emocional bidimensional.

---

## Integrantes

| Nombre | Carné |
|---------|---------|
| María Alejandra Vargas Avilés | C38127 |
| Bryan Geovanny Morales Martínez | C15238 |

---

## Objetivos

- Implementar estructuras de datos dinámicas sin utilizar las colecciones de Java.
- Construir modelos de contexto y frecuencia para diferentes emociones.
- Aplicar la técnica de similitud de cosenos para medir semejanza entre textos.
- Visualizar gráficamente el resultado del análisis emocional.

---

## Funcionalidades

- Carga de archivos de texto para entrenar cada emoción.
- Construcción automática de modelos de contexto.
- Análisis de frases ingresadas por el usuario.
- Cálculo de similitud de cosenos entre la frase y cada emoción.
- Representación gráfica de la emoción predominante en un plano emocional.
- Interfaz gráfica amigable desarrollada con Swing.

---

## Estructura del Proyecto

### Clases principales

| Clase | Descripción |
|--------|-------------|
| `Main.java` | Punto de entrada del programa. |
| `EmotionAnalyzerGUI.java` | Interfaz gráfica principal. |
| `EmotionPlane.java` | Dibuja el plano emocional y la posición de la frase analizada. |
| `ContextTree.java` | Árbol binario que almacena el contexto de las palabras. |
| `ContextNode.java` | Nodo utilizado en el árbol de contextos. |
| `FrequencyTree.java` | Árbol binario que almacena frecuencias de palabras. |
| `WordFreqNode.java` | Nodo utilizado para el árbol de frecuencias. |
| `NeighborList.java` | Lista enlazada de palabras vecinas. |
| `NeighborListNode.java` | Nodo de la lista enlazada de vecinos. |
| `WordBuffer.java` | Buffer dinámico de palabras implementado mediante arreglos. |

---

## Archivos de Entrenamiento

El sistema utiliza archivos de texto para representar las emociones:

- `alegria.txt`
- `tristeza.txt`
- `calma.txt`
- `enojo.txt`

Adicionalmente, se incluyen archivos JSON relacionados con los modelos o datos utilizados durante el desarrollo.

---

## Tecnologías Utilizadas

- **Java**
- **Java Swing** para la interfaz gráfica.
- Estructuras de datos implementadas manualmente:
  - Árboles Binarios de Búsqueda (ABB)
  - Listas Enlazadas
  - Arreglos dinámicos

---

## Algoritmo General

1. Se cargan los archivos correspondientes a cada emoción.
2. El sistema procesa cada texto y construye un árbol de contextos.
3. Se generan modelos de frecuencia para cada emoción.
4. El usuario ingresa una frase.
5. Se calcula la similitud de cosenos entre la frase y cada modelo emocional.
6. Se determina la emoción predominante.
7. El resultado se representa gráficamente en el plano emocional.

---

## Compilación y Ejecución

### Compilar

```bash
javac *.java
```

### Ejecutar

```bash
java Main
```

---

## Ejemplo de Uso

1. Ejecutar la aplicación.
2. Cargar los archivos de entrenamiento para cada emoción.
3. Escribir una frase en el campo de texto.
4. Presionar el botón **Analizar**.
5. Observar la posición obtenida en el plano emocional.

---

## Restricciones del Proyecto

- No se utilizaron colecciones del paquete `java.util` como `ArrayList`, `LinkedList` o `HashMap`.
- Todas las estructuras dinámicas fueron implementadas manualmente.
- El análisis se basa exclusivamente en los textos de entrenamiento proporcionados.

---

## Conclusiones

Este proyecto permitió aplicar conceptos fundamentales de:

- Estructuras de datos dinámicas.
- Árboles binarios de búsqueda.
- Procesamiento básico de lenguaje natural.
- Similitud de cosenos.
- Diseño de interfaces gráficas en Java.

Además, se fortalecieron habilidades relacionadas con la implementación de algoritmos y el desarrollo orientado a objetos.

---
