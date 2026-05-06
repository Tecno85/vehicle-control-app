# Modelo de datos

## Entidad principal: Vehicle

Representa un vehículo registrado en la aplicación.

Un vehículo puede ser:

- Taxi.
- Particular.

Campos iniciales:

- id
- plate
- brand
- model
- type
- status
- currentDriver
- dailyFixedIncome

Notas:

- `dailyFixedIncome` solo aplica para taxis.
- `currentDriver` puede aplicar principalmente para taxis.
- Los carros particulares no necesitan ingreso fijo diario.

---

## VehicleType

Representa el tipo de vehículo.

Valores iniciales:

- TAXI
- PRIVATE

---

## Expense

Representa un gasto asociado a un vehículo.

Campos iniciales:

- id
- vehicleId
- date
- category
- amount
- description

Categorías iniciales:

- FUEL
- WASH
- MAINTENANCE
- SPARE_PARTS
- INSURANCE
- TAXES
- FINES
- OTHER

---

## Novelty

Representa una novedad asociada a un vehículo.

Campos iniciales:

- id
- vehicleId
- date
- type
- description
- priority
- affectsIncome
- incomeAdjustmentType
- adjustedIncomeAmount

Notas:

- `affectsIncome` indica si la novedad afecta el ingreso del taxi.
- `incomeAdjustmentType` solo aplica cuando la novedad afecta el ingreso.
- `adjustedIncomeAmount` se usa cuando el ajuste es por valor personalizado.

---

## NoveltyPriority

Representa la prioridad de una novedad.

Valores iniciales:

- LOW
- MEDIUM
- HIGH

---

## IncomeAdjustmentType

Representa el tipo de ajuste aplicado al ingreso de un taxi.

Valores iniciales:

- NO_INCOME
- HALF_INCOME
- CUSTOM_AMOUNT

---

## VehicleDocument

Representa una fecha importante asociada a un vehículo.

Campos iniciales:

- id
- vehicleId
- type
- dueDate
- notes

---

## VehicleDocumentType

Representa el tipo de documento o pago importante del vehículo.

Valores iniciales:

- SOAT
- TECHNICAL_MECHANICAL_REVIEW
- TAXES

---

## Balance diario para taxis

Para calcular el balance diario de un taxi se usará la siguiente lógica:

```text
Balance diario = ingreso calculado del día - gastos del día
```

Si no existe novedad que afecte el ingreso:

```text
Ingreso calculado = ingreso fijo diario
```

Si existe una novedad que afecta el ingreso:

```text
Ingreso calculado = ingreso ajustado según la novedad
```