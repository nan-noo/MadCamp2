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

        GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { me, response ->
            if (response.error != null) {
                // handle error
            } else {
                //val email = me.optString("email") 이메일은 토스트에 안뜸 왜 그러지? 암튼 아이디만 사용
                val id = me.optString("id")
                val name = me.optString("name")

                //제대로 들어왔는지 확인
                Toast.makeText(
                    getApplicationContext(),

                    "name: $name / id: $id",

                    Toast.LENGTH_LONG
                ).show()
                // send email and id to your web server
            }
        }.executeAsync()
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
    fun requestMe(token: AccessToken) {
        val graphRequest = GraphRequest.newMeRequest(
            token
        ) { `object`, response -> Log.e("result", `object`.toString()) }
        val parameters = Bundle()

        parameters.putString("fields", "id,name,email,gender,birthday")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }
}