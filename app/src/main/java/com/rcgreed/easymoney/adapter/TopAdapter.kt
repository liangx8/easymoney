package com.rcgreed.easymoney.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.rcgreed.easymoney.entity.Money
import com.rcgreed.easymoney.entity.Product

/**
 * Created by arm on 17-12-8.
 */
class TopAdapter(private val ctx:Context,private val model: AdapterModel<Product,Money>): BaseExpandableListAdapter() {


    override fun getGroup(p0: Int): Any =Any()

    override fun isChildSelectable(p0: Int, p1: Int): Boolean = false

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val v :TextView = if(convertView !=null){
            convertView as TextView
        } else {
            TextView(ctx)
        }
        v.text=model.getGroup(groupPosition).productName
        return v
    }

    override fun getChildrenCount(groupPosition: Int): Int = model.getChildCount(groupPosition)

    override fun getChild(p0: Int, p1: Int): Any = Any()

    override fun getGroupId(p0: Int): Long = 0

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val v :TextView = if(convertView !=null){
            convertView as TextView
        } else {
            TextView(ctx)
        }
        v.text=model.getChild(groupPosition,childPosition).seq
        return v
    }

    override fun getChildId(p0: Int, p1: Int): Long = 0

    override fun getGroupCount(): Int = model.groupCount
}
