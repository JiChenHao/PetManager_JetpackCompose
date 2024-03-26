package com.jichenhao.petmanager_jetpackcompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.ui.Main.AlbumScreen
import com.jichenhao.petmanager_jetpackcompose.ui.Main.HomeScreen
import com.jichenhao.petmanager_jetpackcompose.ui.Main.KnowledgeScreen
import com.jichenhao.petmanager_jetpackcompose.ui.Main.PetScreen
import com.jichenhao.petmanager_jetpackcompose.ui.Main.UserScreen
import com.jichenhao.petmanager_jetpackcompose.ui.components.ScreenPage

/*
* 导航图，负责底部导航的逻辑操作
*
* */
@Composable
fun MyNavGraph(
    navController: NavHostController
) {
    Log.d("MyNavGraph", "MyNavGraph被调用了")
    //导航控制中心
    /* NavHost
    NavHost是Compose Navigation的核心组件，它负责构建整个应用的导航图（navigation graph）。在这里，它接收两个主要参数：
    navController: 是一个NavHostController实例，它是导航图的控制器，用于发起屏幕间的跳转操作。
    startDestination: 指定导航图中的起始页面路由字符串，这里是ScreenPage.Home.route，即应用启动后首先显示的页面。
    * */
    NavHost(navController, startDestination = ScreenPage.Home.route) { // 设置起始页为登录页
        /*
        * composable
        在NavHost内部使用composable函数来声明每一个可导航到的屏幕页面。
        * 每个composable块接受一个路由字符串参数和一个lambda表达式，在该表达式内编写实际的UI布局代码。
        参数route对应于页面的唯一标识符，当调用navController.navigate(route)时，
        * Compose会找到与之匹配的composable并显示其中的内容。
        * */
        composable(ScreenPage.Home.route) {
            //当导航至ScreenPage.Home.route时，将会执行lambda表达式内的内容，
            // 也就是展示HomeScreen界面及其相关逻辑。
            HomeScreen()
        }
        composable(ScreenPage.User.route) {
            UserScreen()
        }
        composable(ScreenPage.Pet.route) {
            PetScreen()
        }
        composable(ScreenPage.Knowledge.route) {
            KnowledgeScreen()
        }
        composable(ScreenPage.Album.route) {
            AlbumScreen()
        }
    }

}

