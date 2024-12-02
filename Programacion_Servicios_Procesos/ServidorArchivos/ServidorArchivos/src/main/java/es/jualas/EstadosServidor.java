package es.jualas;

import java.io.*;
import java.net.Socket;

public class EstadosServidor extends Thread {
    private final Socket skCliente; // Socket del cliente conectado
    private final int clienteId; // ID único del cliente

    public EstadosServidor(Socket sCliente, int clienteId) {
        this.skCliente = sCliente; // Inicializa el socket del cliente
        this.clienteId = clienteId; // Inicializa el ID del cliente
    }

    public void run() {
        try {
            System.out.println("Cliente: " + clienteId + " conectado"); // Mensaje de cliente conectado
            DataInputStream in = new DataInputStream(skCliente.getInputStream()); // Flujo de entrada de datos
            DataOutputStream out = new DataOutputStream(skCliente.getOutputStream()); // Flujo de salida de datos

            MaquinaEstados maquinaEstados = new MaquinaEstados(in, out); // Crea una instancia de MaquinaEstados
            maquinaEstados.procesar(); // Procesa las solicitudes del cliente

            skCliente.close(); // Cierra el socket del cliente
            System.out.println("Cliente: " + clienteId + " desconectado"); // Mensaje de cliente desconectado
        } catch (IOException e) {
            System.out.println("Error de conexión con el cliente ID " + clienteId + ": " + e.getMessage());
        }
    }
}