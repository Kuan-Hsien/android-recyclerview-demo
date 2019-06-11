package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_discrete_scrollview.*

class DiscreteScrollViewActivity : AppCompatActivity(),
    DiscreteScrollViewAdapter.OnItemClickListener {

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
            })

            // init Repository
            initRepository()

            // prepare data
            prepareData()
        }
    }


    private fun initDiscreteScrollView() {
        adapter = DiscreteScrollViewAdapter()
        discrete_scrollview_list.adapter = adapter
        adapter.setOnItemClickListener(this)

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


    /**
     * implement onItemClick
     */
    override fun onItemClick(type: DemoItem) {
        // call viewModel.updateData(item), then viewModel would call repository.updateData(item) and update Livedata while enter api.onComplete
    }
}