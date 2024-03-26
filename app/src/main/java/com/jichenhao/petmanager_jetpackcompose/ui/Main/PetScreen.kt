package com.jichenhao.petmanager_jetpackcompose.ui.Main

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.jichenhao.petmanager_jetpackcompose.LoginActivity
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication

@Composable
fun PetScreen(

) {
    Log.d("PetScreen", "PetScreen被调用了")
    val context = LocalContext.current as ComponentActivity
    val loggedInUserEmail = PetManagerApplication.loggedInUser


    Column(
        //本列垂直居中
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is PetScreen",
        )
        Text(text = "Welcome Mr.${loggedInUserEmail}")
        Button(onClick = {
            // 如果选择logout，就要清除内存和全局的登录状态
            val preferences =
                context.getSharedPreferences("login_state_prefs", Context.MODE_PRIVATE)
            with(preferences.edit()) {
                remove("email")
                putBoolean("isUserLoggedIn", false)//更改登陆状态
                apply()
            }
            //更新全局的登录状态
            PetManagerApplication.loginState = false
            PetManagerApplication.loggedInUser = "NOT LOGIN YET"
            //跳转到主Activity
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            context.finish()
        }) {
            Text(text = "Logout")
        }

    }
}