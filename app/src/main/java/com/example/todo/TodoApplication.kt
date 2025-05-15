package com.example.todo

import android.app.Application
import com.example.todo.data.local.TodoDatabase
import com.example.todo.data.remote.TodoApi
import com.example.todo.data.repository.TodoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoApplication : Application() {
    private val database by lazy { TodoDatabase.getDatabase(this) }
    private val api by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(TodoApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }
    val repository by lazy { TodoRepository(api, database.todoDao()) }
}