package com.owasp.top.mobile.demo.ui.common

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class AlertDialogApp (
    context: Context
) {
    private var builder = AlertDialog.Builder(context)

    init {
        builder.setCancelable(false)
    }

    fun setMessage(message: String) {
        builder.setMessage(message)
    }

    fun setTitle(title: String){
        builder.setTitle(title)
    }

    fun setPositiveButton(text: String, action: () -> Unit){
        builder.setPositiveButton(text, object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                action.invoke()
                p0?.cancel()
            }
        })
    }


    fun setNegativeButton(text: String, action: () -> Unit){
        builder.setNegativeButton(text, object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                action.invoke()
                p0?.cancel()
            }
        })
    }

    fun show(){
        // Create the Alert dialog
        val alertDialog: AlertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }
}