package net.justinas.minilist.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecycleViewUtils {

    @JvmStatic
    fun lastVisibleItemPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        return layoutManager.findLastVisibleItemPosition()
    }
}