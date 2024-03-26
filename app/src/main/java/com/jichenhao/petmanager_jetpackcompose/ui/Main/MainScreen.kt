package com.jichenhao.petmanager_jetpackcompose.ui.Main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.jichenhao.petmanager_jetpackcompose.MyNavGraph
import com.jichenhao.petmanager_jetpackcompose.ui.components.ScreenPage
import com.jichenhao.petmanager_jetpackcompose.ui.navigation.PetManagerBottomNavigationBar

@Composable
fun MainScreen(
) {
    var currentRoute by rememberSaveable {
        mutableStateOf(ScreenPage.Home.route)
    }

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            PetManagerBottomNavigationBar(
                selectedNum = when (currentRoute) {
                    "home" -> 1
                    "album" -> 2
                    "knowledge" -> 3
                    "pet" -> 4
                    "user" -> 5
                    else -> 1 // 默认选中第一个
                },
                navController
            )
        },

        ) { innerPadding ->
        /*
        * 在Scaffold的lambda表达式参数 { innerPadding -> ... } 中，
        * innerPadding 参数被用来作为内容区域（Column）的 Modifier.padding() 参数，
        * 目的是确保内容区域与Scaffold的其它组成部分（如顶部栏和底部栏）之间有足够的间距，
        * 防止内容与这些UI元素重叠，从而保持良好的布局效果和视觉体验。
        总结来说，innerPadding 作用在于自动为Scaffold内部的内容区域添加适当的内边距，
        * 以适应Scaffold组件本身的布局特性。
        * */
        Column(modifier = Modifier.padding(innerPadding)) {
            MyNavGraph(navController)
        }

    }

}
