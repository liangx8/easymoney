package com.rcgreed.easymoney.entity

import android.content.ContentValues
import android.database.Cursor

/**
 * Created by arm on 2017-11-24.
 * Entity of Money
 */


const val SEQ = "seq"
const val PRODUCT_CODE = "product_code"
const val AMOUNT = "amount"
const val CONTRACT_DATE = "contract_date"
const val ISSUE_DATE = "issue_date"
const val RETURN_DATE = "return_date"
const val EXPECT_RATE = "expect_rate"
const val ACTUAL_MARGIN = "actual_margin"
const val REMARK = "remark"

abstract class Entity<K> {
    abstract val contentValues: ContentValues
    abstract fun contentValuesWithSeq(key:K) :ContentValues
}

data class Money(
        val seq: String?,
        val productCode: String,
        val amount: Int,
        val contractDate: Long,
        var issueDate: Long?,
        var returnDate: Long?,
        val expectRate: Int,
        var actualMargin: Int?,
        var remark: String?
)
fun populateContentValue(src:Any):ContentValues{

    return when (src){
        is Money -> {
            val cv=ContentValues()
            cv.put(SEQ, src.seq)
            popContentValuesFromMoney(cv,src)
            cv
        }
        else -> throw RuntimeException("unsupported entity class ${src::class}")
    }
}
fun populateContentValuesWithKey(key:String,src:Any):ContentValues{
    return when (src){
        is Money ->{
            val cv=ContentValues()
            cv.put(SEQ, key)
            popContentValuesFromMoney(cv,src)
            cv
        } else ->throw RuntimeException("unsupported entity class ${src::class}")
    }
}
private fun popContentValuesFromMoney(cv:ContentValues,src:Money){
    cv.put(PRODUCT_CODE, src.productCode)
    cv.put(AMOUNT, src.amount)
    cv.put(CONTRACT_DATE, src.contractDate)
    cv.put(ISSUE_DATE, src.issueDate)
    cv.put(RETURN_DATE, src.returnDate)
    cv.put(EXPECT_RATE, src.expectRate)
    cv.put(ACTUAL_MARGIN, src.actualMargin)
    cv.put(REMARK, src.remark)
}
/*
private fun reflectIt(src:Any){
    val jclz=src.javaClass
    val fields=jclz.fields

}
*/
fun populateMoney(csr: Cursor): Money =
        Money(
                csr.getString(csr.getColumnIndex(SEQ)),
                csr.getString(csr.getColumnIndex(PRODUCT_CODE)),
                csr.getInt(csr.getColumnIndex(AMOUNT)),
                csr.getLong(csr.getColumnIndex(CONTRACT_DATE)),
                csr.getLong(csr.getColumnIndex(ISSUE_DATE)),
                csr.getLong(csr.getColumnIndex(RETURN_DATE)),
                csr.getInt(csr.getColumnIndex(EXPECT_RATE)),
                csr.getInt(csr.getColumnIndex(ACTUAL_MARGIN)),
                csr.getString(csr.getColumnIndex(REMARK))
        )
