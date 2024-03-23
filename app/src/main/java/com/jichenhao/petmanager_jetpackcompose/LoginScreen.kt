package com.jichenhao.petmanager_jetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication.Companion.loginRepository
import com.jichenhao.petmanager_jetpackcompose.data.local.Result
import com.jichenhao.petmanager_jetpackcompose.ui.theme.PetManager_JetpackComposeTheme


//===================================START_UI==========================================

//关于导航：
//这里为了能够方便能使用Navgation组件，应该让每个可以通过Navgation跳转的视图都有一个类型为
//() -> Unit 的参数，NavHost 可以为该参数传递一个调用 NavController.navigate() 的 lambda
//该参数指定了在NavGraph导航图中这个页面可以跳转向哪个页面视图
// 关于持久化数据
// 登陆页面的用户名和密码是使用SharedPreference持久化
@Composable
fun LoginScreen(onNavigateToMain: () -> Unit) {
    val context = LocalContext.current as ComponentActivity
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val rememberMe_loaded = loginRepository.loadSavedisRememberMe(context = context)
    var rememberMe by rememberSaveable { mutableStateOf(rememberMe_loaded) }
    //从缓存中读取出来的内容
    var savedUserInfo by remember { mutableStateOf(loginRepository.loadSavedUser(context)) }
    var showPassword by remember { mutableStateOf(false) }

    //读取本地缓存，如果存在被记住的密码，就自动填充
    if (rememberMe_loaded) {
        email = savedUserInfo.email
        password = savedUserInfo.password
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email 输入框
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .focusable(true),
            maxLines = 1,
        )

        // 密码输入框
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            maxLines = 1,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.FavoriteBorder else Icons.Filled.Favorite,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusable(true)
        )

        // 记住密码选项
        Row {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.systemBarsPadding())
            Text("Remember Me")
        }

        // 登录按钮
        Button(
            onClick = {
                val result = loginRepository.login(email, password)
                if (result is Result.Success) {
                    //登陆成功就根据用户的选择去保存密码
                    loginRepository.saveCredentialsIfNeeded(context, email, password, rememberMe)
                    onNavigateToMain() // 假设 main 是 MainActivity 的导航目的地
                }
            }
        ) {
            Text("Log In")
        }
    }
}



//===================================END_UI==========================================

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    val navController = rememberNavController()
    PetManager_JetpackComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //LoginScreen(navController = navController)
        }
    }
}