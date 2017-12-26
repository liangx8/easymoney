package com.rcgreed.easymoney.dao

import android.content.ContentValues
import android.database.Cursor
import com.rcgreed.easymoney.entity.*

/**
 * Created by arm on 2017-12-26.
 */
fun newProductDao(sqlHelper: SQLHelper): Dao<String, Product> {
    return object : AbstractDao<String, Product>() {
        override val gu: GenericUtils<String, Product>
            get() = object : GenericUtils<String, Product> {
                override fun listFromCursor(cursor: Cursor): List<Product> {
                    val itr = object : Iterator<Product>{
                        override fun hasNext(): Boolean {
                            return cursor.moveToNext()
                        }

                        override fun next(): Product {
                            return fromCursor(cursor)
                        }

                    }
                    return Iterable { itr }.toList()
                }

                override fun setSeq(v: Product, key: String): Product =
                        Product(seq = key, productCode = v.productCode, productName = v.productName, averageRate = v.averageRate, description = v.description)

                override fun getSeq(v: Product): String = v.seq

                override fun toContentValues(v: Product): ContentValues = v.toContentValues()

                override fun fromCursor(cursor: Cursor): Product = populateProduct(cursor)
            }
        override val sqlHelper = sqlHelper
        override val newSeq: () -> String = sqlHelper::uniqueStringSeq
        override val total: Int get() = sqlHelper.countTable(table)


        override val table: String = "t_product"

    }
}

fun newMoneyDao(sqlHelper: SQLHelper): Dao<String, Money> {
    return object : AbstractDao<String, Money>() {
        override val gu: GenericUtils<String, Money>
            get() = object : GenericUtils<String,Money> {
                override fun listFromCursor(cursor: Cursor): List<Money> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun setSeq(v: Money, key: String): Money =
                        Money(seq = key,productCode = v.productCode,amount = v.amount,contractDate = v.contractDate,issueDate = v.issueDate,returnDate = v.returnDate,expectRate = v.expectRate,actualMargin = v.actualMargin,remark = v.remark)

                override fun getSeq(v: Money): String = v.seq

                override fun toContentValues(v: Money)= v.toContentValues()

                override fun fromCursor(cursor: Cursor)= populateMoney(cursor)

            }
        override val sqlHelper = sqlHelper
        override val newSeq: () -> String = sqlHelper::uniqueStringSeq
        override val total: Int get() = sqlHelper.countTable(table)
        override val table: String = "t_money"

    }
}