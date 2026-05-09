# Decisiones técnicas

## Lenguaje de programación

Se utilizará Kotlin porque es el lenguaje moderno recomendado para el desarrollo de aplicaciones Android.

Kotlin permite escribir código más claro, seguro y conciso en comparación con Java.

---

## Entorno de desarrollo

Se utilizará Android Studio porque es el IDE oficial para crear aplicaciones Android.

Android Studio permite:

- Crear proyectos Android.
- Ejecutar la aplicación en un emulador.
- Detectar errores de compilación.
- Administrar dependencias.
- Trabajar con Kotlin y Jetpack Compose.

---

## Interfaz de usuario

Se utilizará Jetpack Compose para construir la interfaz gráfica de la aplicación.

Jetpack Compose permite crear pantallas usando código Kotlin, sin necesidad de trabajar directamente con archivos XML para la interfaz.

---

## Base de datos local

La aplicación utilizará SQLite como base de datos local.

Para trabajar con SQLite se utilizará Room, una librería de Android que facilita guardar, consultar, actualizar y eliminar información de forma más ordenada.

Esta decisión permite que la aplicación funcione sin conexión a internet.

Estado actual:

- Room ya está agregado como dependencia.
- Se usa KSP para generar el código de Room.
- Ya existe una primera capa local con entidades, DAOs, convertidores y `AppDatabase`.
- Ya existe un contenedor manual de dependencias (`AppContainer`).
- Ya existe un seed inicial que carga los datos de prueba en Room si la base está vacía.
- La lista de vehículos ya lee desde Room mediante `VehicleListViewModel`.
- El detalle, formularios y vencimientos todavía usan datos de prueba o estado local mientras se migran gradualmente.

Decisión para la primera integración:

- Crear primero la capa local aislada.
- Validar compilación.
- Conectar la UI en pasos posteriores, pantalla por pantalla.

---

## Firebase

Firebase no se utilizará en la primera versión.

Se considera como una posible mejora futura para:

- Sincronización en la nube.
- Inicio de sesión.
- Copias de seguridad.
- Acceso desde varios dispositivos.

---

## Recordatorios

En la primera versión, los recordatorios se mostrarán dentro de la aplicación.

No se implementarán inicialmente notificaciones avanzadas del sistema Android.

Esta decisión reduce la complejidad inicial y permite enfocarse primero en la lógica principal del proyecto.

---

## Enfoque del proyecto

El proyecto aplicará buenas prácticas de desarrollo, pero evitando sobreingeniería.

La prioridad será construir una aplicación clara, funcional y fácil de mantener.
