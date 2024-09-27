# README - Aplicación de Gestión de Novelas- Alberto Valera 

## Link al Repositorio GitHub:
https://github.com/aalbertovalera48/AplicacionNovela.git

## Ejercicio Individual

### Objetivo General
Desarrollar una aplicación Android para gestionar una biblioteca personal de novelas, permitiendo a los usuarios agregar, eliminar, ver detalles, marcar novelas favoritas y añadir reseñas, utilizando Jetpack Compose y Kotlin.

### Objetivos Específicos
- Implementar la gestión de una lista de novelas con funcionalidades básicas de CRUD.
- Utilizar Jetpack Compose para crear una interfaz de usuario intuitiva y dinámica.
- Aplicar navegación entre pantallas de forma fluida.
- Permitir el manejo de reseñas y favoritos para mejorar la interacción del usuario.

## Enunciado

### Funcionalidades de la Aplicación

1. **Pantalla de Lista de Novelas:**
   - Mostrar una lista de novelas almacenadas.
   - Permitir agregar una nueva novela.
   - Marcar una novela como favorita.
   - Navegar a la pantalla de detalles de cada novela.

2. **Pantalla de Detalles de la Novela:**
   - Mostrar los detalles de una novela seleccionada.
   - Permitir añadir reseñas y visualizar las existentes.
   - Marcar o desmarcar la novela como favorita.

3. **Pantalla de Agregar Novela:**
   - Formulario para introducir los detalles de una nueva novela (título, autor, año de publicación, sinopsis).
   - Guardar la novela en la lista.

## Estructura del Código

### Archivo `Pantallas.kt`
Toda la lógica de la aplicación está contenida en este archivo, desde la gestión de la lista de novelas hasta la navegación entre pantallas.

#### Componentes Principales:

1. **MainActivity:**
   - Configura la navegación entre pantallas y gestiona el estado de la lista de novelas utilizando `mutableStateListOf`.

2. **NovelasListScreen:**
   - Pantalla principal que muestra la lista de novelas y permite navegar a la pantalla de detalles o agregar una nueva novela.

3. **NovelaDetailsScreen:**
   - Pantalla donde se muestran los detalles completos de la novela seleccionada. Se permite añadir reseñas y marcar como favorita.

4. **AddNovelaScreen:**
   - Pantalla con un formulario para añadir una nueva novela. Incluye validación de los campos y agrega la novela a la lista una vez completado.

### Modelo de Datos `Novela`
El modelo de datos de la novela incluye propiedades como `id`, `titulo`, `autor`, `añoPublicacion`, `sinopsis`, `esfavorita` y una lista mutable de reseñas.

### Navegación y Estado
Se utiliza `NavController` para la navegación entre pantallas, y los estados de la lista de novelas y las reseñas se manejan utilizando `remember` y `mutableStateListOf`, permitiendo una gestión eficiente del estado.

## Conclusión
La aplicación de gestión de novelas permite consolidar conocimientos clave en el desarrollo de interfaces modernas con Jetpack Compose y Kotlin. Se ha logrado implementar funcionalidades esenciales para la gestión de una lista de novelas, la creación de reseñas y la interacción del usuario a través de la navegación y el manejo de favoritos.

## Aprendizajes Clave
- **Interfaz de Usuario con Jetpack Compose:** Se ha aprendido a estructurar y organizar las pantallas usando composables, creando interfaces intuitivas y reactivas.
- **Navegación:** Se ha implementado una navegación eficaz entre pantallas usando `NavController`.
- **Gestión del Estado:** El manejo de listas y estados con `remember` y `mutableStateListOf` ha sido crucial para mantener la aplicación actualizada de manera eficiente.
  
## Implicaciones Futuras
Este proyecto es una base sólida para la creación de aplicaciones Android más complejas. En el futuro, se podría mejorar con la implementación de persistencia de datos a través de bases de datos locales o almacenamiento en la nube, y la integración de APIs externas para ampliar las funcionalidades.

## Reflexión Final
Este ejercicio ha sido valioso para aplicar conocimientos teóricos en un proyecto práctico, afianzando las bases del desarrollo de aplicaciones móviles y preparándome para abordar proyectos más complejos con una comprensión sólida de los conceptos esenciales.
