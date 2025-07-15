package com.arvin.swapi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arvin.swapi.data.local.db.dao.PlanetDao
import com.arvin.swapi.data.local.db.entity.PlanetEntity

@Database(entities = [PlanetEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao

}