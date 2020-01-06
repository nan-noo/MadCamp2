package com.example.basictabkt.login

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Message
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult
import android.widget.Toast
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.GraphRequest
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.net.URL


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
                Log.e("result>>>>>>>>>>>>>>>>>", me.toString())
            }

            //제대로 들어왔는지 확인
            val id = me.optString("id")
//          val name = me.optString("name")

            JSONTask(id).execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴
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
        parameters.putString("field", "id,name")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

        parameters.keySet()


    }

    class JSONTask(id: String) :
        AsyncTask<String?, String?, String?>() {

        val id = id

        override fun doInBackground(vararg urls: String?): String? {
            try {

                var getContact =
                    URL("http://192.249.19.254:8280/api/contacts/user_id/$id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리
                Log.i("Contact>>>>>>>>>>>>>>>>", getContact) //형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]

//                var getImage =
//                    URL("http://192.249.19.254:8280/api/images/user_id/$id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리
//                Log.i("Contact>>>>>>>>>>>>>>>>", getImage) //형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]
//
//                var getTab3 =
//                    URL("http://192.249.19.254:8280/api/contacts/user_id/$id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리
//                Log.i("Contact>>>>>>>>>>>>>>>>", getTab3) //형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]

                //parsing code 필요


            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}