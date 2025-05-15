package com.example.todo.data.repository

import com.example.todo.data.local.TodoDao
import com.example.todo.data.model.Todo
import com.example.todo.data.remote.TodoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class TodoRepository(
    private val api: TodoApi,
    private val dao: TodoDao
) {
    fun getTodos(): Flow<List<Todo>> = flow {
        // Emit cached data first
        emitAll(dao.getAllTodos())

        try {
            // Fetch fresh data from API
            val response = api.getTodos()
            if (response.isSuccessful) {
                response.body()?.let { todos ->
                    // Update cache
                    dao.insertTodos(todos)
                }
            }
        } catch (e: IOException) {
            // Network error, using cached data
        } catch (e: HttpException) {
            // API error, using cached data
        }
    }

    suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }
}