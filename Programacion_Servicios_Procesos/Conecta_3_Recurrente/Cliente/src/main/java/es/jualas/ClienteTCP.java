package es.jualas;

import java.io.*;
import java.net.*;

public class ClienteTCP {
    private static final String SERVER_ADDRESS = "192.168.1.109"; // Dirección del servidor
    private static final int SERVER_PORT = 2000; // Puerto del servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // Envía mensajes al servidor para solicitar la columna
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             // Lee la entrada del teclado
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            String mensajeServidor;
            // Lee los mensajes del servidor
            while ((mensajeServidor = entrada.readLine()) != null) {
                System.out.println(mensajeServidor);
                // El servidor solicita la columna al cliente
                if (mensajeServidor.equals("Introduce columna (0-3):")) {
                    // Lee la columna introducida por el usuario
                    String columna = teclado.readLine();
                    // Envía la columna al servidor
                    salida.println(columna);
                }
            }
        } catch (IOException e) {
            // Maneja las excepciones de entrada/salida
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
        }
    }
}