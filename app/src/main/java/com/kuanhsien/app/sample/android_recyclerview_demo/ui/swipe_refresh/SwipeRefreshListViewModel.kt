package com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem

class SwipeRefreshListViewModel : ViewModel() {

    private val dataList = mutableListOf<DemoItem>()
    private var updateList = mutableListOf<DemoItem>()  // current update list
    val demoItemListLiveData = MutableLiveData<List<DemoItem>>()


    fun prepareData() {

        // TODO: update data should do in Model layer (such as repository)
        updateList.clear()

        for (i in 1..5) {
            val demoItem = DemoItem(dataList.size)
            updateList.add(0, demoItem)
            dataList.add(0, demoItem)
        }

        demoItemListLiveData.postValue(updateList)
    }
}