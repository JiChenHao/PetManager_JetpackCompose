package com.jichenhao.petmanager_jetpackcompose

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.LoginResponse
import com.jichenhao.petmanager_jetpackcompose.data.dataObject.UserInfo
import com.jichenhao.petmanager_jetpackcompose.data.network.ServiceCreator
import com.jichenhao.petmanager_jetpackcompose.data.network.UserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.await
import javax.inject.Scope

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jichenhao.petmanager_jetpackcompose", appContext.packageName)


        /////////
        val userService = ServiceCreator.create(UserService::class.java)
        val user = UserInfo("test@example.com", "password")
        GlobalScope.launch {
            val loginResponse:LoginResponse = userService.login(user).await()
            Log.d("UserService",loginResponse.data.toString())
        }

    }
}