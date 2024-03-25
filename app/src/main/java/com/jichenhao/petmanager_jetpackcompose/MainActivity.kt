package com.jichenhao.petmanager_jetpackcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginViewModel
import com.jichenhao.petmanager_jetpackcompose.ui.theme.PetManager_JetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint


//一旦进入主页面，就应该使用Android推荐的单Activity编程框架了
//使用ViewModel存放数据，使用Composable函数显示UI界面，使用Navigation实现UI界面之间的跳转
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // START======初始化ViewModel层，其中的数据源Repository均在Application中做唯一实例的初始化

        // END=================ViewMode============================
        Log.d("MainActivity", "MainActivity被调用了")
        setContent {
            PetManager_JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavGraph()
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
    PetManager_JetpackComposeTheme {
        Greeting("Android")
    }
}