# Aplicación de Gestión de Novelas con Fragmentos y Widgets - Alberto Valera

**Link al Repositorio:** https://github.com/aalbertovalera48/AplicacionNovelaFragmentsyWidgets.git

**Link a la base de datos Firebase:** https://gestion-de-novelas-actualizado-default-rtdb.firebaseio.com/


## Descripción
Esta aplicación permite gestionar una biblioteca personal de novelas, proporcionando funcionalidades para agregar, eliminar, buscar y marcar novelas como favoritas. Además, se integra con Firebase para la sincronización de datos y utiliza `AlarmManager` para programar tareas periódicas.
Implementa fragmentos para mostrar la lista de novelas y los detalles de cada novela en una sola actividad. Utiliza fragmentos para crear una interfaz de usuario modular y adaptable a diferentes tamaños de pantalla.
Crea un widget de aplicación que permita a los usuarios ver una lista resumida de sus novelas favoritas desde la pantalla de inicio y acceder rápidamente a la aplicación para ver más detalles

## Características Principales
- **Agregar Novelas:** Permite añadir nuevas novelas a la biblioteca.
- **Eliminar Novelas:** Permite eliminar novelas existentes de la biblioteca.
- **Buscar Novelas:** Facilita la búsqueda de novelas por título.
- **Marcar Favoritas:** Permite marcar novelas como favoritas y visualizarlas en una lista separada.
- **Sincronización con Firebase:** Sincroniza los datos de las novelas con Firebase.
- **Notificaciones Programadas:** Utiliza `AlarmManager` para programar tareas de sincronización periódicas.
- **Widgets:** Muestra las novelas favoritas en la pantalla de inicio mediante un widget.

## Estructura del Código

### Actividades Principales

1. **MainActivity:**
   - Actividad principal que maneja la navegación y las operaciones básicas de la aplicación.
   - Contiene botones para agregar, eliminar, buscar y ver novelas favoritas.

2. **LoginActivity:**
   - Maneja el inicio de sesión de los usuarios.
   - Permite a los usuarios registrarse y autenticarse con Firebase.

3. **RegisterActivity:**
   - Permite a los usuarios registrarse en la aplicación.

4. **SettingsActivity:**
   - Proporciona opciones de configuración, como el cambio de tema y la gestión de copias de seguridad.

5. **FavoritosActivity:**
   - Maneja la visualización de las novelas marcadas como favoritas.
   - Permite al usuario ver y gestionar su lista de novelas favoritas.

### Fragments

1. **NovelasListFragment:**
   - Muestra la lista de novelas y permite la navegación a los detalles de cada novela.
   - Carga las novelas desde Firebase y las muestra en un `RecyclerView`.

2. **NovelaDetailsFragment:**
   - Muestra los detalles completos de una novela seleccionada.
   - Permite añadir reseñas y marcar la novela como favorita.

### Adaptadores

1. **NovelaRecyclerViewAdapter:**
   - Adaptador personalizado para el `RecyclerView` de novelas.
   - Enlaza los datos de las novelas con las vistas en la interfaz de usuario.

2. **NovelaAdapter:**
   - Adaptador para mostrar las novelas en una lista.

### Modelos

1. **Novela:**
   - Clase modelo que representa una novela.
   - Contiene atributos como título, autor, año, sinopsis y un indicador de si la novela es favorita.

### Utilidades

1. **AlarmManager:**
   - Clase utilitaria para manejar las alarmas y recordatorios.
   - Utiliza el `AlarmManager` de Android para programar tareas periódicas y gestionar notificaciones.

2. **Conexion:**
   - Clase que extiende `BroadcastReceiver` para recibir y manejar cambios en la conectividad de red.
   - Notifica a la aplicación sobre cambios en la conexión para gestionar adecuadamente la sincronización con Firebase.

3. **FirebaseConfig:**
   - Clase encargada de gestionar la conexión con Firebase.
   - Contiene métodos para cargar, agregar y eliminar novelas en la base de datos de Firebase.

4. **SyncTask:**
   - Clase que extiende `AsyncTask` para realizar tareas de sincronización de datos en segundo plano.
   - Permite que la aplicación realice operaciones de red sin bloquear la interfaz de usuario.

5. **FileHelper:**
   - Clase utilitaria para leer y escribir archivos en el almacenamiento interno o externo.

### Widgets

1. **NovelaWidgetProvider:**
   - Proveedor de widgets que muestra las novelas favoritas en la pantalla de inicio.
   - Actualiza el widget con los datos de las novelas favoritas desde Firebase.

### Archivos XML

1. **activity_main.xml:** 
   - Diseño de la actividad principal.
   - Contiene elementos como `TextView`, `RecyclerView` y botones para gestionar novelas.

2. **favoritos_activity.xml:** 
   - Diseño para la visualización de novelas favoritas.

3. **agregar_novela.xml:** 
   - Diseño del diálogo para agregar una nueva novela.

4. **eliminar_novela.xml:** 
   - Diseño del diálogo para eliminar una novela.

5. **detalles_novela.xml:** 
   - Diseño para mostrar detalles de una novela específica.

6. **item_novela.xml:** 
   - Diseño para cada elemento en la lista de novelas.

7. **buscar_novela.xml:** 
   - Diseño para la interfaz de búsqueda de novelas.

8. **progress_dialog.xml:** 
   - Diseño para mostrar un diálogo de progreso durante las operaciones de carga y sincronización.

9. **widget_novela.xml:** 
   - Diseño del widget para mostrar novelas favoritas en la pantalla de inicio.

10. **novela_widget_info.xml:** 
    - Configuración del widget de novelas.

## Requisitos

- **Android Studio:** Versión recomendada 4.1 o superior.
- **SDK de Android:** Nivel mínimo 21.
- **Conexión a Internet:** Necesaria para sincronizar datos con Firebase.

## Conclusión
La aplicación de gestión de novelas no solo consolida conocimientos clave en el desarrollo de interfaces modernas con **Jetpack Compose** y **Kotlin**, sino que también proporciona funcionalidades esenciales para la gestión de una biblioteca personal de novelas. La experiencia de usuario se enriquece a través de la interacción fluida, la sincronización de datos en tiempo real y la implementación de notificaciones programadas.

## Aprendizajes Clave
- **Interfaz de Usuario con Jetpack Compose:** Se ha aprendido a estructurar y organizar las pantallas usando composables, creando interfaces intuitivas y reactivas.
- **Navegación:** Implementación de una navegación eficaz entre pantallas usando `NavController`.
- **Gestión del Estado:** El manejo de listas y estados con `remember` y `mutableStateListOf` ha sido crucial para mantener la aplicación actualizada de manera eficiente.
- **Integración de Firebase:** Comprensión de cómo conectar la aplicación con una base de datos en la nube para la persistencia y sincronización de datos.
- **Notificaciones Programadas:** Desarrollo de habilidades para utilizar `AlarmManager` para gestionar recordatorios y alertas.

## Implicaciones Futuras
Este proyecto es una base sólida para la creación de aplicaciones Android más complejas. En el futuro, se podrían implementar características adicionales, como:
- Persistencia de datos utilizando **Room** para una gestión local de la base de datos.
- Integración de APIs externas para enriquecer la experiencia del usuario, como recomendaciones de novelas.
- Desarrollo de una versión web o multiplataforma para ampliar el acceso a la aplicación.

### Nota
El código se encuentra en constante desarrollo y mejora. Agradezco cualquier sugerencia o comentario que pueda ayudar a mejorar la aplicación. ¡Disfruta de la gestión de tus novelas!
