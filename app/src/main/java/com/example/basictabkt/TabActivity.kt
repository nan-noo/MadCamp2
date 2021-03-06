package com.example.basictabkt

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.basictabkt.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import com.facebook.login.LoginManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.basictabkt.login.Contact
import com.facebook.AccessToken

import com.google.gson.Gson
import java.net.URL


class TabActivity : AppCompatActivity(){
    private var btn_logout: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_activity_main)

        //Log.e("before22>>>>>>>>>>>>>>>", global_id)

//        val accessToken = AccessToken.getCurrentAccessToken()
//
//        val userId = accessToken.userId

//        //val userId : String = global_id

//        Log.e("after22>>>>>>>>>>>>>>>", userId)
//
//        Toast.makeText(this, "$userId", Toast.LENGTH_LONG).show()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        //Logout 버튼 누를 시 Logout + 메인 화면으로 돌아가도록
        btn_logout = findViewById(R.id.btn_logout)
        btn_logout!!.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("정말 로그아웃 하시겠습니까?")
            builder.setPositiveButton("예"
            ) { _, _ ->
                LoginManager.getInstance().logOut()
                Toast.makeText(
                    applicationContext,
                    "로그아웃되었습니다.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
            builder.setNegativeButton("아니오"
            ) { _, _ ->
            }
            builder.show()
        }
    }

    //뒤로가기 누를 시 앱 전체 꺼지게
    //로그아웃 안하면 다시 들어와도 TabActivity 실행됨
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
    }