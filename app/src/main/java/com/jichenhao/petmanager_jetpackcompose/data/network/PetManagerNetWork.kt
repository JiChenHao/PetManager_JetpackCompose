package com.jichenhao.petmanager_jetpackcompose.data.network

import android.util.Log
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.LoginResponse
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.io.IOException
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//统一的网络数据源访问入口，对所有网络请求的API进行封装
object PetManagerNetWork {
    private val userService = ServiceCreator.create(UserService::class.java)

    //通过用户输入的用户信息去数据库中查询相关数据,返回是否登陆成功的布尔值
    suspend fun login(userInfo: UserInfo) = userService.login(userInfo).await()


    suspend fun getUserList() = userService.getUserList().await()

    //调用代理对象placeService的方法发起搜索城市数据的请求
    //使用协程技术实现的Retrofit回调的简化写法，
    //并且将searchPlaces()函数声明成了挂起函数
    //这样，当外部调用这个函数的时候，Retrofit就会立即发起网络请求，同时当前的协程也会被阻塞
    //协程函数，
    private suspend fun <T> Call<T>.await(): T {
        Log.d("我的登录", "NetWork层suspend .await()函数执行")
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        Log.d("我的登录", "NetWork层suspend .await()函数执行，且网络连接正常")
                        //这里得到了返回的数据内容
                        val body = response.body()
                        if (body != null) continuation.resume(body)
                        else continuation.resumeWithException(
                            RuntimeException("Received successful response but response body is null")
                        )
                    } else {
                        continuation.resumeWithException(
                            IOException("Unexpected code $response.code. Server responded with error.")
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}