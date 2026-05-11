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

---

## 11-05-2026 - Reportes y consistencia visual

### Actividad realizada

Se agregó una primera pantalla oficial de reportes y se ajustó la navegación visual desde la pantalla principal.

### Avances

- Se agregó la pantalla `Reportes`.
- Se agregó acceso a reportes desde un menú tipo sánduche en la lista principal.
- Se centró visualmente el título `Control Vehicular`.
- Se ajustaron los contadores principales para mostrar número y texto centrados.
- Se unificó el criterio de colores para vencimientos en lista, detalle y reportes.
- Se movió la clasificación de urgencia de documentos a utilidades compartidas.

### Criterio de colores para vencimientos

- Rojo: vencido o faltan 7 días o menos.
- Naranja: faltan entre 8 y 15 días.
- Verde: más de 15 días o fecha por revisar.

### Commits

```text
8b16fe3 feat: agregar reportes desde menu principal
015bf6a fix: ajustar colores de vencimientos por urgencia
02199e4 fix: unificar colores de vencimientos
```

### Decisiones tomadas

- Reportes queda como sección oficial, no como prueba.
- El menú tipo sánduche se usa como acceso simple a secciones sin implementar todavía un drawer completo.
- Se mantiene la navegación simple por estado en `MainActivity.kt`.
- No se agrega todavía Navigation Compose.

### Próximo paso

Evaluar mejoras visuales con fotos reales o iconos más específicos, cuidando rendimiento, consistencia y alcance.

---

## 11-05-2026 - Iconos por categoría y documento

### Actividad realizada

Se mejoró la identificación visual de gastos y documentos usando iconos vectoriales específicos.

### Avances

- Se agregaron iconos locales para categorías de gasto: combustible, lavado, mantenimiento, repuestos, seguro, impuestos, multas y otros.
- Se agregaron iconos locales para tipos de documento: SOAT, tecnicomecánica e impuestos.
- El detalle del vehículo ahora muestra iconos relacionados con cada gasto y documento.
- El formulario de gasto muestra iconos en las opciones de categoría.
- El formulario de documento muestra iconos en las opciones de tipo de documento.

### Decisiones tomadas

- Se decidió continuar con vectores locales antes de usar fotos reales, para mantener la app liviana y consistente.
- Los iconos se centralizaron en un helper compartido para evitar duplicar criterios visuales entre pantallas.

### Validación

- Se ejecutó `./gradlew testDebugUnitTest`.
- La compilación y pruebas unitarias finalizaron correctamente.
