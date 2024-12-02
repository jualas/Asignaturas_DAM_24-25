package es.jualas;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.Arrays;
import java.util.List;

public class TiendaDeLujo {

    private static final Semaphore semaforoAforo = new Semaphore(3, true);  // Semáforo que controla el aforo máximo de 3
    private static final CountDownLatch contadorSalida = new CountDownLatch(10);  // Contador para cerrar la tienda tras 10 clientes
    private static boolean vipEsperando = false;  // Bandera para verificar si un VIP está esperando para entrar
    private static boolean vipDentro = false;  // Bandera para verificar si un VIP está dentro de la tienda
    private static final Object monitor = new Object();  // Monitor para sincronización de acceso

    public static void main(String[] args) {
        // Lista con el orden de lanzamiento de los hilos (clientes y VIPs)
        List<String> orden = Arrays.asList(
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

        // Iterar sobre la lista y lanzar los hilos en el orden especificado
        for (String nombre : orden) {
            Thread hilo;
            if (nombre.startsWith("Cliente")) {
                int id = Integer.parseInt(nombre.split(" ")[1]);
                hilo = new Thread(new Cliente(id), nombre);
                hilo.setPriority(Thread.MIN_PRIORITY);  // Baja prioridad para clientes normales
            } else {
                hilo = new Thread(new ClienteVIP(nombre), nombre);
                hilo.setPriority(Thread.MAX_PRIORITY);  // Alta prioridad para VIPs
            }
            System.out.println("Voy a lanzar " + nombre);  // Mensaje de lanzamiento del hilo
            hilo.start();
        }

        try {
            contadorSalida.await();  // Esperar hasta que todos los clientes hayan salido
            System.out.println("TIENDA CERRADA");  // Mensaje de cierre de la tienda
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Clase para clientes normales
    static class Cliente implements Runnable {
        private int id;

        public Cliente(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Llega a la puerta de la tienda Cliente" + id);  // Cliente llega a la puerta
                synchronized (monitor) {
                    // Verificar si hay un VIP dentro o esperando, en cuyo caso el cliente debe esperar
                    while (vipDentro || vipEsperando) {
                        if (vipDentro) {
                            System.out.println("Cliente" + id + " está esperando porque un VIP está dentro.");
                        } else if (vipEsperando) {
                            System.out.println("Cliente" + id + " está esperando porque un VIP tiene prioridad para entrar.");
                        }
                        monitor.wait();  // Cliente espera hasta que el VIP termine
                    }
                }

                // Cliente entra a la tienda
                semaforoAforo.acquire();
                System.out.println("--> Cliente" + id + " entra en la tienda");
                Thread.sleep((int) (1000 + Math.random() * 1000));  // Tiempo aleatorio dentro de la tienda
                System.out.println("<-- Cliente" + id + " sale de la tienda");  // Cliente sale de la tienda
                semaforoAforo.release();

                synchronized (monitor) {
                    monitor.notifyAll();  // Notificar a los hilos en espera que verifiquen su estado
                }

                contadorSalida.countDown();  // Disminuir el contador de salida al salir el cliente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Clase para clientes VIP
    static class ClienteVIP implements Runnable {
        private String nombre;

        public ClienteVIP(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            try {
                System.out.println("Llega a la puerta de la tienda " + nombre);  // VIP llega a la puerta
                synchronized (monitor) {
                    vipEsperando = true;  // Marcar que un VIP está esperando
                    while (semaforoAforo.availablePermits() < 3) {
                        System.out.println(nombre + " está esperando a que la tienda se vacíe.");  // VIP espera a que se vacíe
                        monitor.wait();  // VIP espera hasta que la tienda esté vacía
                    }
                    vipEsperando = false;  // Desmarcar que el VIP está esperando
                    vipDentro = true;  // Marcar que el VIP está dentro
                }

                // VIP entra a la tienda y ocupa todo el aforo
                semaforoAforo.acquire(3);
                System.out.println("--> " + nombre + " entra en la tienda");
                Thread.sleep(3000);  // VIP pasa 3 segundos en la tienda
                System.out.println("<-- " + nombre + " salió de la tienda");  // VIP sale de la tienda

                // Liberar el aforo y notificar a otros hilos
                semaforoAforo.release(3);
                synchronized (monitor) {
                    vipDentro = false;  // Desmarcar que el VIP está dentro
                    monitor.notifyAll();  // Notificar a otros hilos en espera
                }

                contadorSalida.countDown();  // Disminuir el contador de salida al salir el VIP
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
