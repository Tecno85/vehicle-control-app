# Diseño UI

## Objetivo visual

La aplicación debe sentirse limpia, moderna y fácil de usar.

La prioridad es que la información importante de cada vehículo se pueda leer rápido:

- Estado del vehículo.
- Tipo de vehículo.
- Próximos vencimientos.
- Gastos recientes.
- Novedades recientes.
- Acciones disponibles.

---

## Estilo general

La app usa un estilo:

- Claro.
- Ordenado.
- Moderno.
- Basado en tarjetas.
- Con chips para estados y categorías.
- Con botones visibles.
- Con buen espacio entre secciones.
- Con iconos vectoriales simples para reforzar jerarquía visual.
- Compatible con modo claro y modo oscuro.

La UI debe mantenerse simple mientras la app sigue en fase inicial. Se prefieren mejoras graduales antes que una personalización visual demasiado compleja.

---

## Colores

La interfaz usa una paleta sencilla:

- Azul para acciones principales o información general.
- Verde para estados activos o valores positivos.
- Naranja para taxis o alertas moderadas.
- Rojo para alertas urgentes.
- Morado para novedades.
- Fondos suaves para tarjetas internas y chips.

La app cuenta ahora con una paleta propia para modo claro y modo oscuro.

La paleta debe mantener:

- Buen contraste entre fondo, tarjetas y texto.
- Fondos suaves para chips, recordatorios y acciones.
- Colores consistentes por tipo de información.
- Legibilidad correcta en formularios y detalle.

La preferencia de tema se puede alternar desde la pantalla principal durante la sesión. Más adelante se puede guardar la preferencia del usuario.

---

## Iconos e imágenes

Se usan iconos vectoriales locales en `res/drawable`.

Uso actual:

- Taxi: ilustración vectorial amarilla.
- Particular: ilustración vectorial plateada.
- Información general.
- Información del taxi.
- Documentos.
- Gastos.
- Novedades.
- Acciones rápidas.
- Modo claro / modo oscuro.

Decisión actual:

- Preferir vectores locales antes que imágenes pesadas.
- Mantener los iconos simples, claros y coherentes con el mockup.
- No agregar fotos reales por vehículo todavía.

---

## Pantalla lista de vehículos

La lista de vehículos debe funcionar como vista rápida de la app.

Debe mostrar:

- Título `Control Vehicular`.
- Total de vehículos registrados.
- Chips de taxis y particulares.
- Próximos vencimientos.
- Tarjetas de vehículos.
- Botón flotante `+` para agregar vehículo.
- Botón para alternar modo claro/oscuro.

Los próximos vencimientos:

- Se ordenan por fecha más cercana.
- Muestran los días restantes.
- Muestran solo los primeros vencimientos en la lista principal.

Las tarjetas de vehículo deben mostrar:

- Avatar visual por tipo con icono vectorial.
- Placa.
- Marca y modelo.
- Tipo de vehículo.
- Estado.
- Para taxis: conductor e ingreso diario.

No se incluye búsqueda por ahora, porque el alcance inicial contempla pocos vehículos y la lista debe mantenerse directa.

---

## Pantalla detalle del vehículo

El detalle del vehículo es el centro de gestión de cada vehículo.

Debe mostrar:

- Botón `Volver`.
- Placa como título principal.
- Marca y modelo como subtítulo.
- Chip de tipo de vehículo.
- Chip de estado.
- Información general.
- Documentos y vencimientos.
- Gastos recientes.
- Novedades recientes.
- Acciones rápidas.

Para taxis también debe mostrar:

- Información del taxi.
- Conductor actual.
- Ingreso diario.
- Resumen económico estimado.

Los particulares no deben mostrar información exclusiva de taxi.

Las acciones rápidas deben mantener:

- Tres botones del mismo tamaño visual.
- Texto centrado.
- Icono superior.
- Colores consistentes por acción.

---

## Formularios

Los formularios deben ser simples, claros y consistentes.

Reglas:

- Usar header claro.
- Mostrar el vehículo asociado cuando aplique.
- Usar una tarjeta principal para los datos del formulario.
- Mantener scroll vertical.
- Usar botones `Guardar`, `Cancelar` y `Volver`.
- Mantener compatibilidad visual con modo claro y modo oscuro.

Controles guiados actuales:

- Tipo de vehículo: Taxi / Particular.
- Categoría de gasto.
- Prioridad de novedad.
- Tipo de ajuste de ingreso.
- Tipo de documento.

No se usan todavía:

- Selector de fecha.
- Mensajes de error por campo.

---

## Recordatorios y vencimientos

Los recordatorios se muestran dentro de la app.

La UI debe indicar claramente:

- Qué documento vence.
- A qué vehículo pertenece.
- Cuándo vence.
- Cuántos días faltan.

Estados visuales sugeridos:

- Vencimientos próximos: naranja.
- Vencimientos muy urgentes o vencidos: rojo.
- Vencimientos con más margen: verde o color neutro.

---

## Principio principal

La app debe ayudar a registrar y consultar información rápidamente.

Se prioriza una experiencia clara y práctica sobre una interfaz demasiado compleja.
