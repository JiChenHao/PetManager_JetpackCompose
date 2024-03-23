package com.jichenhao.petmanager_jetpackcompose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jichenhao.petmanager_jetpackcompose.data.local.LoginDataSource
import com.jichenhao.petmanager_jetpackcompose.data.LoginRepository

//全局类，继承自Application，用于更加方便的在项目的任何位置获取全局Context，
//注意要更新注册的Application
class PetManagerApplication : Application() {
    //伴生实例类，用于获取全局Context，也可以在这里定义一些全局要用到的只读变量
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // 创建并暴露LoginRepository的单例
        val loginRepository by lazy { LoginRepository(dataSource = LoginDataSource()) }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}