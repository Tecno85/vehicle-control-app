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

---

## Enfoque de trabajo

El proyecto se desarrollará aplicando buenas prácticas de la industria, pero evitando sobreingeniería.

La forma de trabajo será:

1. Definir el objetivo del cambio.
2. Implementar el cambio paso a paso.
3. Probar en el emulador.
4. Verificar que el cambio sea una unidad lógica completa.
5. Hacer commit con un mensaje claro.

---

## Tecnologías definidas

- Lenguaje: Kotlin.
- IDE: Android Studio.
- Interfaz: Jetpack Compose.
- Base de datos futura: SQLite usando Room.
- Firebase: posible mejora futura, no incluida en la primera versión.
- Arquitectura inicial: separación sencilla por responsabilidades.

---

## Estructura principal creada

Dentro del paquete principal se crearon las siguientes carpetas:

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

---

## Documentación creada

Se creó la carpeta:

```text
docs/
```

Con los siguientes documentos:

```text
docs/
├── 01-descripcion-proyecto.md
├── 02-requisitos-funcionales.md
├── 03-modelo-datos.md
├── 04-flujo-navegacion.md
├── 05-decisiones-tecnicas.md
├── 06-registro-desarrollo.md
├── 07-diseno-ui.md
└── 09-resumen-progreso.md
```

---

## Modelos del dominio creados

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/domain/model/
```

Archivos creados:

```text
Vehicle.kt
VehicleType.kt
Expense.kt
ExpenseCategory.kt
Novelty.kt
NoveltyPriority.kt
IncomeAdjustmentType.kt
VehicleDocument.kt
VehicleDocumentType.kt
```

### Entidades principales

- `Vehicle`: representa un vehículo.
- `Expense`: representa un gasto.
- `Novelty`: representa una novedad.
- `VehicleDocument`: representa un documento o fecha importante.

### Tipos definidos

- `VehicleType`: `TAXI`, `PRIVATE`.
- `ExpenseCategory`: combustible, lavado, mantenimiento, repuestos, seguro, impuestos, multas y otros.
- `NoveltyPriority`: baja, media y alta.
- `IncomeAdjustmentType`: sin ingreso, medio ingreso y valor personalizado.
- `VehicleDocumentType`: SOAT, tecnicomecánica e impuestos.

---

## Datos de prueba creados

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/data/sample/
```

Archivos creados:

```text
SampleVehicles.kt
SampleVehicleDocuments.kt
```

### SampleVehicles.kt

Contiene cuatro vehículos iniciales de prueba:

- Dos taxis.
- Dos vehículos particulares.

Los taxis incluyen:

- Conductor actual.
- Ingreso fijo diario.

Los vehículos particulares no tienen ingreso fijo diario.

### SampleVehicleDocuments.kt

Contiene documentos de prueba para mostrar próximos vencimientos:

- SOAT.
- Revisión tecnicomecánica.
- Impuestos.

Se corrigieron las notas para evitar placas quemadas dentro del texto.

---

## Pantallas creadas

Ruta:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/presentation/screens/vehicles/
```

Archivos creados:

```text
VehicleListScreen.kt
VehicleDetailScreen.kt
```

---

## Pantalla lista de vehículos

Archivo:

```text
VehicleListScreen.kt
```

Estado actual:

- Muestra el título `Control Vehicular`.
- Muestra cantidad total de vehículos.
- Muestra chips de resumen:
    - cantidad de taxis.
    - cantidad de particulares.
- Muestra sección de próximos vencimientos.
- Muestra sección `Vehículos registrados`.
- Muestra tarjetas de vehículos.
- Las tarjetas son clicables.
- La pantalla tiene scroll vertical.
- Se puede acceder al detalle de taxis y particulares.

Componentes incluidos:

- `VehicleCard`
- `SummaryChip`
- `DocumentReminderCard`
- `VehicleTypeChip`
- `VehicleStatusChip`
- `InfoItem`

Funciones auxiliares:

- `getVehicleTypeLabel`
- `getDocumentTypeLabel`
- `formatCurrency`

---

## Pantalla detalle de vehículo

Archivo:

```text
VehicleDetailScreen.kt
```

Estado actual:

- Permite mostrar el detalle de un vehículo seleccionado.
- Tiene botón `Volver`.
- El botón permite regresar a la lista.
- Muestra información general del vehículo.
- Para taxis muestra información adicional:
    - conductor actual.
    - ingreso diario.

Pendiente actual:

- Mejorar visualmente la pantalla de detalle.
- Separar información en tarjetas.
- Preparar secciones futuras:
    - documentos.
    - gastos recientes.
    - novedades recientes.
    - acciones rápidas.

---

## Navegación actual

La navegación se implementó de forma sencilla en `MainActivity.kt`, usando estado local con Compose.

Flujo actual:

```text
Lista de vehículos
  └── tocar tarjeta
        └── Detalle del vehículo
              └── botón Volver
                    └── Lista de vehículos
