package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor

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
    override fun save(v: T): K {
        sqlHelper.insert(table, ContentValues())
        return newKey()
    }

    override fun load(key: K): T {
        val csr = if (key is String)
            sqlHelper.single(table, key)
        else
            sqlHelper.single(table, key.toString())
        val holder = newHolder()
        populateFromCursor(csr, holder as Any)
        return holder
    }

    abstract protected fun newKey(): K
    abstract protected fun newHolder(): T
}