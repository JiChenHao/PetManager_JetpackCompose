package com.jichenhao.petmanager_jetpackcompose.ui.Main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication.Companion.context
import com.jichenhao.petmanager_jetpackcompose.ui.login.LoginViewModel


//这是登录之后看到的主页
//看到这个页面的时候，全局变量loginRepository.user里面就已经保存了已经登录的用户的邮箱（唯一id）
@Composable
fun MainScreen(
    loginViewModel: LoginViewModel,
    onNavigateToLogin: () -> Unit
) {
    val loggedInUserEmail by remember {
        mutableStateOf(loginViewModel.loggedInUserEmail.value)
    }
    Column {
        Text(text = "This is MainScreen")
        if (loggedInUserEmail != null) {
            Text(text = "Welcome Mr.${loggedInUserEmail}")
        }
        Button(onClick = {
            val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
            with(preferences.edit()) {
                putBoolean("isUserLoggedIn", true)
                apply()
            }
            onNavigateToLogin()
        }) {
            Text(text = "Logout")
        }
    }
}