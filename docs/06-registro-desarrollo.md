# Registro de desarrollo

## 05-05-2026

### Actividad realizada

Se configuró el entorno inicial para el desarrollo de la aplicación VehicleControlApp.

### Avances

- Se instaló Android Studio.
- Se creó el proyecto Android con Kotlin.
- Se configuró un emulador Android.
- Se ejecutó la aplicación por primera vez.
- Se creó la carpeta `docs`.
- Se inició la documentación base del proyecto.

### Decisiones tomadas

- El proyecto se llamará `VehicleControlApp`.
- El nombre visible de la aplicación será `Control Vehicular`.
- El paquete será `com.ivanmadrid.vehiclecontrolapp`.
- La aplicación se desarrollará en Kotlin.
- Se utilizará Jetpack Compose para la interfaz.
- Se utilizará SQLite mediante Room para la base de datos local.
- Firebase se considera como una mejora futura, no para la primera versión.
- Se aplicarán buenas prácticas, evitando sobreingeniería.

### Próximo paso

Crear la estructura base de carpetas del código y comenzar con los modelos principales del dominio.

---

## 11-05-2026

### Actividad realizada

Se consolidó un bloque importante de mejoras visuales y experiencia de usuario.

### Avances

- Se mejoró la apariencia de la lista de vehículos.
- Se compactó visualmente el detalle del vehículo.
- Se agregaron iconos vectoriales para taxis y vehículos particulares.
- Se agregaron iconos internos para información, taxi, documentos, gastos, novedades y acciones rápidas.
- Se implementó una paleta propia para modo claro y modo oscuro.
- Se agregó un botón para alternar entre modo claro y oscuro durante la sesión.
- Se revisó que los formularios funcionen correctamente con el tema claro/oscuro.
- Se ajustaron las acciones rápidas para que los tres botones tengan tamaño y alineación visual más consistentes.

### Decisiones tomadas

- Se decidió usar recursos vectoriales locales en `res/drawable` para mantener la app liviana y sin dependencias externas.
- Se decidió no agregar búsqueda en la pantalla principal por ahora, porque la app está pensada inicialmente para pocos vehículos.
- Se mantiene el enfoque de mejoras graduales, evitando agregar funcionalidad que no aporte valor claro en esta etapa.
- La preferencia de modo claro/oscuro todavía no se guarda de forma persistente; queda como posible mejora futura.

### Validación

- Se ejecutó `./gradlew testDebugUnitTest`.
- La compilación y pruebas unitarias finalizaron correctamente.

### Commit

```text
5dc948b feat: mejorar apariencia visual y modo oscuro
```

### Próximo paso

Continuar con mejoras pequeñas y conversadas antes de implementar, priorizando ajustes útiles sobre funcionalidades innecesarias.
