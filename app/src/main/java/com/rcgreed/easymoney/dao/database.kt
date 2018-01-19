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
    fun query(selection:String?,selectionArgs:Array<String>?,orderBy:String?):List<T>
    val total: Int
}

interface GenericUtils<K, T> {
    fun cloneWithNewSeq(v: T, key: K): T
    fun getSeq(v: T): K
    fun toContentValues(v: T): ContentValues
    fun fromCursor(cursor: Cursor): T
    fun listFromCursor(cursor: Cursor): List<T>
}


abstract class AbstractDao<K, T> : Dao<K, T>, GenericUtils<K, T> {

    protected abstract val table: String
    protected abstract val newSeq: () -> K
    protected abstract val sqlHelper: SQLHelper


    override fun listFromCursor(cursor: Cursor): List<T> = Iterable {
        object : Iterator<T> {
            override fun hasNext(): Boolean = cursor.moveToNext()
            override fun next(): T = fromCursor(cursor)
        }
    }.toList()

    override fun add(v: T): K {
        val seq = newSeq()
        val cv = toContentValues(cloneWithNewSeq(v, seq))
        sqlHelper.insert(table, cv)
        return seq
    }

    override fun update(v: T) {
        val key = getSeq(v)
        if (key is String)
            sqlHelper.update(table, key, toContentValues(v))
        else
            sqlHelper.update(table, key.toString(), toContentValues(v))
    }

    override fun load(key: K): T {
        val c = if (key is String)
            sqlHelper.single(table, key)
        else
            sqlHelper.single(table, key.toString())
        return fromCursor(c)
    }

    override fun query(selection: String?, selectionArgs: Array<String>?, orderBy: String?): List<T> {
        return listFromCursor(sqlHelper.query(table,selection,selectionArgs,orderBy))
    }
}
