# Resumen de progreso

## Proyecto

Nombre del proyecto:

- VehicleControlApp

Nombre visible de la aplicación:

- Control Vehicular

Paquete principal:

- com.ivanmadrid.vehiclecontrolapp

## Corte de avance: 16 de julio de 2026

El proyecto queda preparado como candidato `1.0.0-rc1` para validación en un celular Android real.

En este corte se completó:

- Cobertura de repositorios y operaciones Room para vehículos, gastos, novedades y documentos.
- Seis pruebas instrumentadas ejecutadas correctamente en un emulador Android.
- Validación contextual por campo en los cuatro formularios principales.
- Compatibilidad de `java.time` desde Android 7 mediante core library desugaring.
- Punto de extensión documentado para futuras migraciones Room, sin borrado destructivo de datos.
- Datos demo, capturas reales y pieza promocional alineados con julio de 2026.
- Checklist de instalación y validación física del MVP 1.0.

La etiqueta final `v1.0.0` se reservará hasta completar la prueba en un dispositivo físico.

---

## Objetivo general

Desarrollar una aplicación móvil Android en Kotlin para llevar el control de vehículos particulares y taxis.

La aplicación permitirá registrar y consultar información relacionada con:

- Vehículos.
- Gastos.
- Novedades.
- Documentos importantes.
- Fechas de vencimiento.
- Recordatorios internos.

Inicialmente la aplicación manejará:

- Dos taxis.
- Dos vehículos particulares.

Los taxis tienen lógica especial:

- Cada taxi tiene un ingreso fijo diario.
- Ese ingreso no se registra manualmente todos los días.
- El ingreso puede ajustarse mediante novedades como trabajo parcial, día sin operación, conductor enfermo, falla mecánica o accidente.

---

## Tecnologías y enfoque

- Lenguaje: Kotlin.
- IDE: Android Studio.
- Interfaz: Jetpack Compose.
- Estado actual: app funcional inicial con Room para lecturas principales y primer guardado real.
- Base de datos local: SQLite usando Room.
- Firebase: posible mejora futura, no incluida en la primera versión.
- Arquitectura actual: separación sencilla por responsabilidades.

El proyecto mantiene una prioridad clara:

- Construir una app funcional y entendible.
- Evitar sobreingeniería.
- Hacer cambios pequeños.
- Probar antes de hacer commit.
- Documentar las decisiones importantes.

---

## Estructura actual

Ruta principal:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/
├── data/
├── domain/
├── presentation/
├── ui/
├── utils/
└── MainActivity.kt
```

La carpeta `ui` fue creada por Android Studio para el tema de Jetpack Compose y se conserva por ahora.

La carpeta `utils` contiene utilidades simples, como cálculo de días restantes para vencimientos y ordenamiento de documentos por fecha.

---

## Modelos del dominio

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/domain/model/
```

Entidades principales:

- `Vehicle`: representa un vehículo.
- `Expense`: representa un gasto.
- `Novelty`: representa una novedad.
- `VehicleDocument`: representa un documento o fecha importante.

Enums principales:

- `VehicleType`: `TAXI`, `PRIVATE`.
- `ExpenseCategory`: combustible, lavado, mantenimiento, repuestos, seguro, impuestos, multas y otros.
- `NoveltyPriority`: baja, media y alta.
- `IncomeAdjustmentType`: sin ingreso, medio ingreso y valor personalizado.
- `VehicleDocumentType`: SOAT, tecnicomecánica e impuestos.

Por ahora las fechas siguen como texto con formato `yyyy-MM-dd`. En Room se mantienen como texto ISO para conservar simplicidad y facilitar el ordenamiento básico.

---

