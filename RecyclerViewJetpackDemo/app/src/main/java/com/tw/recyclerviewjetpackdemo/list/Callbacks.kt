package com.tw.recyclerviewjetpackdemo.list

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.tw.recyclerviewjetpackdemo.ui.theme.RecyclerViewJetpackDemoTheme

class CallbackListActivity : AppCompatActivity() {

    private val callbackViewModel = CallbackViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewJetpackDemoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val dataItems = (0..100).map { CallbackListDataItem("Click me", it) }
                    MyCallbackList(
                        modifier = Modifier.fillMaxSize(),
                        callbackListDataItem = dataItems,
                        itemClickedCallback = { callbackViewModel.onListItemClicked(it) }
                    )
                }
            }
        }
    }
}

data class CallbackListDataItem(
    val text: String,
    val number: Int,
)

@Composable
fun MyCallbackList(
    modifier: Modifier = Modifier,
    callbackListDataItem: List<CallbackListDataItem>,
    itemClickedCallback: (callbackListDataItem: CallbackListDataItem) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(callbackListDataItem) { item ->
            CallbackListItem(
                callbackListDataItem = item,
                itemClickedCallback = itemClickedCallback
            )
        }
    }
}

@Composable
fun CallbackListItem(
    callbackListDataItem: CallbackListDataItem,
    itemClickedCallback: (callbackListDataItem: CallbackListDataItem) -> Unit,
) {
    Button(onClick = { itemClickedCallback(callbackListDataItem) }) {
        Text(text = callbackListDataItem.text)
    }
}

class CallbackViewModel {

    fun onListItemClicked(callbackListDataItem: CallbackListDataItem) {
        Log.v("Tiny Terry", "Item ${callbackListDataItem.number} was clicked")
    }
}