package com.jichenhao.petmanager_jetpackcompose.data.dataObject

//接受从API返回的数据，包括success（连接是否成功），data（登陆是否成功的结果），message（本次连接的运行结果）
data class LoginResponse(val success: Boolean, val data: Boolean, val message: String)