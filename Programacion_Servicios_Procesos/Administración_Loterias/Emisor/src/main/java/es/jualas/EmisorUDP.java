package es.jualas;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class EmisorUDP {

    private DatagramSocket socket;
    private static final int PUERTO = 9876; // Puerto donde el receptor escucha

    public EmisorUDP() {
        try {
            socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿A qué quieres jugar? QUINIELA o LOTERIA");
            String opcion = scanner.nextLine().toUpperCase();
            enviarSolicitud(opcion);
        } catch (Exception e) {
            System.err.println("Error al iniciar el receptor: " + e.getMessage());
        }
    }

    private void enviarSolicitud(String solicitud) {
        try {
            byte[] buffer = solicitud.getBytes();
            InetAddress direccionServidor = InetAddress.getByName("localhost");

            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO);
            socket.send(peticion);

            // Recibir respuesta
            buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);

            String mensaje = new String(respuesta.getData(), 0, respuesta.getLength()).trim();
            System.out.println("Vamos a solicitar nuestro sorteo " + solicitud + "\n" + mensaje);

        } catch (Exception e) {
            System.err.println("Error al enviar la solicitud: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmisorUDP();
    }
}