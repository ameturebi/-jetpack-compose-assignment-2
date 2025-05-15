package com.example.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Todo
import com.example.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo: StateFlow<Todo?> = _selectedTodo

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            repository.getTodos()
                .catch { error ->
                    _uiState.value = TodoUiState.Error(error.message ?: "Unknown error occurred")
                }
                .collect { todos ->
                    _uiState.value = if (todos.isEmpty()) {
                        TodoUiState.Empty
                    } else {
                        TodoUiState.Success(todos)
                    }
                }
        }
    }

    fun selectTodo(id: Int) {
        viewModelScope.launch {
            repository.getTodoById(id)?.let { todo ->
                _selectedTodo.value = todo
            }
        }
    }

    fun clearSelectedTodo() {
        _selectedTodo.value = null
    }
}

sealed class TodoUiState {
    data object Loading : TodoUiState()
    data object Empty : TodoUiState()
    data class Success(val todos: List<Todo>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
}