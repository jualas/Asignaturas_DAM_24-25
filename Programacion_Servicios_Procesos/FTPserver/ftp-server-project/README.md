# FTP Server Project

Este proyecto configura un servidor FTP y SFTP utilizando Docker Compose. A continuación se presentan las instrucciones para iniciar el servidor y conectarse a él.

## Estructura del Proyecto

```
ftp-server-project
├── docker-compose.yml
└── README.md
```

## Iniciar el Servidor

Para iniciar el servidor FTP y SFTP, asegúrate de tener Docker y Docker Compose instalados en tu máquina. Luego, navega al directorio del proyecto y ejecuta el siguiente comando:

```bash
docker-compose up -d
```

Esto descargará las imágenes necesarias y levantará los contenedores en segundo plano.

## Conexión al Servidor

### Conexión FTP

Puedes conectarte al servidor FTP utilizando cualquier cliente FTP. Utiliza las siguientes credenciales:

- **Host**: localhost
- **Puerto**: 21
- **Usuario**: tu_usuario
- **Contraseña**: tu_contraseña

### Conexión SFTP

Para conectarte al servidor SFTP, utiliza un cliente SFTP con las siguientes credenciales:

- **Host**: localhost
- **Puerto**: 22
- **Usuario**: tu_usuario
- **Contraseña**: tu_contraseña

## Notas Adicionales

- Asegúrate de reemplazar `tu_usuario` y `tu_contraseña` con las credenciales que hayas configurado en el archivo `docker-compose.yml`.
- Para detener el servidor, puedes ejecutar:

```bash
docker-compose down
```

## Licencia

Este proyecto está bajo la Licencia MIT.