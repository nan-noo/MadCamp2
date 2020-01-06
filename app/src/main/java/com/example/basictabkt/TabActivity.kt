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

        val accessToken = AccessToken.getCurrentAccessToken()

        val userId = accessToken.userId

        //val userId : String = global_id

        Log.e("after22>>>>>>>>>>>>>>>", userId)

        Toast.makeText(this, "$userId", Toast.LENGTH_LONG).show()

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

        JSONTask(userId).execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴
    }

    //뒤로가기 누를 시 앱 전체 꺼지게
    //로그아웃 안하면 다시 들어와도 TabActivity 실행됨
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    class JSONTask(id: String) :
        AsyncTask<String?, String?, String?>() {

        val user_id = id

        //Log.i("Contact>>>>>>>>>>>>>>>>", user_id )

        override fun doInBackground(vararg urls: String?): String? {
            try {

                var getContact =
                    URL("http://192.249.19.254:8280/api/contacts/user_id/$user_id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리

                var json = getContact
                var gson = Gson()
                var person : List<Contact> = gson.fromJson(json, Array<Contact>::class.java).toList()

                Log.i("Contact>>>>>>>>>>>>>>>>", person[0].get_id() )//형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]



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