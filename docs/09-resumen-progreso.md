# Resumen de progreso

## Proyecto

Nombre del proyecto:

- VehicleControlApp

Nombre visible de la aplicación:

- Control Vehicular

Paquete principal:

- com.ivanmadrid.vehiclecontrolapp

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
- Muestra tarjetas visuales para cada vehículo.
- Diferencia taxis y particulares.
- Permite entrar al detalle de cualquier vehículo.
- Tiene botón flotante `+` solo en esta pantalla.
- Ya lee los vehículos desde Room mediante `VehicleListViewModel`.
- Ya lee los vencimientos de la lista desde Room mediante `VehicleListViewModel`.

### Detalle del vehículo

Estado actual:

- Muestra header con placa, marca, modelo, tipo y estado.
- Muestra información general.
- Muestra documentos y vencimientos.
- Muestra gastos recientes.
- Muestra novedades recientes.
- Muestra acciones rápidas.
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
  │           └── Registrar documento
  │                 └── Formulario de documento
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
```

Incluye:

- `getDaysUntilLabel`: muestra texto como `Vence hoy`, `Vence mañana`, `Faltan X días` o `Vencido`.
- `sortDocumentsByDueDate`: ordena documentos por fecha de vencimiento.
- `isValidIsoDate`: valida que una fecha tenga formato ISO `yyyy-MM-dd`.

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

La app todavía no permite:

- Consultar historial completo.
- Sincronizar con Firebase.
- Enviar notificaciones del sistema.

---

## Próximos pasos recomendados

1. Revisar visualmente todas las pantallas en emulador.
2. Ajustar textos, espaciados o tamaños que se vean apretados.
3. Probar el guardado real de vehículos desde el botón flotante `+`.
4. Probar el guardado real de gastos desde el detalle de un vehículo.
5. Probar el guardado real de novedades desde el detalle de un vehículo.
6. Probar el guardado real de documentos desde el detalle de un vehículo.
7. Probar la edición de vehículos desde el detalle.
8. Agregar selector de periodo más completo para reportes.
9. Evaluar Navigation Compose cuando haya historial, reportes y ajustes.
