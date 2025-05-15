package com.example.todo.data.remote

import com.example.todo.data.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}