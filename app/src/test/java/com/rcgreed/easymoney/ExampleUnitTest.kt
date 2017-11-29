package com.rcgreed.easymoney

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun sortTest(){
        val target = listOf<Int>(2,3,5,9,30)
        val listInt = listOf<Int>(5,3,2,30,9)

        listInt.sortedBy { it }.forEachIndexed{idx,value -> assertEquals(target[idx],value)}

    }
}
