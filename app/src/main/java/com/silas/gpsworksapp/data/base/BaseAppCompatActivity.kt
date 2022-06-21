package com.silas.gpsworksapp.data.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.silas.gpsworksapp.util.extensions.provideProgressDialog

open class BaseAppCompatActivity : AppCompatActivity() {
    private val progressDialog: Dialog by lazy {
        provideProgressDialog()
    }

    fun showProgressDialog() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    fun dismissProgressDialog() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}