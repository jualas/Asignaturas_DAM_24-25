package jualas.es;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private final Properties properties;

    public Config() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            // Verificar si el archivo config.properties se encuentra
            if (input == null) {
                LOGGER.log(Level.SEVERE, "Lo siento, no se puede encontrar config.properties");
                return;
            }
            // Cargar las propiedades desde el archivo config.properties
            properties.load(input);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar config.properties", e);
        }
    }

    // Obtener el valor de la propiedad mail.email
    public String getEmail() {
        return properties.getProperty("mail.email");
    }

    // Obtener el valor de la propiedad mail.password
    public String getPassword() {
        return properties.getProperty("mail.password");
    }

    // Obtener el valor de la propiedad mail.smtp.host
    public String getSmtpHost() {
        return properties.getProperty("mail.smtp.host");
    }

    // Obtener el valor de la propiedad mail.smtp.port
    public String getSmtpPort() {
        return properties.getProperty("mail.smtp.port");
    }

    // Obtener el valor de la propiedad mail.imap.host
    public String getImapHost() {
        return properties.getProperty("mail.imap.host");
    }

    // Obtener el valor de la propiedad mail.imap.port
    public String getImapPort() {
        return properties.getProperty("mail.imap.port");
    }
}