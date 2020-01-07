package com.example.basictabkt.adapter

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.basictabkt.R
import com.example.basictabkt.helper.ItemData


class ListAdapter(_oData: ArrayList<ItemData>) : BaseAdapter() {
    internal var inflater: LayoutInflater? = null
    private var m_oData: ArrayList<ItemData>? = null
    private var nListCnt = 0

    init {
        m_oData = _oData
        nListCnt = m_oData!!.size
    }

    override fun getCount(): Int {
        Log.i("TAG", "getCount")
        return nListCnt
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val context = parent.context
            if (inflater == null) {
                inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }
            convertView = inflater!!.inflate(R.layout.tab3_listview_item, parent, false)
        }

        val oTextTitle = convertView!!.findViewById(R.id.textView) as TextView
        val oTextDate = convertView.findViewById(R.id.textView2) as TextView

        oTextTitle.setText(m_oData!![position].strTitle)
        oTextDate.setText(m_oData!![position].strDate)
        return convertView
    }
}