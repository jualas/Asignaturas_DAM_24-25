package es.jualas;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MaquinaEstados {
    static final String CLAVE_ACCESO = "1234"; // Clave de acceso predefinida

    // Constantes para los estados del servidor
    public static final int ESPERANDO_CLAVE = 0; // Estado esperando clave de acceso
    public static final int MENU = 1; // Estado del menú principal
    public static final int MENU_LISTAR = 4; // Estado del menú de listado
    public static final int VER = 2; // Estado para visualizar un fichero
    public static final int LISTAR = 3; // Estado para listar archivos
    public static final int SALIR = -1; // Estado para salir
    private static final Set<String> EXTENSIONES_PERMITIDAS = new HashSet<>(Arrays.asList(
    "txt", "log", "xml", "json", "csv", "md", "java", "py", "js", "html", "css", "properties"
));

    private int estado; // Variable para almacenar el estado actual
    private final DataInputStream in; // Flujo de entrada de datos
    private final DataOutputStream out; // Flujo de salida de datos

    public MaquinaEstados(DataInputStream in, DataOutputStream out) {
        this.estado = ESPERANDO_CLAVE; // Inicializa el estado a ESPERANDO_CLAVE
        this.in = in; // Inicializa el flujo de entrada
        this.out = out; // Inicializa el flujo de salida
    }

public void procesar() {
    try {
        while (estado != SALIR) { // Bucle principal que continúa hasta que el estado sea SALIR
            switch (estado) { // Selecciona la acción a realizar según el estado actual
                case ESPERANDO_CLAVE:
                    manejarClave(); // Maneja el estado de espera de clave
                    break;
                case MENU:
                    manejarMenu(); // Maneja el estado del menú principal
                    break;
                case VER:
                    manejarVerFichero(); // Maneja el estado de visualización de fichero
                    break;
                case MENU_LISTAR:
                    manejarMenuListar(); // Maneja el estado del menú de listado
                    break;
            }
        }
    } catch (IOException e) {
        System.out.println("Error de conexión con el cliente: " + e.getMessage()); // Maneja errores de conexión
    }
}

    private void manejarClave() throws IOException {
        out.writeUTF("Introduce la clave de acceso:"); // Solicita la clave de acceso
        System.out.println("Esperando clave de acceso...");
        while (true) {
            String clave = in.readUTF(); // Lee la clave de acceso del cliente
            if (CLAVE_ACCESO.equals(clave)) {
                estado = MENU; // Cambia al estado MENU si la clave es correcta
                System.out.println("Clave correcta. Accediendo al menú.");
                break;
            } else {
                out.writeUTF("Introduce la clave de acceso:"); // Solicita nuevamente la clave si es incorrecta
                System.out.println("Clave incorrecta.");
            }
        }
    }

private void manejarMenu() throws IOException {
    // Lee la opción del usuario utilizando el método de validación
    int opcion = leerOpcionNumerica("Opciones: 1. VER, 2. LISTAR, 3. EXIT", 1, 3);
    System.out.println("Mostrando menú de opciones.");
    switch (opcion) {
        case 1:
            estado = VER; // Cambia al estado VER
            System.out.println("Opción seleccionada: VER");
            break;
        case 2:
            estado = MENU_LISTAR; // Cambia al nuevo estado MENU_LISTAR
            System.out.println("Opción seleccionada: LISTAR");
            break;
        case 3:
            estado = SALIR; // Cambia al estado SALIR
            System.out.println("Opción seleccionada: EXIT");
            break;
    }
}

private void manejarMenuListar() throws IOException {
    // Lee la opción de listado del usuario
    int opcionListar = leerOpcionNumerica("Opciones: 1. Visualizar sólo ficheros, 2. Visualizar directorios y ficheros", 1, 2);
    System.out.println("Listando archivos. Opción: " + opcionListar);
    
    // Procesa la opción de listado
    manejarEnviarDirectorio(opcionListar);
    
    // Vuelve al menú principal
    estado = MENU;
}

private int leerOpcionNumerica(String mensaje, int minimo, int maximo) throws IOException {
    while (true) {
        out.writeUTF(mensaje); // Envía el mensaje de solicitud al cliente
        try {
            int opcion = Integer.parseInt(in.readUTF()); // Lee y convierte la entrada del cliente
            if (opcion >= minimo && opcion <= maximo) {
                return opcion; // Retorna la opción si está dentro del rango válido
            } else {
                // Informa al cliente si la opción está fuera de rango
                out.writeUTF("Opción fuera de rango. Por favor, introduce un número entre " + minimo + " y " + maximo + ".");
                System.out.println("Opción fuera de rango.");
            }
        } catch (NumberFormatException e) {
            // Maneja el caso de entrada no numérica
            out.writeUTF("Entrada no válida. Por favor, introduce un número.");
            System.out.println("Entrada no válida.");
        }
    }
}

private boolean tieneExtensionPermitida(String nombreArchivo) {
    int lastIndexOf = nombreArchivo.lastIndexOf(".");
    if (lastIndexOf == -1) {
        return false; // No tiene extensión
    }
    String extension = nombreArchivo.substring(lastIndexOf + 1).toLowerCase();
    return EXTENSIONES_PERMITIDAS.contains(extension);
}

private void manejarVerFichero() throws IOException {
    out.writeUTF("Introduce el nombre del fichero a visualizar:");
    String nombreFichero = in.readUTF();
    System.out.println("Visualizando fichero: " + nombreFichero);
    File fichero = new File(nombreFichero);
    if (fichero.exists() && !fichero.isDirectory() && fichero.canRead()) {
        if (tieneExtensionPermitida(nombreFichero)) {
            out.writeUTF("INICIO_CONTENIDO");
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    out.writeUTF(linea);
                }
            }
            out.writeUTF("FIN_CONTENIDO");
            System.out.println("Fichero leído y enviado correctamente.");
        } else {
            out.writeUTF("El tipo de archivo no está permitido para visualización.");
            System.out.println("Intento de visualizar un archivo no permitido: " + nombreFichero);
        }
    } else {
        out.writeUTF("El fichero no existe o no es legible.");
        System.out.println("El fichero no existe o no es legible.");
    }
    estado = MENU;
}

    private void manejarListar() throws IOException {
        out.writeUTF("Opciones: 1. Visualizar sólo ficheros, 2. Visualizar directorios y ficheros"); // Solicita la opción de listado
        int opcionListar = Integer.parseInt(in.readUTF()); // Lee la opción de listado
        System.out.println("Listando archivos. Opción: " + opcionListar);
        manejarEnviarDirectorio(opcionListar);
        estado = MENU; // Vuelve al estado MENU
    }

private void manejarEnviarDirectorio(int opcionListar) throws IOException {
    File directorio = new File(".");
    File[] archivos = directorio.listFiles();
    
    out.writeUTF("INICIO_LISTADO"); // Señal de inicio de listado
    if (archivos != null) {
        for (File archivo : archivos) {
            if (opcionListar == 1 && archivo.isFile()) {
                out.writeUTF(archivo.getName());
            } else if (opcionListar == 2) {
                out.writeUTF(archivo.getName() + (archivo.isDirectory() ? " (directorio)" : " (archivo)"));
            }
        }
    }
    out.writeUTF("FIN_LISTADO"); // Señal de fin de listado
}
}