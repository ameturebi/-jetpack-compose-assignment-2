package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.TodoViewModel
import com.example.todo.ui.screens.TodoDetailScreen
import com.example.todo.ui.screens.TodoListScreen
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as TodoApplication
        
        setContent {
            ToDoTheme {
                TodoApp(app)
            }
        }
    }
}

@Composable
fun TodoApp(app: TodoApplication) {
    val navController = rememberNavController()
    val viewModel: TodoViewModel = viewModel { TodoViewModel(app.repository) }

    NavHost(navController = navController, startDestination = "todoList") {
        composable("todoList") {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    viewModel.selectTodo(todoId)
                    navController.navigate("todoDetail")
                }
            )
        }
        composable("todoDetail") {
            TodoDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}