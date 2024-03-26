package com.jichenhao.petmanager_jetpackcompose

import android.app.Application
import com.jichenhao.petmanager_jetpackcompose.data.LoginRepository

//全局类，继承自Application，用于更加方便的在项目的任何位置获取全局Context，
//注意要更新注册的Application
class PetManagerApplication : Application() {
    //伴生实例类，用于获取全局Context，也可以在这里定义一些全局要用到的只读变量
    companion object {

        //@SuppressLint("StaticFieldLeak")
        //lateinit var context: Context
        //然而，请注意在 Jetpack Compose 中获取上下文的方式并不推荐这样做。在 Composable 函数中，
        //建议使用 LocalContext.current 来获取上下文，这可以确保在正确的 Composition 环境下获取到上下文。

        //登录状态
        var loginState = false

        //已登录的用户名
        var loggedInUser: String = ""

        // 创建并暴露LoginRepository的单例
        val loginRepository by lazy { LoginRepository() }
    }

    fun setloginState(state: Boolean) {
        loginState = state
    }

    fun setLoggedInUser(email: String) {
        loggedInUser = email
    }

    override fun onCreate() {
        super.onCreate()
        //context = applicationContext
    }
}