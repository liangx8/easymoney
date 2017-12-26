package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor


/**
 * Created by arm on 2017-11-24.
 * generic basic Dao
 */

interface Dao<K, T> {
    fun add(v: T): K
    fun update(v: T)
    fun load(key: K): T
    val total: Int
    val gu: GenericUtils<K, T>
}

interface GenericUtils<K, T> {
    fun setSeq(v: T, key: K): T
    fun getSeq(v: T): K
    fun toContentValues(v: T): ContentValues
    fun fromCursor(cursor: Cursor): T
    fun listFromCursor(cursor:Cursor): List<T>
}


abstract class AbstractDao<K, T> : Dao<K, T> {
    protected abstract val table: String
    protected abstract val newSeq: () -> K
    protected abstract val sqlHelper: SQLHelper
    override fun add(v: T): K {
        val seq = newSeq()
        val cv = gu.toContentValues(gu.setSeq(v, seq))
        sqlHelper.insert(table, cv)
        return seq
    }

    override fun update(v: T) {
        val key = gu.getSeq(v)
        if (key is String)
            sqlHelper.update(table, key, gu.toContentValues(v))
        else
            throw RuntimeException("out of control")
    }

    override fun load(key: K): T {
        val c = if (key is String)
            sqlHelper.single(table, key)
        else
            throw RuntimeException("out of control")
        return gu.fromCursor(c)
    }
}
