package com.jichenhao.petmanager_jetpackcompose.data.local

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
/*
* Result: 一个通用的数据容器类，用于封装登录操作的结果，
* 通常包含成功/失败的状态以及对应的数据或错误信息。
* 作为一个工具类，他服务于LoginRepository和LoginDataSource
* */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Loading(val loading: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }
}