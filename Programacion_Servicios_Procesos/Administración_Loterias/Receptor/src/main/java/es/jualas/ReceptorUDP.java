package es.jualas;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ReceptorUDP {

    private DatagramSocket socket;
    private static final int PUERTO = 9876; // Puerto donde el servidor escucha
    private Random random = new Random();

    public ReceptorUDP() {
        try {
            socket = new DatagramSocket(PUERTO);
            System.out.println("El servidor UDP queda a la espera de peticiones");
            escuchar();
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private void escuchar() {
        byte[] buffer = new byte[1024];

        while (true) {
            try {
                // Recibir petición
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion);

                String mensaje = new String(peticion.getData(), 0, peticion.getLength()).trim();
                System.out.println("Petición de sorteo recibida: " + mensaje);

                // Generar respuesta
                String respuesta = generarRespuesta(mensaje);

                // Enviar respuesta
                byte[] respuestaBytes = respuesta.getBytes();
                InetAddress direccionCliente = peticion.getAddress();
                int puertoCliente = peticion.getPort();

                DatagramPacket respuestaPacket = new DatagramPacket(respuestaBytes, respuestaBytes.length, direccionCliente, puertoCliente);
                socket.send(respuestaPacket);
                System.out.println("Sorteo generado:\n" + respuesta);

            } catch (Exception e) {
                System.err.println("Error al procesar la petición: " + e.getMessage());
            }
        }
    }

    private String generarRespuesta(String solicitud) {
        switch (solicitud.toUpperCase()) {
            case "QUINIELA":
                return generarQuiniela();
            case "LOTERIA":
                return generarLoteria();
            default:
                return "Sorteo no disponible";
        }
    }

    private String generarQuiniela() {
        StringBuilder quiniela = new StringBuilder();
        quiniela.append("Mi apuesta de QUINIELA:\n");
        for (int i = 1; i <= 15; i++) {
            int pronostico = random.nextInt(3); // 0 para "1", 1 para "X", 2 para "2"
            switch (pronostico) {
                case 0 -> quiniela.append("Partido ").append(i).append(": 1\n");
                case 1 -> quiniela.append("Partido ").append(i).append(": X\n");
                case 2 -> quiniela.append("Partido ").append(i).append(": 2\n");
            }
        }
        return quiniela.toString();
    }

    private String generarLoteria() {
        int numero = random.nextInt(100000); // Genera un número entre 0 y 99999
        return String.format("Mi apuesta de LOTERIA: %05d", numero);
    }

    public void cerrar() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("Servidor cerrado.");
        }
    }

    public static void main(String[] args) {
        ReceptorUDP emisor = new ReceptorUDP();
        Runtime.getRuntime().addShutdownHook(new Thread(emisor::cerrar));
    }
}