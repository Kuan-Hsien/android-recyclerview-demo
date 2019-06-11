package com.kuanhsien.app.sample.android_recyclerview_demo.data

class DemoItemRepository {

    // return List with "size" items from "fromIndex"
    fun getItemList(size: Int, fromIndex: Int): List<DemoItem> {

        val list = mutableListOf<DemoItem>()
        for (i in 1..size) {
            list.add(0, DemoItem(fromIndex -1 + i))
        }

        return list
    }
}