package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rcgreed.easymoney.entity.CREATE_TABLE_MONEY


/**
 * Created by arm on 2017-11-24.
 * Derived from SQLiteOpenHelper
 */
const val DATABASE_NAME = "com.rcgreed.easymoney.db20171124"
const val DB_VERSION = 1

class SQLHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?)
            = if (db == null) throw RuntimeException("Why gave me a null object")
    else {
        db.execSQL("DROP TABLE IF EXISTS t_money")
        db.execSQL(CREATE_TABLE_MONEY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun countTable(table:String):Int{
        val cursor=readableDatabase.query(table, arrayOf("count(*)"),null,null,null,null,null)
        cursor.moveToNext()
        return cursor.getInt(0)
    }

    fun insert(table: String, value: ContentValues) {
        val affect = writableDatabase.insert(table, null, value)
        if (affect != 1L) {
            throw RuntimeException("insert $table error")
        }
    }

    fun single(table: String, key: String): Cursor {
        val csr = readableDatabase.query(table, null, "seq = ?", arrayOf(key), null, null, null)
        if (csr.count == 1) {
            return csr
        } else {
            throw SQLException("multiple results")
        }
    }

    fun update(table: String, seq: String, cv: ContentValues) {

        val affect = writableDatabase.update(table, cv, "seq = ?", arrayOf(seq))
        when (affect) {
            0 -> throw RuntimeException("Can't find $seq to update")
            1 -> return
            else -> throw RuntimeException("Multiple rows affected")
        }

    }
}