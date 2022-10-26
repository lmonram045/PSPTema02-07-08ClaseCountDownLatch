package Principal;

import java.util.concurrent.CountDownLatch;

/** Clase que define el hilo auxiliar, cuyo método run() se encarga de sumar los elementos de la tanda de números
 * recibida por su constructor.
 *
 * El constructor recibe también un objeto CountDownLatch de control de sincronización
 */
public class SumaTanda extends Thread {

    static int[][] tabla = { // Matriz de adyacencia
            {1},
            {1, 1},
            {1, 2, 1},
            {1, 3, 3, 1},
            {1, 4, 6, 4, 1},
            {1, 5, 10, 10, 5, 1},
            {1, 6, 15, 20, 15, 6, 1},
            {1, 7, 21, 35, 35, 21, 7, 1},
            {1, 8, 28, 56, 70, 56, 28, 8, 1},
            {1, 9, 36, 84, 126, 126, 84, 36, 9, 1}
    };

    static int[] resultadoTanda; // Array que almacena los resultados de cada tanda

    int indiceTanda; // Índice de la tanda de números a sumar
    CountDownLatch cdl; // Objeto de control de sincronización

    /** Constructor de la clase
     *
     * @param indiceTanda Índice de la tanda de números a sumar
     * @param cdl Objeto de control de sincronización
     */
    SumaTanda(CountDownLatch cdl, int indiceTanda) {
        this.cdl = cdl;
        this.indiceTanda = indiceTanda;
    }

    /**
     * Método run() del hilo auxiliar
     *
     * Suma los elementos de la tanda de números recibida por el constructor.
     *
     * Cuando finaliza esta suma y se almacena el valor, se llama al método countDown() de la barrera
     */
    @Override
    public void run() {
        int elementos = tabla[indiceTanda].length; // Número de elementos de la tanda
        int sumaTanda = 0; // Acumulador de la suma de los elementos de la tanda

        for (int i = 0; i < elementos; i++) {
            sumaTanda += tabla[indiceTanda][i];
        }

        resultadoTanda[indiceTanda] = sumaTanda; // Almacena el resultado de la suma de la tanda

        // Muestra un mensaje
        System.out.println("Suma de la tanda " + indiceTanda + ": " + sumaTanda);

        // finalizada la suma de los elementos de la tanda y almacenado el valor, el hilo llama al método countDown()

        try {
            cdl.countDown();
        } catch (Exception e) { }

    }

}
