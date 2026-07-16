# Migraciones Room

## Estado actual

- Base de datos: `AppDatabase`.
- Versión actual: `1`.
- Esquema exportado: `app/schemas/com.ivanmadrid.vehiclecontrolapp.data.local.AppDatabase/1.json`.
- Registro de migraciones: `AppDatabaseMigrations.kt`.
- No se usa `fallbackToDestructiveMigration`, porque una actualización no debe borrar datos del usuario.

## Cuándo crear una migración

Se requiere una migración cuando cambie la estructura persistida, por ejemplo:

- agregar o eliminar una columna;
- crear una tabla;
- cambiar índices o claves;
- modificar el tipo o nulabilidad de un campo.

Un cambio únicamente visual o de lógica que no modifique entidades Room no debe aumentar la versión.

## Procedimiento para la versión 2

1. Modificar las entidades necesarias.
2. Cambiar `version = 1` por `version = 2` en `AppDatabase.kt`.
3. Crear `MIGRATION_1_2` dentro de `AppDatabaseMigrations`.
4. Agregar la migración al arreglo `all`.
5. Compilar para generar el esquema `2.json`.
6. Comparar los esquemas 1 y 2.
7. Crear una prueba con `MigrationTestHelper` que parta del esquema 1, inserte datos y valide que sobrevivan a la migración.
8. Ejecutar pruebas unitarias, instrumentadas y una actualización real en emulador antes de publicar.

Ejemplo de estructura:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE vehicles ADD COLUMN notes TEXT")
    }
}

val all = arrayOf(MIGRATION_1_2)
```

El SQL del ejemplo solo ilustra el proceso; no debe copiarse si la versión 2 requiere otro cambio.

## Regla de publicación

No se publica una versión que cambie entidades sin:

- esquema nuevo exportado;
- migración registrada;
- prueba de migración correcta;
- verificación de que los datos existentes permanecen disponibles.
