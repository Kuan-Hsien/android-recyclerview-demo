package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.data.BundleKey
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_discrete_scrollview.*

/**
 * Demo component: DiscreteScrollView
 *
 * https://github.com/yarolegovich/DiscreteScrollView
 */
class DiscreteScrollViewActivity : AppCompatActivity(),
    DiscreteScrollViewAdapter.OnItemClickListener, DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>,
    View.OnLayoutChangeListener {

    private lateinit var viewModel: DiscreteScrollViewViewModel
    private lateinit var adapter: DiscreteScrollViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discrete_scrollview)

        initDiscreteScrollView()

        viewModel = ViewModelProviders.of(this).get(DiscreteScrollViewViewModel::class.java)

        viewModel.apply {
            updateItemPosLiveData.observe(this@DiscreteScrollViewActivity, Observer { position ->
                adapter.insertData(position, updateList)

                // after notifyItemRangeInserted, will trigger onLayoutChange
                discrete_scrollview_list.addOnLayoutChangeListener(this@DiscreteScrollViewActivity)
            })

            scrollListToPosition.observe(this@DiscreteScrollViewActivity, Observer {
                discrete_scrollview_list.smoothScrollToPosition(it)
            })

            // init Repository
            initRepository()
        }

        // Get data from bundle
        resolveIntent()

        // init click item text
        tv_discrete_scrollview_list_click_item_title.text = getString(R.string.click_item_1s, "")
    }

    override fun onStart() {
        super.onStart()

        // prepare data
        viewModel.prepareData()
    }

    override fun onDestroy() {
        super.onDestroy()

        discrete_scrollview_list.removeItemChangedListener(this)
    }

    private fun initDiscreteScrollView() {
        adapter = DiscreteScrollViewAdapter()
        discrete_scrollview_list.adapter = adapter
        adapter.setOnItemClickListener(this)
        discrete_scrollview_list.addOnItemChangedListener(this)

        discrete_scrollview_list.setOffscreenItems(2)
        discrete_scrollview_list.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build()
        )
    }

    // Get data from bundle
    private fun resolveIntent() {
        if (intent.hasExtra(BundleKey.EXTRA_KEY_DISCRETE_SCROLLVIEW_INIT_POS.key)) {
            viewModel.itemIdSelected = intent.getIntExtra(BundleKey.EXTRA_KEY_DISCRETE_SCROLLVIEW_INIT_POS.key, DEFAULT_ITEM_ID)
        }
    }


    /**
     *  implement onItemClick
     */
    override fun onItemClick(item: DemoItem) {
        // call viewModel.updateData(item), then viewModel would call repository.updateData(item) and update Livedata while enter api.onComplete

        tv_discrete_scrollview_list_click_item_title.text = getString(R.string.click_item_1s, item.id.toString())
    }

    /**
     *  Called when new item is selected. It is similar to the onScrollEnd of ScrollStateChangeListener, except that it is
     *  also called when currently selected item appears on the screen for the first time.
     *  viewHolder will be null, if data set becomes empty
     */
    override fun onCurrentItemChanged(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {

        val data = adapter.getData()[adapterPosition]

        tv_discrete_scrollview_list_current_item_title.text = getString(R.string.current_item_1s, data.id.toString())
    }

    override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        discrete_scrollview_list.removeOnLayoutChangeListener(this@DiscreteScrollViewActivity)
        viewModel.scrollList()
    }
}