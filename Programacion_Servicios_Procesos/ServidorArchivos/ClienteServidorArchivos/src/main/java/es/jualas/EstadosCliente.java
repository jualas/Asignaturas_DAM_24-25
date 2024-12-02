package es.jualas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EstadosCliente {

    public static void main(String[] args) {
        try {
            // Establece la conexión con el servidor en el puerto 2020
            Socket skCliente = new Socket("localhost", 2020);
            DataInputStream in = new DataInputStream(skCliente.getInputStream());
            DataOutputStream out = new DataOutputStream(skCliente.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String mensaje;
                try {
                    // Lee el mensaje del servidor
                    mensaje = in.readUTF();
                } catch (EOFException e) {
                    // Muestra un mensaje si el servidor se desconecta
                    System.out.println("Servidor desconectado.");
                    break;
                } catch (IOException e) {
                    // Muestra un mensaje si hay un error en la comunicación
                    System.out.println("Error en la comunicación con el servidor: " + e.getMessage());
                    break;
                }

                // Imprime el mensaje recibido del servidor
                System.out.println(mensaje);

                if (mensaje.contains("Opciones")) {
                    // Lee la opción del usuario y la envía al servidor
                    String opcion = scanner.nextLine();
                    out.writeUTF(opcion);
                    if (opcion.equals("3")) {
                        // Si la opción es "3", sale del bucle y cierra la conexión
                        break;
                    }
                } else if (mensaje.contains("Introduce la clave de acceso:") || mensaje.contains("Clave incorrecta")) {
                    // Lee la clave de acceso del usuario y la envía al servidor
                    String clave = scanner.nextLine();
                    out.writeUTF(clave);
                } else if (mensaje.contains("Introduce el nombre del fichero a visualizar:") || mensaje.contains("Opciones: 1. Visualizar sólo ficheros, 2. Visualizar directorios y ficheros")) {
                    // Lee la respuesta del usuario y la envía al servidor
                    String respuesta = scanner.nextLine();
                    out.writeUTF(respuesta);
                }
            }
            // Cierra la conexión con el servidor
            skCliente.close();
        } catch (UnknownHostException e) {
            // Muestra un mensaje si no se puede determinar la dirección IP del host
            System.err.println("Host desconocido: " + e.getMessage());
        } catch (ConnectException e) {
            // Muestra un mensaje si no se puede establecer una conexión con el servidor
            System.err.println("No se puede conectar al servidor: " + e.getMessage());
        } catch (SocketException e) {
            // Muestra un mensaje si hay un error en la creación o acceso a un socket
            System.err.println("Error de socket: " + e.getMessage());
        } catch (SecurityException e) {
            // Muestra un mensaje si hay una violación de seguridad
            System.err.println("Violación de seguridad: " + e.getMessage());
        } catch (IOException e) {
            // Muestra un mensaje si hay un error en la entrada/salida
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}