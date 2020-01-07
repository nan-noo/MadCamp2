package com.example.basictabkt

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.basictabkt.login.Contact
import com.facebook.AccessToken
import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.net.URL
import android.app.DatePickerDialog
import java.util.*
import kotlin.collections.ArrayList
import android.widget.EditText
import com.example.basictabkt.adapter.ListAdapter
import java.text.SimpleDateFormat
import com.example.basictabkt.helper.ItemData
import android.widget.Toast
import android.widget.AdapterView




class Tab3Fragment : Fragment() {
    //Facebook ID
    val accessToken = AccessToken.getCurrentAccessToken()
    val userId = accessToken.userId
    private var m_oListView: ListView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab3, container, false)

        // 불러오기
        JSONTaskGet(userId).execute("http://192.249.19.254:8280/")

        // 일정 추가
        val post_btn = view.findViewById(R.id.post_btn3) as Button
        post_btn.setOnClickListener {
            val builder2 = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.tab3_alert_dialog_with_edittext, null)
            var myCalendar = Calendar.getInstance()

            var myDatePicker: DatePickerDialog.OnDateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = "yyyy/MM/dd"    // 출력형식   2018/11/28
                    val sdf = SimpleDateFormat(myFormat, Locale.KOREA)

                    val get_date = dialogLayout.findViewById(R.id.Date) as EditText
                    get_date.setText(sdf.format(myCalendar.time))
                }

            val get_Date = dialogLayout.findViewById(R.id.Date) as EditText
            val get_toDo = dialogLayout.findViewById(R.id.ToDo) as EditText

            get_Date.setOnClickListener {
                DatePickerDialog(
                    context!!,
                    myDatePicker,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            builder2.setTitle("To Do!")
            builder2.setView(dialogLayout)
            builder2.setPositiveButton("확인") { dialogInterface, i ->
                val date = get_Date.text.toString()
                val toDo = get_toDo.text.toString()

                val str = date.split("/")
                val year = str[0]
                val month = str[1]
                val day = str[2]

                //Toast.makeText(context, "string 어떻게? $date, $year, $month, $day", Toast.LENGTH_LONG).show() //2020/10/80

                JSONTaskPost(userId, year, month, day, toDo).execute("http://192.249.19.254:8280/")
                JSONTaskGet(userId).execute("http://192.249.19.254:8280/")
            }
            builder2.setNegativeButton("취소") { dialogInterface, i ->
            }
            builder2.show()
        }




        //삭제하기
        val listview = view.findViewById(R.id.listView) as ListView

        listview.setOnItemLongClickListener {  parent, v, position, arg3 ->
            //Toast.makeText(context,"hello", Toast.LENGTH_LONG).show()
            val builder = AlertDialog.Builder(context)
            builder.setTitle("일정 삭제")
            builder.setMessage("정말 삭제하시겠습니까?")
            builder.setPositiveButton("확인") { dialogInterface, i ->
                val _id = global_todo_list[position].get_id()
                JSONTaskDel(_id).execute("http://192.249.19.254:8280/")
                JSONTaskGet(userId).execute("http://192.249.19.254:8280/")
            }
            builder.setNegativeButton("취소"){ dialogInterface, i ->
            }
            builder.show()

            true
        }


        // 불러오기 버튼
        val get_btn = view.findViewById(R.id.get_btn3) as Button
        get_btn.setOnClickListener {
            val num = global_todo_list.size
            var i = 0
            val oData = ArrayList<ItemData>()

            val currentTime = Calendar.getInstance().time
            val date_text =
                SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(currentTime)

            while(i < num){
                val year = global_todo_list[i].get_year()
                val month = global_todo_list[i].get_month()
                val day = global_todo_list[i].get_day()
                val toDo = global_todo_list[i].get_toDo()

                val oItem = ItemData()
                oItem.strTitle = "$toDo"
                oItem.strDate = "$year/$month/$day"
                oData.add(oItem)

                var tv = view.findViewById(R.id.textView4) as TextView
                if("$year/$month/$day" == date_text){
                    tv.text = "$toDo"
                }
                else{
                    tv.text = "없음!"
                }
                i++
            }

        m_oListView = view.findViewById(R.id.listView) as ListView
        val oAdapter = ListAdapter(oData)
        m_oListView!!.adapter = oAdapter

        }

        return view
    }


    class JSONTaskPost(userId: String, year: String, month: String, day: String, toDo: String):
        AsyncTask<String?, String?, String?>() {

        private val user_id = userId
        private val year = year
        private val month = month
        private val day = day
        private val toDo = toDo

        override fun doInBackground(vararg urls: String?): String? {
            try {
                //post
                var post = httpPost {
                    url("http://192.249.19.254:8280/api/todos")
                    body {
                        form("user_id=$user_id&" +
                                "year=$year&" +
                                "month=$month&" +
                                "day=$day&" +
                                "toDo=$toDo"
                        )
                    }
                }
                Log.i("postTodo>>>>>>>>>>>>>", post.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    class JSONTaskGet(id: String) :
        AsyncTask<String?, String?, String?>() {

        private val user_id = id

        override fun doInBackground(vararg urls: String?): String? {
            try {

                var getTodo =
                    URL("http://192.249.19.254:8280/api/todos/after/user_id/$user_id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리

                var json = getTodo
                var gson = Gson()
                global_todo_list = gson.fromJson(json, Array<Contact>::class.java).toList()

                Log.i("Todo>>>>>>>>>>>>>>>>", getTodo )//형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","user_id":"010-121324-1124","image_url":"dafoudfasfi", "image_title":"dfdfd"}]


            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    class JSONTaskDel(_id: String?):
        AsyncTask<String?, String?, String?>() {

        private val id = _id

        override fun doInBackground(vararg urls: String?): String? {
            try {
                //delete
                var delete = httpDelete {
                    url("http://192.249.19.254:8280/api/todos/delete/$id")
                }
                Log.i("delete>>>>>>>>>>>>>>>>", delete.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    companion object{
        lateinit var global_todo_list: List<Contact>
    }

}// Required empty public constructor



