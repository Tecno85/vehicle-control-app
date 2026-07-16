# Control Vehicular

Aplicación Android para control vehicular de taxis y particulares, con gestión de gastos, novedades, documentos, vencimientos e historial.

## Descripción

Control Vehicular es una aplicación móvil desarrollada para administrar una pequeña flota de vehículos particulares y taxis.

La app permite registrar y consultar información importante de cada vehículo, como gastos, novedades operativas, documentos, fechas de vencimiento e historial. En el caso de los taxis, también permite manejar un ingreso diario esperado y calcular un resumen económico según gastos y novedades del día.

<img src="./docs/images/control-vehicular-v1.png" width="900" alt="Presentación de Control Vehicular 1.0">

## Capturas

| Lista de vehículos | Detalle del vehículo | Historial |
| --- | --- | --- |
| <img src="./docs/images/vehicle-list.png" width="220" alt="Lista de vehículos"> | <img src="./docs/images/vehicle-detail.png" width="220" alt="Detalle del vehículo"> | <img src="./docs/images/vehicle-history.png" width="220" alt="Historial del vehículo"> |

## Funcionalidades principales

- Registro, edición y eliminación de vehículos.
- Diferenciación entre taxis y vehículos particulares.
- Gestión de gastos por vehículo.
- Gestión de novedades por vehículo.
- Gestión de documentos y fechas de vencimiento.
- Próximos vencimientos con colores según urgencia.
- Detalle completo por vehículo.
- Historial agrupado por fecha.
- Resumen económico para taxis.
- Reportes básicos de la flota.
- Modo claro y modo oscuro.
- Persistencia local de datos.

## Tecnologías usadas actualmente

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Room.
- SQLite.
- Material Design 3.
- Gradle Kotlin DSL.

## Estado del proyecto

El proyecto se encuentra en la versión candidata `1.0.0-rc1`, preparada para validación en un dispositivo Android real.

Actualmente la app permite trabajar con datos locales usando Room, incluyendo creación, edición y eliminación de vehículos, gastos, novedades y documentos. También cuenta con una interfaz visual personalizada, iconos vectoriales, modo claro/oscuro, reportes e historial por vehículo.

Proyecto académico desarrollado como parte del proceso de formación ADSO.

No incluye todavía:

- Inicio de sesión.
- Firebase.
- Sincronización en la nube.
- Notificaciones del sistema Android.
- Exportación a PDF o Excel.

## Estructura general

```text
app/src/main/java/com/ivanmadrid/vehiclecontrolapp/
├── data/
├── domain/
├── presentation/
├── ui/
├── utils/
└── MainActivity.kt
```

Descripción breve:

- `domain`: modelos principales del negocio.
- `data`: base de datos local, entidades, DAOs, repositorios y datos iniciales.
- `presentation`: pantallas, componentes visuales y ViewModels.
- `ui`: tema visual de Jetpack Compose.
- `utils`: utilidades de fechas, validaciones y cálculos.

## Pantallas principales

- Lista de vehículos.
- Detalle del vehículo.
- Formulario de vehículo.
- Formulario de gasto.
- Formulario de novedad.
- Formulario de documento.
- Historial del vehículo.
- Reportes.

## Cómo ejecutar el proyecto

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Ejecutar la app en un emulador o dispositivo Android.

El proyecto usa Gradle Wrapper, por lo que no es necesario instalar Gradle manualmente.

### Instalar en un celular Android

El dispositivo debe usar Android 7.0 o superior.

1. Activar las opciones de desarrollador y la depuración USB en el celular.
2. Conectar el dispositivo y aceptar la autorización de depuración.
3. Comprobar la conexión con `adb devices`.
4. Instalar la compilación candidata:

```bash
./gradlew installDebug
```

También se puede generar el APK con `./gradlew assembleDebug` y copiar `app/build/outputs/apk/debug/app-debug.apk` al dispositivo. Android solicitará autorización para instalar aplicaciones desde esa fuente.

## Pruebas

Para ejecutar las pruebas unitarias:

```bash
./gradlew testDebugUnitTest
```

La base de datos también cuenta con pruebas instrumentadas en memoria. Para ejecutarlas se requiere un emulador o dispositivo conectado:

```bash
./gradlew connectedDebugAndroidTest
```

Para compilar la versión debug:

```bash
./gradlew assembleDebug
```

## Documentación

El proyecto incluye documentación en la carpeta `docs/`, donde se registran:

- Descripción general.
- Requisitos funcionales.
- Modelo de datos.
- Flujo de navegación.
- Decisiones técnicas.
- Registro de desarrollo.
- Diseño UI.
- Resumen de progreso.
- Estrategia de migraciones Room.
- Lista de validación de la entrega MVP 1.0.

## Próximos pasos posibles

- Mejorar reportes con selector de periodo.
- Agregar historial con filtros.
- Validar la versión candidata en un celular real.
- Evaluar notificaciones internas o del sistema.
- Evaluar sincronización en la nube más adelante.

## Autor

Ivan Dario Madrid Daza
