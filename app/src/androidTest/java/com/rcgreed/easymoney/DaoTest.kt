package com.rcgreed.easymoney

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rcgreed.easymoney.dao.SQLHelper
import com.rcgreed.easymoney.dao.newProductDao
import com.rcgreed.easymoney.entity.Product
import com.rcgreed.easymoney.entity.SEQ
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by arm on 2017-11-29.
 * unit test
 */
@RunWith(AndroidJUnit4::class)
class DaoProductTest {
    private val sqlHelper = SQLHelper(InstrumentationRegistry.getTargetContext())
    private val dao = newProductDao(sqlHelper)
    private val data = ArrayList<String>()
    @Before
    fun prepare() {
        //sqlHelper.rebuildDatabase(sqlHelper.writableDatabase)
        (0 until 10).mapTo(data) { dao.add(Product("", PRODUCTCODE, "理财产品$it", 500, "Production description")) }

    }

    @After
    fun cleanup() {
        sqlHelper.writableDatabase.execSQL("DELETE FROM t_product WHERE product_code = ?", arrayOf(PRODUCTCODE))
    }

    @Test
    fun mainTest() {


        val products = dao.query("product_code = ?", arrayOf(PRODUCTCODE),"$SEQ")
        data.sortedBy { it }.forEachIndexed { index, value -> assertEquals("index: ($index)",value, products[index].seq) }
    }
}

const val PRODUCTCODE = "445234"