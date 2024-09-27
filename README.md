# README - Aplicación Android de Gestión de Novelas

## Link al Repositorio GitHub: 


## Ejercicio Individual

### Objetivo General
Desarrollar competencias en la creación de una aplicación Android que permita a los usuarios gestionar una biblioteca personal de novelas, aplicando los conocimientos adquiridos en el uso de Jetpack Compose y Kotlin.

### Objetivos Específicos
- Implementar una interfaz intuitiva para la gestión de novelas.
- Crear funcionalidades que permitan agregar, eliminar, ver detalles, marcar como favorita y añadir reseñas a las novelas.
- Utilizar Jetpack Compose para diseñar una interfaz dinámica y responsiva.
- Desarrollar una aplicación que permita la persistencia de datos durante la sesión.

## Enunciado

### Funcionalidades Básicas de la Aplicación
1. **Agregar Novelas:**
   - Permitir al usuario añadir una nueva novela proporcionando el título, autor, año de publicación y una breve sinopsis.
   
2. **Eliminar Novelas:**
   - El usuario puede eliminar novelas de su lista.

3. **Ver Detalles de las Novelas:**
   - Al seleccionar una novela, la aplicación muestra los detalles completos de la novela, incluyendo la sinopsis y una opción para marcarla como favorita.

4. **Marcar Novelas Favoritas:**
   - El usuario puede marcar una novela como favorita, destacándola en la lista.

5. **Añadir Reseñas:**
   - Permitir que los usuarios añadan y visualicen reseñas para cada novela.

6. **Interfaz de Usuario:**
   - La interfaz está diseñada con Jetpack Compose para ser intuitiva y fácil de usar, aprovechando las capacidades modernas de diseño en Android.

## Estructura del Código

### 1. `MainActivity.kt`
La actividad principal que contiene la lógica de navegación entre las diferentes pantallas de la aplicación, usando `NavController` para gestionar las rutas de las pantallas de lista de novelas, agregar nuevas novelas y mostrar los detalles.

**Descripción:**
- **MainActivity:** Controla la navegación y gestiona el estado de la lista de novelas.
- **NavHost:** Define las rutas entre las pantallas de la aplicación, facilitando la navegación.

### 2. `Novela.kt`
La clase de datos que define los atributos principales de una novela, como el título, autor, año de publicación, sinopsis, estado de favorita y lista de reseñas.

**Descripción:**
- **Novela:** Clase de datos que representa cada novela en la biblioteca.

### 3. `NovelasListScreen.kt`
Pantalla principal donde se lista todas las novelas. Permite al usuario navegar a la pantalla de detalles de cada novela y marcar novelas como favoritas.

**Descripción:**
- **NovelasListScreen:** Muestra la lista de novelas y proporciona la opción de añadir nuevas novelas.
- **NovelaItem:** Cada elemento de la lista, que incluye el título, autor, año de publicación y opción de favorito.

### 4. `AddNovelaScreen.kt`
Pantalla para agregar una nueva novela a la lista. El usuario proporciona título, autor, año de publicación y sinopsis, los cuales se validan antes de agregar la novela a la lista.

**Descripción:**
- **AddNovelaScreen:** Formulario para ingresar los datos de la nueva novela.

### 5. `NovelaDetailsScreen.kt`
Pantalla de detalles de una novela seleccionada. Muestra la información completa de la novela y permite al usuario agregar reseñas.

**Descripción:**
- **NovelaDetailsScreen:** Presenta los detalles completos de la novela seleccionada y la opción de añadir reseñas.
- **Reseñas:** Sección donde el usuario puede añadir y ver reseñas asociadas a la novela.

## Conclusión

La creación de esta aplicación Android de gestión de novelas ha permitido consolidar el uso de tecnologías modernas como Jetpack Compose para el desarrollo de interfaces dinámicas, así como el manejo de estados y navegación. La implementación de funcionalidades esenciales como agregar, eliminar y gestionar novelas ha facilitado el desarrollo de competencias prácticas en el entorno Android. Además, esta aplicación demuestra cómo una buena arquitectura y diseño de interfaz pueden mejorar la experiencia del usuario, incentivando el uso continuo y la personalización de sus bibliotecas literarias.

## Aprendizajes Clave
- **Interacción con el Usuario:** Diseñar interfaces que prioricen la simplicidad y la intuición.
- **Navegación y Manejo de Estado:** Usar `NavController` y el sistema de estados de Compose para actualizar la UI de manera eficiente.
- **Persistencia de Datos en Sesión:** Implementación de estructuras que permiten manejar los datos en tiempo real sin perder información durante la navegación.
  
## Implicaciones Futuras
Esta aplicación puede evolucionar con la integración de almacenamiento persistente para guardar las novelas entre sesiones, la sincronización con APIs de bibliotecas públicas o la creación de listas colaborativas. Las competencias adquiridas en este proyecto también abren la puerta a nuevos desafíos en el desarrollo de aplicaciones móviles más complejas.

## Reflexión Final
Este proyecto ha servido como un ejercicio práctico que refuerza la teoría aprendida, con un enfoque en la creación de interfaces de usuario modernas y responsivas. La integración de múltiples componentes ha sido clave para crear una aplicación completa que brinda una experiencia enriquecida y amigable.


