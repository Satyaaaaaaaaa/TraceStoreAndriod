package com.sutonglabs.tracestore.ui.toast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.sutonglabs.tracestore.R

fun showCustomToast(context: Context, message: String) {

    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.custom_toast, null)

    val text = layout.findViewById<TextView>(R.id.toast_text)
    text.text = message

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.setGravity(Gravity.BOTTOM, 0, 150)
    toast.show()
}