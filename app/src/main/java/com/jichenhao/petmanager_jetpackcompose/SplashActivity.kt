package com.jichenhao.petmanager_jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jichenhao.petmanager_jetpackcompose.ui.theme.PetManager_JetpackComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashContent(onSplashFinished = { startMainActivity(this@SplashActivity) })
        }


        // 延迟一段时间后启动主Activity
        lifecycleScope.launch {
            delay(2000) // SPLASH_DELAY_MILLIS是你想要显示Splash的时间
            if (loadSavedIfUserLoggedIn(this@SplashActivity)) {
                //如果已经登录，就读取已登录的用户email
                val email = loadSavedLoggedInUserEmail(this@SplashActivity)
                //设置登录状态&& 已登录用户信息
                PetManagerApplication.loginState = true
                if (email != null) {
                    PetManagerApplication.loggedInUser = email
                }
                //跳转到主页
                startMainActivity(this@SplashActivity)
            } else {
                //没检测到登录就跳转到登录页面
                startLoginActivity(this@SplashActivity)
            }
        }
    }
}

//读取用户登录状态用于保持APP登录状态
private fun loadSavedIfUserLoggedIn(context: Context): Boolean {
    val preferences = context.getSharedPreferences("login_state_prefs", Context.MODE_PRIVATE)
    return preferences.getBoolean("isUserLoggedIn", false)
}

//读取已经登录用户的用户名
private fun loadSavedLoggedInUserEmail(context: Context): String? {
    val preferences = context.getSharedPreferences("login_state_prefs", Context.MODE_PRIVATE)
    return preferences.getString("email", "NOT FOUND")
}

//打开主Activity
fun startMainActivity(context: ComponentActivity) {
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
    context.finish()
}

//打开LoginActivity
fun startLoginActivity(context: ComponentActivity) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
    context.finish()
}

@Composable
fun SplashContent(onSplashFinished: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // 在这里添加你的Splash Screen内容，例如：
        // Text("AppName", style = TextStyle(fontSize = 30.sp, color = Colors.White))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "吉晨豪",
                style = TextStyle(fontSize = 30.sp, color = Color.Black)
            )
            Text(
                text = "宠物时光管理",
                style = TextStyle(fontSize = 30.sp, color = Color.Black)
            )
        }
        // 如果需要，可以添加动画或其他过渡效果
        // ...
    }
    LaunchedEffect(Unit) {
        onSplashFinished()
    }
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    PetManager_JetpackComposeTheme {
        Greeting2("Android")
    }
}