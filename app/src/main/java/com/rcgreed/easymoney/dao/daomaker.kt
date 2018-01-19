package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor
import com.rcgreed.easymoney.entity.*

/**
 * Created by arm on 2017-12-26.
 * Product Dao
 */
fun newProductDao(sqlHelper: SQLHelper): Dao<String, Product> =
        object : AbstractDao<String, Product>() {
            override fun cloneWithNewSeq(v: Product, key: String): Product = v.copy(seq = key)

            override fun getSeq(v: Product): String = v.seq

            override fun toContentValues(v: Product): ContentValues = v.toContentValues()

            override fun fromCursor(cursor: Cursor): Product = populateProduct(cursor)

            override val sqlHelper = sqlHelper

            override val newSeq: () -> String = sqlHelper::uniqueStringSeq

            override val table: String = "t_product"

            override val total = sqlHelper.countTable(table)
        }
fun newTranHeadDao(sqlHelper: SQLHelper): Dao<String, TranHead> =
        object : AbstractDao<String, TranHead>() {
            override fun cloneWithNewSeq(v: TranHead, key: String): TranHead = v.copy(seq = key)

            override fun getSeq(v: TranHead): String = v.seq

            override fun toContentValues(v: TranHead): ContentValues = v.toContentValues()

            override fun fromCursor(cursor: Cursor): TranHead = tranHeadFromCursor(cursor)

            override val sqlHelper = sqlHelper

            override val newSeq: () -> String = sqlHelper::uniqueStringSeq

            override val table: String = "t_tran_head"

            override val total = sqlHelper.countTable(table)
        }
fun newTranBodyDao(sqlHelper: SQLHelper): Dao<String, TranBody> =
        object : AbstractDao<String, TranBody>() {
            override fun cloneWithNewSeq(v: TranBody, key: String): TranBody = v.copy(seq = key)

            override fun getSeq(v: TranBody): String = v.seq

            override fun toContentValues(v: TranBody): ContentValues = v.toContentValues()

            override fun fromCursor(cursor: Cursor): TranBody = tranBodyFromCursor(cursor)


            override val sqlHelper = sqlHelper

            override val newSeq: () -> String = sqlHelper::uniqueStringSeq

            override val table: String = "t_tran_body"

            override val total = sqlHelper.countTable(table)
        }
