package jualas.es;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailReader {
    private static final Logger LOGGER = Logger.getLogger(EmailReader.class.getName());
    private final String email;
    private final String password;
    private final String imapHost;
    private final String imapPort;

    public EmailReader(String email, String password, String imapHost, String imapPort) {
        this.email = email;
        this.password = password;
        this.imapHost = imapHost;
        this.imapPort = imapPort;
    }

    public boolean verifyConnection() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", imapHost);
        properties.put("mail.imaps.port", imapPort);
        properties.put("mail.imaps.ssl.enable", "true");

        try {
            // Crear una sesión de correo y conectarse al almacén de correos
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(imapHost, email, password);
            store.close();
            return true;
        } catch (Exception e) {
            // Registrar el error si la conexión falla
            LOGGER.log(Level.SEVERE, "Error al verificar la conexión", e);
            return false;
        }
    }

    public void readEmails() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", imapHost);
        properties.put("mail.imaps.port", imapPort);
        properties.put("mail.imaps.ssl.enable", "true");

        try {
            // Crear una sesión de correo y conectarse al almacén de correos
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(imapHost, email, password);

            // Abrir la carpeta de entrada (INBOX) en modo solo lectura
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // Buscar correos no leídos
            Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            // Imprimir detalles de cada correo no leído
            for (Message message : messages) {
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Sent Date: " + message.getSentDate());
            }

            // Cerrar la carpeta y el almacén de correos
            emailFolder.close(false);
            store.close();
        } catch (Exception e) {
            // Registrar el error si la lectura de correos falla
            LOGGER.log(Level.SEVERE, "Error al leer los correos", e);
        }
    }
}