package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItemRepository

class DiscreteScrollViewViewModel : ViewModel() {

    // repository
    private lateinit var repository: DemoItemRepository

    fun initRepository() {
        this.repository = DemoItemRepository()
    }

    // For testing
    fun initRepository(repository: DemoItemRepository) {
        this.repository = repository
    }


    // data
    private val dataList = mutableListOf<DemoItem>()

    // updated data from which position
    private val _updateItemPosLiveData = MutableLiveData<Int>()
    val updateItemPosLiveData: LiveData<Int>
        get() = _updateItemPosLiveData

    var updateList = mutableListOf<DemoItem>()  // current update list


    // fun
    fun prepareData(size: Int = 10, fromIndex: Int = dataList.size) {

        updateList = repository.getItemList(size, fromIndex).toMutableList()
        dataList.addAll(0, updateList)

        _updateItemPosLiveData.postValue(0)
    }
}