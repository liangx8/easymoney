package com.rcgreed.easymoney

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rcgreed.easymoney.dao.SQLHelper
import com.rcgreed.easymoney.dao.newMoneyDao
import com.rcgreed.easymoney.dao.uniqueStringSeq
import com.rcgreed.easymoney.entity.Money
import com.rcgreed.easymoney.entity.toContentValues
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
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
    private val data = ArrayList<Money>()
    @Before
    fun prepare() {
        val now = Date().time
        (0 until 10).mapTo(data) { Money(uniqueStringSeq(), "44934", it * 1000000, now, now, null, 500, null, null) }
        data.forEach { dao.add(it) }
        data.sortBy { it.seq }
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
        val moneys=dao.newPaging(null,null,10).page(0,"order by seq")
        data.forEachIndexed { index, money ->  assertEquals(money.seq,moneys[index].seq)}
    }
}
