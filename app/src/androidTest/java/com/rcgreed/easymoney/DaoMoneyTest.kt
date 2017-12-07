package com.rcgreed.easymoney

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rcgreed.easymoney.dao.SQLHelper
import com.rcgreed.easymoney.dao.newMoneyDao
import com.rcgreed.easymoney.entity.Money
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DaoMoneyTest {
    private val sqlHelper = SQLHelper(InstrumentationRegistry.getTargetContext())
    private val dao = newMoneyDao(sqlHelper)
    private val data = mutableListOf<String>()
    @Before
    fun prepare() {
        val now = Date().time
        (0 until 10).mapTo(data){
            dao.add(Money(seq = "",
                    productCode = "44934",
                    amount = it * 1000000,
                    contractDate = now,
                    issueDate = now,
                    returnDate = null,
                    expectRate = 500,
                    actualMargin = null,
                    remark = null))
        }
    }

    @After
    fun cleanup() {
        sqlHelper.writableDatabase.execSQL("DELETE FROM t_money")

    }

    @Test
    fun useAppContext() {

        assertEquals(data.size, dao.total)

        val paging = dao.newPaging(null, null, 4)
        assertEquals(4, paging.page(0, null).size)
        assertTrue(paging.current < dao.total)
        if (paging.current <= dao.total) {
            paging.more(null)
        }

        val moneys = dao.newPaging(null, null, 10).page(0, "order by seq")

        data.sortedBy { it }.forEachIndexed { idx, seq -> assertEquals(seq, moneys[idx].seq) }
    }
}
