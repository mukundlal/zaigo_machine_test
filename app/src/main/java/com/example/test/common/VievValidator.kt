package com.example.test.common

import android.opengl.Visibility
import android.view.View
import android.widget.EditText


//Extension functions

fun EditText.isValidInput():Boolean
{
    return if (this.text.isEmpty())
    {
        this.error = "${this.tag} can not be empty"
        false
    }else
        true
}
fun View.hide()
{
    this.visibility=View.GONE
}
fun View.show()
{
    this.visibility=View.VISIBLE
}
