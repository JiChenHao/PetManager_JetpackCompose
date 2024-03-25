package com.jichenhao.petmanager_jetpackcompose.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.liveData
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.data.network.PetManagerNetWork
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Singleton

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
/*
MVVM中的仓库层
* LoginRepository: 这是一个接口或抽象类，它聚合了多个数据源（本地的和网络的（使用接口连接）），
* 并提供统一的API给上层组件（ViewModel）使用。
* 也即是书上的仓库层，下面决定数据源来自什么地方。
* 它处理数据获取逻辑，并决定何时从内存缓存、本地数据库还是远程服务器获取数据。
* 它仅仅负责获取数据，至于获取数据之后的数据对比、数据处理等等，由其上层ViewModel层负责实现
* 为了能够在APP启动后的任意时刻获取登陆信息，我们应该在自己的Application类中初始化这个类的实例
* */
@Singleton
class LoginRepository() {


    //为了将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象
    //这里的LiveData()函数有一个特性：
    //它可以自动构建并且返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，
    //这样我们就可以在LiveData()的代码块中调用任意的挂起函数了
    //从网络中获取到我们需要的数据
    suspend fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        //这里将liveData的线程参数类型指定成了Dispatchers.IO，这样代码块中所有的代码就运行在子线程中了
        val userList = PetManagerNetWork.getUserList().data
        //=====TEST
        Log.d("我的登录", userList.toString())
        //=====END_TEST


        //因为Android是不允许主线程中进行网络请求的
        Log.d("我的登录", "Repository层login执行")
        val userToInternet = UserInfo(email, password)
        val result = try {
            //获取网络请求的结果
            Log.d("我的登录", "开始获取网络请求")
            val loginResult = PetManagerNetWork.login(userToInternet)
            Log.d("我的登录", "网络请求返回的message是${loginResult.message}")
            if (loginResult.success) {
                val loginResultBoolean = loginResult.data//布尔值
                com.jichenhao.petmanager_jetpackcompose.data.local.Result.Success(loginResultBoolean)
            } else {
                com.jichenhao.petmanager_jetpackcompose.data.local.Result.Error(
                    IOException("Error NetError ")
                )
            }
        } catch (e: Exception) {
            com.jichenhao.petmanager_jetpackcompose.data.local.Result.Error(
                IOException(
                    "Error logging in",
                    e
                )
            )
        }
        //emit实际上是类似于调用LiveData的setValue方法通知数据变化
        emit(result)
    }


    // 加载保存的凭证
    fun loadSavedUser(context: Context): UserInfo {
        val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        val email = preferences.getString("email", "")
        val password = preferences.getString("password", "")
        if (email != null && password != null) {
            val userInfo = UserInfo(email = email, password = password)
            return userInfo
        } else {
            return UserInfo("notFound", "notFound")
        }
    }

    fun loadSavedisRememberMe(context: Context): Boolean {
        val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        return preferences.getBoolean("rememberMe", false)
    }

    // 根据用户的选择，选择是否进行保存密码
    fun saveCredentialsIfNeeded(
        context: Context,
        email: String,
        password: String,
        rememberMe: Boolean
    ) {
        val preferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        if (rememberMe) {
            with(preferences.edit()) {
                putString("email", email)
                putString("password", password)
                putBoolean("rememberMe", rememberMe)//将是否记住我也写进缓存
                apply()
            }
        } else {
            //清空缓存内容
            // 如果不勾选“记住密码”，则清除之前存储的信息
            with(preferences.edit()) {
                remove("email")
                remove("password")
                putBoolean("rememberMe", false)//将是否记住我也写进缓存
                apply()
            }
        }
    }
}