```

Todavía no se está usando Navigation Compose.

La navegación actual es suficiente para esta etapa inicial y evita complejidad innecesaria.

---

## MainActivity

Archivo:

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/MainActivity.kt
```

Responsabilidades actuales:

- Cargar el tema de la app.
- Mostrar el `Scaffold`.
- Mostrar el botón flotante `+`.
- Controlar si se muestra:
    - `VehicleListScreen`.
    - `VehicleDetailScreen`.

El botón flotante `+` está preparado visualmente para agregar vehículos, pero todavía no abre formulario.

---

## Decisiones importantes

### Ingresos de taxis

Los ingresos de taxis no se registrarán manualmente todos los días.

Cada taxi tendrá un ingreso fijo diario.

Ese ingreso solo cambiará cuando exista una novedad que lo afecte, por ejemplo:

- Conductor enfermo.
- Día de descanso.
- Falla mecánica.
- Accidente.
- Trabajo parcial.

### Documentos y vencimientos

Todos los vehículos, taxis y particulares, tendrán fechas importantes:

- SOAT.
- Revisión tecnicomecánica.
- Impuestos.

La primera versión mostrará recordatorios internos dentro de la app.

### Firebase

Firebase no se usará en la primera versión.

Puede considerarse más adelante para:

- Sincronización en la nube.
- Autenticación.
- Copias de seguridad.
- Acceso desde varios dispositivos.

---

## Git y commits

Se inicializó Git en el proyecto.

También se ajustó el archivo `.gitignore` para excluir archivos innecesarios como:

- `.idea/`
- `.gradle/`
- `build/`
- `local.properties`
- archivos generados.
- archivos temporales.
- keystores.
- `google-services.json`.

Método de commits definido:

- Cambios pequeños.
- Cambios coherentes.
- Probar antes de hacer commit.
- Mensajes claros.

Ejemplos de commits realizados:

```text
feat: crear estructura inicial de VehicleControlApp
chore: actualizar gitignore del proyecto Android
feat: mejorar visualizacion de lista de vehiculos
style: mejorar diseño de tarjetas de vehiculos
docs: definir lineamientos iniciales de diseno ui
style: mejorar tarjetas de lista de vehiculos
feat: agregar resumen superior de vehiculos
feat: agregar seccion de proximos vencimientos
style: mejorar tarjetas de proximos vencimientos
feat: agregar boton flotante para vehiculos
style: agregar titulo a lista de vehiculos
refactor: preparar tarjetas para navegacion a detalle
feat: habilitar scroll y detalle para todos los vehiculos
```

---

## Estado actual del proyecto

La app ya permite:

- Ejecutarse en el emulador.
- Mostrar una lista de vehículos.
- Diferenciar taxis y particulares.
- Mostrar información especial para taxis.
- Mostrar próximos vencimientos.
- Hacer scroll en la lista.
- Entrar al detalle de cualquier vehículo.
- Volver desde el detalle a la lista.
- Tener botón flotante visual para futura acción de agregar vehículo.

---

## Próximos pasos recomendados

1. Mejorar visualmente `VehicleDetailScreen.kt`.
2. Agregar secciones futuras en detalle:
    - documentos.
    - gastos recientes.
    - novedades recientes.
    - acciones rápidas.
3. Crear formulario visual para vehículo.
4. Crear formulario visual para documento o fecha importante.
5. Crear formulario visual para gasto.
6. Crear formulario visual para novedad.
7. Agregar Room y SQLite cuando las pantallas principales estén claras.
8. Reemplazar datos de prueba por datos persistentes.