package com.example.basictabkt.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult
import android.widget.Toast
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.GraphRequest




@SuppressLint("Registered")
class LoginCallback : FacebookCallback<LoginResult>{

    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    override fun onSuccess(loginResult: LoginResult) {
        Log.e("Callback :: ", "onSuccess")
        requestMe(loginResult.accessToken)
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
    private fun requestMe(token: AccessToken) {
        val graphRequest = GraphRequest.newMeRequest(
            token
        ) { me, response ->
            if (response.error != null) {
                // handle error
            } else {
                Log.e("result", me.toString())
            }

            //제대로 들어왔는지 확인
//            val id = me.optString("id")
//            val name = me.optString("name")
//
//            Toast.makeText(
//                getApplicationContext(),
//
//                "name: $name / id: $id",
//
//                Toast.LENGTH_LONG
//            ).show()
            // send email and id to your web server
        }
        val parameters = Bundle()
        parameters.putString("user_info", "id,name,email,gender,birthday")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }
}