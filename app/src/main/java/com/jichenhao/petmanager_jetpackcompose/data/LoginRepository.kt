package com.jichenhao.petmanager_jetpackcompose.data

import android.content.Context
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.LoggedInUser
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.data.local.LoginDataSource
import com.jichenhao.petmanager_jetpackcompose.data.local.Result


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
/*
MVVM中的ViewModel层
* LoginRepository: 这是一个接口或抽象类，它聚合了多个数据源（可能包括LoginDataSource），
* 并提供统一的API给上层组件使用。
* 也即是书上的仓库层，下面决定数据源来自什么地方。
* 它处理数据获取逻辑，并决定何时从内存缓存、本地数据库还是远程服务器获取数据。
* 它负责处理登录和登出功能，并维护一个内存中的已登录用户缓存
* 为了能够在APP启动后的任意时刻获取登陆信息，我们应该在自己的Application类中初始化这个类的实例
* */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    /*
    * private set 是一个访问器修饰符，它表示 user 变量的 setter 方法（用于赋值）是私有的。
    * 这意味着外部类不能直接修改 user 的值，只能通过 LoginRepository 类内部的方法来改变。
    * */
    var user: LoggedInUser? = null
        private set

    /*
    * get() = user != null 定义了一个只读计算属性 isLoggedIn，当获取其值时，会返回 user 是否为非空的结果。
    * 因此，不需要额外的存储空间来保存登录状态，而是每次调用时实时计算。
    * */
    val isLoggedIn: Boolean
        get() = user != null
    /*
    * init 是一个特殊的方法，在创建类实例时自动调用。
    * 在这个特定情况下，init 方法的主要目的是初始化类成员变量 user，将其设置为 null。
    * 这确保了每次创建 LoginRepository 实例时，user 都会有一个初始值，即未登录状态
    * */

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    //登出功能
    fun logout() {
        user = null
        dataSource.logout()
        //登出并清除已登录数据的同时，应该退出MainActivity并回到LoginActivity
    }

    //登陆功能
    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login直接调用的LoginDataSource里面的login方法进行与数据源的判断，获取判断结果
        val result = dataSource.login(username, password)
        //成功就将已登录用户设置为成功返回的data中的用户
        if (result is Result.Success) {
            //setLoggedInUser(result.data)
            setLoggedInUser(result.data)
            //登陆状态是实时计算的，不需要手动设置，如果user = null就是没登陆，!=null就是已经登陆了。
        }
        return result
    }

    private fun setLoggedInUser(userInfo: LoggedInUser) {
        this.user = userInfo
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
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