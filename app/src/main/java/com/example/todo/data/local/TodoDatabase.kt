package com.example.todo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todo.data.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Fetch initial data when database is created
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = INSTANCE
                            database?.let { db ->
                                try {
                                    val retrofit = retrofit2.Retrofit.Builder()
                                        .baseUrl("https://jsonplaceholder.typicode.com/")
                                        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                                        .build()
                                    val api = retrofit.create(com.example.todo.data.remote.TodoApi::class.java)
                                    val response = api.getTodos()
                                    if (response.isSuccessful) {
                                        response.body()?.let { todos ->
                                            db.todoDao().insertTodos(todos)
                                        }
                                    }
                                } catch (e: Exception) {
                                    // Handle error silently as this is initial data fetch
                                }
                            }
                        }
                    }
                }).build().also { database ->
                    INSTANCE = database
                }
                INSTANCE!!
        }
    }
}}