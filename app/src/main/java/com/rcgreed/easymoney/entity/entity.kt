package com.rcgreed.easymoney.entity

import android.content.ClipDescription
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
const val CREATE_TABLE_MONEY = "CREATE TABLE t_money ($SEQ TEXT,$PRODUCT_CODE TEXT,$AMOUNT INT,$CONTRACT_DATE INT,$ISSUE_DATE INT,$RETURN_DATE INT,$EXPECT_RATE INT,$ACTUAL_MARGIN INT,$REMARK TEXT)"

data class Money(
        val seq: String,
        val productCode: String,
        val amount: Int,
        val contractDate: Long,
        var issueDate: Long?,
        var returnDate: Long?,
        val expectRate: Int,
        var actualMargin: Int?,
        var remark: String?
)

fun Money.toContentValues(): ContentValues {
    val cv = ContentValues()
    cv.put(SEQ, this.seq)
    popContentValuesFromMoney(cv, this)
    return cv
}

private fun popContentValuesFromMoney(cv: ContentValues, src: Money) {
    cv.put(PRODUCT_CODE, src.productCode)
    cv.put(AMOUNT, src.amount)
    cv.put(CONTRACT_DATE, src.contractDate)
    cv.put(ISSUE_DATE, src.issueDate)
    cv.put(RETURN_DATE, src.returnDate)
    cv.put(EXPECT_RATE, src.expectRate)
    cv.put(ACTUAL_MARGIN, src.actualMargin)
    cv.put(REMARK, src.remark)
}

fun populateMoney(csr: Cursor): Money =
        Money(
                seq = csr.getString(csr.getColumnIndex(SEQ)),
                productCode = csr.getString(csr.getColumnIndex(PRODUCT_CODE)),
                amount = csr.getInt(csr.getColumnIndex(AMOUNT)),
                contractDate = csr.getLong(csr.getColumnIndex(CONTRACT_DATE)),
                issueDate = csr.getLong(csr.getColumnIndex(ISSUE_DATE)),
                returnDate = csr.getLong(csr.getColumnIndex(RETURN_DATE)),
                expectRate = csr.getInt(csr.getColumnIndex(EXPECT_RATE)),
                actualMargin = csr.getInt(csr.getColumnIndex(ACTUAL_MARGIN)),
                remark = csr.getString(csr.getColumnIndex(REMARK))
        )

/**
 * Entity of Product
 */
const val PRODUCT_NAME = "product_name"
const val AVERAGE_RATE = "average_rate"
const val DESCRIPTION = "description"

data class Product(
        val seq: String,
        // 产品代码
        val productCode: String,
        // 产品名称
        val productName: String,
        // 平均利率
        var averageRate: Int,
        var description: String?
)

const val CREATE_TABLE_PRODUCT = "CREATE TABLE t_product ($SEQ TEXT,$PRODUCT_CODE TEXT,$PRODUCT_NAME TEXT,$AVERAGE_RATE INT,$DESCRIPTION TEXT)"

fun Product.toContentValues(): ContentValues {
    val cv = ContentValues()
    cv.put(SEQ, this.seq)
    popuContentValueFromProduct(cv, this)
    return cv
}

private fun popuContentValueFromProduct(cv: ContentValues, p: Product) {
    cv.put(PRODUCT_CODE, p.productCode)
    cv.put(PRODUCT_NAME, p.productName)
    cv.put(AVERAGE_RATE, p.averageRate)
    cv.put(DESCRIPTION, p.description)
}

fun populateProduct(csr: Cursor): Product = Product(
        seq = csr.getString(csr.getColumnIndex(SEQ)),
        productCode = csr.getString(csr.getColumnIndex(PRODUCT_CODE)),
        productName = csr.getString(csr.getColumnIndex(PRODUCT_NAME)),
        averageRate = csr.getInt(csr.getColumnIndex(AVERAGE_RATE)),
        description = csr.getString(csr.getColumnIndex(DESCRIPTION))
)