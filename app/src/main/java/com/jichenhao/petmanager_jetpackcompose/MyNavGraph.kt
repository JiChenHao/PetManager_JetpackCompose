package com.jichenhao.petmanager_jetpackcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginScreen
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginViewModel

/*
* 页面跳转导航
*
* */
@Composable
fun MyNavGraph(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login_screen", route = "Parent") { // 设置起始页为登录页
        // 定义登录页面路由及对应的Composable内容
        composable("login_screen") {
            //规定LoginScreen可以跳转向主页面
            LoginScreen(loginViewModel,onNavigateToMain = { navController.navigate("main_Screen") })
        }

        // 定义主屏幕页面路由及对应的Composable内容
        composable("main_screen") { // 这里就是设置“main_screen”作为导航目的地
            MainScreen { loginViewModel; navController.navigate("login_screen") }
        }

        // 可以继续添加更多的页面...
    }
}

