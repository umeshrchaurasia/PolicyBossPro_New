package com.policyboss.policybosspro.homeMainKotlin

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object Util {



    @JvmStatic  fun bitmapToBase64(bitmap: Bitmap): String {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}