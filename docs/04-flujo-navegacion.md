# Flujo de navegación

## Estado actual

La aplicación usa una navegación sencilla controlada desde `MainActivity.kt` con estado local de Compose.

Todavía no se usa Navigation Compose. Esta decisión se mantiene por ahora para evitar complejidad mientras la app sigue en fase visual/prototipo funcional.

---

## Pantallas actuales

Pantallas conectadas actualmente:

1. Lista de vehículos.
2. Detalle del vehículo.
3. Formulario de vehículo.
4. Formulario de gasto.
5. Formulario de novedad.
6. Formulario de documento.
7. Reportes.

Pantallas planeadas para más adelante:

1. Historial del vehículo.
2. Ajustes.

---

## Enum de navegación

La navegación se controla con:

```kotlin
enum class AppScreen {
    VEHICLE_LIST,
    VEHICLE_DETAIL,
    VEHICLE_FORM,
    EXPENSE_FORM,
    NOVELTY_FORM,
    DOCUMENT_FORM,
    REPORTS,
}
```

También se usa:

```kotlin
selectedVehicle: Vehicle?
```

Este valor permite mantener el vehículo seleccionado cuando se navega desde el detalle hacia formularios asociados.

---

## Flujo principal actual

```text
Lista de vehículos
  ├── menú principal
  │     └── Reportes
  │           ├── Volver
  │           └── Back del sistema
  │                 └── Lista de vehículos
  │
  ├── tocar tarjeta de vehículo
  │     └── Detalle del vehículo
  │           ├── Volver
  │           ├── Back del sistema
  │           │     └── Lista de vehículos
  │           │
  │           ├── Editar vehículo
  │           │     └── Formulario de vehículo
  │           │           ├── Volver / Cancelar
  │           │           └── Back del sistema
  │           │                 └── Detalle del mismo vehículo
  │           │
  │           ├── Registrar gasto
  │           │     └── Formulario de gasto
  │           │           ├── Volver
  │           │           └── Cancelar
  │           │           └── Back del sistema
  │           │                 └── Detalle del mismo vehículo
  │           │
  │           ├── Registrar novedad
  │           │     └── Formulario de novedad
  │           │           ├── Volver
  │           │           └── Cancelar
  │           │           └── Back del sistema
  │           │                 └── Detalle del mismo vehículo
  │           │
  │           └── Registrar documento
  │                 └── Formulario de documento
  │                       ├── Volver
  │                       └── Cancelar
  │                       └── Back del sistema
  │                             └── Detalle del mismo vehículo
  │
  └── tocar botón flotante +
        └── Formulario de vehículo
              ├── Volver
              └── Cancelar
              └── Back del sistema
                    └── Lista de vehículos
```

---

## Botón flotante

El botón flotante `+` solo se muestra en la pantalla de lista de vehículos.

El botón tiene estilo circular y funciona como acción principal para agregar un vehículo.

No aparece en:

- Detalle del vehículo.
- Formulario de gasto.
- Formulario de novedad.
- Formulario de documento.
- Formulario de vehículo.

Esto evita confusión porque el botón `+` solo aplica a crear un vehículo desde la lista.

---

## Back nativo de Android

La app maneja el botón o gesto Back del sistema Android usando `BackHandler` en `MainActivity.kt`.

Comportamiento actual:

- En lista principal: Back permite salir de la app.
- En detalle de vehículo: Back vuelve a la lista.
- En reportes: Back vuelve a la lista.
- En agregar vehículo: Back vuelve a la lista.
- En editar vehículo: Back vuelve al detalle.
- En formularios de gasto, novedad y documento: Back vuelve al detalle del vehículo.

Este manejo mantiene la navegación simple por estado sin introducir Navigation Compose todavía.

---

## Detalle del vehículo

La pantalla de detalle funciona como centro de gestión de cada vehículo.

Para todos los vehículos muestra:

- Header con placa, marca y modelo.
- Tipo de vehículo.
- Estado.
- Información general.
- Documentos y vencimientos.
- Gastos recientes.
- Novedades recientes.
- Acciones rápidas.

Para taxis también muestra:

- Información del taxi.
- Conductor actual.
- Ingreso diario.
- Resumen económico estimado.

Los vehículos particulares no muestran secciones exclusivas de taxi.

---

## Formularios

Los formularios actuales guardan datos reales en Room y mantienen una presentación visual consistente.

Formulario de vehículo:

- Placa.
- Marca.
- Modelo.
- Tipo de vehículo.
- Estado.
- Datos de taxi si se selecciona `Taxi`.

Formulario de gasto:

- Fecha.
- Categoría.
- Valor.
- Descripción.
- Opciones de categoría con tarjetas visuales e iconos.

Formulario de novedad:

- Fecha.
- Tipo de novedad.
- Prioridad.
- Descripción.
- Ajuste de ingreso solo para taxis.
- Opciones visuales para prioridad.
- Tarjeta para marcar impacto en la operación del taxi.

Formulario de documento:

- Tipo de documento.
- Fecha de vencimiento.
- Notas.
- Opciones de tipo de documento con tarjetas visuales e iconos.

---

## Criterio para migrar a Navigation Compose

La navegación actual puede mantenerse mientras las pantallas sean pocas y el flujo sea simple.

Conviene evaluar Navigation Compose cuando se implementen:

- Historial del vehículo.
- Ajustes.
- Pantallas con parámetros más complejos.
