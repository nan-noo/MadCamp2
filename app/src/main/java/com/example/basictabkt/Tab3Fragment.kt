package com.example.basictabkt

import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tab3.*


class Tab3Fragment : Fragment() {
//    private var chronometer: Chronometer? = null
    private var pauseOffset: Long = 0
    private var running: Boolean = false

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

        listView.adapter = adapter


//        chronometer = view.findViewById(R.id.chronometer)
//        chronometer!!.format = "%s"
//        chronometer!!.base = SystemClock.elapsedRealtime()
//
//        chronometer!!.onChronometerTickListener =
//            Chronometer.OnChronometerTickListener { chronometer ->
//                if (SystemClock.elapsedRealtime() - chronometer.base >= 10000000) {
//                    Log.d("elapsedtime", (SystemClock.elapsedRealtime() - chronometer.base).toString())
//                    chronometer.base = SystemClock.elapsedRealtime()
//                    Toast.makeText(context, "OutOfBound!", Toast.LENGTH_SHORT).show()
//                }
//            }

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
                running = false
            }
            else{
//                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                minsec.text = "00:00"
                milli.text = ".00"
            }
        }


        return view
    }

}// Required empty public constructor



