package com.example.basictabkt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basictabkt.adapter.GalleryImageAdapter
import com.example.basictabkt.adapter.GalleryImageClickListener
import com.example.basictabkt.adapter.Image
import com.example.basictabkt.fragment.GalleryFullscreenFragment
import kotlinx.android.synthetic.main.fragment_tab2.*


class Tab2Fragment : Fragment(), GalleryImageClickListener {
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
        // load images
        loadImages()

        return view
    } // end of onCreate

    private fun loadImages() {
        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
        imageList.add(Image("http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles"))
        imageList.add(Image("https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait"))
        imageList.add(Image("https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower"))
        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
        imageList.add(Image("https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night"))
        imageList.add(Image("http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles"))
        imageList.add(Image("https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait"))
        imageList.add(Image("https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower"))
        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
        imageList.add(Image("https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night"))
        imageList.add(Image("http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles"))
        imageList.add(Image("https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait"))
        imageList.add(Image("https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower"))
        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
        imageList.add(Image("https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night"))
        imageList.add(Image("http://uart.kr/web/product/big/201610/652_shop1_454262.jpg", "Bedroom in Arles"))
        imageList.add(Image("https://blog.singulart.com/wp-content/uploads/2019/11/1200px-Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project-1-1140x1386.jpg", "Portrait"))
        imageList.add(Image("https://van-gogh-reproductions.com/wp-content/uploads/2018/11/Van-gogh-sunflowers.jpg", "Sunflower"))
        imageList.add(Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/2-van-gogh-starry-night-lori-grimmett.jpg", "Starry Night"))
        imageList.add(Image("https://i.pinimg.com/originals/8b/4c/8e/8b4c8e451080d559423afb7731456bfc.jpg", "Cafe Terrace At Night"))
        galleryAdapter.notifyDataSetChanged()
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
} // end of class