## Datos de prueba

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/data/sample/
```

Archivos actuales:

```text
SampleVehicles.kt
SampleVehicleDocuments.kt
SampleExpenses.kt
SampleNovelties.kt
```

Los datos de prueba permiten:

- Mostrar cuatro vehículos iniciales.
- Diferenciar taxis y particulares.
- Mostrar documentos y vencimientos.
- Mostrar gastos recientes.
- Mostrar novedades recientes.
- Calcular un resumen económico estimado para taxis.

Los datos de prueba se cargan como seed inicial en Room si la base de datos está vacía.

---

## Pantallas implementadas

Pantallas principales:

```text
presentation/screens/vehicles/VehicleListScreen.kt
presentation/screens/vehicles/VehicleDetailScreen.kt
presentation/screens/vehicles/VehicleDetailCards.kt
presentation/screens/vehicles/VehicleFormScreen.kt
presentation/screens/reports/ReportsScreen.kt
presentation/screens/expenses/ExpenseFormScreen.kt
presentation/screens/novelties/NoveltyFormScreen.kt
presentation/screens/documents/DocumentFormScreen.kt
```

### Lista de vehículos

Estado actual:

- Muestra `Control Vehicular`.
- Muestra cantidad total de vehículos.
- Muestra chips de taxis y particulares.
- Muestra próximos vencimientos ordenados por fecha.
- Muestra días restantes para vencimientos.
- Muestra colores de vencimiento según urgencia real.
- Muestra tarjetas visuales para cada vehículo.
- Muestra iconos vectoriales por tipo de vehículo.
- Permite alternar modo claro/oscuro y recuerda la preferencia localmente.
- Tiene menú tipo sánduche para acceder a secciones como Reportes.
- Diferencia taxis y particulares.
- Permite entrar al detalle de cualquier vehículo.
- Tiene botón flotante `+` solo en esta pantalla.
- Ya lee los vehículos desde Room mediante `VehicleListViewModel`.
- Ya lee los vencimientos de la lista desde Room mediante `VehicleListViewModel`.

No se incluye búsqueda por ahora porque el alcance inicial contempla pocos vehículos.

### Detalle del vehículo

Estado actual:

- Muestra header con placa, marca, modelo, tipo y estado.
- Muestra información general.
- Muestra documentos y vencimientos.
- Colorea documentos según urgencia real del vencimiento.
- Muestra gastos recientes.
- Muestra novedades recientes.
- Muestra acciones rápidas.
- Usa iconos vectoriales internos para mejorar lectura visual.
- Tiene acciones rápidas con botones del mismo tamaño visual y texto centrado.
- Para taxis muestra información del taxi.
- Para taxis muestra un resumen económico estimado.
- Los particulares no muestran información de taxi ni resumen económico.
- Ya observa el vehículo seleccionado desde Room mediante `VehicleDetailViewModel`.
- Ya lee documentos, gastos y novedades desde Room mediante `VehicleDetailViewModel`.

El resumen económico actual es una estimación con datos guardados en Room:

```text
Balance estimado del día = ingreso ajustado por novedades del día - gastos del día
```

Las novedades de taxi pueden describir el impacto operativo del día como:

- No trabajó.
- Trabajó medio día.
- Trabajó con ingreso diferente.

El resumen toma por defecto la fecha más reciente entre gastos y novedades del vehículo.
También permite elegir entre las fechas recientes disponibles para revisar otros días registrados.
Más adelante puede agregarse un selector de periodo más completo.

### Reportes

Estado actual:

- Existe pantalla `Reportes`.
- Se accede desde el menú tipo sánduche de la pantalla principal.
- Muestra total de vehículos.
- Muestra cantidad de taxis y particulares.
- Muestra suma de ingreso diario fijo de taxis.
- Muestra cantidad de documentos vencidos o con 15 días o menos.
- Muestra próximos vencimientos.
- Usa el mismo criterio de colores por urgencia que lista y detalle.

La pantalla es intencionalmente simple para evitar convertirla todavía en un dashboard complejo.

### Formularios visuales

Ya existen formularios visuales para:

- Agregar vehículo.
- Registrar gasto.
- Registrar novedad.
- Registrar documento.

Los formularios:

- Tienen scroll vertical.
- Muestran el vehículo asociado cuando aplica.
- Usan tarjetas para separar contenido.
- Tienen botones `Guardar`, `Cancelar` y `Volver`.
- Funcionan visualmente con modo claro y modo oscuro.
- Validan campos obligatorios antes de guardar.
- Validan fechas con formato `yyyy-MM-dd`.
- Validan valores monetarios mayores que cero.
- Validan ingreso diario obligatorio para taxis.
- Validan ingreso personalizado cuando una novedad de taxi afecta la operación con valor propio.
- El formulario de vehículo ya guarda registros reales en Room.
- El formulario de vehículo también se reutiliza para editar vehículos existentes.
- El formulario de gasto ya guarda registros reales en Room.
- El formulario de novedad ya guarda registros reales en Room.
- El formulario de documento ya guarda registros reales en Room.

Controles guiados implementados:

- Tipo de vehículo: Taxi / Particular.
- Categoría de gasto.
- Prioridad de novedad.
- Impacto operativo del día para taxis.
- Tipo de documento.

---

## Navegación actual

La navegación sigue implementada de forma sencilla en `MainActivity.kt`, usando estado local con Compose.

Enum actual:

```kotlin
enum class AppScreen {
    VEHICLE_LIST,
    VEHICLE_DETAIL,
    VEHICLE_FORM,
    EXPENSE_FORM,
    NOVELTY_FORM,
    DOCUMENT_FORM,
    VEHICLE_HISTORY,
    REPORTS,
}
```

Flujo actual:

```text
Lista de vehículos
  ├── tocar vehículo
  │     └── Detalle del vehículo
  │           ├── Editar
  │           │     └── Formulario de vehículo
  │           ├── Registrar gasto
  │           │     └── Formulario de gasto
  │           ├── Registrar novedad
  │           │     └── Formulario de novedad
  │           ├── Registrar documento
  │           │     └── Formulario de documento
  │           └── Ver historial
  │                 └── Historial del vehículo
  │
  ├── menú principal
  │     └── Reportes
  │
  └── tocar botón +
        └── Formulario de vehículo
