package com.jichenhao.petmanager_jetpackcompose.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//单例类，Retrofit构建器
object ServiceCreator {
    //这里注意，android会将自己的地址默认为localhost（127.0.0.1）
    //如果要访问电脑本地服务器，需要将ip地址设置为10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:8080/api/"
    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}