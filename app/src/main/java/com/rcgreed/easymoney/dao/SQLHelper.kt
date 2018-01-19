package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import com.rcgreed.easymoney.entity.*
import java.util.*


/**
 * Created by arm on 2017-11-24.
 * Derived from SQLiteOpenHelper
 */
const val DATABASE_NAME = "com.rcgreed.easymoney.db20171124"
const val DB_VERSION = 1

class SQLHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) {
            throw RuntimeException("Why gave me a null object")
        } else {
            rebuildDatabase(db)
        }
    }
    private fun rebuildDatabase(db : SQLiteDatabase){
        db.run {
            execSQL("DROP TABLE IF EXISTS $TABLE_TRAN_HEAD")
            execSQL(SQL_CREATE_TRAN_HEAD)
            execSQL("DROP TABLE IF EXISTS $TABLE_TRAN_BODY")
            execSQL(SQL_CREATE_TRAN_BODY)
            execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
            execSQL(SQL_CREATE_PRODUCT)

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun countTable(table: String): Int = intResult(table, arrayOf("COUNT(*)"),null,null)

    fun insert(table: String, value: ContentValues) {
        val rawId = writableDatabase.insertOrThrow(table, null, value)
        if (rawId == -1L) {
            throw RuntimeException("insert $table error")
        }
    }

    fun single(table: String, key: String): Cursor = singleRow(table,null, "$SEQ = ?", arrayOf(key))

    fun intResult(table: String,columns:Array<String>,selection:String?,selectArgs: Array<String>?): Int {
        return singleRow(table, columns,selection,selectArgs).getInt(0)
    }

    private fun singleRow(table:String,columns:Array<String>?,selection:String?,selectArgs:Array<String>?): Cursor{
        val csr=readableDatabase.query(table,columns,selection,selectArgs,null,null,null,null)
        if (csr.count == 1) {
            csr.moveToNext()
            return csr
        } else {
            throw SQLException("multiple results")
        }
    }

    fun update(table: String, seq: String, cv: ContentValues) {

        val affect = writableDatabase.update(table, cv, "$SEQ = ?", arrayOf(seq))
        when (affect) {
            0 -> throw RuntimeException("Can't find $seq to update")
            1 -> return
            else -> throw RuntimeException("Multiple rows affected")
        }
    }

    private val b = ByteArray(10)
    private val r = Random(Date().time)
    @Synchronized
    fun uniqueStringSeq(): String {
        r.nextBytes(b)
        return Base64.encodeToString(b, Base64.NO_CLOSE or Base64.NO_PADDING or Base64.NO_WRAP)
    }
    fun all(table:String,orderBy: String?) : Cursor = query(table,null,null,orderBy)

    fun query(table: String, selection: String?, selectionArgs: Array<String>?, orderBy: String?): Cursor =
            readableDatabase.query(table,null,selection,selectionArgs,null,null,orderBy)
}
