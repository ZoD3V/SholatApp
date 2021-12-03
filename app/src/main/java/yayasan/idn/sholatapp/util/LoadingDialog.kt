package yayasan.idn.sholatapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import yayasan.idn.sholatapp.R

class LoadingDialog(val mActivity:Activity) {
    private lateinit var isDialog: AlertDialog

    //set loadng
    @SuppressLint("InflateParams")
    fun startLoading(){
        //setview
        val infalter = mActivity.layoutInflater
        val dialog = infalter.inflate(R.layout.loading_item,null)

        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialog)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }

}