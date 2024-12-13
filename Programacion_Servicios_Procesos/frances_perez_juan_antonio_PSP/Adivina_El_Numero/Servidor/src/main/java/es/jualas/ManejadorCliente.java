package es.jualas;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ManejadorCliente implements Runnable {
    private Socket cliente;
    private int clienteId;
    private BufferedReader entrada;
    private PrintWriter salida;
    private int numeroAdivinar;
    private int intentos;

    public ManejadorCliente(Socket cliente, int clienteId) {
        this.cliente = cliente;
        this.clienteId = clienteId;
        this.numeroAdivinar = new Random().nextInt(10) + 1; // Genera un número aleatorio entre 1 y 10
        this.intentos = 0;
    }

    @Override
    public void run() {
        try {
            inicializarComunicacion();
            manejarJuego();
        } catch (IOException e) {
            System.err.println("Error al manejar el cliente" + clienteId + ": " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    private void inicializarComunicacion() throws IOException {
        entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        salida = new PrintWriter(cliente.getOutputStream(), true);
        salida.println("Servidor TCP: ¡¡¡Bienvenido al juego!!! Intenta adivinar un número entre 1 y 10. Tienes 3 oportunidades.");
        System.out.println("Cliente" + clienteId + ": El número a acertar es " + numeroAdivinar);
    }

    private void manejarJuego() throws IOException {
        while (intentos < 3) {
            salida.println("Introduce un número:");
            String input = entrada.readLine();
            int numero;
            try {
                numero = Integer.parseInt(input);
                if (numero < 1 || numero > 10) {
                    salida.println("Servidor TCP: El número está fuera de rango (1-10). Termina el juego.");
                    System.out.println("Cliente" + clienteId + ": El número está fuera de rango. Termina el juego.");
                    return;
                }
            } catch (NumberFormatException e) {
                salida.println("Servidor TCP: Dato erróneo. Termina el juego.");
                System.out.println("Cliente" + clienteId + ": Dato erróneo. Termina el juego.");
                return;
            }

            intentos++;
            System.out.println("Cliente" + clienteId + ": Recibo " + numero);
            if (numero == numeroAdivinar) {
                salida.println("Servidor TCP: ¡Has acertado!");
                System.out.println("Cliente" + clienteId + ": ¡Ha acertado!");
                return;
            } else if (numero < numeroAdivinar) {
                salida.println("Servidor TCP: El número es mayor. Intentos restantes: " + (3 - intentos));
                System.out.println("Cliente" + clienteId + ": No ha acertado. El número es mayor.");
            } else {
                salida.println("Servidor TCP: El número es menor. Intentos restantes: " + (3 - intentos));
                System.out.println("Cliente" + clienteId + ": No ha acertado. El número es menor.");
            }
        }

        salida.println("Servidor TCP: Has agotado tus 3 intentos. El número era " + numeroAdivinar + ". Termina el juego.");
        System.out.println("Cliente" + clienteId + ": Ha agotado sus intentos. Termina el juego.");
    }

    private void cerrarConexion() {
        try {
            salida.println("Servidor TCP: La partida ha terminado. Gracias por jugar. Cerrando conexión...");
            entrada.close();
            salida.close();
            cliente.close();
            System.out.println("Cliente" + clienteId + ": Desconectado");
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión con el cliente" + clienteId + ": " + e.getMessage());
        }
    }
}