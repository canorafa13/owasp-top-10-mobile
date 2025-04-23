package com.owasp.top.mobile.demo.ui.common

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.owasp.top.mobile.demo.databinding.DialogLoaderBinding

class DialogLoader(private val context: Context) {

    private var dialog: AlertDialog? = null

    fun show(message: String = "Loading...") {
        val builder = AlertDialog.Builder(context)
        val binding = DialogLoaderBinding.inflate(LayoutInflater.from(context))

        binding.loadingMessage.setText(message)
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }

    fun isLoading(show: Boolean) {
        if(show){
            show()
        } else {
            dismiss()
        }
    }
}