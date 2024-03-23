package com.jichenhao.petmanager_jetpackcompose.data.local

import com.jichenhao.petmanager_jetpackcompose.data.dataObject.LoggedInUser
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
/*
* LoginDataSource: 通常作为数据层的一部分，
* 负责与后端服务、数据库或者其他持久化存储进行交互，实现登录请求的实际网络调用或本地数据查询。
* 在这里进行了登陆的用户名和密码与数据源的匹配
* */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            //判断用户名和密码是否与数据源匹配
            if (username == "jichenhao01@qq.com" && password == "123456") {
                //记录已登录的用户信息，并且在登陆期间一直保存
                val myUser = LoggedInUser("jichenhao01@qq.com")
                return Result.Success(myUser)
            } else {
                return Result.Error(IOException("Wrong userName or password!!"))
            }
            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}