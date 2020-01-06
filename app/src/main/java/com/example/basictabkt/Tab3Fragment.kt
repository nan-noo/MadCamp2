package com.example.basictabkt

import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.postDelayed
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tab3.*


class Tab3Fragment : Fragment() {
//    private var chronometer: Chronometer? = null
    private var pauseOffset: Long = 0
    private var running: Boolean = false

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab3, container, false)
        val milli = view.findViewById<TextView>(R.id.milliseconds)
        val minsec = view.findViewById<TextView>(R.id.minsec)
        val handler = Handler()
        var startTime:Long = 0
        var passedTime:Long = 0
        var timeBuff:Long = 0
        var elementsArray:ArrayList<String> = arrayListOf()
        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, elementsArray)
        val listView = view.findViewById(R.id.recList) as ListView
        val linButtonView = view.findViewById(R.id.linButtons) as ViewGroup
        val autoTransition = AutoTransition()
        listView.adapter = adapter

        var runnable: Runnable? = null
        runnable = Runnable {
            if (running) {
//                val millisecondsTime = SystemClock.elapsedRealtime() - chronometer!!.base

                passedTime = SystemClock.elapsedRealtime() - startTime
                val updateTime = timeBuff + passedTime
                val milliseconds = (updateTime % 1000).toInt() / 10
                var seconds = (updateTime / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60

                milli.text = String.format(".%02d", milliseconds)
                minsec.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(runnable!!, 0)
            }
        }

        val button1 = view.findViewById(R.id.start) as Button
        button1.setOnClickListener {
            if (!running) {
//                chronometer!!.base = SystemClock.elapsedRealtime() - pauseOffset
//                chronometer!!.start()

                startTime = SystemClock.elapsedRealtime()
                handler.postDelayed(runnable, 0)
                button1.setText("Pause")
                running = true

            } else{
//                chronometer!!.stop()
//                pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base

                timeBuff += passedTime
                handler.removeCallbacks(runnable)
                button1.setText("Start")
                running = false
            }
        }

        val button2 = view.findViewById(R.id.record) as Button
        button2.setOnClickListener{
            elementsArray.add(String.format("%s%s", minsec.text, milli.text))
            adapter.notifyDataSetChanged()
            autoTransition.duration = 200
            TransitionManager.beginDelayedTransition(listView)
            TransitionManager.beginDelayedTransition(linButtonView, autoTransition)
            listView.visibility = View.VISIBLE
        }

        val button3 = view.findViewById(R.id.reset) as Button
        button3.setOnClickListener {

            startTime = 0
            timeBuff = 0
            passedTime = 0
            if (running) {
//                chronometer!!.stop()
//                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0

                minsec.text = "00:00"
                milli.text = ".00"
                button1.setText("Start")
                elementsArray.clear()
                adapter.notifyDataSetChanged()
                autoTransition.duration = 100
                TransitionManager.beginDelayedTransition(listView, autoTransition)
                TransitionManager.beginDelayedTransition(linButtonView)
                listView.visibility = View.GONE
                running = false
            }
            else{
//                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                minsec.text = "00:00"
                milli.text = ".00"
                elementsArray.clear()
                adapter.notifyDataSetChanged()
                autoTransition.duration = 100
                TransitionManager.beginDelayedTransition(listView, autoTransition)
                TransitionManager.beginDelayedTransition(linButtonView)
                listView.visibility = View.GONE
            }
        }


        return view
    }

}// Required empty public constructor



