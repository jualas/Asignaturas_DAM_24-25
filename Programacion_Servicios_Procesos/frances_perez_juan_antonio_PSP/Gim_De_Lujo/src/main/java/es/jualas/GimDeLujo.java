package es.jualas;


import java.util.concurrent.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GimDeLujo {

    // Semáforo para controlar el acceso a la sala de registro
    private static final Semaphore semaforoRegistro = new Semaphore(1, true);
    // Semáforo para controlar el acceso a la sala de máquinas
    private static final Semaphore semaforoMaquinas = new Semaphore(3, true);
    // Barrera cíclica para esperar a que todos terminen antes de cerrar el gimnasio
    private static final CyclicBarrier barrieraCierre = new CyclicBarrier(10, () -> System.out.println("Gimnasio cerrado"));
    // Generador de números aleatorios
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Lista de clientes
        List<String> clientes = Arrays.asList(
                "Cliente0", "Cliente1", "Cliente2", "Cliente3", "Cliente4", "Cliente5", "Cliente6"
        );
        // Lista de deportistas de élite
        List<String> deportistasElite = Arrays.asList("Messi", "Usain Bolt", "Simone Biles");

        // Lanzar el primer deportista de élite
        lanzarHilo(deportistasElite.get(0), true);

        // Lanzar todos los clientes
        for (String cliente : clientes) {
            lanzarHilo(cliente, false);
        }

        // Lanzar los dos deportistas de élite restantes
        for (int i = 1; i < deportistasElite.size(); i++) {
            lanzarHilo(deportistasElite.get(i), true);
        }
    }

    // Método para lanzar un hilo
    private static void lanzarHilo(String nombre, boolean esDeportistaElite) {
        Thread hilo;
        if (esDeportistaElite) {
            // Crear hilo para deportista de élite
            hilo = new Thread(new DeportistaElite(nombre), nombre);
        } else {
            // Crear hilo para cliente
            hilo = new Thread(new Cliente(nombre), nombre);
        }
        // Imprimir mensaje de lanzamiento
        System.out.println("Lanzo " + nombre);
        // Iniciar el hilo
        hilo.start();
    }

    // Clase abstracta Persona que implementa Runnable
    static abstract class Persona implements Runnable {
        protected String nombre;

        public Persona(String nombre) {
            this.nombre = nombre;
        }

        // Método para registrarse
        protected void registrarse() throws InterruptedException {
            try {
                // Adquirir semáforo de registro
                semaforoRegistro.acquire();
                // Imprimir mensaje de entrada a la sala de registro
                System.out.println("-->" + nombre + ": ENTRA en la sala de REGISTRO");
                // Simular tiempo de registro
                Thread.sleep(1000 + random.nextInt(2000));
            } catch (InterruptedException e) {
                // Manejar excepción de interrupción
                System.err.println("Error durante el registro de " + nombre + ": " + e.getMessage());
                throw e;
            } finally {
                // Imprimir mensaje de salida de la sala de registro
                System.out.println("   -->" + nombre + ": sale de la sala de REGISTRO");
                // Liberar semáforo de registro
                semaforoRegistro.release();
            }
        }

        // Método abstracto para usar las máquinas
        protected abstract void usarMaquinas() throws InterruptedException;

        @Override
        public void run() {
            try {
                // Llamar al método registrarse
                registrarse();
                // Llamar al método usarMaquinas
                usarMaquinas();
                // Esperar en la barrera de cierre
                barrieraCierre.await();
            } catch (InterruptedException e) {
                // Manejar excepción de interrupción
                System.err.println("Interrupción en la ejecución de " + nombre + ": " + e.getMessage());
            } catch (BrokenBarrierException e) {
                // Manejar excepción de barrera rota
                System.err.println("Error en la barrera de cierre para " + nombre + ": " + e.getMessage());
            } catch (Exception e) {
                // Manejar cualquier otra excepción
                System.err.println("Error inesperado para " + nombre + ": " + e.getMessage());
            }
        }
    }

    // Clase Cliente que extiende Persona
    static class Cliente extends Persona {
        public Cliente(String nombre) {
            super(nombre);
        }

        @Override
        protected void usarMaquinas() throws InterruptedException {
            try {
                // Adquirir semáforo de máquinas
                semaforoMaquinas.acquire();
                // Imprimir mensaje de entrada a la sala de máquinas
                System.out.println("   -->" + nombre + ": ENTRA en la sala de MAQUINAS");
                // Simular tiempo de uso de las máquinas
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Manejar excepción de interrupción
                System.err.println("Error mientras " + nombre + " usaba las máquinas: " + e.getMessage());
                throw e;
            } finally {
                // Imprimir mensaje de salida de la sala de máquinas
                System.out.println("   -->" + nombre + ": sale de la sala de MAQUINAS");
                // Liberar semáforo de máquinas
                semaforoMaquinas.release();
            }
        }
    }

    // Clase DeportistaElite que extiende Persona
    static class DeportistaElite extends Persona {
        public DeportistaElite(String nombre) {
            super(nombre);
        }

        @Override
        protected void usarMaquinas() throws InterruptedException {
            try {
                // Adquirir semáforo de máquinas para uso exclusivo
                semaforoMaquinas.acquire(3);
                // Imprimir mensaje de entrada a la sala de máquinas
                System.out.println("   " + nombre + ": ENTRA en la sala de MAQUINAS ");
                // Simular tiempo de uso de las máquinas
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Manejar excepción de interrupción
                System.err.println("Error mientras " + nombre + " usaba las máquinas en exclusiva: " + e.getMessage());
                throw e;
            } finally {
                // Imprimir mensaje de salida de la sala de máquinas
                System.out.println("   -->" + nombre + ": sale de la sala de MAQUINAS");
                // Liberar semáforo de máquinas
                semaforoMaquinas.release(3);
            }
        }
    }
}