```

Todavía no se usa Navigation Compose. La navegación actual sigue siendo suficiente para esta etapa y evita complejidad innecesaria.

---

## Utilidades creadas

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/utils/
```

Archivo:

```text
DateFormatUtils.kt
FormValidationUtils.kt
```

Incluye:

- `getDaysUntilLabel`: muestra texto como `Vence hoy`, `Vence mañana`, `Faltan X días` o `Vencido`.
- `getDaysUntilCount`: calcula días hasta una fecha.
- `getDocumentUrgency`: clasifica vencimientos como vencido, urgente, atención, normal o fecha por revisar.
- `sortDocumentsByDueDate`: ordena documentos por fecha de vencimiento.
- `isValidIsoDate`: valida que una fecha tenga formato ISO `yyyy-MM-dd`.
- `validateVehicleForm`: valida datos mínimos del vehículo y reglas de taxi.
- `validateExpenseForm`: valida fecha, categoría y valor de gasto.
- `validateNoveltyForm`: valida fecha, tipo, prioridad e impacto de ingreso.
- `validateDocumentForm`: valida tipo de documento y fecha de vencimiento.

---

## Estado actual del proyecto

La app ya permite:

- Ejecutarse en Android Studio.
- Mostrar lista de vehículos.
- Cargar vehículos iniciales desde Room usando seed.
- Diferenciar taxis y particulares.
- Consultar detalle de cualquier vehículo.
- Ver documentos, gastos y novedades por vehículo desde Room.
- Ver próximos vencimientos ordenados.
- Ver días restantes para vencimientos.
- Ver resumen económico estimado para taxis.
- Abrir formularios visuales para vehículo, gasto, novedad y documento.
- Navegar entre lista, detalle y formularios.
- Usar el botón Back nativo de Android para volver desde detalle, reportes y formularios.
- Agregar vehículos nuevos y guardarlos en Room desde el botón flotante `+`.
- Editar vehículos existentes desde el detalle.
- Eliminar vehículos desde el detalle con confirmación.
- Registrar gastos reales desde el detalle de un vehículo y guardarlos en Room.
- Editar gastos individuales desde el detalle.
- Eliminar gastos individuales desde el detalle con confirmación.
- Registrar novedades reales desde el detalle de un vehículo y guardarlas en Room.
- Editar novedades individuales desde el detalle.
- Eliminar novedades individuales desde el detalle con confirmación.
- Registrar documentos reales desde el detalle de un vehículo y guardarlos en Room.
- Editar documentos individuales desde el detalle.
- Eliminar documentos individuales desde el detalle con confirmación.
- Acciones de edición y eliminación más claras en el detalle.
- Validar formato de fecha `yyyy-MM-dd` en gastos, novedades y documentos.
- Evitar registrar vehículos con placas duplicadas.
- Calcular resumen económico diario de taxis usando gastos y novedades del mismo día.
- Cambiar el día del resumen económico entre fechas recientes disponibles.
- Mostrar en novedades recientes cuándo una novedad cambió la operación del taxi.
- Usar una paleta visual propia en modo claro y modo oscuro.
- Alternar el tema claro/oscuro desde la pantalla principal y recordar la preferencia localmente.
- Mostrar iconos vectoriales para vehículos y secciones internas.
- Mostrar iconos vectoriales específicos para categorías de gasto y tipos de documento.
- Mostrar iconos vectoriales específicos para prioridades de novedades.
- Usar formularios de gasto y documento con opciones visuales tipo tarjeta.
- Usar un formulario de novedades más visual para prioridad e impacto en operación.
- Consultar una pantalla inicial de reportes.
- Consultar historial completo por vehículo, agrupado por fecha.
- Ver colores de vencimiento consistentes en lista, detalle y reportes.
- Usar un botón flotante `+` circular y exclusivo de la lista principal.
- Contar con un `README.md` inicial para GitHub.
- Mostrar capturas principales de la app desde `docs/images/`.
- Mantener `.gitignore` configurado para excluir archivos locales, generados y sensibles.

