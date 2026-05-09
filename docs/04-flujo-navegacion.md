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

Pantallas planeadas para más adelante:

1. Historial del vehículo.
2. Reportes.
3. Ajustes.

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
  ├── tocar tarjeta de vehículo
  │     └── Detalle del vehículo
  │           ├── Volver
  │           │     └── Lista de vehículos
  │           │
  │           ├── Registrar gasto
  │           │     └── Formulario de gasto
  │           │           ├── Volver
  │           │           └── Cancelar
  │           │                 └── Detalle del mismo vehículo
  │           │
  │           ├── Registrar novedad
  │           │     └── Formulario de novedad
  │           │           ├── Volver
  │           │           └── Cancelar
  │           │                 └── Detalle del mismo vehículo
  │           │
  │           └── Registrar documento
  │                 └── Formulario de documento
  │                       ├── Volver
  │                       └── Cancelar
  │                             └── Detalle del mismo vehículo
  │
  └── tocar botón flotante +
        └── Formulario de vehículo
              ├── Volver
              └── Cancelar
                    └── Lista de vehículos
```

---

## Botón flotante

El botón flotante `+` solo se muestra en la pantalla de lista de vehículos.

No aparece en:

- Detalle del vehículo.
- Formulario de gasto.
- Formulario de novedad.
- Formulario de documento.
- Formulario de vehículo.

Esto evita confusión porque el botón `+` solo aplica a crear un vehículo desde la lista.

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

Los formularios actuales son visuales y todavía no guardan datos reales.

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

Formulario de novedad:

- Fecha.
- Tipo de novedad.
- Prioridad.
- Descripción.
- Ajuste de ingreso solo para taxis.

Formulario de documento:

- Tipo de documento.
- Fecha de vencimiento.
- Notas.

---

## Criterio para migrar a Navigation Compose

La navegación actual puede mantenerse mientras las pantallas sean pocas y el flujo sea simple.

Conviene evaluar Navigation Compose cuando se implementen:

- Historial del vehículo.
- Reportes.
- Ajustes.
- Edición de registros existentes.
- Pantallas con parámetros más complejos.
