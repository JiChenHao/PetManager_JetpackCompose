package com.jichenhao.petmanager_jetpackcompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.ui.Main.MainScreen
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginScreen
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginViewModel

/*
* 页面跳转导航
*
* */
@Composable
fun MyNavGraph() {
    Log.d("MyNavGraph", "MyNavGraph被调用了")
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main_screen", route = "Parent") { // 设置起始页为登录页
        // 定义主屏幕页面路由及对应的Composable内容
        composable("main_screen") {
            // 这里就是设置“main_screen”作为导航目的地
            MainScreen()
        }
    }

    // 可以继续添加更多的页面...
}

