package ru.netology.NMedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AppDB private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                instance ?: AppDB(
                    buildDatabase(context, arrayOf(PostsTable.DDL))
                ).also {
                    instance = it
                }
            }
        }
    }

    private fun buildDatabase(context: Context, DDLs: Array<String>) = DBHelper(
        context, 1, "app db", DDLs,
    ).writableDatabase
}