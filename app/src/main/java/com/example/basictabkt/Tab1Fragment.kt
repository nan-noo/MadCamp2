package com.example.basictabkt

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basictabkt.login.Contact
import com.facebook.AccessToken
import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.io.Serializable
import java.net.URL
import java.util.ArrayList
import java.util.LinkedHashSet

class Tab1Fragment : Fragment() {

    val accessToken = AccessToken.getCurrentAccessToken()
    val userId = accessToken.userId

    var activityInstance: Activity? = null

    class ContactItem : Serializable {
        var user_phNumber: String? = null
        var user_Name: String? = null
        //사진 부분
        var photo_id: Long = 0
        var person_id: Long = 0
        var id: Int = 0
        var real_id: Int = 0

        override fun toString(): String {
            return this.user_phNumber!!
        }

        override fun hashCode(): Int {
            return user_phNumber.hashCode()
//            return phNumberChanged.hashCode()
        }

        fun equals(o: Serializable?): Boolean {
            return if (o is ContactItem) user_phNumber == o.user_phNumber else false

        }

    }
    private val contactList: ArrayList<ContactItem>
        get() {
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_ID, ContactsContract.Contacts._ID)

            val selectionArgs: Array<String>? = null
            val sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"

            val cursor = context(activityInstance).contentResolver.query(uri, projection, null,
                selectionArgs, sortOrder)



            val hashlist = LinkedHashSet<ContactItem>()
            val contactItems: ArrayList<ContactItem>
            if (cursor!!.moveToFirst()) {
                do {
                    val photo_id = cursor!!.getLong(2)
                    val person_id = cursor!!.getLong(3)
// contactItem 안쓸거임 수정하기 귀찮아서 그냥 안쓰고 냅둠
                    val contactItem = ContactItem()
                    contactItem.user_phNumber = cursor.getString(0)
                    contactItem.user_Name = cursor.getString(1)
                    contactItem.photo_id = photo_id
                    contactItem.person_id = person_id
                    contactItem.real_id = cursor.getInt(cursor.getColumnIndex("_id"))
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    val user_phNumber = cursor.getString(0)
                    val user_Name = cursor.getString(1)
                    val real_id = cursor.getString(cursor.getColumnIndex("_id"))

                    JSONTaskPost(userId, user_Name, user_phNumber, real_id).execute("http://192.249.19.254:8280/")
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    hashlist.add(contactItem)

                } while (cursor.moveToNext())
            }

            contactItems = ArrayList(hashlist)
            for (i in contactItems.indices) {
                contactItems[i].id = i
            }

            cursor.close()

