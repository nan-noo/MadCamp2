package com.example.basictabkt

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Tab1Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class Tab3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3, container, false)
    }

}// Required empty public constructor


