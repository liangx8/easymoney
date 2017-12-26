package com.rcgreed.easymoney.adapter

/**
 * Created by arm on 17-12-8.
 */
interface AdapterModel<G, C> {
    val groupCount:Int
    fun getChildCount(groupIdx: Int) :Int
    fun getGroup(idx: Int): G
    fun getChild(groupIdx: Int,childIdx:Int) : C
    fun notifyChange()
}