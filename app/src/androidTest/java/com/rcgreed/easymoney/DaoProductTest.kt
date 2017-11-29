package com.rcgreed.easymoney

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rcgreed.easymoney.dao.Paging
import com.rcgreed.easymoney.dao.SQLHelper

import com.rcgreed.easymoney.dao.newProductDao
import com.rcgreed.easymoney.dao.uniqueStringSeq

import com.rcgreed.easymoney.entity.Product
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.ArrayList

/**
 * Created by arm on 17-11-29.
 */
@RunWith(AndroidJUnit4::class)
class DaoProductTest {
    private val sqlHelper = SQLHelper(InstrumentationRegistry.getTargetContext())
    private val dao = newProductDao(sqlHelper)
    private val data = ArrayList<Product>()
    @Before
    fun prepare(){
        if (dao.total==0) {
            (0 until 10).mapTo(data) { Product(uniqueStringSeq(), "44395", "理财产品$it", 500, "Production description") }
            data.forEach { dao.add(it) }
        } else {
            data.addAll(dao.newPaging(null,null,10).page(0,null))
        }
    }
    @After
    fun cleanup(){
        //sqlHelper.writableDatabase.execSQL("DELETE FROM t_product")
    }
    @Test
    fun mainTest(){
        val products=dao.newPaging(null,null,data.size).page(0,"order by seq")
        data.sortedBy { it.seq }.forEachIndexed { index, product ->  assertEquals(product.seq,products[index].seq)}
    }
}