package com.tw.bottominputlikewhatsapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val TAG:String = this.javaClass.simpleName
    private var dialogShownOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvBottomOpenDialog = findViewById<TextView>(R.id.tvBottomOpenDialog)

        tvBottomOpenDialog.setOnClickListener {

            openBottomDialog()
        }

    }

    private fun openBottomDialog() {

            val dialog = Dialog( this@MainActivity )

            dialog.setCancelable(true)
            dialog.setContentView(R.layout.input_dialog_screen)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowlp = dialog.window?.attributes

            windowlp?.gravity = Gravity.BOTTOM
            dialog.window?.attributes = windowlp

            val tvSave: TextView = dialog.findViewById(R.id.tvSave)
            val tvCancel: TextView = dialog.findViewById(R.id.tvCancel)
            val etInputText: EditText = dialog.findViewById(R.id.etInputText)

            tvSave.setOnClickListener {

                if (etInputText.text.toString().isEmpty()){
                    etInputText.error = "Enter updated name"
                }else{
                    dialog.dismiss()
                    dialogShownOnce = true
                }
            }

            tvCancel.setOnClickListener {
                dialog.dismiss()
                dialogShownOnce = true
            }


            if (!dialog.isShowing) {
                dialog.show()
                //  dialogShownOnce = true
            }
    }


}