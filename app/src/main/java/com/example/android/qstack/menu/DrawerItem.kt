package com.example.android.qstack.menu

import android.view.ViewGroup
import com.example.android.qstack.menu.DrawerAdapter


abstract class DrawerItem<T : DrawerAdapter.ViewHolder> {

    var isChecked: Boolean = false
    open var isSelectable: Boolean = true

    fun setChecked(isChecked: Boolean): DrawerItem<T> {
        this.isChecked = isChecked
        return this
    }

    abstract fun createViewHolder(parent: ViewGroup): T

    abstract fun bindViewHolder(holder: T)
}
