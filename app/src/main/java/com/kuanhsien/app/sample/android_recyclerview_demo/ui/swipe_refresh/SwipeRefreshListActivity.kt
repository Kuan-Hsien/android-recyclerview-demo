package com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem
import kotlinx.android.synthetic.main.activity_swipe_refresh_list.*


private const val REFRESH_DELAY: Long = 1500 // 1.5 seconds

class SwipeRefreshListActivity : AppCompatActivity(),
    SwipeRefreshListAdapter.OnItemClickListener {

    private lateinit var viewModel: SwipeRefreshListViewModel
    private lateinit var adapter: SwipeRefreshListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_refresh_list)

        initSwipeRefreshLayout()

        initRecyclerView()

        viewModel = ViewModelProviders.of(this).get(SwipeRefreshListViewModel::class.java)

        viewModel.apply {
            demoItemListLiveData.observe(this@SwipeRefreshListActivity, Observer {
                adapter.insertData(0, it)
            })

            // prepare data
            prepareData()
        }

    }

    /**
     * Setup swipe refresh layout
     */
    private fun initSwipeRefreshLayout() {

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method a)
        // set single color, default is black
        // root_swipe_refresh_list_container.setColorSchemeColors(Color.RED)

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method b)
        // loop change color, each for 1 second
        root_swipe_refresh_list_container.setColorSchemeResources(
            android.R.color.holo_orange_light,
            android.R.color.holo_purple
        )

        // [Swipe Refresh Layout]
        // 2. Set Refresh Listener
        root_swipe_refresh_list_container.setOnRefreshListener {

            // simulate refresh success after refreshDelay, and close animation
            Handler().postDelayed({

                root_swipe_refresh_list_container.isRefreshing = false
                viewModel.prepareData()
                Snackbar.make(root_swipe_refresh_list_container, "Scroll up to view new items", Snackbar.LENGTH_LONG)
                    .setAction("OK") {
                        recyclerview_swipe_refresh_list.smoothScrollToPosition(0)
                    }
                    .show()

            }, REFRESH_DELAY)
        }
    }

    /**
     * Setup recycler view
     */
    private fun initRecyclerView() {
        adapter = SwipeRefreshListAdapter()
        adapter.setOnItemClickListener(this)
        recyclerview_swipe_refresh_list.adapter = adapter
        recyclerview_swipe_refresh_list.layoutManager = LinearLayoutManager(this)
        (recyclerview_swipe_refresh_list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false     // close default item animation
    }

    /**
     * implement onItemClick
     */
    override fun onItemClick(item: DemoItem) {

    }
}