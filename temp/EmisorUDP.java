package es.jualas;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EmisorUDP {

    private DatagramSocket socket; // Socket para enviar y recibir paquetes UDP
    private static final int PUERTO = 9876; // Puerto donde el receptor escucha

    public EmisorUDP() {
        try {
            socket = new DatagramSocket(); // Inicializa el socket UDP
            Scanner scanner = new Scanner(System.in); // Escáner para leer la entrada del usuario
            System.out.println("¿A qué quieres jugar? QUINIELA o LOTERIA"); // Pregunta al usuario qué juego quiere jugar
            String opcion = scanner.nextLine().toUpperCase(); // Lee la opción del usuario y la convierte a mayúsculas
            enviarSolicitud(opcion); // Envía la solicitud con la opción del usuario
        } catch (SocketException e) {
            System.err.println("Error al crear el socket: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.err.println("Error al leer la entrada del usuario: " + e.getMessage());
        }
    }

    private void enviarSolicitud(String solicitud) {
        try {
            byte[] buffer = solicitud.getBytes(); // Convierte la solicitud a bytes
            InetAddress direccionServidor = InetAddress.getByName("localhost"); // Dirección del servidor

            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO); // Crea el paquete de solicitud
            socket.send(peticion); // Envía el paquete de solicitud

            // Recibir respuesta
            buffer = new byte[1024]; // Buffer para recibir la respuesta
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length); // Crea el paquete de respuesta
            socket.receive(respuesta); // Recibe el paquete de respuesta

            String mensaje = new String(respuesta.getData(), 0, respuesta.getLength()).trim(); // Convierte la respuesta a String
            System.out.println("Vamos a solicitar nuestro sorteo " + solicitud + "\n" + mensaje); // Imprime la respuesta del servidor

        } catch (UnknownHostException e) {
            System.err.println("Error al resolver la dirección del servidor: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al enviar o recibir el paquete: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error en los argumentos del paquete: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmisorUDP(); // Crea una instancia de EmisorUDP
    }
}