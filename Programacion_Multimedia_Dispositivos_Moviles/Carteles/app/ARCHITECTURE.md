# Arquitectura de la Aplicación Carteles

## Visión general
Esta aplicación permite a los usuarios ver y compartir carteles
de películas de Cine y otros tipos de eventos culturales.

## Componentes principales
1. Activitys:
   - SplashScreenActivity: Muestra el logo de la aplicación mientras se carga  la misma
   - LoginActivity: Maneja el inicio de sesión
   - RVActivity: Muestra la lista de carteles
   - ContactoActivity: Muestra la información de contacto

2. Adaptadores
   - ItemAdapter: Adaptador para la lista de carteles

### ItemAdapter

`ItemAdapter` es un adaptador para la lista de carteles que se muestra en la `RVActivity`. Este adaptador extiende `RecyclerView.Adapter` y se encarga de crear y vincular las vistas de cada elemento de la lista.

#### Componentes principales:
- **ItemViewHolder**: Clase interna que contiene las vistas de cada elemento (imagen del cartel, título, año de lanzamiento, estado de conservación y precio estimado).
- **onCreateViewHolder**: Método que crea una nueva vista para cada elemento de la lista.
- **onBindViewHolder**: Método que vincula los datos del elemento a las vistas del `ViewHolder`.
- **getItemCount**: Método que devuelve el número total de elementos en la lista.

#### Funcionamiento:
1. **Creación de vistas**: `onCreateViewHolder` infla el layout de cada elemento de la lista.
2. **Vinculación de datos**: `onBindViewHolder` asigna los datos del cartel a las vistas correspondientes.
3. **Conteo de elementos**: `getItemCount` devuelve el tamaño de la lista de carteles.

Este adaptador permite que la `RecyclerView` muestre los datos de los carteles de manera eficiente y reutilizable.

3. Modelos de datos
   - Item: Representa un cartel de película o evento cultural.

### Item

La clase `Item` es un modelo de datos que representa un cartel individual en la aplicación. Esta clase se utiliza para almacenar y manejar la información relacionada con cada cartel.

#### Propiedades:
- `title`: String - El título de la película o evento.
- `year`: Int - El año de lanzamiento o creación del cartel.
- `size`: String - Las dimensiones del cartel (por ejemplo, "70x100 cm").
- `condition`: String - El estado de conservación del cartel.
- `illustrator`: String - El nombre del ilustrador o artista del cartel.
- `country`: String - El país de origen del cartel.
- `estimatedPrice`: Double - El precio estimado del cartel.

#### Uso:
- Esta clase se utiliza principalmente en el `ItemAdapter` para poblar la `RecyclerView` en `RVActivity`.
- Proporciona una estructura consistente para manejar los datos de los carteles en toda la aplicación.
- Facilita la expansión futura si se necesitan añadir más propiedades a los carteles.


#### Ejemplo de creación:

```kotlin
val cartel = Item(
    "Casablanca",
    1942,
    "70x100 cm",
    "Excelente",
    "Bill Gold",
    "Estados Unidos",
    5000.0
)
```

## Flujo de datos

El flujo de datos en la aplicación Carteles sigue un patrón lineal a través de las diferentes actividades y componentes:

1. **Inicio de la aplicación**:
    - La aplicación comienza en `SplashScreenActivity`, que muestra el logo mientras se inicializan los componentes necesarios.

2. **Autenticación del usuario**:
    - Después del splash screen, se presenta `LoginActivity`.
    - El usuario introduce sus credenciales.
    - Las credenciales se validan localmente o contra un servidor remoto (por implementar).
    - Si la autenticación es exitosa, se procede a la pantalla principal.

3. **Carga de datos de carteles**:
    - `RVActivity` se inicia después de la autenticación exitosa.
    - Esta actividad solicita los datos de los carteles (actualmente desde una lista estática, en el futuro podría ser desde una base de datos local o un servidor remoto).
    - Los datos se cargan en una lista de objetos `Item`.

4. **Visualización de carteles**:
    - `RVActivity` utiliza un `RecyclerView` para mostrar la lista de carteles.
    - `ItemAdapter` toma la lista de objetos `Item` y los convierte en vistas para el `RecyclerView`.
    - Cada elemento de la lista se infla y se vincula con los datos correspondientes.

5. **Interacción del usuario**:
    - El usuario puede desplazarse por la lista de carteles.
    - (Funcionalidad futura) Al seleccionar un cartel, se podría abrir una vista detallada.

6. **Información de contacto**:
    - El usuario puede navegar a `ContactoActivity` para ver la información de contacto.
    - Esta actividad muestra datos estáticos sobre cómo contactar con los administradores de la aplicación.

7. **Cierre de sesión** (por implementar):
    - Cuando el usuario cierra sesión, se borran los datos de autenticación y se vuelve a `LoginActivity`.

Este flujo de datos asegura que la información se presente de manera lógica y segura, manteniendo la integridad de los datos del usuario y de los carteles a lo largo de la experiencia de uso de la aplicación.

## Bibliotecas externas

[Lista de bibliotecas externas utilizadas y su propósito]

## Patrones de diseño

[Descripción de los patrones de diseño utilizados, si los hay]

## Recursos (directorio res)

### Layouts (res/layout)
- `activity_login.xml`: Layout para la pantalla de inicio de sesión.
- `activity_splash_screen.xml`: Layout para la pantalla de bienvenida.
- `activity_rv.xml`: Layout que contiene el RecyclerView para mostrar la lista de carteles.
- `activity_contacto.xml`: Layout para la pantalla de información de contacto.
- `activity_item.xml`: Layout para cada elemento individual en la lista de carteles.

### Drawables (res/drawable)
- `ic_launcher_background.xml`: Fondo del icono de la aplicación.
- `ic_launcher_foreground.xml`: Primer plano del icono de la aplicación.
- `splash_screen.png`: Imagen utilizada en la pantalla de bienvenida.

### Valores (res/values)
- `colors.xml`: Definiciones de colores utilizados en la aplicación.
- `strings.xml`: Cadenas de texto utilizadas en la interfaz de usuario.
- `themes.xml`: Definiciones de temas y estilos para la aplicación.

### Mipmap (res/mipmap)
- Carpetas `mipmap-hdpi`, `mipmap-mdpi`, `mipmap-xhdpi`, `mipmap-xxhdpi`, `mipmap-xxxhdpi`: Contienen los iconos de la aplicación en diferentes resoluciones.

### Menu (res/menu) (si existe)
- `main_menu.xml`: Definición del menú principal de la aplicación.

Estos recursos son fundamentales para la estructura y el diseño de la interfaz de usuario de la aplicación Carteles. Los layouts definen la estructura visual de cada pantalla, los drawables y mipmaps proporcionan los recursos gráficos, y los archivos de valores contienen configuraciones importantes como colores, textos y temas.