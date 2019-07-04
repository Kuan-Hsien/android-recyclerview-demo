package com.kuanhsien.app.sample.android_recyclerview_demo.data


private const val DEMO_MAX_INDEX = 20
private const val DEMO_MIN_INDEX = 0

enum class LoadDataDirection {
    BEFORE,
    AFTER;
}

enum class LoadDataOrder {
    DESC,
    ASC;
}

/**
 *  Demo data from AFTER to BEFORE:
 *      100, 99, 98, ..., 3, 2, 1, 0
 * */
class DemoItemRepository {

    // return List with "size" items from "fromIndex"
    fun getItemList(size: Int, fromIndex: Int): List<DemoItem> {

        val list = mutableListOf<DemoItem>()
        for (i in 1..size) {
            list.add(0, DemoItem(fromIndex -1 + i))
        }

        return list
    }

    /**
     * [Size]
     *  Max size of return list
     *  Not garuntee size if there is no remain data to return
     *
     * [FromIndex]
     *  From which index
     *  Return list would start from this index
     *
     * [Direction]
     *  Demo data from AFTER to BEFORE:
     *  100, 99, 98, ..., 3, 2, 1, 0
     *
     *
     *  Scenario: size = 20, order = DESC
     *
     *  If:
     *      fromIndex = 100, direction = BEFORE
     *  Return:
     *      List = 100, 99, 98, ... , 82, 81
     *      NextIndex = 80
     *
     *  If:
     *     fromIndex = 15, direction = BEFORE
     *  Return:
     *      List = 15, 14, 13, ... , 1, 0
     *      NextIndex = null
     *
     *  If:
     *      fromIndex = 0, direction = AFTER
     *  Return:
     *      List = 19, 18, 17, ... , 1, 0
     *      NextIndex = 20
     *
     *  If:
     *      fromIndex = 99, direction = AFTER
     *  Return:
     *      List = 100, 99
     *      NextIndex = null
     *
     * [ORDER]
     *  Return list order, default is DESC
     *
     *  If:
     *      size = 20, fromIndex = 0, direction = AFTER, order = DESC
     *  Return:
     *      List = 19, 18, 17, ... , 1, 0
     *      NextIndex = 20
     *
     *  If:
     *      size = 20, fromIndex = 0, direction = AFTER, order = ASC
     *  Return:
     *      List = 0, 1, 2, ... , 18, 19
     *      NextIndex = 20
     *
     */
    fun getItemFrom(input: DemoQueryInput): DemoQueryOutput {

        val list = mutableListOf<DemoItem>()
        var index: Int = input.fromIndex

        for (i in 1..input.size) {

            // add cur index
            when(input.order) {
                LoadDataOrder.ASC -> list.add(DemoItem(index))
                LoadDataOrder.DESC -> list.add(0, DemoItem(index))
            }

            // next index
            index = when(input.direction) {
                LoadDataDirection.BEFORE -> index - 1
                LoadDataDirection.AFTER -> index + 1
            }

            if (index > DEMO_MAX_INDEX || index < DEMO_MIN_INDEX) {
                return DemoQueryOutput(list, null)
            }
        }

        return DemoQueryOutput(list, index)
    }
}