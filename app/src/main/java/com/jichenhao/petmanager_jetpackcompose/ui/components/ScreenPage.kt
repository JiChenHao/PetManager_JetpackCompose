package com.jichenhao.petmanager_jetpackcompose.ui.components

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.jichenhao.petmanager_jetpackcompose.R

sealed class ScreenPage(
    val route: String,
    @StringRes val resId: Int = 0, // 如果没有文字标题，就不需要使用这个属性
    val iconSelect: Int,
    val iconUnselect: Int,
    var isShowText: Boolean = true
) {
    object Home : ScreenPage(
        route = "home",
        resId = R.string.str_main_title_home,
        iconSelect = R.drawable.ic_home_yellow,
        iconUnselect = R.drawable.ic_home_black
    )

    object Album : ScreenPage(
        route = "album",
        resId = R.string.str_main_title_recommend,
        iconSelect = R.drawable.ic_album_yellow,
        iconUnselect = R.drawable.ic_album_black
    )

    object Knowledge : ScreenPage(
        route = "knowledge",
        iconSelect = R.drawable.ic_knowledge_yellow,
        iconUnselect = R.drawable.ic_knowledge_black,
        isShowText = false
    )

    object Pet : ScreenPage(
        route = "pet",
        resId = R.string.str_main_title_find,
        iconSelect = R.drawable.ic_pet_yellow,
        iconUnselect = R.drawable.ic_pet_black
    )

    object User : ScreenPage(
        route = "user",
        resId = R.string.str_main_title_mine,
        iconSelect = R.drawable.ic_user_yellow,
        iconUnselect = R.drawable.ic_user_black
    )
}
