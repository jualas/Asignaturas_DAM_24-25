package es.jualas;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorTCP {
    private static final int PORT = 2000; // Puerto del servidor
    private static AtomicInteger clienteIdCounter = new AtomicInteger(1); // Contador de clientes

    public void iniciarServidor() {
        // Inicia el servidor
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Soy el servidor TCP esperando a que alguien se conecte en el puerto 2000");

            while (true) {
                Socket cliente = serverSocket.accept();
                int clienteId = clienteIdCounter.getAndIncrement();
                System.out.println("¡Se ha conectado un nuevo jugador: Cliente" + clienteId + "!");
                new Thread(new ManejadorCliente(cliente, clienteId)).start(); // Crea y empieza un nuevo hilo para manejar al cliente
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP(); // Crea una instancia del servidor
        servidor.iniciarServidor(); // Inicia el servidor
    }
}