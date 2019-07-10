package com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuanhsien.app.sample.android_recyclerview_demo.data.*

class SwipeRefreshListViewModel : ViewModel() {

    // Repository
    private lateinit var repository: DemoItemRepository

    fun initRepository() {
        this.repository = DemoItemRepository()
    }

    // For testing
    fun initRepository(repository: DemoItemRepository) {
        this.repository = repository
    }


    // LiveData
    // call ui to hide swipe-refresh animation
    private val _isRefreshDoneLiveData = MutableLiveData<Boolean>()
    val isRefreshDoneLiveData : LiveData<Boolean>
        get() = _isRefreshDoneLiveData

    // updated data from which position
    private val _updateItemPosLiveData = MutableLiveData<Int>()
    val updateItemPosLiveData: LiveData<Int>
        get() = _updateItemPosLiveData


    // Data
    private val dataList = mutableListOf<DemoItem>()
    var updateList = mutableListOf<DemoItem>()  // current update list
    private var nextIndex: Int? = 0     // if no next item, nextIndex would be null


    // Fun
    fun getDataFromBottom(size: Int = LOAD_DATA_SIZE, index: Int? = nextIndex) {

        updateList.clear()

        if (index == null) {
            _isRefreshDoneLiveData.postValue(false)
            return

        } else {
            val response = repository.getItemFrom(
                DemoQueryInput(
                    size = size,
                    fromIndex = index,
                    direction = LoadDataDirection.AFTER,
                    order = LoadDataOrder.DESC
                )
            )

            updateList = response.list.toMutableList()
            nextIndex = response.nextIndex

            dataList.addAll(0, updateList)

            _updateItemPosLiveData.postValue(0)
            _isRefreshDoneLiveData.postValue(true)
        }
    }
}