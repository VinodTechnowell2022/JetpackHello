package com.tw.recyclerviewjetpackdemo.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.navigation.compose.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tw.recyclerviewjetpackdemo.ui.theme.RecyclerViewJetpackDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MviActivity : AppCompatActivity() {

    // To inject via dagger, pass the Provider into the nav graph
    // and then run .get() on the provider to create an instance
    private val mviViewModel = MviViewModel(AnswerService())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewJetpackDemoTheme {
                MviApp(mviViewModel)
            }
        }
    }
}

@Composable
fun MviApp(
    mviViewModel: MviViewModel
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "question") {
        composable("question") {
            MviQuestionDestination(
                mviViewModel = mviViewModel,
                // You could pass the nav controller to further composables,
                // but I like keeping nav logic in a single spot by using the hoisting pattern
                // hoisting probably won't work as well in deep hierarchies,
                // in which case CompositionLocal might be more appropriate
                onConfirm = { navController.navigate("result") },
            )
        }
        composable("result") {
            MviResultDestination(
                mviViewModel,
            )
        }
    }
}

@Composable
fun MviQuestionDestination(
    mviViewModel: MviViewModel,
    onConfirm: () -> Unit
) {
    val textFieldState = remember { mutableStateOf(TextFieldValue()) }

    val viewState = mviViewModel.viewState.collectAsState()
    val onConfirmClicked = { mviViewModel.onAction(
        MviViewModel.UiAction.AnswerConfirmed(
            textFieldState.value.text
        )
    ) }

    // We only want the event stream to be attached once
    // even if there are multiple re-compositions
    LaunchedEffect("key") {
        mviViewModel.oneShotEvents
            .onEach {
                when (it) {
                    MviViewModel.OneShotEvent.NavigateToResults -> onConfirm()
                }
            }
            .collect()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "What do you call a mexican cheese?")
        TextField(
            value = textFieldState.value,
            onValueChange = { textFieldState.value = it }
        )
        if (viewState.value.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = onConfirmClicked) {
                Text(text = "Confirm")
            }
        }
    }
}

@Composable
fun MviResultDestination(
    mviViewModel: MviViewModel
) {
    val viewState = mviViewModel.viewState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = viewState.value.textToDisplay)
    }
}

class MviViewModel(
    private val answerService: AnswerService,
) {

    private val coroutineScope = MainScope()

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    // See https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
    // For why channel > SharedFlow/StateFlow in this case
    private val _oneShotEvents = Channel<OneShotEvent>(Channel.BUFFERED)
    val oneShotEvents = _oneShotEvents.receiveAsFlow()

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.AnswerConfirmed -> {
                coroutineScope.launch {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                    withContext(Dispatchers.IO) { answerService.save(uiAction.answer) }
                    val text = if (uiAction.answer == "Nacho cheese") {
                        "You've heard too many cheese jokes"
                    } else {
                        "Nacho cheese"
                    }
                    _viewState.value = _viewState.value.copy(textToDisplay = text)
                    _oneShotEvents.send(OneShotEvent.NavigateToResults)
                    _viewState.value = _viewState.value.copy(isLoading = false)
                }
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val textToDisplay: String = "",
    )

    sealed class OneShotEvent {
        object NavigateToResults : OneShotEvent()
    }

    sealed class UiAction {
        class AnswerConfirmed(val answer: String) : UiAction()
    }
}
