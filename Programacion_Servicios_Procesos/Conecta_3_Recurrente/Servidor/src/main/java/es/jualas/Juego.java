package es.jualas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.io.PrintWriter;

public class Juego {
    public static final int FILAS = 3; // Número de filas del tablero
    public static final int COLUMNAS = 4; // Número de columnas del tablero
    private char[][] tablero; // Matriz que representa el tablero del juego

    public Juego() {
        tablero = new char[FILAS][COLUMNAS]; // Inicializa el tablero
        inicializarTablero(); // Llama al método para inicializar el tablero
    }

    public void inicializarTablero() {
        // Llena el tablero con el carácter '-'
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = '-';
            }
        }
    }

    public boolean tableroLleno() {
        // Verifica si el tablero está lleno
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == '-') {
                    return false; // Si hay al menos un espacio vacío, retorna false
                }
            }
        }
        return true; // Si no hay espacios vacíos, retorna true
    }

    public void realizarJugada(int columna, char ficha) {
        // Realiza una jugada en la columna especificada con la ficha dada
        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == '-') {
                tablero[i][columna] = ficha; // Coloca la ficha en la primera posición vacía de la columna
                break;
            }
        }
    }

    public int realizarJugadaAleatoria() {
        // Realiza una jugada aleatoria en una columna válida
        Random random = new Random();
        int columna;
        do {
            columna = random.nextInt(COLUMNAS); // Genera un número aleatorio entre 0 y COLUMNAS-1
        } while (tablero[0][columna] != '-'); // Repite hasta encontrar una columna no llena
        return columna; // Retorna la columna seleccionada
    }

    public boolean esGanadora(char ficha) {
        // Verifica si la ficha dada ha ganado
        return checkHorizontal(ficha) || checkVertical(ficha) || checkDiagonal(ficha);
    }

    private boolean checkHorizontal(char ficha) {
        // Verifica si hay una línea horizontal ganadora
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i][j + 1] == ficha && tablero[i][j + 2] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(char ficha) {
        // Verifica si hay una línea vertical ganadora
        for (int j = 0; j < COLUMNAS; j++) {
            for (int i = 0; i <= FILAS - 3; i++) {
                if (tablero[i][j] == ficha && tablero[i + 1][j] == ficha && tablero[i + 2][j] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(char ficha) {
        // Verifica si hay una línea diagonal ganadora
        for (int i = 0; i <= FILAS - 3; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i + 1][j + 1] == ficha && tablero[i + 2][j + 2] == ficha) {
                    return true;
                }
            }
        }
        for (int i = 2; i < FILAS; i++) {
            for (int j = 0; j <= COLUMNAS - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i - 1][j + 1] == ficha && tablero[i - 2][j + 2] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    public void enviarTablero(PrintWriter salida) {
        // Envía el tablero al cliente
        for (char[] fila : tablero) {
            salida.println(new String(fila));
        }
    }

    public void enviarTableroServidor() {
        // Imprime el tablero en el servidor
        for (char[] fila : tablero) {
            System.out.println(new String(fila));
        }
    }

    public char[][] getTablero() {
        // Retorna el tablero
        return tablero;
    }
}