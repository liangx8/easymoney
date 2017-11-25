package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor
import android.util.Base64
import com.rcgreed.easymoney.entity.Money
import com.rcgreed.easymoney.entity.populateMoney
import java.util.*


/**
 * Created by arm on 2017-11-24.
 * generic basic Dao
 */

interface Dao<K, T> {
    fun save(v: T): K
    fun load(key: K): T
}

abstract class AbstractDao<K, T>(private val sqlHelper: SQLHelper) : Dao<K, T> {
    protected abstract val table: String
    protected abstract val populateObject: (Cursor) -> T
    protected abstract val newKey: () -> K
    override fun save(v: T): K {
        sqlHelper.insert(table, ContentValues())
        return newKey()
    }

    override fun load(key: K): T {
        val csr = if (key is String)
            sqlHelper.single(table, key)
        else
            sqlHelper.single(table, key.toString())
        return populateObject(csr)
    }
}

class MoneyDao (sqlHelper: SQLHelper) : AbstractDao<String, Money>(sqlHelper) {
    override val populateObject: (Cursor) -> Money = ::populateMoney
    override val newKey: () -> String = ::newStringKey
    override val table = "t_money"
}

fun newStringKey(): String {
    val uuid = UUID.randomUUID()
    return Base64.encodeToString(null, Base64.DEFAULT).substring(0, 22)
}