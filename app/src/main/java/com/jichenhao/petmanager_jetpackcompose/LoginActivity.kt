package com.jichenhao.petmanager_jetpackcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginScreen
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginViewModel
import com.jichenhao.petmanager_jetpackcompose.ui.theme.PetManager_JetpackComposeTheme


// 只要进到这个Activity中的时候，一定是：
// Application中的登录状态为false && login_state_prefs中的登录状态为false
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = LoginViewModel(PetManagerApplication.loginRepository)
        Log.d("LoginActivity", "LoginActivity被调用了")
        setContent {
            PetManager_JetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(loginViewModel = loginViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    PetManager_JetpackComposeTheme {
        Greeting3("Android")
    }
}