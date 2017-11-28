package com.rcgreed.easymoney.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.rcgreed.easymoney.entity.Money
import com.rcgreed.easymoney.entity.SEQ
import com.rcgreed.easymoney.entity.populateMoney
import com.rcgreed.easymoney.entity.toContentValues

/**
 * Created by arm on 2017-11-25.
 * class MoneyDao
 */
fun newMoneyDao(sqlHelper: SQLHelper): Dao<String, Money> {
    return object : AbstractDao<String, Money>() {
        override val total: Int get() = sqlHelper.countTable(table)


        override fun newPaging(selection: String?, selectionArgs: Array<String>?, limit: Int): Paging<Money> = object :AbstractPaging<Money>(){
            override val table: String ="t_money"
            override val selection: String? = selection
            override val limit: Int = limit
            override val selectionArgs: Array<String>? = selectionArgs
            override val db: SQLiteDatabase = sqlHelper.readableDatabase
            override val toObj: (Cursor) -> Money = ::populateMoney
        }

        override val table: String = "t_money"
        override fun add(v: Money): String {
            val cv = v.toContentValues()
            val seq = uniqueStringSeq()
            cv.put(SEQ, seq)
            sqlHelper.insert(table, cv)
            return seq
        }

        override fun update(v: Money) {
            sqlHelper.update(table, v.seq, v.toContentValues())
        }

        override fun load(key: String): Money = populateMoney(sqlHelper.single(table, key))

    }
}