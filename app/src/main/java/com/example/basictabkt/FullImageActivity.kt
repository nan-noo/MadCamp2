package com.example.basictabkt

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class FullImageActivity : AppCompatActivity() {

    private val img = intArrayOf(
        R.drawable.c,
        R.drawable.e,
        R.drawable.j,
        R.drawable.q,
        R.drawable.c,
        R.drawable.e,
        R.drawable.j,
        R.drawable.q,
        R.drawable.c,
        R.drawable.e,
        R.drawable.j,
        R.drawable.q,
        R.drawable.c,
        R.drawable.e,
        R.drawable.j,
        R.drawable.q,
        R.drawable.c,
        R.drawable.e,
        R.drawable.j,
        R.drawable.q,
        R.drawable.c
    ) // drawable 폴더에서 가져온 이미지


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_gallery_fullscreen)
//        val imageAdapter = ImageAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager1)
//        viewPager.adapter = imageAdapter

        setContentView(R.layout.fullimage)

        // get intent data
        val i = intent

        // Selected image id
        val position = i.extras!!.getInt("id")

        val imageView = findViewById<ImageView>(R.id.image_center)
        imageView.setImageResource(img[position])

    }

}