package es.jualas;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.util.Random;

public class ReceptorUDP {

    private DatagramSocket socket; // Socket para la comunicación UDP
    private static final int PUERTO = 9876; // Puerto donde el servidor escucha
    private Random random = new Random(); // Generador de números aleatorios

    public ReceptorUDP() {
        try {
            socket = new DatagramSocket(PUERTO); // SocketException
            System.out.println("El servidor UDP queda a la espera de peticiones");
            escuchar(); // Llama al método para escuchar peticiones
        } catch (SocketException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("Permisos insuficientes para abrir el socket: " + e.getMessage());
        }
    }

    private void escuchar() {
        byte[] buffer = new byte[1024]; // Buffer para recibir datos

        while (true) {
            try {
                // Recibir petición
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion); // IOException

                String mensaje = new String(peticion.getData(), 0, peticion.getLength()).trim(); // Convierte los datos recibidos a una cadena
                System.out.println("Petición de sorteo recibida: " + mensaje);

                // Generar respuesta
                String respuesta = generarRespuesta(mensaje); // Genera la respuesta basada en la solicitud

                // Enviar respuesta
                byte[] respuestaBytes = respuesta.getBytes(); // Convierte la respuesta a bytes
                InetAddress direccionCliente = peticion.getAddress(); // Obtiene la dirección del cliente
                int puertoCliente = peticion.getPort(); // Obtiene el puerto del cliente

                DatagramPacket respuestaPacket = new DatagramPacket(respuestaBytes, respuestaBytes.length, direccionCliente, puertoCliente);
                socket.send(respuestaPacket); // IOException
                System.out.println("Sorteo generado:\n" + respuesta);

            } catch (IOException e) {
                System.err.println("Error al procesar la petición: " + e.getMessage());
            } catch (SecurityException e) {
                System.err.println("Permisos insuficientes para enviar/recibir datos: " + e.getMessage());
            }
        }
    }

    private String generarRespuesta(String solicitud) {
        switch (solicitud.toUpperCase()) {
            case "QUINIELA":
                return generarQuiniela(); // Genera una respuesta para QUINIELA
            case "LOTERIA":
                return generarLoteria(); // Genera una respuesta para LOTERIA
            default:
                return "Sorteo no disponible"; // Respuesta por defecto si la solicitud no es válida
        }
    }

    private String generarQuiniela() {
        StringBuilder quiniela = new StringBuilder();
        quiniela.append("Mi apuesta de QUINIELA:\n");
        for (int i = 1; i <= 15; i++) {
            int pronostico = random.nextInt(3); // Genera un pronóstico aleatorio (0, 1 o 2)
            switch (pronostico) {
                case 0 -> quiniela.append("Partido ").append(i).append(": 1\n"); // Pronóstico "1"
                case 1 -> quiniela.append("Partido ").append(i).append(": X\n"); // Pronóstico "X"
                case 2 -> quiniela.append("Partido ").append(i).append(": 2\n"); // Pronóstico "2"
            }
        }
        return quiniela.toString(); // Devuelve la cadena con la quiniela generada
    }

    private String generarLoteria() {
        int numero = random.nextInt(100000); // Genera un número entre 0 y 99999
        return String.format("Mi apuesta de LOTERIA: %05d", numero); // Devuelve la cadena con el número de lotería
    }

    public void cerrar() {
        if (socket != null && !socket.isClosed()) {
            socket.close(); // Cierra el socket si está abierto
            System.out.println("Servidor cerrado.");
        }
    }

    public static void main(String[] args) {
        ReceptorUDP emisor = new ReceptorUDP(); // Crea una instancia del servidor UDP
        Runtime.getRuntime().addShutdownHook(new Thread(emisor::cerrar)); // Añade un hook para cerrar el servidor al finalizar
    }
}