package com.example.basictabkt

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basictabkt.adapter.GalleryImageAdapter
import com.example.basictabkt.adapter.GalleryImageClickListener
import com.example.basictabkt.adapter.Image
import com.example.basictabkt.fragment.GalleryFullscreenFragment
import com.example.basictabkt.login.Contact
import com.facebook.AccessToken
import com.google.gson.Gson
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.net.URL


class Tab2Fragment : Fragment(), GalleryImageClickListener {

    //Facebook ID
    val accessToken = AccessToken.getCurrentAccessToken()
    val userId = accessToken.userId

    // gallery column count
    private val SPAN_COUNT = 3
    private val imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryImageAdapter
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tab2, container, false)
        // init adapter
        galleryAdapter = GalleryImageAdapter(imageList)
        galleryAdapter.listener = this
        // init recycler view
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView!!.adapter = galleryAdapter

        //post
        uploadImages()

        // load images
        JSONTaskGet(userId).execute("http://192.249.19.254:8280/")
        val get_btn = view.findViewById(R.id.get_btn2) as Button
        get_btn.setOnClickListener{
            loadImages()
        }

        //delete
        return view
    } // end of onCreate

    private fun loadImages() {
        val num = global_image_list.size
        var i = 0

        imageList.clear() // 리스트 초기화

        while(i < num){
            var image_title = global_image_list[i].get_title()
            var image_url = global_image_list[i].get_url()

            imageList.add(Image("$image_url", "$image_title"))

            i++
        }

//        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
//        imageList.add(Image("http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles"))
//        imageList.add(Image("https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait"))
//        imageList.add(Image("https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower"))
//        imageList.add(Image("https://images.robertharding.com/preview/RM/RH/VERTICAL/817-457051.jpg", "Spring"))
//        imageList.add(Image("https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night"))
//        imageList.add(Image("http://mblogthumb2.phinf.naver.net/MjAxODAxMDJfMTM4/MDAxNTE0ODQ2NzUxMzE2._j5Yfrx4cEfYuPS2AgafAgjKBJFy68VqwbmAB1cDKNwg.pcsb8coprg_lwuTNwGcvfn7rw_ZF2OaQuz3TOtWBzf0g.JPEG.yoojy10/%EC%A7%84%EC%A3%BC%EB%AA%A9%EA%B1%B8%EC%9D%B4%EB%A5%BC_%ED%95%9C_%EC%86%8C%EB%85%80.jpg?type=w800", "Girl with a Pearl Earring"))
//        imageList.add(Image("https://t1.daumcdn.net/liveboard/weekly/e61eb519062a4f448b53bb03e993b681.JPG", "Mona Lisa"))
//        imageList.add(Image("https://t1.daumcdn.net/cfile/tistory/2227B24854AF75EC27", "Moi et le Village"))
//        imageList.add(Image("https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile1.uf.tistory.com%2Fimage%2F99B18D375BB49DAF2CEE89", "Kiss"))
//        imageList.add(Image("https://cdn.getyourguide.com/img/tour_img-312981-146.jpg", "Last Supper"))
//        imageList.add(Image("https://t1.daumcdn.net/cfile/tistory/2237604E54FD087530", "L'Angélus"))
//        imageList.add(Image("https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/Tm7/image/6UiTPOov5KPAxIhKQj58g3vXlU0.JPG", "Le Joueur de fifre"))
//        imageList.add(Image("https://i.pinimg.com/originals/d4/1f/2b/d41f2bc603ac163db6fb22ddf03a8d3a.jpg", "Woman with a Parasol"))
//        imageList.add(Image("https://post-phinf.pstatic.net/MjAxODA2MjRfMjgy/MDAxNTI5ODIzMzkxMDYy.MYyboKWJYyuKwGZLkiiWan33HvK36pjcHyMbIrJX830g.ByuUy0ARhIBA21fhZ3cZQC4R4puh0GE3MD1-gVaBmykg.JPEG/the-weeping-woman.jpg?type=w1200", "Weeping Woman"))
//        imageList.add(Image("https://www.100scenes-in-gardenhistory.org/wp-content/uploads/2016/10/1899_the-japanese-bridge-the-water-lily-pond.jpg", "The Water-Lily Pond"))
//        imageList.add(Image("http://mblogthumb4.phinf.naver.net/MjAxNzA0MDFfMzEg/MDAxNDkxMDEzMTg5MDU2.cpCLoxGYfK1haEccDZ1Q4HA0bN_p7A2sIinyPxGySzsg.9nctHWx9ITbm73y7WCn__cLrtnwHVDo25Iafb6UYou4g.JPEG.gallerysarang/1872_%EC%A0%9C%EB%B9%84%EA%BD%83%EC%9D%84_%EB%93%A0_%EB%B2%A0%EB%A5%B4%ED%8A%B8_%EB%AA%A8%EB%A6%AC%EC%A1%B0__%EB%A7%88%EB%84%A4.jpg?type=w800", "Berthe Morisot au bouquet de violettes"))
//        imageList.add(Image("https://upload.wikimedia.org/wikipedia/commons/b/bb/Pierre-Auguste_Renoir_-_La_Yole.jpg", "Boating On The Seine"))
//        imageList.add(Image("http://cfile217.uf.daum.net/image/2462D34B53C5F36D20F13F", "Un dimanche après-midi à l'Île de la Grande Jatte"))
//        imageList.add(Image("https://upload.wikimedia.org/wikipedia/commons/f/f4/The_Scream.jpg", "The Scream"))
//        imageList.add(Image("http://m.hi-korea.kr/web/product/big/201712/2511_shop1_405031.jpg", "Des glaneuses"))
        galleryAdapter.notifyDataSetChanged()
    }

    private fun uploadImages(){

        JSONTaskPost(userId, "https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId, "http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://images.robertharding.com/preview/RM/RH/VERTICAL/817-457051.jpg", "Spring").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"http://mblogthumb2.phinf.naver.net/MjAxODAxMDJfMTM4/MDAxNTE0ODQ2NzUxMzE2._j5Yfrx4cEfYuPS2AgafAgjKBJFy68VqwbmAB1cDKNwg.pcsb8coprg_lwuTNwGcvfn7rw_ZF2OaQuz3TOtWBzf0g.JPEG.yoojy10/%EC%A7%84%EC%A3%BC%EB%AA%A9%EA%B1%B8%EC%9D%B4%EB%A5%BC_%ED%95%9C_%EC%86%8C%EB%85%80.jpg?type=w800", "Girl with a Pearl Earring")
        JSONTaskPost(userId,"https://upload.wikimedia.org/wikipedia/commons/b/bb/Pierre-Auguste_Renoir_-_La_Yole.jpg", "Boating On The Seine").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/Tm7/image/6UiTPOov5KPAxIhKQj58g3vXlU0.JPG", "Le Joueur de fifre").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://upload.wikimedia.org/wikipedia/commons/f/f4/The_Scream.jpg", "The Scream").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://i.pinimg.com/originals/d4/1f/2b/d41f2bc603ac163db6fb22ddf03a8d3a.jpg", "Woman with a Parasol").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"http://m.hi-korea.kr/web/product/big/201712/2511_shop1_405031.jpg", "Des glaneuses").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://t1.daumcdn.net/liveboard/weekly/e61eb519062a4f448b53bb03e993b681.JPG", "Mona Lisa").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://cdn.getyourguide.com/img/tour_img-312981-146.jpg", "Last Supper").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://t1.daumcdn.net/cfile/tistory/2237604E54FD087530", "L'Angélus").execute("http://192.249.19.254:8280/")
        JSONTaskPost(userId,"https://t1.daumcdn.net/cfile/tistory/2227B24854AF75EC27", "Moi et le Village").execute("http://192.249.19.254:8280/")
    }

    override fun onClick(position: Int) {
        // handle click of image
        val bundle = Bundle()
        bundle.putSerializable("images", imageList)
        bundle.putInt("position", position)
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        val galleryFragment = GalleryFullscreenFragment()
        galleryFragment.arguments = bundle
        if (fragmentTransaction != null) {
            galleryFragment.show(fragmentTransaction, "gallery")
        }
    }

    class JSONTaskPost(userId: String, image_url: String, image_title: String):
        AsyncTask<String?, String?, String?>() {

        private val user_id = userId
        private val image_url = image_url
        private val image_title = image_title

        override fun doInBackground(vararg urls: String?): String? {
            try {
                //post
                var post = httpPost {
                    url("http://192.249.19.254:8280/api/images")
                    body {
                        form("user_id=$user_id&" +
                                "image_url=$image_url&" +
                                "image_title=$image_title"
                        )
                    }
                }
                Log.i("postImage>>>>>>>>>>>>>", post.message())

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

                var getImage =
                    URL("http://192.249.19.254:8280/api/images/user_id/$user_id").readText() // 로그인한 유저가 받은 아이디로 찾기 //not found 일 때 처리

                var json = getImage
                var gson = Gson()
                global_image_list = gson.fromJson(json, Array<Contact>::class.java).toList()

                Log.i("Image>>>>>>>>>>>>>>>>", getImage )//형태>> [{"_id":"5e11cddde1fc032f3ba8e4c3","user_id":"010-121324-1124","image_url":"dafoudfasfi", "image_title":"dfdfd"}]


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
                    url("http://192.249.19.254:8280/api/images/delete/$id")
                }
                Log.i("delete>>>>>>>>>>>>>>>>", delete.message())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    companion object{
        lateinit var global_image_list: List<Contact>
    }
} // end of class
