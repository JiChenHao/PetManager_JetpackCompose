package com.jichenhao.petmanager_jetpackcompose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.jichenhao.petmanager_jetpackcompose.ui.components.ScreenPage

//底部导航栏，可以在任意界面引入，使用MyNavGraph所构建的导航图进行导航
@Composable
fun PetManagerBottomNavigationBar(
    selectedNum: Int,//用来更换图标
    navController: NavHostController
) {
    var hereSelectedNum by rememberSaveable {
        mutableIntStateOf(selectedNum)
    }

    //===START_定义跳转函数===
    fun onGotoUserScreen() {// 跳转到User
        navController.navigate(
            ScreenPage.User.route,
        )
    }

    fun onGotoHomeScreen() {// 跳转到Home
        navController.navigate(
            ScreenPage.Home.route,
        )
    }

    fun onGotoPetScreen() {// 跳转到Pet
        navController.navigate(
            ScreenPage.Pet.route,
        )
    }

    fun onGotoKnowledgeScreen() {// 跳转到Knowledge
        navController.navigate(
            ScreenPage.Knowledge.route,
        )
    }

    fun onGotoAlbumScreen() {// 跳转到Album
        navController.navigate(
            ScreenPage.Album.route,
        )
    }

    /*
    * 另外，需要注意的一点是，如果跳转的目标路由地址不存在时，
    * NavController会直接抛出IllegalArgumentException异常，导致应用崩溃，
    * 因此在执行navigate方法时我们应该进行异常捕获，并给出用户提示：
    * */
    //封装定义一个跳转+报错的函数，方便重复调用
    fun NavHostController.navigateWithCall(
        route: String,
        onNavigateFailed: ((IllegalArgumentException) -> Unit)?,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        try {
            this.navigate(route, builder)
        } catch (e: IllegalArgumentException) {
            onNavigateFailed?.invoke(e)
        }
    }
    //===END_定义跳转函数===

    //底部导航栏UI
    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        //更改图标并且跳转内容页
                        hereSelectedNum = 1
                        onGotoHomeScreen()
                    },
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = if (hereSelectedNum == 1) ScreenPage.Home.iconSelect else ScreenPage.Home.iconUnselect),
                        contentDescription = "Localized description"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 添加间隔以均匀分布
                IconButton(
                    onClick = { //更改图标并且跳转内容页
                        hereSelectedNum = 2
                        onGotoKnowledgeScreen()
                    },
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = if (hereSelectedNum == 2) ScreenPage.Knowledge.iconSelect else ScreenPage.Knowledge.iconUnselect),
                        contentDescription = "宠物知识",
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 添加间隔以均匀分布
                IconButton(
                    onClick = {
                        //更改图标并且跳转内容页
                        hereSelectedNum = 3
                        onGotoAlbumScreen()
                    },
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = if (hereSelectedNum == 3) ScreenPage.Album.iconSelect else ScreenPage.Album.iconUnselect),
                        contentDescription = "Localized description"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 添加间隔以均匀分布
                IconButton(
                    onClick = { //更改图标并且跳转内容页
                        hereSelectedNum = 4
                        onGotoPetScreen()
                    },
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = if (hereSelectedNum == 4) ScreenPage.Pet.iconSelect else ScreenPage.Pet.iconUnselect),
                        contentDescription = "Localized description",
                    )
                    Text(text = "宠物")
                }
                Spacer(modifier = Modifier.width(8.dp)) // 添加间隔以均匀分布
                IconButton(
                    onClick = { //更改图标并且跳转内容页
                        hereSelectedNum = 5
                        onGotoUserScreen()
                    },
                    modifier = Modifier
                        .size(60.dp)
                ) {
                    Icon(
                        painterResource(id = if (hereSelectedNum == 5) ScreenPage.User.iconSelect else ScreenPage.User.iconUnselect),
                        contentDescription = "Localized description",
                    )
                    Text(text = "我")
                }
            }
        },
        contentPadding = PaddingValues(horizontal = 16.dp),

        )
}