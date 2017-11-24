package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by arm on 2017-11-24.
 * Derived from SQLiteOpenHelper
 */
const val DATABASE_NAME = "com.rcgreed.easymoney.db20171124"
const val DB_VERSION = 1

class SQLHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null){
            throw RuntimeException("Why gave me a null object")
        } else {
            db.execSQL("")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun insert(table:String,value:ContentValues){
        writableDatabase.insert(table,null,value);
    }
    fun single(table: String,key:String):Cursor{
        val csr=readableDatabase.query(table,null,"seq = ?", arrayOf(key),null,null,null)
        if(csr.count==1){
            return csr
        } else {
            throw SQLException("multiple results")
        }
    }

}