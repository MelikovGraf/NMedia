package ru.netology.nmedia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database( // Какие сущности будут в таблице
    entities = [PostEntity::class],
    version = 1
)
abstract class AppDB : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        //Создание базы данны
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, AppDB::class.java, "app.db"
            ).allowMainThreadQueries()
                .build()
    }
}