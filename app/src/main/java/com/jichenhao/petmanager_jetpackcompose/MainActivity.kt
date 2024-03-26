package com.jichenhao.petmanager_jetpackcompose

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.jichenhao.petmanager_jetpackcompose.ui.Main.MainScreen
import com.jichenhao.petmanager_jetpackcompose.ui.components.ScreenPage
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
                    MainScreen()
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