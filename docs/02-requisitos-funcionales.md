# Requisitos funcionales

## RF-01: Visualizar vehículos

La aplicación debe permitir visualizar inicialmente cuatro vehículos:

- Dos taxis.
- Dos carros particulares.

Cada vehículo debe mostrar información básica como:

- Placa.
- Marca.
- Modelo.
- Tipo de vehículo.
- Estado.

---

## RF-02: Diferenciar tipo de vehículo

La aplicación debe permitir diferenciar los vehículos por tipo:

- Taxi.
- Particular.

Esta diferencia es importante porque los taxis tendrán funciones adicionales relacionadas con ingresos diarios y balance.

---

## RF-03: Configurar ingreso fijo diario para taxis

La aplicación debe permitir configurar un ingreso fijo diario para cada taxi.

Este valor será usado para calcular el ingreso esperado del día.

Los carros particulares no tendrán ingreso fijo diario.

---

## RF-04: Registrar gastos por vehículo

La aplicación debe permitir registrar gastos asociados a cada vehículo.

Cada gasto debe incluir:

- Vehículo.
- Fecha.
- Categoría.
- Valor.
- Descripción.

Categorías iniciales:

- Combustible.
- Lavado.
- Mantenimiento.
- Repuestos.
- Seguro.
- Impuestos.
- Multas.
- Otros.

---

## RF-05: Registrar novedades por vehículo

La aplicación debe permitir registrar novedades asociadas a cada vehículo.

Cada novedad debe incluir:

- Vehículo.
- Fecha.
- Tipo de novedad.
- Descripción.
- Prioridad.

---

## RF-06: Ajustar ingreso de taxis por novedad

Cuando una novedad afecte el trabajo de un taxi, la aplicación debe permitir ajustar el ingreso del día.

Tipos de ajuste iniciales:

- Sin ingreso.
- Medio ingreso.
- Valor personalizado.

Ejemplos:

- Conductor enfermo.
- Día de descanso.
- Falla mecánica.
- Accidente.
- Trabajo parcial.

---

## RF-07: Registrar fechas importantes del vehículo

La aplicación debe permitir registrar fechas importantes para cada vehículo:

- Vencimiento del SOAT.
- Vencimiento de la revisión tecnicomecánica.
- Fecha de pago o vencimiento de impuestos.

Estas fechas aplican tanto para taxis como para carros particulares.

---

## RF-08: Mostrar recordatorios internos

La aplicación debe mostrar recordatorios internos cuando una fecha importante esté próxima a vencer.

Ejemplos:

- SOAT vence en 15 días.
- Revisión tecnicomecánica vence en 7 días.
- Impuesto pendiente de pago.

En la primera versión, estos recordatorios se mostrarán dentro de la aplicación, no como notificaciones del sistema Android.

---

## RF-09: Calcular balance diario de taxis

La aplicación debe calcular el balance diario de cada taxi.

La lógica será:

```text
Balance diario = ingreso calculado del día - gastos del día
```

Si no existe novedad que afecte el ingreso:

```text
Ingreso calculado = ingreso fijo diario
```

Si existe novedad:

```text
Ingreso calculado = ingreso ajustado según la novedad
```
---

## RF-10: Consultar resumen por vehículo

La aplicación debe permitir consultar un resumen básico por vehículo.

Para taxis:

- Ingreso fijo diario.
- Ingreso calculado del día.
- Gastos del día.
- Balance diario.
- Próximos vencimientos.
- Últimas novedades.

Para carros particulares:

- Gastos registrados.
- Próximos vencimientos.
- Últimas novedades.