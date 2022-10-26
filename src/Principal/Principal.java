package Principal;

import java.util.concurrent.CountDownLatch;


public class Principal {


    /**
     * Realiza la suma total de los elementos de la matriz de adyacencia, cuando el objeto CountDownLatch que controla
     * la sincronización de los hilos auxiliares ha llegado a cero (lo permite)
     *
     * @param args argumentos de linea de comandos
     */
    public static void main(String[] args) {

        final int NUM_TANDAS = SumaTanda.tabla.length; // Número de tandas de números a sumar

        int sumaTotal = 0; // Acumulador de la suma total de los elementos de la matriz de adyacencia

        SumaTanda.resultadoTanda = new int[NUM_TANDAS]; // Array que almacena los resultados de cada tanda

        // Objeto tipo CountDownLatch para 10 hilos (uno para cada tanda de números). Este objeto pondrá en espera cada
        // hilo desde donde se invoque su método await() (en nuestro caso, sólo el hilo principal), hasta que cada uno
        // de los 10 hilos que controla realice una llamada al método countDown() de este objeto.
        CountDownLatch cdl = new CountDownLatch(NUM_TANDAS);

        // Mensaje de espera
        System.out.println("Obteniendo la suma de los elementos de cada tanda...........\n");

        // lanza un hilo por cada tanda de elementos (10 en total)
        for (int i = 0; i < NUM_TANDAS; i++) {
            new SumaTanda(cdl, i).start(); // cada nuevo hilo recibe el objeto CountDownLatch y el índice de la tanda cuyos elementos debe sumar
        }

        try {
            // Coloca el hilo desde donde se ejecuta esta llamada al método await() (el hilo principal, en nuestro caso),
            // a la espera de que cada hilo controlado por la cuenta atrás llame al método countDown().
            // Ningún hilo controlado llamará a este método hasta que no haya completado la suma de su tanda
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cuando se reanuda el hilo principal, todos los hilos controlados por la cuenta atrás han terminado de sumar
        // su tanda. Por tanto, es el momento de realizar la suma total
        for (int i = 0; i < NUM_TANDAS; i++) {
            sumaTotal += SumaTanda.resultadoTanda[i];
        }

        // Imprime la suma total
        System.out.println("\nTodas las tandas han sido sumadas. La suma total es: " + sumaTotal);
    }

}
