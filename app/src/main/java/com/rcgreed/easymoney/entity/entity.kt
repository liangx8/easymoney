package com.rcgreed.easymoney.entity

/**
 * Created by arm on 2017-11-24.
 * Entity of Money
 */


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