La app todavía no permite:

- Sincronizar con Firebase.
- Enviar notificaciones del sistema.

---

## Estabilización técnica

Se completó una primera fase de estabilización orientada a proteger los datos y hacer más confiables los formularios:

- Room exporta el esquema de la base de datos en `app/schemas/`.
- El esquema inicial queda versionado para poder comparar cambios y preparar migraciones futuras.
- Existen pruebas instrumentadas con una base Room en memoria para verificar el CRUD básico de vehículos.
- Las pruebas de Room verifican que la placa se consulte sin distinguir mayúsculas y minúsculas.
- La eliminación transaccional comprueba que también se borren gastos, novedades y documentos asociados.
- Los repositorios cuentan con una primera prueba unitaria de mapeo y delegación de operaciones.
- Los formularios de vehículo, gasto, novedad y documento muestran un mensaje comprensible si Room no puede guardar la información.
- La app solo navega fuera del formulario después de confirmar que el guardado terminó correctamente.

Validación realizada:

```text
testDebugUnitTest: correcto
assembleDebug: correcto
assembleDebugAndroidTest: correcto
connectedDebugAndroidTest: 3 pruebas correctas en Pixel 7 AVD
```

Las pruebas instrumentadas fueron ejecutadas correctamente en un emulador Android API 37.

### Validación manual en emulador

También se realizó una revisión funcional y visual en un Pixel 7 con Android API 37:

- Carga inicial de los cuatro vehículos y datos seed.
- Lista principal en modo claro y modo oscuro.
- Persistencia de la preferencia de tema.
- Menú principal y acceso a Reportes.
- Navegación con Back desde Reportes, formularios e historial.
- Detalle de vehículo particular y sus estados vacíos.
- Historial agrupado por fecha con gastos y novedades.
- Formulario de gasto con vehículo asociado y fecha actual predeterminada.
- Mensaje de validación al intentar guardar un vehículo incompleto.
- Entrada mediante teclado y conservación de los valores al ocultarlo.
- Creación real de un vehículo, actualización del contador y persistencia en Room.
- Edición de un vehículo existente y regreso al mismo detalle.
- Confirmación de eliminación indicando placa y datos relacionados.
- Eliminación del vehículo de prueba y restauración del contador inicial.

Durante la revisión se detectó que el botón flotante se exponía únicamente como `+` a las tecnologías de asistencia. Se añadió la descripción accesible `Agregar vehículo` sin cambiar su apariencia visual.

---

## Próximos pasos recomendados

1. Mantener las pruebas instrumentadas actualizadas cuando cambien entidades o relaciones.
2. Probar manualmente los flujos completos de creación, edición y eliminación.
3. Revisar visualmente modo claro, modo oscuro, teclado y pantallas pequeñas.
4. Añadir migraciones Room cuando cambie por primera vez la estructura de las entidades.
5. Mejorar los errores por campo sin convertir los formularios en flujos complejos.
6. Agregar selector de periodo más completo para reportes cuando la base actual esté estabilizada.
7. Evaluar Navigation Compose cuando haya ajustes o rutas con parámetros más complejos.
