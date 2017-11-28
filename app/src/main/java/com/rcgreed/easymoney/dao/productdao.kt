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
                override val table: String
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                override val selection: String?
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                override val limit: Int
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                override val selectionArgs: Array<String>?
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                override val db: SQLiteDatabase
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                override val toObj: (Cursor) -> Product
                    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            }
        }

        override val table: String = "t_product"

    }
}