package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor
import android.util.Base64
import com.rcgreed.easymoney.entity.*
import java.io.ByteArrayOutputStream
import java.util.*


/**
 * Created by arm on 2017-11-24.
 * generic basic Dao
 */

interface Dao<K, T> {
    fun add(v: T): K
    fun update(v: T)
    fun load(key: K): T
    fun total():Int
    fun newPaging(selection:String,selectionArgs:Array<String>,orderBy:String,limit:Int):Paging<T>
}

interface Paging<T>{
    val limit:Int
    fun page(start:Int):Array<T>
    fun more() :Array<T>
    fun taggleOrder()
}
abstract class AbstractDao<K,T>:Dao<K,T>{
    protected abstract val table:String
}