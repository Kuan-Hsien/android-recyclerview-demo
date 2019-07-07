package com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
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

            updateItemPosLiveData.observe(this@SwipeRefreshListActivity, Observer {position ->
                adapter.insertData(position, updateList)
            })

            isRefreshDoneLiveData.observe(this@SwipeRefreshListActivity, Observer { hasRefreshData ->

                // only show hint if query is manual triggered
                if (swipe_refresh_list_root.isRefreshing) {

                    if (hasRefreshData) {
                        // if there are some items in updateList, show snackbar with scroll-up button
                        Snackbar.make(swipe_refresh_list_root, getString(R.string.hint_scroll_up_view_items), Snackbar.LENGTH_SHORT)
                            .setAction(R.string.button_ok) {
                                recyclerview_swipe_refresh_list.smoothScrollToPosition(0)
                            }
                            .show()
                    } else {
                        // if no data to update, show toast
                        Toast.makeText(this@SwipeRefreshListActivity, R.string.hint_no_more_data, Toast.LENGTH_SHORT).show()
                    }
                }

                // [Swipe Refresh Layout]
                // 3. after refresh complete, close animation
                swipe_refresh_list_root.isRefreshing = false
            })


            // init Repository
            initRepository()

            // prepare data
            getDataFromBottom()
        }
    }

    /**
     * Setup swipe refresh layout
     */
    private fun initSwipeRefreshLayout() {

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method a)
        // set single color, default is black
        // swipe_refresh_list_root.setColorSchemeColors(Color.RED)

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method b)
        // loop change color, each for 1 second
        swipe_refresh_list_root.setColorSchemeResources(
            android.R.color.holo_orange_light,
            android.R.color.holo_purple
        )

        // [Swipe Refresh Layout]
        // 2. Set Refresh Listener
        swipe_refresh_list_root.setOnRefreshListener {

            // simulate refresh success after refreshDelay
            Handler().postDelayed({

                viewModel.getDataFromBottom()

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
     * Implement onItemClick
     */
    override fun onItemClick(item: DemoItem) {
        // callback onItemClick
    }
}