            return contactItems
        }

    private var mArrayList: List<Contact>? = null
    private var mArrayList3: List<Contact>? = null
    private var mArrayList2: ArrayList<ContactItem>? = null
    private var mAdapter: CustomAdapter? = null
    private var count = -1

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activityInstance = activity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //권한이 부여되어 있는지 확인
        val permissionCheckR =
            ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_CONTACTS)
        val permissionCheckW =
            ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.WRITE_CONTACTS)
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)

        //MainActivity 돌아올때마다 메세지 나옴
        if (permissionCheckR == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context!!.applicationContext, "연락처 읽기권한 있음", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context!!.applicationContext, "연락처 읽기권한 없음", Toast.LENGTH_SHORT).show()

            //권한설정 dialog에서 거부를 누르면
            //ActivityCompat.shouldShowRequestPermissionRationale 메소드의 반환값이 true가 된다.
            //단, 사용자가 "Don't ask again"을 체크한 경우
            //거부하더라도 false를 반환하여, 직접 사용자가 권한을 부여하지 않는 이상, 권한을 요청할 수 없게 된다.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activityInstance!!,
                    android.Manifest.permission.READ_CONTACTS
                )
            ) {
                //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
                Toast.makeText(context!!.applicationContext, "연락처 읽기권한이 필요합니다", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.requestPermissions(
                    activityInstance!!,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    CONTACTS_PERMISSION
                )
            } else {
                ActivityCompat.requestPermissions(
                    activityInstance!!,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    CONTACTS_PERMISSION
                )
            }

        }

        if (permissionCheckW == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context!!.applicationContext, "연락처 쓰기권한 있음", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context!!.applicationContext, "연락처 쓰기권한 없음", Toast.LENGTH_SHORT).show()

            //권한설정 dialog에서 거부를 누르면
            //ActivityCompat.shouldShowRequestPermissionRationale 메소드의 반환값이 true가 된다.
            //단, 사용자가 "Don't ask again"을 체크한 경우
            //거부하더라도 false를 반환하여, 직접 사용자가 권한을 부여하지 않는 이상, 권한을 요청할 수 없게 된다.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activityInstance!!,
                    android.Manifest.permission.WRITE_CONTACTS
                )
            ) {
                //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
                Toast.makeText(context!!.applicationContext, "연락처 쓰기권한이 필요합니다", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.requestPermissions(
                    activityInstance!!,
                    arrayOf(android.Manifest.permission.WRITE_CONTACTS),
                    CONTACTS_PERMISSION
                )
            } else {
                ActivityCompat.requestPermissions(
                    activityInstance!!,
                    arrayOf(android.Manifest.permission.WRITE_CONTACTS),
                    CONTACTS_PERMISSION
                )
            }

        }

        val contextWrapper = ContextWrapper(context)
        val mRecyclerView = view.findViewById(R.id.recyclerview_main_list) as RecyclerView
        val mLinearLayoutManager = LinearLayoutManager(context!!)
        mRecyclerView.layoutManager = mLinearLayoutManager
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mArrayList2 = contactList

        //get
        JSONTaskGet(userId).execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴

        // 추가하기 버튼!
        val post_btn = view.findViewById(R.id.post_btn) as Button
        post_btn.setOnClickListener{
            val builder2 = AlertDialog.Builder(context)
            val inflater = layoutInflater
            builder2.setTitle("연락처 추가")
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
            val name  = dialogLayout.findViewById(R.id.editText_name) as EditText
            val phNum  = dialogLayout.findViewById(R.id.editText_phN) as EditText
            builder2.setView(dialogLayout)
            builder2.setPositiveButton("확인") { dialogInterface, i ->
                val user_Name = name.text.toString()
                val user_phNumber = phNum.text.toString()

                Toast.makeText(context, "name, phNum: $user_Name , $user_phNumber", Toast.LENGTH_LONG).show()

                JSONTaskPost2(userId, user_Name, user_phNumber).execute("http://192.249.19.254:8280/")
                JSONTaskGet(userId).execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴
            }
            builder2.setNegativeButton("취소"){ dialogInterface, i ->

            }
            builder2.show()
        }


        // 불러오기 버튼!
        val get_btn = view.findViewById(R.id.get_btn) as Button
        get_btn.setOnClickListener {

            mArrayList = global_person_list
            mAdapter = CustomAdapter(mArrayList2, mArrayList, context!!)
            mAdapter!!.itemLongClick = object : CustomAdapter.ItemLongClick {
                override fun onLongClick(view: View, position: Int) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("연락처 삭제")
                    builder.setMessage("정말 삭제하시겠습니까?")
                  //  Log.i("before delete1>>>>>>>>>", global_person_list[0].get_id())
                    val dialog_listener = object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    //context(activityInstance), activityInstance!!, activityInstance!!.applicationContext, context!!, context!!.applicationContext, contextWrapper.baseContext, contextWrapper.baseContext.applicationContext
                                    val mContext = contextWrapper.baseContext.applicationContext
                                  //  Log.i("before delete2>>>>>>>>>", global_person_list[0].get_id())
                                    val cur = mContext.contentResolver.query(
                                        ContactsContract.Contacts.CONTENT_URI,
                                        null,
                                        null,
                                        null,
                                        null
                                    )
                                    var count = 0
                                    if (cur!!.moveToFirst()) {
                                        do {
                                           // Log.i("before delete3>>>>>>>>>", global_person_list[0].get_id())
                                            if (position == count) {
                                                val lookupKey =
                                                    cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                                              //  Log.i("before delete4>>>>>>>>>", global_person_list[0].get_id())
                                                val uri = Uri.withAppendedPath(
                                                    ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                                    lookupKey
                                                )
                                                mContext.contentResolver.delete(uri, null, null)
                                                mArrayList3 = global_person_list

                                              //  Log.i("before delete5>>>>>>>>>", global_person_list[0].get_id())

                                                JSONTaskDel(global_person_list[position].get_id()).execute("http://192.249.19.254:8280/")
                                                JSONTaskGet(userId).execute("http://192.249.19.254:8280/") //AsyncTask 시작시킴

                                                val longClick = mAdapter!!.itemLongClick
                                                mAdapter = CustomAdapter(mArrayList2, mArrayList3, context!!)
                                                mAdapter!!.itemLongClick = longClick
                                                mRecyclerView.adapter = mAdapter
                                                break
                                            }
                                            count++
                                        } while (cur.moveToNext())
                                    }
                                    Log.d("Longclick", "Longclick" + position)
                                }
                            }
                        }
                    }
                    builder.setPositiveButton("확인", dialog_listener)
                    builder.setNegativeButton("취소", dialog_listener)
                    builder.show()
                }
            }
            mRecyclerView.adapter = mAdapter


            val dividerItemDecoration = DividerItemDecoration(
                mRecyclerView.context,
                mLinearLayoutManager.orientation
            )
            mRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        return view

    }

    //grantResults[]는 요청한 권한의 허용 여부 확인 가능
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            CONTACTS_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context!!.applicationContext, "연락처 열람권한 승인함", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context!!.applicationContext, "연락처 열람권한 거부함", Toast.LENGTH_SHORT).show()
            }
            CONTACTS_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context!!.applicationContext, "연락처 쓰기권한 승인함", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context!!.applicationContext, "연락처 쓰기권한 거부함", Toast.LENGTH_SHORT).show()
            }

        }
    }


    class JSONTaskGet(id: String) :
        AsyncTask<String?, String?, String?>() {

        private val user_id = id

        override fun doInBackground(vararg urls: String?): String? {
            try {

                var getContact =
                    URL("http://192.249.19.254:8280/api/contacts/user_id/$user_id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리

                var json = getContact
                var gson = Gson()
                global_person_list = gson.fromJson(json, Array<Contact>::class.java).toList()

                Log.i("Contact>>>>>>>>>>>>>>>>", getContact )//형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","phNum":"010-121324-1124","name":"dafoudfasfi"}]


            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    class JSONTaskPost(userId: String, user_Name: String, user_phNumber: String, real_id: String):
        AsyncTask<String?, String?, String?>() {

        private val user_id = userId
        private val user_name = user_Name
        private val user_phNumber = user_phNumber
        private val _id = real_id

        override fun doInBackground(vararg urls: String?): String? {
            try {
                //post
                var post = httpPost {
                    url("http://192.249.19.254:8280/api/contacts")
                    body {
                        form("user_id=$user_id&" +
                                "phNum=$user_phNumber&" +
                                "name=$user_name&" +
                                "_id=$_id"
                        )
                    }
                }
                Log.i("post>>>>>>>>>>>>>>>>>>", post.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    class JSONTaskPost2(userId: String, user_Name: String, user_phNumber: String):
        AsyncTask<String?, String?, String?>() {

        private val user_id = userId
        private val user_name = user_Name
        private val user_phNumber = user_phNumber

        override fun doInBackground(vararg urls: String?): String? {
            try {
                //post
                var post = httpPost {
                    url("http://192.249.19.254:8280/api/contacts")
                    body {
                        form("user_id=$user_id&" +
                                "phNum=$user_phNumber&" +
                                "name=$user_name&"
                        )
                    }
                }
                Log.i("post>>>>>>>>>>>>>>>>>>", post.message())

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
                    url("http://192.249.19.254:8280/api/contacts/delete/$id")
                }
                Log.i("delete>>>>>>>>>>>>>>>>", delete.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    companion object {
        lateinit var id: String

        lateinit var global_person_list: List<Contact>

        internal val CONTACTS_PERMISSION = 1

        fun context(activity: Activity?) : Context {
            return activity!!.applicationContext
        }
    }
}// Required empty public constructor


