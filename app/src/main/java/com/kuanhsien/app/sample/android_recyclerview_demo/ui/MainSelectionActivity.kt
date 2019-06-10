package com.kuanhsien.app.sample.android_recyclerview_demo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoType
import kotlinx.android.synthetic.main.activity_main.*

class MainSelectionActivity : AppCompatActivity(), MainSelectionAdapter.OnItemClickListener {

    private lateinit var viewModel: MainSelectionViewModel
    private lateinit var mainSelectionAdapter: MainSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        viewModel = ViewModelProviders.of(this).get(MainSelectionViewModel::class.java)

        viewModel.apply {
            demoTypeListLiveData.observe(this@MainSelectionActivity, Observer {
                mainSelectionAdapter.setData(it)
            })

            // prepare data
            prepareData()
        }

    }

    override fun onResume() {
        super.onResume()

        recyclerview_main_selection_list.isClickable = true
    }

    /**
     * Setup recycler view
     */
    private fun initRecyclerView() {
        mainSelectionAdapter = MainSelectionAdapter()
        mainSelectionAdapter.setOnItemClickListener(this)
        recyclerview_main_selection_list.adapter = mainSelectionAdapter
        recyclerview_main_selection_list.layoutManager = LinearLayoutManager(this)
        (recyclerview_main_selection_list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false     // close default item animation
    }


    /**
     * 把被點擊到的 session info 傳入 detail 頁面
     */
    override fun onItemClick(type: DemoType) {

        if (recyclerview_main_selection_list.isClickable) {
            recyclerview_main_selection_list.isClickable = false

            val intent = when (type) {
                DemoType.SwipeRefresh ->
                    Intent(this, SwipeRefreshActivity::class.java)
                else ->
                    Intent(this, SwipeRefreshActivity::class.java)
            }

            startActivity(intent)
        }
    }
}
