package com.ho.elhawarycenter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ho.elhawarycenter.model.Case

@Database(entities = [Case::class], version = 1, exportSchema = false)
abstract class CaseDb : RoomDatabase() {
    abstract fun caseDao(): CaseDao

    companion object {
        @Volatile
        private var INSTANCE: CaseDb? = null


        fun getDatabase(context: Context): CaseDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaseDb::class.java,
                    "patient_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}