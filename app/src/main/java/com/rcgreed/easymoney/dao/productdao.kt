package com.rcgreed.easymoney.dao

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

        override fun total(): Int =sqlHelper.countTable(table)

        override fun newPaging(selection: String, selectionArgs: Array<String>, orderBy: String, limit: Int): Paging<Product> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override val table: String = "t_product"

    }
}