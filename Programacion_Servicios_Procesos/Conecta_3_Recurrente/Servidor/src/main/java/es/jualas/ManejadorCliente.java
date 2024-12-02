package es.jualas;

import java.io.*;
import java.net.*;

// Clase que maneja la lógica del juego para cada cliente conectado
public class ManejadorCliente implements Runnable {
    private Socket cliente;
    private Juego juego;
    private int clienteId;
    private BufferedReader entrada;
    private PrintWriter salida;

    // Constructor que inicializa el manejador con el socket del cliente y su ID
    public ManejadorCliente(Socket cliente, int clienteId) {
        this.cliente = cliente;
        this.juego = new Juego();
        this.clienteId = clienteId;
    }

    // Método principal que se ejecuta cuando se inicia el hilo
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

    // Configura los streams de entrada y salida para la comunicación con el cliente
    private void inicializarComunicacion() throws IOException {
        entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        salida = new PrintWriter(cliente.getOutputStream(), true);
        salida.println("Servidor del juego: ¡¡¡Bienvenido al juego, Cliente" + clienteId + "!!! Envía una columna");
    }

    // Maneja el flujo principal del juego
    private void manejarJuego() throws IOException {
        boolean juegoEnCurso = true;
        while (juegoEnCurso) {
            if (tableroLleno()) {
                finalizarJuegoEmpatado();
                break;
            }

            juego.enviarTablero(salida);
            int columna = obtenerJugadaCliente();
            if (columna == -1) {
                juegoEnCurso = false;
                break;
            }

            realizarJugadaCliente(columna);
            if (verificarVictoriaCliente()) {
                juegoEnCurso = false;
                break;
            }

            realizarJugadaServidor();
            if (verificarVictoriaServidor()) {
                juegoEnCurso = false;
                break;
            }

            salida.println("Servidor de juegos: Seguimos jugando");
        }
    }

    // Verifica si el tablero está lleno, lo que resultaría en un empate
    private boolean tableroLleno() {
        if (juego.tableroLleno()) {
            salida.println("Servidor de juegos: El tablero está lleno. La partida termina en empate!");
            System.out.println("La partida ha terminado en empate con el cliente" + clienteId);
            return true;
        }
        return false;
    }

    // Obtiene y valida la jugada del cliente
    private int obtenerJugadaCliente() throws IOException {
        salida.println("Introduce columna (0-3):");
        String columnaStr = entrada.readLine();
        try {
            int columna = Integer.parseInt(columnaStr);
            if (columna < 0 || columna >= Juego.COLUMNAS || juego.getTablero()[0][columna] != '-') {
                salida.println("Servidor de juegos: Has introducido un valor incorrecto. Aprende a jugar");
                System.out.println("El cliente" + clienteId + " ha introducido una entrada no válida. Terminará el juego.");
                return -1;
            }
            return columna;
        } catch (NumberFormatException e) {
            salida.println("Servidor de juegos: Has introducido un valor incorrecto. Aprende a jugar");
            System.out.println("El cliente" + clienteId + " ha introducido una entrada no válida. Terminará el juego.");
            return -1;
        }
    }

    // Realiza la jugada del cliente en el tablero
    private void realizarJugadaCliente(int columna) {
        System.out.println("Columna recibida del cliente" + clienteId + ": " + columna);
        juego.realizarJugada(columna, 'X');
        System.out.println("Después de la jugada del cliente" + clienteId);
        juego.enviarTableroServidor();
    }

    // Verifica si el cliente ha ganado la partida
    private boolean verificarVictoriaCliente() {
        if (juego.esGanadora('X')) {
            juego.enviarTablero(salida);
            salida.println("Servidor de juegos: ¡Has ganado!");
            System.out.println("El cliente" + clienteId + " ha ganado. FIN de la partida.");
            return true;
        }
        return false;
    }

    // Realiza una jugada aleatoria para el servidor
    private void realizarJugadaServidor() {
        int columnaServidor = juego.realizarJugadaAleatoria();
        juego.realizarJugada(columnaServidor, 'O');
        System.out.println("Después de la jugada del servidor con el cliente" + clienteId + ": " + columnaServidor);
        juego.enviarTableroServidor();
    }

    // Verifica si el servidor ha ganado la partida
    private boolean verificarVictoriaServidor() {
        if (juego.esGanadora('O')) {
            juego.enviarTablero(salida);
            salida.println("Servidor de juegos: El servidor ha ganado la partida.");
            System.out.println("He ganado contra el cliente" + clienteId + ". FIN de la partida.");
            return true;
        }
        return false;
    }

    // Notifica al cliente que el juego ha terminado en empate
    private void finalizarJuegoEmpatado() {
        salida.println("Servidor de juegos: El tablero está lleno. La partida termina en empate!");
        System.out.println("La partida ha terminado en empate con el cliente" + clienteId);
    }

    // Cierra la conexión con el cliente y reinicia el tablero
    private void cerrarConexion() {
        try {
            salida.println("Servidor de juegos: La partida ha terminado. Gracias por jugar. Cerrando conexión...");
            entrada.close();
            salida.close();
            cliente.close();
            juego.inicializarTablero();
            System.out.println("Soy el servidor TCP esperando a que alguien se conecte");
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión con el cliente" + clienteId + ": " + e.getMessage());
        }
    }
}