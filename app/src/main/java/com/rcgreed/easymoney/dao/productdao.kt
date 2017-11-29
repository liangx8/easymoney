package com.rcgreed.easymoney.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.rcgreed.easymoney.entity.Product
import com.rcgreed.easymoney.entity.SEQ
import com.rcgreed.easymoney.entity.populateProduct
import com.rcgreed.easymoney.entity.toContentValues

/**
 * Created by arm on 17-11-25.
 */
fun newProductDao(sqlHelper: SQLHelper):Dao<String,Product>{
    return object :AbstractDao<String,Product>(){
        override fun add(v: Product): String {
            val cv=v.toContentValues()
            val seq= uniqueStringSeq()
            cv.put(SEQ,seq)
            sqlHelper.insert(table,cv)
            return seq
        }

        override fun update(v: Product) {
            sqlHelper.update(table,v.seq,v.toContentValues());
        }

        override fun load(key: String): Product = populateProduct(sqlHelper.single(table,key))

        override val total: Int get()=sqlHelper.countTable(table)

        override fun newPaging(selection: String?, selectionArgs: Array<String>?, limit: Int): Paging<Product> {
            return object :AbstractPaging<Product>(){
                override val table = "t_product"
                override val selection: String? = selection
                override val limit: Int = limit
                override val selectionArgs: Array<String>? = selectionArgs
                override val db: SQLiteDatabase = sqlHelper.readableDatabase
                override val toObj: (Cursor) -> Product = ::populateProduct
            }
        }

        override val table: String = "t_product"

    }
}