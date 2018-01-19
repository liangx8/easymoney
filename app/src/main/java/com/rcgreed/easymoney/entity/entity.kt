package com.rcgreed.easymoney.entity


import android.content.ContentValues
import android.database.Cursor

/**
 * Created by arm on 2017-11-24.
 * Entity of Money
 */

const val TABLE_MONEY = "t_money"
const val SEQ = "seq"
const val PRODUCT_CODE = "product_code"
const val AMOUNT = "amount"
const val CONTRACT_DATE = "contract_date"
const val ISSUE_DATE = "issue_date"
const val RETURN_DATE = "return_date"
const val ACTUAL_MARGIN = "actual_margin"
const val REMARK = "remark"
const val CLOSED = "closed"
const val HEAD_SEQ = "head_seq"
const val TAG = "tag"
const val VALID = "valid"
@Deprecated("will be remove")
const val CREATE_TABLE_MONEY = ""


const val TABLE_TRAN_HEAD = "t_tran_head"
const val EXPECT_RATE = "expect_rate"
const val CYCLE_DAY = "cycle_day"
const val CREATED_TIME = "created_time"
const val TABLE_TRAN_BODY = "t_tran_body"
/**
 * Entity of Product
 */
const val TABLE_PRODUCT = "t_product"
const val PRODUCT_NAME = "product_name"
const val AVERAGE_RATE = "average_rate"
const val DESCRIPTION = "description"


const val SQL_CREATE_TRAN_BODY = "CREATE TABLE $TABLE_TRAN_BODY ($AMOUNT INT,$HEAD_SEQ TEXT,$ISSUE_DATE INT,$SEQ TEXT,$TAG TEXT,$VALID INT,$REMARK TEXT)"
const val SQL_CREATE_TRAN_HEAD = "CREATE TABLE $TABLE_TRAN_HEAD ($SEQ TEXT,$EXPECT_RATE INT, $PRODUCT_CODE TEXT,$CYCLE_DAY INT,$CONTRACT_DATE INT, $REMARK TEXT, $CREATED_TIME INT, $CLOSED INT)"
const val SQL_CREATE_PRODUCT = "CREATE TABLE $TABLE_PRODUCT ($SEQ TEXT,$PRODUCT_CODE TEXT,$PRODUCT_NAME TEXT,$AVERAGE_RATE INT,$DESCRIPTION TEXT)"

fun styleUnderscore(src: String): String {
    val b = StringBuilder()
    src.forEach {
        if (it.isUpperCase()) {
            b.append("_")
            b.append(it.toLowerCase())
        } else {
            b.append(it)
        }
    }
    return b.toString()
}

data class TranHead(
        val seq: String,
        val expectRate: Int, // 450 代表4.5%
        val productCode: String,
        val cycleDay: Int, // 天
        val contractDate: Long,
        val remark: String?,
        val createdTime: Long,
        val closed: Boolean
)

fun TranHead.toContentValues(): ContentValues {
    val cv = ContentValues()
    cv.put(SEQ, seq)
    cv.put(EXPECT_RATE, expectRate)
    cv.put(PRODUCT_CODE, productCode)
    cv.put(CYCLE_DAY, cycleDay)
    cv.put(CONTRACT_DATE, contractDate)
    if (remark != null)
        cv.put(REMARK, remark)
    cv.put(CREATED_TIME, createdTime)
    cv.put(CLOSED, closed)
    return cv
}

fun tranHeadFromCursor(cursor: Cursor): TranHead = TranHead(
        seq = cursor.getString(cursor.getColumnIndex(SEQ)),
        expectRate = cursor.getInt(cursor.getColumnIndex(EXPECT_RATE)),
        productCode = cursor.getString(cursor.getColumnIndex(PRODUCT_CODE)),
        cycleDay = cursor.getInt(cursor.getColumnIndex(CYCLE_DAY)),
        contractDate = cursor.getLong(cursor.getColumnIndex(CONTRACT_DATE)),
        remark = cursor.getString(cursor.getColumnIndex(REMARK)),
        createdTime = cursor.getLong(cursor.getColumnIndex(CREATED_TIME)),
        closed = cursor.getInt(cursor.getColumnIndex(CLOSED)) != 0
)

data class TranHeadThumbnail(
        val head: TranHead,
        val inAmount: Int,
        val outAmount: Int,
        val latestUpdate: Long
)

data class TranBody(
        val seq: String,
        val headSeq: String,
        val issueDate: Long,
        val tag: String,
        val valid: Boolean, // true 实际交易，false 无金额交易
        val remark: String?,
        val amount: Int// 100 代表1元, 用正/负来表示收入或者支出

)

fun TranBody.toContentValues(): ContentValues {
    val cv = ContentValues()
    cv.put(SEQ, seq)
    cv.put(HEAD_SEQ, headSeq)
    cv.put(ISSUE_DATE, issueDate)
    cv.put(TAG, tag)
    cv.put(VALID, valid)
    if (remark != null)
        cv.put(REMARK, remark)
    cv.put(AMOUNT, amount)
    return cv
}

fun tranBodyFromCursor(cursor: Cursor): TranBody = TranBody(
        seq = cursor.getString(cursor.getColumnIndex(SEQ)),
        headSeq = cursor.getString(cursor.getColumnIndex(HEAD_SEQ)),
        issueDate = cursor.getLong(cursor.getColumnIndex(ISSUE_DATE)),
        tag = cursor.getString(cursor.getColumnIndex(TAG)),
        valid = cursor.getInt(cursor.getColumnIndex(VALID)) != 0,
        remark = cursor.getString(cursor.getColumnIndex(REMARK)),
        amount = cursor.getInt(cursor.getColumnIndex(AMOUNT))
)

data class Product(
        val seq: String,
        // 产品代码
        val productCode: String,
        // 产品名称
        val productName: String,
        // 平均利率
        val averageRate: Int,
        val description: String?
)

fun Product.toContentValues(): ContentValues {
    val cv = ContentValues()
    cv.put(SEQ, seq)
    cv.put(PRODUCT_CODE, productCode)
    cv.put(PRODUCT_NAME, productName)
    cv.put(AVERAGE_RATE, averageRate)
    if (description != null)
        cv.put(DESCRIPTION, description)
    return cv
}


fun populateProduct(csr: Cursor): Product = Product(
        seq = csr.getString(csr.getColumnIndex(SEQ)),
        productCode = csr.getString(csr.getColumnIndex(PRODUCT_CODE)),
        productName = csr.getString(csr.getColumnIndex(PRODUCT_NAME)),
        averageRate = csr.getInt(csr.getColumnIndex(AVERAGE_RATE)),
        description = csr.getString(csr.getColumnIndex(DESCRIPTION))
)