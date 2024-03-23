package com.jichenhao.petmanager_jetpackcompose.data.dataObject

//成功登陆的用户，不保存其密码，仅保存用户id，即用户Email
data class LoggedInUser(val email:String)