package com.kuanhsien.app.sample.android_recyclerview_demo.data

import androidx.appcompat.app.AppCompatActivity
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview.DiscreteScrollViewActivity
import com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh.SwipeRefreshActivity
import com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh.SwipeRefreshListActivity

enum class DemoType(val titleRes: Int, val activityClass: Class<out AppCompatActivity>) {
    SwipeRefresh(R.string.title_swipe_refresh_activity, SwipeRefreshActivity::class.java),
    SwipeRefreshList(R.string.title_swipe_refresh_list_activity, SwipeRefreshListActivity::class.java),
    DiscreteScrollView(R.string.title_discrete_scrollview_activity, DiscreteScrollViewActivity::class.java),
    ;

    companion object {
        fun getDemoTypeList(): List<DemoType> = enumValues<DemoType>().toMutableList()
    }
}