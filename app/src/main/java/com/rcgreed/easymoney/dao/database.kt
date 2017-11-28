package com.rcgreed.easymoney.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


/**
 * Created by arm on 2017-11-24.
 * generic basic Dao
 */

interface Dao<K, T> {
    fun add(v: T): K
    fun update(v: T)
    fun load(key: K): T
    val total: Int
    /**
     * @selection don't include WHERE keyword
     */
    fun newPaging(selection: String?, selectionArgs: Array<String>?, limit: Int): Paging<T>
}

interface Paging<T> {
    fun page(start: Int, orderBy: String?): List<T>
    fun more(orderBy: String?): List<T>
    val current: Int
}

abstract class AbstractDao<K, T> : Dao<K, T> {
    protected abstract val table: String
}

abstract class AbstractPaging<T> : Paging<T> {
    abstract protected val table: String
    abstract protected val selection: String?
    abstract protected val limit: Int
    abstract protected val selectionArgs: Array<String>?
    abstract protected val db: SQLiteDatabase
    abstract protected val toObj: (Cursor) -> T
    override val current: Int
        get() = start
    private var start = 0
    override fun page(st: Int, orderBy: String?): List<T> {
        if (limit == 0) throw RuntimeException("zero limit is not allow")
        val sb = if (selection == null)
            StringBuffer("SELECT * FROM $table")
        else
            StringBuffer("SELECT * FROM $table WHERE $selection")
        if (orderBy != null) {
            sb.append(" ")
            sb.append(orderBy)
        }
        sb.append(" LIMIT $limit OFFSET $st")
        val cursor = db.rawQuery(sb.toString(), selectionArgs)
        val objList = ArrayList<T>()

        while (cursor.moveToNext()) {
            objList.add(toObj(cursor))
        }
        if (objList.size == 0) {
            throw RuntimeException("no more result")
        }
        start = st + objList.size
        return objList.toList()
    }

    override fun more(orderBy: String?): List<T> = page(start, orderBy)
}