package com.jichenhao.petmanager_jetpackcompose

import android.os.Bundle
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // START======初始化ViewModel层，其中的数据源Repository均在Application中做唯一实例的初始化
        val loginViewModel = LoginViewModel(PetManagerApplication.loginRepository)


        // END=================ViewMode============================
        setContent {
            PetManager_JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavGraph(loginViewModel)
                }
            }
        }
    }
}


@Composable
fun MainScreen(onNavigateToLogin: () -> Unit) {
    Row {
        Text(text = "This is MainScreen")
        Button(onClick = {
            onNavigateToLogin()
        }) {
            Text(text = "Go to LoginScreen")
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