package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import com.rcgreed.easymoney.entity.CREATE_TABLE_MONEY
import com.rcgreed.easymoney.entity.CREATE_TABLE_PRODUCT
import java.util.*


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

        db.run {
            execSQL("DROP TABLE IF EXISTS t_money")
            execSQL(CREATE_TABLE_MONEY)
            execSQL("DROP TABLE IF EXISTS t_product")
            execSQL(CREATE_TABLE_PRODUCT)
        }
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
        val rawId = writableDatabase.insertOrThrow(table, null, value)
        if (rawId == -1L) {
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

    fun intResult(sql: String,selectArgs: Array<String>):Int {
        return singleRow(sql,selectArgs).getInt(0)
    }

    private fun singleRow(sql: String, selectArgs: Array<String>): Cursor {
        val csr = readableDatabase.rawQuery(sql,selectArgs)
        if (csr.count == 1){
            csr.moveToNext()
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
    val b=ByteArray(10)
    val r= Random(Date().time)
    @Synchronized
    fun uniqueStringSeq():String{
        r.nextBytes(b)
        return Base64.encodeToString(b, Base64.NO_CLOSE or Base64.NO_PADDING or Base64.NO_WRAP )
    }}
