package es.jualas;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.Arrays;
import java.util.List;

public class TiendaDeLujo {

    private static final Semaphore storeSemaphore = new Semaphore(3, true);
    private static final List<String> order = Arrays.asList(
            "Cliente 1",
            "Cliente 2",
            "VIP Victoria Beckham",
            "Cliente 3",
            "Cliente 4",
            "Cliente 5",
            "Cliente 6",
            "VIP David Beckham",
            "Cliente 7",
            "Cliente 8"
    );
    private static final CountDownLatch latch = new CountDownLatch(order.size()); // Contador para esperar que todos los hilos terminen

    public static void main(String[] args) {
        // Lanzar los hilos según el orden definido en la lista "order"
        for (String name : order) {
            Thread thread;
            if (name.startsWith("Cliente")) {
                int id = Integer.parseInt(name.split(" ")[1]);
                thread = new Thread(new Customer(id), name);
                thread.setPriority(Thread.MIN_PRIORITY); // Baja prioridad para clientes normales
            } else {
                thread = new Thread(new VIPCustomer(name), name);
                thread.setPriority(Thread.MAX_PRIORITY); // Alta prioridad para VIPs
            }
            System.out.println("Voy a lanzar " + name);
            thread.start();
        }

        try {
            // Espera a que todos los hilos terminen
            latch.await();
            System.out.println("TIENDA CERRADA");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Customer implements Runnable {
        private int id;

        public Customer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                // Simula un tiempo de llegada aleatorio
                //Thread.sleep((int) (Math.random() * 1000));
                System.out.println("Llega a la puerta de la tienda Cliente" + id);

                // Intenta entrar a la tienda
                storeSemaphore.acquire();
                System.out.println("--> Cliente" + id + " entra en la tienda");

                // Simula el tiempo dentro de la tienda
                Thread.sleep((int) (1000 + Math.random() * 1000));
                System.out.println("<-- Cliente" + id + " sale de la tienda");

                // Libera el permiso en el semáforo
                storeSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();  // Marca que este cliente ha terminado
            }
        }
    }

    static class VIPCustomer implements Runnable {
        private String name;

        public VIPCustomer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                // Simula un tiempo de llegada aleatorio
                //Thread.sleep((int) (Math.random() * 1000));
                System.out.println("Llega a la puerta de la tienda " + name);

                // Intenta adquirir los 3 permisos para tener la tienda en exclusiva
                storeSemaphore.acquire(3);
                System.out.println("--> " + name + " entra en la tienda");

                // Simula el tiempo dentro de la tienda
                Thread.sleep(3000);
                System.out.println("<-- " + name + " salió de la tienda");

                // Libera los 3 permisos
                storeSemaphore.release(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();  // Marca que este VIP ha terminado
            }
        }
    }
}
