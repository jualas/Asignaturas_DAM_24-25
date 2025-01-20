package jualas.es;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static FTPClient clienteFTP;

    public static void main(String[] args) {
        Properties propiedades = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Cargar la configuración del servidor FTP desde el archivo de propiedades
            if (input == null) {
                System.out.println("Lo siento, no se pudo encontrar el archivo config.properties");
                return;
            }
            propiedades.load(input);
            // Obtener las propiedades del servidor FTP
            String servidorURL = propiedades.getProperty("ftp.host");
            int puerto = Integer.parseInt(propiedades.getProperty("ftp.puerto"));
            String usuario = propiedades.getProperty("ftp.usuario");
            String password = propiedades.getProperty("ftp.password");

            // Crear una instancia del cliente FTP
            clienteFTP = new FTPClient();
            System.out.println("Conectando al servidor FTP...");
            clienteFTP.connect(servidorURL, puerto);
            int reply = clienteFTP.getReplyCode();
            System.out.println(clienteFTP.getReplyString());
            // Verificar si la conexión fue exitosa
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conexión establecida. Iniciando sesión...");
                clienteFTP.login(usuario, password);
                System.out.println(clienteFTP.getReplyString());
                // Configurar el modo pasivo
                clienteFTP.enterLocalPassiveMode();
                System.out.println("Sesión iniciada. Modo pasivo activado.");
                Scanner scanner = new Scanner(System.in);
                String comando;
                // Bucle para mostrar el menú y ejecutar los comandos del usuario
                do {
                    mostrarMenu();
                    comando = scanner.nextLine();
                    System.out.println();
                    switch (comando) {
                        case "1":
                            // Mostrar el directorio actual
                            mostrarDirectorioActual();
                            break;
                        case "2":
                            // Cambiar al directorio especificado por el usuario
                            System.out.print("Introduce nombre del directorio: ");
                            String directorio = scanner.nextLine();
                            cambiarDirectorio(directorio);
                            break;
                        case "3":
                            // Subir al directorio padre
                            subirDirectorioPadre();
                            break;
                        case "4":
                            // Subir un fichero al servidor
                            System.out.print("Introduce nombre del fichero: ");
                            String ficheroSubir = scanner.nextLine();
                            subirFichero(ficheroSubir);
                            break;
                        case "5":
                            // Borrar un fichero del servidor
                            System.out.print("Introduce nombre del fichero a borrar: ");
                            String ficheroBorrar = scanner.nextLine();
                            borrarFichero(ficheroBorrar);
                            break;
                        case "6":
                            // Salir del programa
                            mostrarDespedida();
                            break;
                        default:
                            // Opción no válida
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                } while (!comando.equals("6"));
                // Cerrar sesión y desconectar del servidor FTP
                clienteFTP.logout();
                clienteFTP.disconnect();
            } else {
                // Desconectar si la conexión fue rechazada
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión establecida");
                System.exit(1);
            }
        } catch (NumberFormatException ex) {
            logger.log(Level.SEVERE, "Error de formato numérico", ex);
        } catch (NullPointerException ex) {
            logger.log(Level.SEVERE, "Error de puntero nulo", ex);
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "Error de seguridad", ex);
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, "Argumento ilegal", ex);
        } catch (FTPConnectionClosedException ex) {
            logger.log(Level.SEVERE, "Conexión FTP cerrada", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de E/S", ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, "Error de interrupción", ex);
        }
    }

    // Mostrar las opciones del menú al usuario
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("1. Mostrar el directorio actual");
        System.out.println("2. Entrar en un directorio");
        System.out.println("3. Subir al directorio padre");
        System.out.println("4. Subir fichero");
        System.out.println("5. Borrar un fichero");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Método para mostrar el directorio actual y listar su contenido
    private static void mostrarDirectorioActual() throws IOException {
        // Obtener el directorio de trabajo actual del servidor FTP
        String directorioActual = clienteFTP.printWorkingDirectory();
        System.out.println();
        // Imprimir el directorio actual
        System.out.println("257 \"" + directorioActual + "\" is current directory.");

        // Obtener la lista de archivos y directorios en el directorio actual
        FTPFile[] archivos = clienteFTP.listFiles();
        for (FTPFile archivo : archivos) {
            // Construir la cadena de permisos del archivo o directorio
            String permisos = (archivo.isDirectory() ? "d" : "-") +
                    (archivo.hasPermission(FTPFile.USER_ACCESS, FTPFile.READ_PERMISSION) ? "r" : "-") +
                    (archivo.hasPermission(FTPFile.USER_ACCESS, FTPFile.WRITE_PERMISSION) ? "w" : "-") +
                    (archivo.hasPermission(FTPFile.USER_ACCESS, FTPFile.EXECUTE_PERMISSION) ? "x" : "-") +
                    (archivo.hasPermission(FTPFile.GROUP_ACCESS, FTPFile.READ_PERMISSION) ? "r" : "-") +
                    (archivo.hasPermission(FTPFile.GROUP_ACCESS, FTPFile.WRITE_PERMISSION) ? "w" : "-") +
                    (archivo.hasPermission(FTPFile.GROUP_ACCESS, FTPFile.EXECUTE_PERMISSION) ? "x" : "-") +
                    (archivo.hasPermission(FTPFile.WORLD_ACCESS, FTPFile.READ_PERMISSION) ? "r" : "-") +
                    (archivo.hasPermission(FTPFile.WORLD_ACCESS, FTPFile.WRITE_PERMISSION) ? "w" : "-") +
                    (archivo.hasPermission(FTPFile.WORLD_ACCESS, FTPFile.EXECUTE_PERMISSION) ? "x" : "-");

            // Propietario y grupo del archivo o directorio (en este caso, se usa "ftp" como valor predeterminado)
            String owner = "ftp";
            String group = "ftp";

            // Tamaño del archivo en bytes, formateado para alineación
            String tamano = String.format("%10d", archivo.getSize());

            // Fecha de modificación del archivo, formateada como "MMM dd HH:mm"
            String fecha = new SimpleDateFormat("MMM dd HH:mm").format(archivo.getTimestamp().getTime());

            // Nombre del archivo o directorio
            String nombre = archivo.getName();

            // Imprimir la información del archivo o directorio en formato similar a "ls -l"
            System.out.printf("%s 1 %s %s %s %s %s\n", permisos, owner, group, tamano, fecha, nombre);
        }
        // Imprimir la respuesta del servidor FTP
        System.out.println(clienteFTP.getReplyString());
    }

    // Cambiar el directorio de trabajo actual
    private static void cambiarDirectorio(String directorio) {
        try {
            System.out.println();
            // Cambiar el directorio de trabajo en el servidor FTP
            clienteFTP.changeWorkingDirectory(directorio);
            // Imprimir la respuesta del servidor FTP
            System.out.println(clienteFTP.getReplyString());
        } catch (IOException ex) {
            // Manejar excepción si ocurre un error al cambiar de directorio
            logger.log(Level.SEVERE, "Error al cambiar de directorio", ex);
        }
    }

    // Subir al directorio padre
    private static void subirDirectorioPadre() {
        try {
            System.out.println();
            // Cambiar al directorio padre en el servidor FTP
            clienteFTP.changeToParentDirectory();
            // Imprimir la respuesta del servidor FTP
            System.out.println(clienteFTP.getReplyString());
        } catch (IOException ex) {
            // Manejar excepción si ocurre un error al subir al directorio padre
            logger.log(Level.SEVERE, "Error al subir al directorio padre", ex);
        }
    }

    // Borrar un fichero del servidor
    private static void borrarFichero(String nombreFichero) {
        try {
            System.out.println();
            // Borrar el fichero especificado del servidor FTP
            clienteFTP.deleteFile(nombreFichero);
            // Imprimir la respuesta del servidor FTP
            System.out.println(clienteFTP.getReplyString());
        } catch (IOException ex) {
            // Manejar excepción si ocurre un error al borrar el fichero
            logger.log(Level.SEVERE, "Error al borrar el fichero", ex);
        }
    }

    // Subir un fichero al servidor
    private static void subirFichero(String nombreFichero) {
    if (nombreFichero == null || nombreFichero.isEmpty()) {
        System.out.println("El nombre del fichero no puede estar vacío.");
        return;
    }

    // Asumir que el fichero está en el mismo directorio donde se ejecuta el programa
    File fichero = new File(nombreFichero);
    System.out.println();
    if (!fichero.exists()) {
        System.out.println("No existe el fichero");
        return;
    }
    if (!fichero.canRead()) {
        System.out.println("No se puede leer el fichero");
        return;
    }

    try (InputStream input = new FileInputStream(fichero)) {
        clienteFTP.storeFile(fichero.getName(), input);
        System.out.println(clienteFTP.getReplyString());
    } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error al subir el fichero", ex);
    }
}

    private static void mostrarDespedida() throws InterruptedException {
        // Texto que se mueve
        String texto = "Hay Dios mio cómo estoy de los dibujos en el terminal.";
        // Coche mirando a la derecha
        String coche = "🚗💨";

        // Longitud máxima del desplazamiento
        int desplazamientos = 50; // Número de pasos
        int tiempoFrame = 100; // Pausa entre frames en milisegundos

        for (int i = 0; i < desplazamientos; i++) {
            // Generar espacios para simular el desplazamiento
            String espacios = " ".repeat(i);

            // Sobreescribir la línea actual con el texto desplazado
            System.out.print("\r" + espacios + texto + " " + coche);

            // Pausa para simular movimiento
            Thread.sleep(tiempoFrame);
        }

        // Imprimir una nueva línea al final para evitar sobrescribir
        System.out.println("\n Aupa ahí, COPON !!!!!");
    }


}