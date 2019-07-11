package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuanhsien.app.sample.android_recyclerview_demo.data.*

const val DEFAULT_ITEM_ID = 0

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

    private val _scrollListToPosition = MutableLiveData<Int>()
    val scrollListToPosition: LiveData<Int>
        get() = _scrollListToPosition

    private val dataList = mutableListOf<DemoItem>()
    var updateList = mutableListOf<DemoItem>()  // current update list
    private var nextIndex: Int? = 0     // if no next item, nextIndex would be null

    var itemIdSelected = DEFAULT_ITEM_ID
    var isFirstScroll = true


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

    fun scrollList() {

        // only first time load data need to scroll to specific position
        if (isFirstScroll) {
            isFirstScroll = false

            // indexOfFirst: Returns index of the first element matching the given [predicate], or -1 if the list does not contain such element.
            val selectedIndex = dataList.indexOfFirst {
                it.id == itemIdSelected
            }

            if (selectedIndex > -1) {
                _scrollListToPosition.postValue(selectedIndex)
            }
        }
    }
}