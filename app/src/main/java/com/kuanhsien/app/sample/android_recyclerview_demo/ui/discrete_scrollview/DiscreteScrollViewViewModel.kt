package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuanhsien.app.sample.android_recyclerview_demo.data.*

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
    // updated data from which position
    private val _updateItemPosLiveData = MutableLiveData<Int>()
    val updateItemPosLiveData: LiveData<Int>
        get() = _updateItemPosLiveData

    private val dataList = mutableListOf<DemoItem>()
    var updateList = mutableListOf<DemoItem>()  // current update list
    private var nextIndex: Int? = 0     // if no next item, nextIndex would be null


    // fun
    fun prepareData(size: Int = LOAD_DATA_SIZE, index: Int? = nextIndex) {

        if (index == null) {
            Log.d("KH_DEMO", "[DiscreteScrollViewViewModel][prepareData] nextIndex == null")
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
            dataList.addAll(0, updateList)

            _updateItemPosLiveData.postValue(0)
        }
    }
}