package com.jichenhao.petmanager_jetpackcompose.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.MainActivity
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.ui.theme.PetManager_JetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

//===================================START_UI==========================================

//关于导航：
//这里为了能够方便能使用Navigation组件，应该让每个可以通过Navigation跳转的视图都有一个类型为
//() -> Unit 的参数，NavHost 可以为该参数传递一个调用 NavController.navigate() 的 lambda
//该参数指定了在NavGraph导航图中这个页面可以跳转向哪个页面视图
// 关于持久化数据
// 登陆页面的用户名和密码是使用SharedPreference持久化
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
) {
    Log.d("LoginScreen", "LoginScreen被调用了")
    /*
    * 但是如果是与Navigation关联的类，一般建议将ViewModel的依初始化放到NavGraph中
    * */

    //只要处于Login页面，就一定是
    //// Application中的登录状态为false && login_state_prefs中的登录状态为false
    //所以不必读取login_state_prefs中的数据，只需要登录成功的时候写入即可
    val context = LocalContext.current as ComponentActivity
    //输入框的变量
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    //从login_prefs中读取的上次是否选择了记住密码的选项
    val rememberMe_loaded = loadSavedisRememberMe(context = context)
    //是否记住密码的checkBox的变量
    var rememberMe by rememberSaveable { mutableStateOf(rememberMe_loaded) }
    //从缓存中读取出来的内容
    var savedUserInfo by remember { mutableStateOf(loadSavedUser(context)) }
    //是否显示密码
    var showPassword by remember { mutableStateOf(false) }

    //读取本地缓存，如果存在被记住的密码，就自动填充
    if (rememberMe_loaded) {
        email = savedUserInfo.email
        password = savedUserInfo.password
    }


    // ====START监听
    //监听ViewModel中loginResult的变化，一旦变为true，说明登录成功，就进行页面跳转动作
    val loginResult by loginViewModel.loginResult.collectAsState(initial = false)
    LaunchedEffect(key1 = loginResult) { // key1用于监听loginResult的变化
        if (loginResult) {
            //登陆成功就根据用户的选择去保存密码
            saveCredentialsIfNeeded(context, email, password, rememberMe)
            //同时更新全局的登录状态
            PetManagerApplication.loginState = true
            PetManagerApplication.loggedInUser = email
            //跳转到主Activity
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            context.finish()
        }
    }
    // ====END监听


    // ====START监听
    //监听ViewModel中showDialog的变化，一旦变为true，说明密码错误，提醒Dialog
    val showDialog by loginViewModel.showDialog.collectAsState(initial = false)

    //
    var screenShowDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = showDialog) { // key1用于监听loginResult的变化
        if (showDialog) {
            // 显示Dialog
            screenShowDialog = true
        }
    }
    // ====END监听

    when {
        screenShowDialog -> {
            AlertDialog(
                onDismissRequest = {
                    screenShowDialog = false
                    loginViewModel.unShowDialog()
                }, // 关闭对话框
                title = { Text("错误") },
                text = { Text("密码错误，请检查您的密码。") },
                confirmButton = {
                    TextButton(onClick = {
                        screenShowDialog = false
                        loginViewModel.unShowDialog()
                    }) {
                        Text("确定")
                    }
                }
            )
        }

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
                        imageVector = if (showPassword) Icons.Default.Lock else Icons.Filled.Lock,
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
                Log.d("我的登录", "LoginButtonDown")
                loginViewModel.login(email, password)
                //订阅ViewModel中的loginResult，一旦登陆成功这边就可以处理UI界面
            }
        ) {
            Text("Log In")
        }
    }
}

/*====================应用有context参数的逻辑应该位于界面中====================*/

// 加载保存的凭证
private fun loadSavedUser(context: Context): UserInfo {
    val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val email = preferences.getString("email", "")
    val password = preferences.getString("password", "")
    if (email != null && password != null) {
        val userInfo_all = UserInfo(email, password)
        return userInfo_all
    } else {
        return UserInfo("notFound", "notFound")
    }
}

private fun loadSavedisRememberMe(context: Context): Boolean {
    val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    return preferences.getBoolean("rememberMe", false)
}


// 根据用户的选择，选择是否进行保存密码
private fun saveCredentialsIfNeeded(
    context: Context,
    email: String,
    password: String,
    rememberMe: Boolean
) {
    val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    if (rememberMe) {
        with(preferences.edit()) {
            putString("email", email)
            putString("password", password)
            putBoolean("rememberMe", rememberMe)//将是否记住我也写进缓存
            apply()
        }
    } else {
        //清空缓存内容
        // 如果不勾选“记住密码”，则清除之前存储的信息
        with(preferences.edit()) {
            remove("email")
            remove("password")
            putBoolean("rememberMe", false)//将是否记住我也写进缓存
            apply()
        }
    }
    //只要登陆成功就记住登录状态
    val preferences_state = context.getSharedPreferences("login_state_prefs", Context.MODE_PRIVATE)
    with(preferences_state.edit()) {
        putString("email", email)
        putBoolean("isUserLoggedIn", true)//记住登陆状态
        apply()
    }

}
//默认一次登录之后，如果不主动退出就不会退出
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