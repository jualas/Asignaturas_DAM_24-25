package es.jualas;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorTCP {
    private static final int PORT = 2000; // Puerto del servidor
    private static final int FILAS = 3; // Número de filas del tablero
    private static final int COLUMNAS = 4; // Número de columnas del tablero
    private char[][] tablero; // Tablero del juego

    // Constructor de la clase
    public ServidorTCP() {
        tablero = new char[FILAS][COLUMNAS]; // Inicializa el tablero
        inicializarTablero(); // Llama al metodo para inicializar el tablero
    }

    // Metodo para enviar tablero al cliente
    private void inicializarTablero() {
        // Llena el tablero con caracteres '-'
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = '-';
            }
        }
    }

    // Metodo para enviar tablero al servidor
   public void iniciarServidor() {
    // Inicia el servidor y espera conexiones de clientes
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        // Mensaje indicando que el servidor está esperando conexiones
        System.out.println("Soy el servidor TCP esperando a que alguien se conecte en el puerto 2000");

        while (true) {
            // Espera a que un cliente se conecte
            try (Socket cliente = serverSocket.accept()) {
                // Mensaje indicando que un nuevo jugador se ha conectado
                System.out.println("¡Se ha conectado un nuevo jugador!");
                // Maneja la conexión del cliente
                manejarCliente(cliente);
            } catch (IOException e) {
                // Maneja errores al aceptar la conexión del cliente
                System.err.println("Error al manejar el cliente: " + e.getMessage());
            }
        }
    } catch (IOException e) {
        // Maneja errores al iniciar el servidor
        System.err.println("Error al iniciar el servidor: " + e.getMessage());
    }
}

    // Metodo para manejar cliente
    private void manejarCliente(Socket cliente) throws IOException {
    // Crea los flujos de entrada y salida para la comunicación con el cliente
    BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
    PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

    // Envía un mensaje de bienvenida al cliente
    salida.println("Servidor del juego: ¡¡¡Bienvenido al juego!!! Envía una columna");
    boolean juegoEnCurso = true;

    try {
        while (juegoEnCurso) {
            // Verifica si el tablero está lleno
            if (tableroLleno()) {
                salida.println("Servidor de juegos: El tablero está lleno. La partida termina en empate!");
                System.out.println("La partida ha terminado en empate.");
                juegoEnCurso = false; // Termina el juego
                break; // Sale del bucle
            }

            // Envía el tablero al cliente
            enviarTablero(salida);
            salida.println("Introduce columna (0-3):");
            String columnaStr = entrada.readLine();
            int columna;

            try {
                // Intenta convertir la entrada del cliente a un número entero
                columna = Integer.parseInt(columnaStr);
                // Verifica si la columna es válida
                if (columna < 0 || columna >= COLUMNAS) {
                    salida.println("Servidor de juegos: Has introducido un valor incorrecto. Aprende a jugar");
                    System.out.println("El cliente ha introducido una entrada no válida. Terminará el juego.");
                    juegoEnCurso = false;
                    break;
                }
                // Verifica si la columna está llena
                if (tablero[0][columna] != '-') {
                    salida.println("Servidor de juegos: Has introducido una columna no válida. Aprende a jugar");
                    System.out.println("El cliente ha introducido una columna no válida. Terminará el juego.");
                    juegoEnCurso = false;
                    break;
                }
            } catch (NumberFormatException e) {
                // Maneja el caso en que la entrada no sea un número válido
                salida.println("Servidor de juegos: Has introducido un valor incorrecto. Aprende a jugar");
                System.out.println("El cliente ha introducido una entrada no válida. Terminará el juego.");
                juegoEnCurso = false;
                break;
            }

            // Realiza la jugada del cliente
            System.out.println("Columna recibida " + columna);
            realizarJugadaCliente(columna, 'X');
            System.out.println("Después de la jugada del cliente");
            enviarTableroServidor();
            // Verifica si el cliente ha ganado
            if (esGanadora('X')) {
                enviarTablero(salida);
                salida.println("Servidor de juegos: ¡Has ganado!");
                System.out.println("El cliente ha ganado. FIN de la partida.");
                juegoEnCurso = false;
                break;
            }

            // Realiza una jugada aleatoria para el servidor
            int columnaServidor = realizarJugadaAleatoria();
            realizarJugadaCliente(columnaServidor, 'O');
            System.out.println("Después de la jugada del servidor :" + columnaServidor);
            enviarTableroServidor();
            // Verifica si el servidor ha ganado
            if (esGanadora('O')) {
                enviarTablero(salida);
                salida.println("Servidor de juegos: El servidor ha ganado la partida.");
                System.out.println("He ganado. FIN de la partida.");
                juegoEnCurso = false;
                break;
            }

            // Informa al cliente que el juego continúa
            salida.println("Servidor de juegos: Seguimos jugando");
        }
    } catch (SocketException e) {
        // Maneja la desconexión abrupta del cliente
        System.err.println("Error al manejar el cliente: Connection reset. El cliente se ha desconectado a lo loco.");
    } finally {
        // Cierra los flujos y el socket, y reinicia el tablero
        salida.println("Servidor de juegos: La partida ha terminado. Gracias por jugar. Cerrando conexión...");
        entrada.close();
        salida.close();
        cliente.close();
        inicializarTablero();
        System.out.println("Soy el servidor TCP esperando a que alguien se conecte");
    }
}

    // Metodo para realizar jugada del cliente
    private void realizarJugadaCliente(int columna, char ficha) {
        // Coloca la ficha en la columna especificada
        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == '-') {
                tablero[i][columna] = ficha;
                break;
            }
        }
    }

    // Metodo para realizar jugada aleatoria
    private int realizarJugadaAleatoria() {
        // Realiza una jugada aleatoria en una columna válida
        Random random = new Random();
        int columna;
        do {
            columna = random.nextInt(COLUMNAS);
        } while (tablero[0][columna] != '-');
        return columna;
    }

    // Metodo para verificar victoria horizontal
    private boolean checkHorizontal(char ficha) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i][j + 1] == ficha && tablero[i][j + 2] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    // Metodo para verificar victoria vertical
    private boolean checkVertical(char ficha) {
        for (int j = 0; j < COLUMNAS; j++) {
            for (int i = 0; i <= FILAS - 3; i++) {
                if (tablero[i][j] == ficha && tablero[i + 1][j] == ficha && tablero[i + 2][j] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    // Metodo para verificar victoria diagonal
    private boolean checkDiagonal(char ficha) {
        // Diagonal ↘
        for (int i = 0; i <= FILAS - 3; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i + 1][j + 1] == ficha && tablero[i + 2][j + 2] == ficha) {
                    return true;
                }
            }
        }
        // Diagonal ↙
        for (int i = 2; i < FILAS; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i - 1][j + 1] == ficha && tablero[i - 2][j + 2] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    // Metodo para verificar victoria llamando a los metodos anteriores
    private boolean esGanadora(char ficha) {
        // Verifica si hay una victoria horizontal, vertical o diagonal
        return checkHorizontal(ficha) || checkVertical(ficha) || checkDiagonal(ficha);
    }

    private void enviarTablero(PrintWriter salida) {
        // Envía el tablero al cliente
        for (char[] fila : tablero) {
            salida.println(new String(fila));
        }
    }

    // Metodo para verificar tablero lleno
    private boolean tableroLleno() {
        // Verifica si el tablero está lleno
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Metodo para enviar tablero al servidor
    private void enviarTableroServidor() {
        // Muestra el tablero en el servidor
        for (char[] fila : tablero) {
            System.out.println(new String(fila));
        }
    }

    // Metodo main
    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP();
        servidor.iniciarServidor();
    }
}