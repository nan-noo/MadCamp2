package com.example.basictabkt

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment


class Tab3Fragment : Fragment() {
    private var chronometer: Chronometer? = null
    private var pauseOffset: Long = 0
    private var running: Boolean = false
    private var milli: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab3, container, false)
        val milli = view.findViewById<TextView>(R.id.milliseconds)
        val handler = Handler()

        chronometer = view.findViewById(R.id.chronometer)
        chronometer!!.format = "%s"
        chronometer!!.base = SystemClock.elapsedRealtime()

        chronometer!!.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                if (SystemClock.elapsedRealtime() - chronometer.base >= 10000000) {
                    Log.d("elapsedtime", (SystemClock.elapsedRealtime() - chronometer.base).toString())
                    chronometer.base = SystemClock.elapsedRealtime()
                    Toast.makeText(context, "OutOfBound!", Toast.LENGTH_SHORT).show()
                }
            }

        var runnable: Runnable? = null
        runnable = Runnable {
            if (running) {
                val millisecondsTime = SystemClock.elapsedRealtime() - chronometer!!.base
                val milliseconds = (millisecondsTime % 1000).toInt() / 10
                milli.text = String.format(".%02d", milliseconds)
                handler.postDelayed(runnable!!, 0)
            }
        }

        val button1 = view.findViewById(R.id.start) as Button
        button1.setOnClickListener {
            if (!running) {
                chronometer!!.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer!!.start()
                handler.postDelayed(runnable, 0)
                button1.setText("Pause")
                running = true
            } else{
                chronometer!!.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base
                handler.postDelayed(runnable, 0)
                handler.removeCallbacks(runnable)
                button1.setText("Start")
                running = false
            }
        }


        val button2 = view.findViewById(R.id.reset) as Button
        button2.setOnClickListener {
            if (running) {
                chronometer!!.stop()
                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                milli.text = ".00"
                button1.setText("Start")
                running = false
            }
            else{
                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                milli.text = ".00"
            }
        }


        return view
    }

}// Required empty public constructor



