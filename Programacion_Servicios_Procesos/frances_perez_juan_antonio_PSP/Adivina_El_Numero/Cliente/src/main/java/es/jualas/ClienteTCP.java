package es.jualas;

import java.io.*;
import java.net.*;

public class ClienteTCP {
    private static final String SERVER_ADDRESS = "localhost"; // Dirección del servidor
    private static final int SERVER_PORT = 2000; // Puerto del servidor

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            String mensajeServidor;
            while ((mensajeServidor = entrada.readLine()) != null) {
                System.out.println(mensajeServidor);
                if (mensajeServidor.equals("Introduce un número:")) {
                    String numero = teclado.readLine();
                    salida.println(numero);
                }
            }
        } catch (IOException e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
        }
    }
}