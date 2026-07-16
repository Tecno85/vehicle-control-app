# Entrega MVP 1.0

## Versión candidata

- Nombre de versión: `1.0.0-rc1`.
- Código de versión: `1`.
- Android mínimo: Android 7.0, API 24.
- Persistencia: Room local, esquema 1.
- Estado: candidata para validación en dispositivo físico.

## Alcance incluido

- Gestión completa de vehículos.
- Gestión de gastos, novedades y documentos.
- Ajuste de ingreso diario para taxis.
- Vencimientos y recordatorios internos.
- Historial por vehículo.
- Reportes básicos.
- Modo claro y oscuro.
- Mensajes de validación generales y por campo.
- Manejo visible de errores de guardado.

## Verificaciones automatizadas

- Pruebas unitarias de fechas, validaciones, balance y mappers.
- Pruebas unitarias de repositorios locales.
- Pruebas instrumentadas CRUD con Room en memoria.
- Prueba de eliminación transaccional de datos relacionados.
- Compilación del APK debug y del APK de pruebas.

## Validación pendiente en celular

Antes de crear la etiqueta final `v1.0.0`, comprobar en un dispositivo físico:

- instalación desde cero;
- apertura y carga de los cuatro vehículos iniciales;
- creación, edición y eliminación en los cuatro módulos;
- persistencia después de cerrar y volver a abrir la app;
- teclado numérico y desplazamiento en formularios;
- modo claro y oscuro;
- legibilidad con el tamaño de fuente habitual del dispositivo;
- comportamiento del gesto o botón Back;
- ausencia de cierres inesperados durante una sesión normal.

## Criterio de publicación

Si la validación física no encuentra defectos bloqueantes:

1. cambiar `versionName` de `1.0.0-rc1` a `1.0.0`;
2. ejecutar todas las pruebas y compilaciones;
3. crear el commit de publicación;
4. crear la etiqueta Git `v1.0.0`;
5. publicar el APK como archivo de una GitHub Release.

No se debe publicar como versión final si existe pérdida de datos, un formulario que no permite completar una operación principal o un cierre inesperado reproducible.
