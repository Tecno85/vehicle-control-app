package com.ivanmadrid.vehiclecontrolapp.data.local

import androidx.room.migration.Migration

/**
 * Registro único de migraciones Room.
 *
 * La base permanece en versión 1 hasta que cambie una entidad. Cuando exista una
 * versión nueva, la migración y su prueba deben agregarse aquí antes de publicar.
 */
object AppDatabaseMigrations {
    val all: Array<Migration> = emptyArray()
}
