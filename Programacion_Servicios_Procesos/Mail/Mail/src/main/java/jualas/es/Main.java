package jualas.es;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cargar configuración desde el archivo config.properties
        Config config = new Config();
        String email = config.getEmail();
        String password = config.getPassword();
        String smtpHost = config.getSmtpHost();
        String smtpPort = config.getSmtpPort();
        String imapHost = config.getImapHost();
        String imapPort = config.getImapPort();

        // Crear instancias de EmailReader y EmailSender
        EmailReader emailReader = new EmailReader(email, password, imapHost, imapPort);
        EmailSender emailSender = new EmailSender(email, password, smtpHost, smtpPort);

        // Verificar la conexión al servidor de correo
        if (emailReader.verifyConnection()) {
            System.out.println("Conectado al servidor de correo exitosamente.");
        } else {
            System.out.println("Error al conectar al servidor de correo. Por favor verifica las credenciales y la conexión a internet.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Mostrar opciones al usuario
            System.out.println("1. Leer Emails");
            System.out.println("2. Enviar Email");
            System.out.println("3. Salir");
            System.out.print("Elegir una Opción: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consumir nueva línea

                switch (choice) {
                    case 1:
                        // Leer correos electrónicos
                        emailReader.readEmails();
                        break;
                    case 2:
                        // Enviar un correo electrónico
                        emailSender.sendEmail();
                        break;
                    case 3:
                        // Salir del programa
                        System.out.println("Salir...");
                        return;
                    default:
                        System.out.println("Opción no válida. Por favor inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor ingresa un número entre 1 y 3.");
                scanner.nextLine(); // Limpiar entrada no válida
            }
        }
    }
}