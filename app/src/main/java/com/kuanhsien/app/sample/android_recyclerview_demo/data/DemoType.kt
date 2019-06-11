package com.kuanhsien.app.sample.android_recyclerview_demo.data

import com.kuanhsien.app.sample.android_recyclerview_demo.R

enum class DemoType(val titleRes: Int) {
    SwipeRefresh(R.string.title_swipe_refresh_activity),
    SwipeRefreshList(R.string.title_swipe_refresh_list_activity),
    ;

    companion object {
        fun getDemoTypeList(): List<DemoType> = enumValues<DemoType>().toMutableList()
    }
}