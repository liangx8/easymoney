package com.rcgreed.easymoney

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.rcgreed.easymoney.adapter.AdapterModel
import com.rcgreed.easymoney.adapter.TopAdapter
import com.rcgreed.easymoney.dao.SQLHelper
import com.rcgreed.easymoney.dao.newMoneyDao
import com.rcgreed.easymoney.dao.newProductDao
import com.rcgreed.easymoney.entity.Money
import com.rcgreed.easymoney.entity.Product

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val sqlHelper by lazy { SQLHelper(this@MainActivity) }
    private val moneyDao by lazy { newMoneyDao(sqlHelper) }
    private val productDao by lazy { newProductDao(sqlHelper) }

    private val adapter: TopAdapter by lazy {
        /*
        lazy(()->T),延时初始变量的用法，十分有用
         */
        TopAdapter(this, object : AdapterModel<Product, Money> {
            override val groupCount: Int = productDao.total
            override fun getChildCount(groupIdx: Int): Int = sqlHelper.intResult("SELECT COUNT(*) FROM t_money WHERE product = ?", arrayOf(getGroup(groupIdx).productCode))

            override fun getGroup(idx: Int): Product {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getChild(groupIdx: Int, childIdx: Int): Money {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        list_top.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }
}
