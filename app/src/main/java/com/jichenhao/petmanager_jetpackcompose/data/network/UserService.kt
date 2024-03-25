package com.jichenhao.petmanager_jetpackcompose.data.network

import com.jichenhao.petmanager_jetpackcompose.data.dataObject.GetUserListResponse
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//这个类负责向API发起调用请求并获得返回的JSON数据
interface UserService {
    //使用@GET注解，当调用这个方法的时候，Retrofit就会发起一条GET请求，去访问@GET注解中配置的地址
    @POST("login")
    //方法的返回值被声明成了Call<UserInfo>，这样Retrofit就会将服务器返回的JSON数据自动解析成LoginResponse对象
    fun login(@Body user: UserInfo): Call<LoginResponse>

    @GET("getUserList")
    fun getUserList(): Call<GetUserListResponse>
}