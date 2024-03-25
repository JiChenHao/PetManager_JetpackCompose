package com.jichenhao.petmanager_jetpackcompose.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.jichenhao.petmanager_jetpackcompose.PetManagerApplication
import com.jichenhao.petmanager_jetpackcompose.data.LoginRepository
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.data.local.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/*
* LoginViewModel: ViewModel 层，它持有登录相关的业务逻辑，并通过 LiveData 或 Flow 等可观察对象与 UI 绑定。
* ViewModel 不依赖于 Activity 的生命周期，所以可以在配置改变时保留登录过程的状态。
* 调用了LoginRepository，可以获取所需要的数据
* ViewModel 通常负责处理特定用户事件的业务逻辑。例如，用户点击某个按钮以刷新部分数据。
* ViewModel 通常通过公开界面可以调用的函数来处理这种情况。
*
* 在传统 Android 开发中，当我们想要创建带有参数的 ViewModel，或者需要依赖注入时，通常会自定义 ViewModelProvider.Factory。
* 但在 Jetpack Compose 中，如果我们只是简单地使用无参构造函数的 ViewModel，
* 那么通常不需要手动创建 ViewModelFactory。
* 然而，当你确实需要传递参数给 ViewModel 或者进行依赖注入时，仍然可以使用自定义的 ViewModelFactory。
* 例如，使用 Hilt 进行依赖注入时，Hilt 会自动为你生成 ViewModel 工厂。
* 这个ViewModel仅仅服务于LoginScreen
* */
class LoginViewModel(private val loginRepository: LoginRepository = PetManagerApplication.loginRepository) :
    ViewModel() {
    //这个类如果经过ViewModelFactory初始化的话，其中的loginRepository也是PetManagerApplication中的
    //登陆结果，UI界面通过观察这个值更新UI界面，处理登陆结果
    private var _loginResult = MutableStateFlow<Boolean>(false)
    val loginResult: MutableStateFlow<Boolean> get() = _loginResult

    private var _loggedInUserEmail = MutableLiveData<String>()
    val loggedInUserEmail get() = _loggedInUserEmail

    //用来控制显示Dialog
    private var _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog: MutableStateFlow<Boolean> get() = _showDialog

    /*
    *
    * 登录
    * */
    fun login(email: String, password: String) {
        Log.d("我的登录", "viewModel的login执行")
        viewModelScope.launch {
            Log.d("我的登录", "viewModelScope协程执行")
            val loginLiveData = loginRepository.login(email, password)
            // 观察LiveData并处理结果
            loginLiveData.asFlow().collect { result ->
                when (result) {
                    //登录成功
                    is Result.Success -> {
                        _loginResult.value = result.data
                        _loggedInUserEmail.value = email
                    }

                    is Result.Error -> {
                        Log.d("我的登录", "ViewModel接收到ERROR${result.toString()}")
                        _loginResult.value = false
                        showDialog()
                    }

                    is Result.Loading -> {

                    }

                    else -> {}
                }

            }
            // 可以在这里进一步处理登录成功或失败的情况，比如验证密码正确性等
        }
    }

    fun logout() {
        _loginResult.value = false
        _loggedInUserEmail.value = ""
    }

    private fun showDialog() {
        _showDialog.value = true
    }

    fun unShowDialog() {
        _showDialog.value = false
    }

}


