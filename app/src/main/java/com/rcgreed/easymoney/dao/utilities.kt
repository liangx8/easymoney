package com.rcgreed.easymoney.dao

import java.util.*

import android.util.Base64
/**
 * Created by arm on 17-11-25.
 */
@Synchronized
fun uniqueStringSeq():String{

        val b=ByteArray(10)
        val r= Random(Date().time)
        r.nextBytes(b)

        return Base64.encodeToString(b,Base64.NO_CLOSE or Base64.NO_PADDING or Base64.NO_WRAP )
}