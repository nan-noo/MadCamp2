package com.example.basictabkt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.basictabkt.login.LoginCallback
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton


class MainActivity : AppCompatActivity() {

    private var mContext: Context? = null
    private var btn_facebook_login: LoginButton? = null
    private var mLoginCallback: LoginCallback? = null
    private var mCallbackManager: CallbackManager? = null
    private var btn_custom_login: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = applicationContext
        mCallbackManager = CallbackManager.Factory.create()
        mLoginCallback = LoginCallback()

        btn_facebook_login = findViewById(R.id.login_button)
        btn_facebook_login!!.setPermissions(listOf("public_profile", "email"))
        btn_facebook_login!!.registerCallback(mCallbackManager, mLoginCallback)

        //custom button
        btn_custom_login = findViewById(R.id.btn_custom_login)
        btn_custom_login!!.setOnClickListener { btn_facebook_login!!.performClick() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


        //로그인 창을 그냥 닫았을시에도 실행됨. 수정ㄱㄱ
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
    }

}
