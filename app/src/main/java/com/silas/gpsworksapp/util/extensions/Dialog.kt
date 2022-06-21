package com.silas.gpsworksapp.util.extensions

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.silas.gpsworksapp.R

fun Context.provideProgressDialog(text: String? = getString(R.string.dialog_loading)): Dialog {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.setContentView(R.layout.dialog_loading)

    val loadingMessage = dialog.findViewById<TextView?>(R.id.loadingDialog_message)
    loadingMessage?.text = text

    return dialog
}