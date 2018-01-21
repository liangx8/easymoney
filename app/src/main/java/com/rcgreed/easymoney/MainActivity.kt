package com.rcgreed.easymoney

import android.app.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rcgreed.easymoney.adapter.AdapterModel
import com.rcgreed.easymoney.adapter.TopAdapter
import com.rcgreed.easymoney.dao.SQLHelper
import com.rcgreed.easymoney.dao.newProductDao
import com.rcgreed.easymoney.dao.newTranBodyDao
import com.rcgreed.easymoney.dao.newTranHeadDao
import com.rcgreed.easymoney.entity.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val sqlHelper by lazy { SQLHelper(this@MainActivity) }
    private val tranHeadDao by lazy { newTranHeadDao(sqlHelper) }
    private val tranBodyDao by lazy { newTranBodyDao(sqlHelper) }
    private val productDao by lazy { newProductDao(sqlHelper) }
    private val adapter: TopAdapter by lazy {
        /*
        lazy(()->T),延时初始变量的用法，十分有用
         */
        TopAdapter(this, object : AdapterModel<Product, TranHeadThumbnail> {
            override val groupCount: Int = tranHeadDao.total


            override fun notifyChange() {

                heads = productDao.query(null, null, null)
                children.clear()
            }

            private var heads: List<Product> = productDao.query(null, null, null)


            private val children: SparseArray<List<TranHeadThumbnail>> = SparseArray<List<TranHeadThumbnail>>()


            override fun getChildCount(groupIdx: Int): Int {
                val list =
                        if (children.indexOfKey(groupIdx) < 0) {
                            val t = tranHeadDao.query("$PRODUCT_CODE = ?", arrayOf(heads[groupIdx].productCode), null)
                                    .map(this@MainActivity::transfer)
                                    .sortedBy { it.latestUpdate }
                            children.put(groupIdx, t)
                            t
                        } else {
                            children[groupIdx]
                        }
                return list.size
            }

            override fun getGroup(idx: Int): Product = heads[idx]

            override fun getChild(groupIdx: Int, childIdx: Int): TranHeadThumbnail {
                return children[groupIdx][childIdx]

            }
        })
    }

    private fun transfer(src: TranHead): TranHeadThumbnail {
        val body = tranBodyDao.query("$HEAD_SEQ = ?", arrayOf(src.seq), null)
        val inSum = body.sumBy {
            if (it.amount > 0)
                it.amount
            else
                0
        }
        val outSum = body.sumBy {
            if (it.amount < 0)
                -it.amount
            else
                0
        }
        val tt: TranBody? = body.maxBy { it.issueDate }

        return TranHeadThumbnail(
                head = src,
                inAmount = inSum,
                outAmount = outSum,
                latestUpdate = tt?.issueDate ?: 0
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tablayout.addTab(tablayout.newTab().setText(R.string.tab1_title))
        tablayout.addTab(tablayout.newTab().setText(R.string.tab2_title))
        pager.adapter=PagerAdapter(supportFragmentManager)
        //pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        pager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                Log.d("page","$position")
            }
        })

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem=tab?.position ?:0
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TRANSACTION_ENTRY_RESULT_CODE) {

            when (resultCode) {
                Activity.RESULT_OK -> Toast.makeText(this, "OK", Toast.LENGTH_LONG)
                Activity.RESULT_CANCELED -> Toast.makeText(this, "Cancel", Toast.LENGTH_LONG)
                else -> Toast.makeText(this, "Nothing", Toast.LENGTH_LONG)
            }.show()

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

const val TRANSACTION_ENTRY_RESULT_CODE = 1

class PagerAdapter(fm: FragmentManager ) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            else -> throw RuntimeException("impossible $position")
        }
    }
    override fun getCount(): Int = 2
}

class Tab1Fragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.tabfragment1,container,false)
    }
}
class Tab2Fragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.tabfragment2,container,false)
    }
}