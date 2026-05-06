# Flujo de navegación

## Pantallas principales

La aplicación tendrá inicialmente las siguientes pantallas:

1. Inicio.
2. Lista de vehículos.
3. Detalle del vehículo.
4. Formulario de vehículo.
5. Registrar gasto.
6. Registrar novedad.
7. Registrar documento o fecha importante.
8. Historial del vehículo.

---

## Flujo principal

```text
Inicio
  └── Lista de vehículos
        ├── Detalle Taxi
        │     ├── Registrar gasto
        │     ├── Registrar novedad
        │     ├── Registrar documento
        │     └── Historial
        │
        └── Detalle Particular
              ├── Registrar gasto
              ├── Registrar novedad
              ├── Registrar documento
              └── Historial
```
---

## Pantalla de inicio

La pantalla de inicio mostrará un resumen general de la aplicación.

Información sugerida:

- Total de vehículos registrados.
- Próximos vencimientos.
- Alertas importantes.
- Acceso rápido a la lista de vehículos.

---

## Lista de vehículos

La pantalla de lista de vehículos mostrará los vehículos registrados.

Cada vehículo debe mostrar:

- Placa.
- Marca.
- Modelo.
- Tipo de vehículo.
- Estado.
- Próximo vencimiento importante.

---

## Detalle del vehículo

La pantalla de detalle mostrará la información completa de un vehículo seleccionado.

Para todos los vehículos mostrará:

- Placa.
- Marca.
- Modelo.
- Tipo.
- Estado.
- Gastos recientes.
- Novedades recientes.
- Documentos o fechas importantes.

Para taxis también mostrará:

- Conductor actual.
- Ingreso fijo diario.
- Ingreso calculado del día.
- Balance diario.

---

## Formulario de vehículo

Esta pantalla permitirá crear o editar un vehículo.

Campos iniciales:

- Placa.
- Marca.
- Modelo.
- Tipo de vehículo.
- Estado.
- Conductor actual.
- Ingreso fijo diario.

Notas:

- Conductor actual será opcional.
- Ingreso fijo diario solo aplicará para taxis.

---

## Registrar gasto

Esta pantalla permitirá registrar un gasto asociado a un vehículo.

Campos iniciales:

- Fecha.
- Categoría.
- Valor.
- Descripción.

---

## Registrar novedad

Esta pantalla permitirá registrar una novedad asociada a un vehículo.

Campos iniciales:

- Fecha.
- Tipo de novedad.
- Prioridad.
- Descripción.
- Afecta ingreso.

Si la novedad afecta el ingreso de un taxi, se mostrará también:

- Tipo de ajuste.
- Valor personalizado, si aplica.

---

## Registrar documento o fecha importante

Esta pantalla permitirá registrar fechas importantes del vehículo.

Campos iniciales:

- Tipo de documento o pago.
- Fecha de vencimiento.
- Notas.

Tipos iniciales:

- SOAT.
- Revisión tecnicomecánica.
- Impuestos.

---

Historial del vehículo

Esta pantalla mostrará los registros asociados a un vehículo.

Secciones iniciales:

- Gastos.
- Novedades.
- Documentos o fechas importantes.