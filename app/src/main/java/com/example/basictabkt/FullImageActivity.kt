package com.example.basictabkt

import android.os.Bundle
import android.app.Activity
import android.widget.ImageView


class FullImageActivity : Activity() {

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
        setContentView(R.layout.fullimage)

        // get intent data
        val i = intent

        // Selected image id
        val position = i.extras!!.getInt("id")

        val imageView = findViewById<ImageView>(R.id.image_center)
        imageView.setImageResource(img[position])
    }


}