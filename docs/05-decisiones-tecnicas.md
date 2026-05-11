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
- La lista de vehículos ya lee vehículos y vencimientos desde Room mediante `VehicleListViewModel`.
- El detalle ya lee el vehículo seleccionado, documentos, gastos y novedades desde Room mediante `VehicleDetailViewModel`.
- El formulario de agregar vehículo ya guarda registros reales en Room mediante `VehicleFormViewModel`.
- El formulario de registrar gasto ya guarda registros reales en Room mediante `ExpenseFormViewModel`.
- El formulario de registrar novedad ya guarda registros reales en Room mediante `NoveltyFormViewModel`.
- El formulario de registrar documento ya guarda registros reales en Room mediante `DocumentFormViewModel`.
- El formulario de vehículo se reutiliza para agregar y editar, usando `insertVehicle` o `updateVehicle` según corresponda.
- La eliminación de vehículo borra primero documentos, gastos y novedades asociados dentro de una transacción Room, y después elimina el vehículo.

Decisión para la integración gradual:

- Conectar primero las lecturas desde Room.
- Luego conectar los guardados pantalla por pantalla.
- Mantener la navegación simple mientras la app siga siendo pequeña.

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

---

## Skills locales de apoyo

Se crearon dos skills locales de Codex para guiar el desarrollo del proyecto:

```text
~/.codex/skills/design-system-skill/SKILL.md
~/.codex/skills/ui-ux-pro-max/SKILL.md
```

Uso esperado:

- `design-system-skill`: mantener consistencia visual, tokens, colores, espaciado, iconos y componentes compartidos.
- `ui-ux-pro-max`: revisar experiencia de usuario, accesibilidad, Material Design 3, formularios, navegación y acciones destructivas.

Decisión:

- Las skills no reemplazan el criterio del proyecto.
- Deben proteger la identidad visual ya lograda.
- No deben impulsar rediseños grandes ni sobreingeniería sin aprobación.
- Se usan como guía antes de cambios visuales o de UX importantes.
