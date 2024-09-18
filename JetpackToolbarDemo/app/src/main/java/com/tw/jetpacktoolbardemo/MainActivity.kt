package com.tw.jetpacktoolbardemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.tw.jetpacktoolbardemo.ui.theme.JetpackToolbarDemoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackToolbarDemoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = Color(
                                    ContextCompat.getColor(applicationContext,
                                    R.color.teal_200))
                            ),
                            title = {
                                Text(text = "Jetpack Toolbar", fontSize = 14.sp, fontStyle = FontStyle.Italic)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    Toast.makeText(this@MainActivity, "Menu Clicked", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "menu")
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    Toast.makeText(this@MainActivity, "Notification Clicked", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                                }

                                IconButton(onClick = {
                                    Toast.makeText(this@MainActivity, "Search Clicked", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Filled.Search, contentDescription = "Search")
                                }
                            }
                        )
                    },
                    floatingActionButton = { FloatingActionButton(onClick = { }) {
                        IconButton(onClick = {
                            Toast.makeText(this@MainActivity, "FAB Clicked", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                    } }


                ) { innerPadding ->
                    Log.e(this.javaClass.simpleName, "onCreate innerPadding value is: $innerPadding", )
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(start = 20.dp, top = 108.dp, end = 20.dp, bottom = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackToolbarDemoTheme {
        Greeting("Android")
    }
}