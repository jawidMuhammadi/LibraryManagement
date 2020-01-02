package com.example.library.data

import android.app.Application
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Book::class, Student::class], exportSchema = false, version = 2)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Application): AppDataBase {
            synchronized(this) {
                return INSTANCE ?: createDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun createDatabase(context: Application): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, "library_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //yet do nothing
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}