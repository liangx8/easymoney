package com.rcgreed.easymoney


import com.rcgreed.easymoney.entity.styleUnderscore
import org.junit.Test

import org.junit.Assert.*


data class SampleInt(val x: String)
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun sortTest(){

        val target = mutableListOf<SampleInt>()
        target.add(SampleInt("ab"))
        target.add(SampleInt("cd"))
        target.add(SampleInt("ef"))
        val listInt = mutableListOf<SampleInt>()
        target.forEach{listInt.add(it)}

        listInt.sortedBy { it.x }.forEachIndexed{idx,value -> assertEquals(target[idx].x,value.x)}
    }
    @Test
    fun styleCamelTest(){
        val camel="expectRate"
        val underscore="expect_rate"
        assertEquals(underscore, styleUnderscore(camel))
    }
}
