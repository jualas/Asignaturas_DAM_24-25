package es.jualas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static final int PUERTO = 2020; // Puerto en el que el servidor escuchará las conexiones
    private static int contadorClientes = 0; // Contador para generar IDs únicos

    public static void main(String[] args) {
        try {
            ServerSocket skServidor = new ServerSocket(PUERTO); // Crea un ServerSocket para escuchar en el puerto especificado
            System.out.println("Escuchando en el puerto " + PUERTO);

            while (true) {
                Socket sc = skServidor.accept(); // Acepta una conexión de un cliente
                int clienteId = ++contadorClientes; // Genera un ID único para el cliente
                System.out.println("Cliente: " + clienteId + " conectado");
                new EstadosServidor(sc, clienteId).start(); // Crea y lanza un nuevo hilo para manejar la conexión del cliente
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la traza de la excepción en caso de error
        }
    }
}