package com.example.basictabkt

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
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


        chronometer = view.findViewById(R.id.chronometer)
        chronometer!!.format = "%s"
        chronometer!!.base = SystemClock.elapsedRealtime()

        chronometer!!.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                if (SystemClock.elapsedRealtime() - chronometer.base >= 100000) {
                    chronometer.base = SystemClock.elapsedRealtime()
                    Toast.makeText(context, "OutOfBound!", Toast.LENGTH_SHORT).show()
                }
            }

        val button1 = view.findViewById(R.id.start) as Button
        button1.setOnClickListener {
            if (!running) {
                chronometer!!.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer!!.start()
                running = true

            }
        }

        val button2 = view.findViewById(R.id.pause) as Button
        button2.setOnClickListener {
            if (running) {
                chronometer!!.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base
                running = false
            }
        }

        val button3 = view.findViewById(R.id.reset) as Button
        button3.setOnClickListener {
            if (running) {
                chronometer!!.stop()
                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                running = false
            }
            else{
                chronometer!!.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
            }
        }

        return view
    }

}// Required empty public constructor



