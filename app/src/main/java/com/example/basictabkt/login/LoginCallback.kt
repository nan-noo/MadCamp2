package com.example.basictabkt.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult

import com.facebook.GraphRequest


@SuppressLint("Registered")
class LoginCallback : FacebookCallback<LoginResult>{


    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    override fun onSuccess(loginResult: LoginResult) {
        Log.e("Callback :: ", "onSuccess")
        requestMe(loginResult.accessToken)
        Log.e("Callback :: ", "requestME whererere")

    }

    // 로그인 창을 닫을 경우, 호출됩니다.
    override fun onCancel() {
        Log.e("Callback :: ", "onCancel")

    }

    // 로그인 실패 시에 호출됩니다.
    override fun onError(error: FacebookException) {
        Log.e("Callback :: ", "onError : " + error.message)
    }

    // 사용자 정보 요청
    private fun requestMe(token: AccessToken){

        val graphRequest = GraphRequest.newMeRequest(
            token
        ) { me, response ->
            if (response.error != null) {
                // handle error
            } else {
                Log.e("result>>>>>>>>>>>>>>>>>", me.toString())
            }
        }
        val parameters = Bundle()
        parameters.putString("field", "id,name")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

        parameters.keySet()
    }

}