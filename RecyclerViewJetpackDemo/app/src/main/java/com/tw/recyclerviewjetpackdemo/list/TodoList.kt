package com.tw.recyclerviewjetpackdemo.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.activity.compose.setContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.tw.recyclerviewjetpackdemo.ui.theme.RecyclerViewJetpackDemoTheme
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class TodoActivity : AppCompatActivity() {

    private val todoViewModel = TodoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewJetpackDemoTheme {
                TodoScreen(todoViewModel = todoViewModel)
            }
        }
    }
}

@Composable
fun TodoScreen(
    todoViewModel: TodoViewModel
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val viewState = todoViewModel.viewStateStream().collectAsState()
        TodoList(
            modifier = Modifier.fillMaxSize(),
            todos = viewState.value.todoList,
            onTodoChecked = { todo, isChecked ->
                todoViewModel.onAction(TodoViewModel.UiAction.TodoCompleted(todo, isChecked))
            }
        )
    }
}

@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    todos: List<Todo>,
    onTodoChecked: (todo: Todo, isChecked: Boolean) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todos) { todo ->
            TodoItem(
                modifier = Modifier.fillMaxWidth(),
                todo = todo,
                onTodoChecked = onTodoChecked
            )
        }
    }
}

@Composable
fun TodoItem(
    modifier: Modifier,
    todo: Todo,
    onTodoChecked: (todo: Todo, isChecked: Boolean) -> Unit,
) {
    Card(modifier = modifier, ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = todo.title)
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = todo.complete,
                onCheckedChange = { isChecked -> onTodoChecked(todo, isChecked) }
            )
        }
    }
}

class TodoViewModel {

    private val todoRepository = TodoRepository()
    private val viewStatePublisher: MutableStateFlow<ViewState> = MutableStateFlow(initViewState())
    private val lastViewState: ViewState
        get() = viewStatePublisher.value

    private fun initViewState(): ViewState {
        val todos = todoRepository.fetchTodos()
        return ViewState(todoList = todos)
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.TodoCompleted -> {
                // Alternatively, filter out the item
//                val newList = lastViewState.todoList.filter { it.id != uiAction.todo.id }
                val newList = lastViewState.todoList.map {
                    if (it.id == uiAction.todo.id) {
                        it.copy(complete = uiAction.isCompleted)
                    } else {
                        it
                    }
                }
                emit(lastViewState.copy(todoList = newList))
            }
        }
    }

    data class ViewState(
        val todoList: List<Todo>
    )

    sealed class UiAction {
        class TodoCompleted(val todo: Todo, val isCompleted: Boolean) : UiAction()
    }

    private fun emit(viewState: ViewState) {
        viewStatePublisher.value = viewState
    }

    fun viewStateStream(): StateFlow<ViewState> {
        return viewStatePublisher.asStateFlow()
    }
}

class TodoRepository {

    fun fetchTodos(): List<Todo> {
        return (0..100).map {
            Todo(
                id = it,
                title = "Item - $it",
                complete = Random.nextInt(0, 3) == 0,
            )
        }
    }
}

data class Todo(
    val id: Int,
    val title: String,
    val complete: Boolean,
)
