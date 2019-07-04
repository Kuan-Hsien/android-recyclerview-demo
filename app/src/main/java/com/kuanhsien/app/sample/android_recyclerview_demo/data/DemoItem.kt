package com.kuanhsien.app.sample.android_recyclerview_demo.data

const val LOAD_DATA_SIZE = 5

data class DemoItem(
    val id: Int
)

data class DemoQueryInput(
    val size: Int = LOAD_DATA_SIZE,
    val fromIndex: Int = 0,     // fromIndex could not be null
    val direction: LoadDataDirection = LoadDataDirection.BEFORE,
    val order: LoadDataOrder = LoadDataOrder.DESC
)

data class DemoQueryOutput(
    val list: List<DemoItem>,
    val nextIndex: Int?         // nextIndex is nullable
)