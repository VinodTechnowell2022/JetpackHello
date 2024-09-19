package com.tw.recyclerviewjetpackdemo.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.tw.recyclerviewjetpackdemo.ui.theme.RecyclerViewJetpackDemoTheme

class SimpleListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewJetpackDemoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val dataItems = (0..100).map { SimpleListDataItem("How easy was that!") }
                    MySimpleList(
                        modifier = Modifier.fillMaxSize(),
                        dataItems,
                    )
                }
            }
        }
    }
}

data class SimpleListDataItem(
    val text: String
)

@Composable
fun MySimpleList(
    modifier: Modifier = Modifier,
    simpleListDataItems: List<SimpleListDataItem>
) {
    LazyColumn(modifier = modifier) {
        items(simpleListDataItems) { data ->
            MySimpleListItem(simpleListDataItem = data)
        }
    }
}

@Composable
fun MySimpleListItem(simpleListDataItem: SimpleListDataItem) {
    Text(text = simpleListDataItem.text)
}
