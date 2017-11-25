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
}

abstract class AbstractDao<K, T>(private val sqlHelper: SQLHelper) : Dao<K, T> {
    protected abstract val table: String
    protected abstract val populateObject: (Cursor) -> T
    protected abstract val newKey: () -> K

    override fun add(v: T): K {

        val key=newKey()
        when (key){
            is String -> cv.put(SEQ,key)
            is Long -> cv.put(SEQ,key)
            is Int -> cv.put(SEQ,key)

            else -> throw RuntimeException(message = "unsupported seq class")
        }
        sqlHelper.insert(table, cv)
        return key
    }

    override fun update(v: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(key: K): T {
        val csr = if (key is String)
            sqlHelper.single(table, key)
        else
            sqlHelper.single(table, key.toString())
        return populateObject(csr)
    }
}
fun newMoneyDao(sqlHelper: SQLHelper):AbstractDao<String,Money>{
    return object : AbstractDao<String, Money>(sqlHelper) {
        override val table: String = "t_money"
        override val populateObject: (Cursor) -> Money = ::populateMoney
        override val newKey: () -> String = ::newStringKey
    }
}
@Synchronized
fun newStringKey(): String {
    val uuid = UUID.randomUUID()
    val baOutputStream=ByteArrayOutputStream()

    return Base64.encodeToString(baOutputStream.toByteArray(), Base64.DEFAULT).substring(0, 22)
}