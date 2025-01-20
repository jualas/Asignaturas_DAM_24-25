package jualas.es;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class EmailSender {
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class.getName());
    private final String email;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;

    public EmailSender(String email, String password, String smtpHost, String smtpPort) {
        this.email = email;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar el correo electrónico del destinatario
        System.out.print("Introduce el correo electrónico del destinatario: ");
        String to = scanner.nextLine();
        if (to.isEmpty()) {
            System.out.println("El correo electrónico del destinatario no puede estar vacío.");
            return;
        }
        if (!isValidEmail(to)) {
            System.out.println("El correo electrónico del destinatario no es válido.");
            return;
        }

        // Solicitar el asunto del correo
        System.out.print("Introduce el asunto: ");
        String subject = scanner.nextLine();
        if (subject.isEmpty()) {
            System.out.println("El asunto no puede estar vacío.");
            return;
        }

        // Solicitar el cuerpo del mensaje
        System.out.print("Introduce el cuerpo del mensaje: ");
        String body = scanner.nextLine();
        if (body.isEmpty()) {
            System.out.println("El cuerpo del mensaje no puede estar vacío.");
            return;
        }

        // Solicitar el nombre del archivo adjunto
        System.out.print("Introduce el nombre del archivo adjunto: ");
        String fileName = scanner.nextLine();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Crear una sesión de correo con autenticación
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            // Crear un mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(email));
            message.setSubject(subject);

            // Crear la parte del cuerpo del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Crear un multipart para el mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Adjuntar archivo si se especifica
            if (!fileName.isEmpty()) {
                String workingDir = System.getProperty("user.dir");
                File file = new File(workingDir, fileName);
                if (file.exists()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                } else {
                    System.out.println("El archivo no existe en el directorio actual.");
                }
            }

            // Establecer el contenido del mensaje
            message.setContent(multipart);

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Correo enviado exitosamente.");
        } catch (SendFailedException e) {
            // Capturar y manejar la excepción de dirección no válida
            LOGGER.log(Level.SEVERE, "Error al enviar el correo: Dirección no válida", e);
            System.out.println("Error al enviar el correo: Dirección no válida.");
        } catch (Exception e) {
            // Registrar el error si el envío del correo falla
            LOGGER.log(Level.SEVERE, "Error al enviar el correo", e);
        }
    }

    // Método para validar la dirección de correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}